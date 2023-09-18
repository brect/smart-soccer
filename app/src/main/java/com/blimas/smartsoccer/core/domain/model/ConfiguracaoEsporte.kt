package com.blimas.smartsoccer.core.domain.model

data class ConfiguracaoEsporte(
    val tipoEsporte: TipoEsporte,
    val quantidadeMinimaPorTime: Int
) {
    // Método para calcular a quantidade mínima de jogadores com base na configuração do esporte
    fun calcularQuantidadeMinimaJogadores(quantidadeTimes: Int): Int {
        return quantidadeMinimaPorTime * quantidadeTimes
    }
}
