package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Entity

@Entity(tableName = "jogador_posicao_ref", primaryKeys = ["jogadorId", "posicaoId"])
data class JogadorPosicaoCrossRef(
    val jogadorId: Int,
    val posicaoId: Int
)