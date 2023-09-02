package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import com.padawanbr.smartsoccer.presentation.common.ListItem

data class JogadoresItem (
    val id: String,
    val jogadores: List<JogadorItem>,

    override val key: String = id
)  : ListItem {
    // Propriedade calculada para armazenar a quantidade de jogadores disponíveis (não no DM)
    val jogadoresDisponiveis: Int
        get() = jogadores?.count { it.estaNoDepartamentoMedico?.not() ?: true } ?: 0

    // Propriedade calculada para armazenar a quantidade de jogadores no departamento médico
    val jogadoresNoDM: Int
        get() = jogadores?.count { it.estaNoDepartamentoMedico ?: false } ?: 0

    // Propriedade calculada para armazenar a média de habilidades dos jogadores
    val mediaJogadores: Float
        get() {
            val totalHabilidades = jogadores.sumByDouble { jogador ->
                jogador.habilidades?.values?.average() ?: 0.0
            }
            return if (jogadores.isNotEmpty()) {
                (totalHabilidades / jogadores.size).toFloat()
            } else {
                0.0F
            }
        }
}