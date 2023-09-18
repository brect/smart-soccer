package com.blimas.smartsoccer.core.domain.model

data class Grupo(
    val id: String,
    val nome: String,
    val endereco: String,
    val configuracaoEsporte: ConfiguracaoEsporte,
    val diaDoJogo: String,
    val horarioInicio: String,
    val quantidadeTimes: Int,
    val rangeIdade: RangeIdade
){
    // Propriedade calculada para armazenar a quantidade mínima de jogadores
    val quantidadeMinimaJogadores: Int
        get() = configuracaoEsporte.calcularQuantidadeMinimaJogadores(quantidadeTimes)
}