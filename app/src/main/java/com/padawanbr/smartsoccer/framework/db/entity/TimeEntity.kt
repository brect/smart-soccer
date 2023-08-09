package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val mediaHabilidades: Float,
    val torneioId: String
)