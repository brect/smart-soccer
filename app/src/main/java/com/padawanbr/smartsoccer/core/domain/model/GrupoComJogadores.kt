package com.padawanbr.smartsoccer.core.domain.model

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
}
