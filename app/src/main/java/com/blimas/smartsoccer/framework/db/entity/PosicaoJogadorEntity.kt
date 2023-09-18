package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posicao_jogador")
class PosicaoJogadorEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String
)