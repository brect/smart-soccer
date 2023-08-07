package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID

fun sortearTimes(
    jogadores: MutableList<Jogador>,
    numeroTimes: Int,
    considerarPosicoes: Boolean = true,
    considerarOveralls: Boolean = true,
    considerarDepartamentoMedico: Boolean = false
): List<Time> {
    val jogadoresDisponiveis = jogadores.filter { jogador ->
        if (considerarDepartamentoMedico) {
            !jogador.estaNoDepartamentoMedico
        } else {
            true
        }
    }.sortedByDescending { jogador ->
        if (considerarOveralls) {
            jogador.calcularMediaHabilidades()
        } else {
            0.0f
        }
    }

    val times = List(numeroTimes) { Time(UUID.randomUUID().toString(), "Time ${'A' + it}", mutableListOf()) }

    // Cria um mapa para armazenar os jogadores agrupados por posição
    val jogadoresPorPosicao: MutableMap<String, MutableList<Jogador>> = mutableMapOf()

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

    while (!isMediaHabilidadesEquilibrada(times)) {
        realocarJogadoresEntreTimes(times)
    }

    return times
}

fun isMediaHabilidadesEquilibrada(times: List<Time>): Boolean {
    val mediaGeral = times.flatMap { it.jogadores }.map { it.calcularMediaHabilidades() }.average()

    return times.all {
        val mediaTime = it.jogadores.map { jogador -> jogador.calcularMediaHabilidades() }.average()
        Math.abs(mediaTime - mediaGeral) <= 0.1 * mediaGeral
    }
}

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
