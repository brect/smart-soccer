package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID

// Função para sortear times a partir de uma lista de jogadores
fun sortearTimes(
    jogadores: MutableList<Jogador>,  // Lista de jogadores
    numeroTimes: Int,                 // Número de times a serem criados
    considerarPosicoes: Boolean = true,           // Se deve considerar a posição dos jogadores
    considerarOveralls: Boolean = true,           // Se deve considerar a habilidade geral dos jogadores
    considerarDepartamentoMedico: Boolean = true  // Se deve considerar jogadores no departamento médico
): List<Time> {

    // Filtra e ordena os jogadores disponíveis
    val jogadoresDisponiveis = jogadores.filter { jogador ->
        considerarDepartamentoMedico || !jogador.estaNoDepartamentoMedico
    }.sortedByDescending { jogador ->
        if (considerarOveralls) {
            jogador.calcularMediaHabilidades()
        } else {
            0.0f
        }
    }

    // Inicializa a lista de times
    val times = List(numeroTimes) { Time(UUID.randomUUID().toString(), "Time ${'A' + it}", mutableListOf()) }

    // Mapa para agrupar jogadores por posição, se necessário
    val jogadoresPorPosicao: MutableMap<String, MutableList<Jogador>> = mutableMapOf()

    // Agrupa jogadores por posição se a flag considerarPosicoes for verdadeira
    if (considerarPosicoes) {
        jogadoresDisponiveis.forEach { jogador ->
            val posicaoChave = jogador.posicao?.let { PosicaoJogador.fromAbreviacaoString(it.abreviacao)?.abreviacao } ?: ""
            if (jogadoresPorPosicao.containsKey(posicaoChave)) {
                jogadoresPorPosicao[posicaoChave]?.add(jogador)
            } else {
                jogadoresPorPosicao[posicaoChave] = mutableListOf(jogador)
            }
        }
    }

    // Distribui os jogadores pelos times
    for ((index, jogador) in jogadoresDisponiveis.withIndex()) {
        val timeAtual = times[index % numeroTimes]
        if (considerarPosicoes && jogador.posicao != null) {
            val posicaoChave = PosicaoJogador.fromAbreviacaoString(jogador.posicao.abreviacao)?.abreviacao ?: ""
            if (jogadoresPorPosicao.containsKey(posicaoChave)) {
                val jogadoresPosicao = jogadoresPorPosicao[posicaoChave]!!
                if (jogadoresPosicao.isNotEmpty()) {
                    timeAtual.jogadores.add(jogadoresPosicao.removeAt(0))
                } else {
                    timeAtual.jogadores.add(jogador)
                }
            } else {
                timeAtual.jogadores.add(jogador)
            }
        } else {
            timeAtual.jogadores.add(jogador)
        }
    }

    // Equilibra os times com base na média de habilidades
    while (!isMediaHabilidadesEquilibrada(times)) {
        realocarJogadoresEntreTimes(times)
    }

    // Retorna a lista de times
    return times
}

// Verifica se a média de habilidades dos jogadores nos times é equilibrada
fun isMediaHabilidadesEquilibrada(times: List<Time>): Boolean {
    val mediaGeral = times.flatMap { it.jogadores }.map { it.calcularMediaHabilidades() }.average()
    return times.all {
        val mediaTime = it.jogadores.map { jogador -> jogador.calcularMediaHabilidades() }.average()
        Math.abs(mediaTime - mediaGeral) <= 0.1 * mediaGeral
    }
}

// Realoca jogadores entre os times para equilibrar as habilidades
fun realocarJogadoresEntreTimes(times: List<Time>) {
    val mediaGeral = times.flatMap { it.jogadores }.map { it.calcularMediaHabilidades() }.average()
    for (time in times) {
        val mediaTime = time.jogadores.map { it.calcularMediaHabilidades() }.average()
        val diferencaMedia = mediaTime - mediaGeral
        val jogadoresExcedentes = time.jogadores.filter { it.calcularMediaHabilidades() - diferencaMedia > mediaGeral }
        if (jogadoresExcedentes.isNotEmpty()) {
            val jogador = jogadoresExcedentes.first()
            time.jogadores.remove(jogador)
            val timeDestino = times.minByOrNull { Math.abs(it.jogadores.map { it.calcularMediaHabilidades() }.average() - mediaGeral) }
            timeDestino?.jogadores?.add(jogador)
        }
    }
}
