package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val mediaHabilidades: Float, // Média das habilidades dos jogadores do time
    val torneioId: String // ID do Torneio ao qual o Time está associado
)