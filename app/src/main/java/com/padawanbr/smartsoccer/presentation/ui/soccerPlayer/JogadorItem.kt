package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import com.padawanbr.smartsoccer.presentation.common.ListItem

data class JogadorItem(
    val id: String,
    val nome: String?,
    val idade: Int?,
    val posicao: String?,
    val habilidades: Map<String, Float>?,
    var estaNoDepartamentoMedico: Boolean?,

    override val key: String = id
) : ListItem {
    // Função de extensão para calcular a média das habilidades
    fun calcularMediaHabilidades(): Float {
        // Verifica se o mapa de habilidades não é nulo e não está vazio
        habilidades?.let {
            if (it.isNotEmpty()) {
                // Calcula a média das habilidades
                val somaHabilidades = it.values.sum()
                return somaHabilidades / it.size
            }
        }
        // Retorna 0.0 se não houver habilidades ou o mapa for nulo/vazio
        return 0.0f
    }
}
