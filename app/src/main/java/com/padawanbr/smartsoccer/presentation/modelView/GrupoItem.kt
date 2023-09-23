package com.padawanbr.smartsoccer.presentation.modelView

import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.presentation.common.adapter.ListItem
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

