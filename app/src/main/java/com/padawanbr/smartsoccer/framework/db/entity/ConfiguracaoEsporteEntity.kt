package com.padawanbr.smartsoccer.framework.db.entity

import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte

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