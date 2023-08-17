package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class JogadorComTimes(
    @Embedded val jogador: JogadorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "jogadorId",
        associateBy = Junction(TimeJogadorCrossRef::class)
    )
    val times: List<TimeEntity>
)