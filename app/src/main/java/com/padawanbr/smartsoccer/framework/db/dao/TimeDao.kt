package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity


@Dao
interface TimeDao {
    @Query("SELECT * FROM time")
    fun getAll(): List<TimeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(time: TimeEntity)

    @Update
    fun update(time: TimeEntity)

    @Delete
    fun delete(time: TimeEntity)
}