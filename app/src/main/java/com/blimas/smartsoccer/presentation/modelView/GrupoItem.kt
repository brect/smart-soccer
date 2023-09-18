package com.blimas.smartsoccer.presentation.modelView

import com.blimas.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.blimas.smartsoccer.core.domain.model.RangeIdade
import com.blimas.smartsoccer.core.domain.model.TipoEsporte
import com.blimas.smartsoccer.presentation.common.adapter.ListItem
import java.util.UUID

data class GrupoItem(
    val id: String,
    val nome: String,
    val endereco: String,
    val configuracaoEsporte: ConfiguracaoEsporte,
    val diaDoJogo: String?,
    val horarioInicio: String?,
    val quantidadeTimes: Int?,
    val rangeIdade: RangeIdade?,

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
        UUID.randomUUID().toString()
    )
}

