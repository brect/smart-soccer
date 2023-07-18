package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity

@Dao
interface GrupoDao {
    @Query("SELECT * FROM grupo")
    fun getAll(): List<GrupoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grupo: GrupoEntity)

    @Update
    fun update(grupo: GrupoEntity)

    @Delete
    fun delete(grupo: GrupoEntity)
}