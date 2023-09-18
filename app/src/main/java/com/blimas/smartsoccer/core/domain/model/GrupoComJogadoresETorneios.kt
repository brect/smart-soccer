package com.blimas.smartsoccer.core.domain.model

data class GrupoComJogadoresETorneios(
    val grupo: Grupo,
    val jogadores: List<Jogador>,
    val torneios: List<Torneio>
) {
    // Propriedade calculada para armazenar a quantidade de jogadores disponíveis (não no DM)
    val jogadoresDisponiveis: Int
        get() = jogadores?.count { it.estaNoDepartamentoMedico?.not() ?: true } ?: 0

    // Propriedade calculada para armazenar a quantidade de jogadores no departamento médico
    val jogadoresNoDM: Int
        get() = jogadores?.count { it.estaNoDepartamentoMedico ?: false } ?: 0

    // Propriedade calculada para armazenar a média de habilidades dos jogadores

    val mediaHabilidades: Float
        get() = jogadores.map { it.calcularMediaHabilidades() }.average().toFloat()

}



