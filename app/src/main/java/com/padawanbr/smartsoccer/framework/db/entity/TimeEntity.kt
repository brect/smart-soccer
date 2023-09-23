package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.Time
import java.util.UUID

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey
    val timeId: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val mediaHabilidades: Float,
    @ColumnInfo(name = "torneioId")
    val torneioId: String,
)

fun TimeEntity.toTimeModel() = Time(
    id = timeId,
    nome = nome,
    jogadores = mutableListOf(),
)

fun List<TimeEntity>.toListTimeModel(): List<Time> {
    return map { it.toTimeModel() }
}

fun TimeComJogadoresEntity.toTimeModel(): Time {
    return Time(
        id = this.time.timeId,
        nome = this.time.nome,
        jogadores = this.jogadores
            .sortedBy {
                it.posicao?.id
            }.map {
                it.toJogadorModel()
            }
            .toMutableList()
    )
}