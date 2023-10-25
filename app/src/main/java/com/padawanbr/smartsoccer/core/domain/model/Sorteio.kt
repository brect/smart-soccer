package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID
import kotlin.math.abs

fun sortearTimes(
    jogadores: MutableList<Jogador>,
    numeroTimes: Int,
    considerarPosicoes: Boolean = true,
    considerarOveralls: Boolean = true,
    considerarDepartamentoMedico: Boolean = true,
    desvioMediaPermitido: Double = 0.1
): List<Time> {

    val jogadoresDisponiveis = ordenarEfiltrarJogadores(jogadores, considerarOveralls, considerarDepartamentoMedico)

    val times = List(numeroTimes) { Time(UUID.randomUUID().toString(), "Time ${'A' + it}", mutableListOf()) }

    // Agrupa jogadores por posição se necessário
    val jogadoresPorPosicao = if (considerarPosicoes) agruparPorPosicao(jogadoresDisponiveis) else mutableMapOf()

    // Atribui jogadores aos times
    atribuirJogadoresATimes(jogadoresDisponiveis, times, jogadoresPorPosicao, considerarPosicoes)

    // Balanceia os times
    while (considerarOveralls && !isMediaHabilidadesEquilibrada(times, desvioMediaPermitido)) {
        realocarJogadoresEntreTimes(times)
    }

    return times
}

// Função auxiliar para ordenar e filtrar jogadores com base nas condições fornecidas
fun ordenarEfiltrarJogadores(
    jogadores: MutableList<Jogador>,
    considerarOveralls: Boolean,
    considerarDepartamentoMedico: Boolean
): List<Jogador> {
    // Filtra jogadores com base no estado do departamento médico e ordena por habilidades, se necessário
    return jogadores.filter { considerarDepartamentoMedico || !it.estaNoDepartamentoMedico }
        .sortedByDescending { if (considerarOveralls) it.calcularMediaHabilidades() else 0.0f }
}

// Agrupa jogadores por suas posições
fun agruparPorPosicao(jogadores: List<Jogador>): MutableMap<String, MutableList<Jogador>> {
    val agrupados = jogadores.groupBy {
        it.posicao?.let { PosicaoJogador.fromAbreviacaoString(it.abreviacao)?.abreviacao } ?: ""
    }
    // Converte List para MutableList
    return agrupados.mapValues { it.value.toMutableList() }.toMutableMap()
}

// Atribui jogadores aos times
fun atribuirJogadoresATimes(
    jogadores: List<Jogador>,
    times: List<Time>,
    jogadoresPorPosicao: MutableMap<String, MutableList<Jogador>>,
    considerarPosicoes: Boolean
) {
    for ((index, jogador) in jogadores.withIndex()) {
        val timeAtual = times[index % times.size]

        if (considerarPosicoes && jogador.posicao != null) {
            val posicaoChave = PosicaoJogador.fromAbreviacaoString(jogador.posicao.abreviacao)?.abreviacao ?: ""
            val jogadoresPosicao = jogadoresPorPosicao[posicaoChave] ?: mutableListOf()

            timeAtual.jogadores.add(jogadoresPosicao.removeAt(0))
        } else {
            timeAtual.jogadores.add(jogador)
        }
    }
}


// Adicione o parâmetro 'toleranciaMedia' com um valor padrão (por exemplo, 0.1)
fun isMediaHabilidadesEquilibrada(times: List<Time>, toleranciaMedia: Double = 0.1): Boolean {
    val mediaGeral = times.flatMap { it.jogadores }.map { it.calcularMediaHabilidades() }.average()
    return times.all {
        val mediaTime = it.jogadores.map { jogador -> jogador.calcularMediaHabilidades() }.average()
        abs(mediaTime - mediaGeral) <= toleranciaMedia * mediaGeral  // Use 'toleranciaMedia' aqui
    }
}

// Realoca jogadores entre times para equilibrar a média das habilidades
fun realocarJogadoresEntreTimes(times: List<Time>) {
    val mediaGeral = times.flatMap { it.jogadores }.map { it.calcularMediaHabilidades() }.average()

    for (time in times) {
        val mediaTime = time.jogadores.map { it.calcularMediaHabilidades() }.average()
        val diferencaMedia = mediaTime - mediaGeral
        val jogadoresExcedentes =
            time.jogadores.filter { it.calcularMediaHabilidades() - diferencaMedia > mediaGeral }

        if (jogadoresExcedentes.isNotEmpty()) {
            val jogador = jogadoresExcedentes.first()
            time.jogadores.remove(jogador)

            val timeDestino = times.minByOrNull {
                abs(it.jogadores.map { it.calcularMediaHabilidades() }.average() - mediaGeral)
            }
            timeDestino?.jogadores?.add(jogador)
        }
    }
}
