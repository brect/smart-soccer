package com.blimas.smartsoccer.core.domain.model

data class GrupoComJogadores(
    val grupo: Grupo,
    val jogadores: List<Jogador>
) {
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
