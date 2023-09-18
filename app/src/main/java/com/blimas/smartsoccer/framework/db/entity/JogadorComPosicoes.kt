package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class JogadorComPosicoes(
    @Embedded val jogador: JogadorEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(JogadorPosicaoCrossRef::class)
    )
    val posicoes: List<PosicaoJogadorEntity>
)