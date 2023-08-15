package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadores
import com.padawanbr.smartsoccer.core.domain.model.Time
import java.util.UUID

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val mediaHabilidades: Float,
    val torneioId: String
)

fun TimeEntity.toTimeModel() = Time(
    id = id,
    nome = nome,
    jogadores = mutableListOf(),
)

fun List<TimeEntity>.toListTimeModel(): List<Time> {
    return map { it.toTimeModel() }
}