package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TimeComJogadoresEntity(
    @Embedded val time: TimeEntity,
    @Relation(
        parentColumn = "timeId",
        entityColumn = "jogadorId",
        associateBy = Junction(TimeJogadorCrossRef::class)
    )
    val jogadores: List<JogadorEntity>
)