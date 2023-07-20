package com.padawanbr.smartsoccer.presentation

import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.Sorteio
import com.padawanbr.smartsoccer.presentation.common.ListItem

data class GrupoItem(
    val id: Int,
    val nome: String,
    val quantidadeMinimaJogadores: Int,
    val quantidadeMinimaJogadoresPorTime: Int,
    val quantidadeTimes: Int,
    val jogadores: MutableList<Jogador>?,
    val sorteios: MutableList<Sorteio>?,

    override val key: Long = id.toLong()
) : ListItem
