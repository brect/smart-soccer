package com.blimas.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.blimas.smartsoccer.framework.db.entity.SorteioEntity

@Dao
interface SorteioDao {
    @Query("SELECT * FROM sorteio")
    fun getAll(): List<SorteioEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sorteio: SorteioEntity)

    @Update
    fun update(sorteio: SorteioEntity)

    @Delete
    fun delete(sorteio: SorteioEntity)
}