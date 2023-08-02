package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID

data class Grupo(
    val id: String,
    val nome: String,
    val quantidadeTimes: Int,
    val configuracaoEsporte: ConfiguracaoEsporte,
){
    // Propriedade calculada para armazenar a quantidade mínima de jogadores
    val quantidadeMinimaJogadores: Int
        get() = configuracaoEsporte.calcularQuantidadeMinimaJogadores(quantidadeTimes)
}