package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TorneioComTimesAndJogadores(
    @Embedded val torneio: TorneioEntity,
    @Relation(
        entity = TimeEntity::class,
        parentColumn = "torneioId",
        entityColumn = "torneioId",
    )
    val times: List<TimeComJogadoresEntity>
)