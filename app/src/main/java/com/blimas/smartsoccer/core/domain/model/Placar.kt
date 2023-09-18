package com.blimas.smartsoccer.core.domain.model

data class Placar(
    val jogo: Jogo,
    val time: Time,
    val gols: Int
)