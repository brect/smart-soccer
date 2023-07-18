package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grupo")
data class GrupoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val quantidadeMinimaJogadores: Int,
    val quantidadeMinimaJogadoresPorTime: Int,
    val quantidadeTimes: Int
)