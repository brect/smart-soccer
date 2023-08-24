package com.padawanbr.smartsoccer.presentation.ui.groups

import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.presentation.common.ListItem
import com.padawanbr.smartsoccer.presentation.ui.competition.CompetitionItem
import java.util.UUID

data class GrupoComJogadoresItem(
    val id: String,
    val nome: String,
    val endereco: String,
    val configuracaoEsporte: ConfiguracaoEsporte,
    val diaDoJogo: String?,
    val horarioInicio: String?,
    val quantidadeTimes: Int?,
    val rangeIdade: RangeIdade?,
    val jogadores: MutableList<Jogador>?,
    val jogadoresDisponiveis: Int?,
    val jogadoresNoDM: Int?,
    val mediaJogadores: Float?,
    val torneios: List<CompetitionItem>?,

    override val key: String = id
) : ListItem {
    constructor() : this(
        UUID.randomUUID().toString(),
        "",
        "",
        ConfiguracaoEsporte(
            TipoEsporte.FUTEBOL_CAMPO,
            TipoEsporte.FUTEBOL_CAMPO.quantidadeMinimaPorTime
        ),
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}

