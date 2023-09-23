package com.padawanbr.smartsoccer.core.domain.model

data class Jogo(
    val id: String,
    val timeA: Time,
    val timeB: Time,
    val placar: Map<Time, Int>,
    val goleadores: MutableList<Jogador>
)