package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class JogoComPlacares(
    @Embedded val jogo: JogoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "jogoId"
    )
    val placares: List<PlacarEntity>
)