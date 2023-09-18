package com.blimas.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.blimas.smartsoccer.framework.db.entity.TimeComJogadoresEntity
import com.blimas.smartsoccer.framework.db.entity.TimeEntity

@Dao
interface TimeDao {

    @Query("SELECT * FROM time")
    fun getAll(): List<TimeComJogadoresEntity>

    @Transaction
    @Query("SELECT * FROM time WHERE timeId = :timeId")
    suspend fun getTimeWithJogadoresById(timeId: String): TimeComJogadoresEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(time: TimeEntity)

    @Update
    suspend fun update(time: TimeEntity)

    @Delete
    suspend fun delete(time: TimeEntity)
}
