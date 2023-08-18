package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.padawanbr.smartsoccer.core.domain.model.Time

data class TorneioComTimesAndJogadores(
    @Embedded val torneio: TorneioEntity,
    @Relation(
        entity = TimeEntity::class,
        parentColumn = "torneioId",
        entityColumn = "torneioId",
    )
    val times: List<TimeComJogadoresEntity>
)