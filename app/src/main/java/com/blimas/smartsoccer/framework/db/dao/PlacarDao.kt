package com.blimas.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.blimas.smartsoccer.framework.db.entity.PlacarEntity


@Dao
interface PlacarDao {
    @Query("SELECT * FROM placar")
    fun getAll(): List<PlacarEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(placar: PlacarEntity)

    @Update
    fun update(placar: PlacarEntity)

    @Delete
    fun delete(placar: PlacarEntity)
}