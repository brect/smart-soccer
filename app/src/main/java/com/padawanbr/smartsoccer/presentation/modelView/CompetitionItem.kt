package com.padawanbr.smartsoccer.presentation.modelView

import com.padawanbr.smartsoccer.core.domain.model.CriterioDesempateItem
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.presentation.common.adapter.ListItem

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

