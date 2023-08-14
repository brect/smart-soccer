package com.padawanbr.smartsoccer.presentation.ui.competition

import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.CriterioDesempateItem
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.presentation.common.ListItem
import java.util.UUID

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

