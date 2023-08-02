package com.padawanbr.smartsoccer.presentation

import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.Sorteio
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.presentation.common.ListItem
import java.util.UUID

data class GrupoItem(
    val id: String,
    val nome: String,
    val quantidadeTimes: Int,
    val configuracaoEsporte: ConfiguracaoEsporte,
    val jogadores: MutableList<Jogador>?,
    val jogadoresDisponiveis: Int?,
    val jogadoresNoDM: Int?,

    override val key: String = id
) : ListItem {
    constructor() : this(
        UUID.randomUUID().toString(),
        "",
        0,
        ConfiguracaoEsporte(TipoEsporte.UNDEFINED, TipoEsporte.UNDEFINED.quantidadeMinimaPorTime),
        null,
        null,
        null
    )
}
