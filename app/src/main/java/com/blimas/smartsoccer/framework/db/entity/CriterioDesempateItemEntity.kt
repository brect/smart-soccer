package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "criterio_desempate_item")
data class CriterioDesempateItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val criterio: String, // Converter o enum CriterioDesempate para String
    val prioridade: Int,
    val torneioId: String
)