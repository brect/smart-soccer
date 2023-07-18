package com.padawanbr.smartsoccer.core.domain.model

data class Grupo(
    val nome: String,
    val quantidadeMinimaJogadores: Int,
    val quantidadeMinimaJogadoresPorTime: Int,
    val quantidadeTimes: Int,
    val jogadores: MutableList<Jogador>,
    val sorteios: MutableList<Sorteio>
)