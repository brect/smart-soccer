package com.padawanbr.smartsoccer.core.domain.model

data class Grupo(
    val id: Int,
    val nome: String,
    val quantidadeTimes: Int,
    val configuracaoEsporte: ConfiguracaoEsporte,
    val jogadores: MutableList<Jogador>?,
    val sorteios: MutableList<Sorteio>?,
){
    // Propriedade calculada para armazenar a quantidade mínima de jogadores
    val quantidadeMinimaJogadores: Int
        get() = configuracaoEsporte.calcularQuantidadeMinimaJogadores(quantidadeTimes)
}