package com.blimas.smartsoccer.framework.db.entity

import com.blimas.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.blimas.smartsoccer.core.domain.model.TipoEsporte

data class ConfiguracaoEsporteEntity(
    val tipoEsporte: String,
    val quantidadeMinimaPorTime: Int
)


fun ConfiguracaoEsporteEntity.toConfiguracaoEsporteModel(): ConfiguracaoEsporte {
    return ConfiguracaoEsporte(
        tipoEsporte = TipoEsporte.valueOf(tipoEsporte),
        quantidadeMinimaPorTime = quantidadeMinimaPorTime
    )
}