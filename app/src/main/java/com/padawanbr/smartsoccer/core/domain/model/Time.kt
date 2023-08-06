package com.padawanbr.smartsoccer.core.domain.model

data class Time(
    val id: String,
    val nome: String,
    val jogadores: MutableList<Jogador>,
) {
    val mediaHabilidades: Float
        get() = jogadores.map { it.calcularMediaHabilidades() }.average().toFloat()
}