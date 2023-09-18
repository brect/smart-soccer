package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sorteio")
data class SorteioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val totalDeJogosNaSerie: Int
)