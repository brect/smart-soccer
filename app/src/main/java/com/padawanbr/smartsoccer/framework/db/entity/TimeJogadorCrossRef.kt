package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity

@Entity(tableName = "time_jogador_cross_ref", primaryKeys = ["timeId", "jogadorId"])
data class TimeJogadorCrossRef(
    val timeId: String,
    val jogadorId: String
)