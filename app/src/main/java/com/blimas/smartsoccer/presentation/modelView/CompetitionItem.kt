package com.blimas.smartsoccer.presentation.modelView

import com.blimas.smartsoccer.core.domain.model.CriterioDesempateItem
import com.blimas.smartsoccer.core.domain.model.Partida
import com.blimas.smartsoccer.core.domain.model.Time
import com.blimas.smartsoccer.core.domain.model.TipoTorneio
import com.blimas.smartsoccer.presentation.common.adapter.ListItem

data class CompetitionItem(
    val id: String,
    val nome: String,
    val tipoTorneio: TipoTorneio?,
    val criteriosDesempate: List<CriterioDesempateItem>,
    val times: List<Time>,
    val partidas: List<Partida>,
    val grupoId: String,

    override val key: String = id
) : ListItem

