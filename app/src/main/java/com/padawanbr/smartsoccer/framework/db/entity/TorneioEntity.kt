package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import java.util.UUID

@Entity(tableName = "torneio")
data class TorneioEntity(
    @PrimaryKey
    val id: String,
    val nome: String,
    val tipoTorneio: String, // Converter o enum TipoTorneio para String
    val grupoId: String
)


