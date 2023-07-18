package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "time")
data class TimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)