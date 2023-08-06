package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID


//data class Time(val nome: String, val jogadores: MutableList<Jogador>)

fun sortearTimes(
    jogadores: MutableList<Jogador>,
    numeroTimes: Int
): List<Time> {
    val jogadoresDisponiveis = jogadores.filter { !it.estaNoDepartamentoMedico }
        .sortedByDescending { it.calcularMediaHabilidades() }

    val times = List(numeroTimes) { Time(UUID.randomUUID().toString(), "Time ${'A' + it}", mutableListOf()) }

    for ((index, jogador) in jogadoresDisponiveis.withIndex()) {
        val timeAtual = times[index % numeroTimes]
        timeAtual.jogadores.add(jogador)
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
