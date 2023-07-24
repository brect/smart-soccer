package com.padawanbr.smartsoccer.presentation

import com.padawanbr.smartsoccer.presentation.common.ListItem

data class JogadorItem(
    val id: Int,
    val nome: String,
    val idade: Int,
    val posicoes: String,
    val classificacoes: String,
    var estaNoDepartamentoMedico: Boolean,

    override val key: Long = id.toLong()
) : ListItem
