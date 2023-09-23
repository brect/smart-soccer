package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "partida")
data class PartidaEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val timeCasaId: String, // ID do Time que joga em casa
    val timeVisitanteId: String, // ID do Time visitante
    val placarCasa: Int?,
    val placarVisitante: Int?,
    val torneioId: String // ID do Torneio ao qual a Partida está associada
)
