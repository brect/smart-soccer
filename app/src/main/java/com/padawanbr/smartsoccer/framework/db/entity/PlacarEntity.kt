package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "placar")
data class PlacarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val jogoId: Int,
    val timeId: Int,
    val gols: Int
)