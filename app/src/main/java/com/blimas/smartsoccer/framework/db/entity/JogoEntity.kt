package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jogo")
data class JogoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val timeAId: Int,
    val timeBId: Int
)