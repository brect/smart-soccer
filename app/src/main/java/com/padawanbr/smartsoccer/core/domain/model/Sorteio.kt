package com.padawanbr.smartsoccer.core.domain.model

data class Sorteio(
    val id: String,
    val totalDeJogosNaSerie: Int,
    val jogos: MutableList<Jogo>,
    val times: MutableList<Time>
)