package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.CriterioDesempateItem
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import java.util.UUID

@Entity(tableName = "torneio")
data class TorneioEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val tipoTorneio: TipoTorneio,
    val criteriosDesempate: List<CriterioDesempateItem>,
    val grupoId: String
)




