package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TorneioComTimesAndJogadores(
    @Embedded val torneio: TorneioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "torneioId"
    )
    val times: List<TimeEntity>
)