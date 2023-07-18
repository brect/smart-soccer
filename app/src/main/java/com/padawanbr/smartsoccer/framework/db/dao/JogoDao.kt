package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import com.padawanbr.smartsoccer.framework.db.entity.JogoComPlacares
import com.padawanbr.smartsoccer.framework.db.entity.JogoEntity

@Dao
interface JogoDao {
    @Query("SELECT * FROM jogo")
    fun getAll(): List<JogoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jogo: JogoEntity)

    @Update
    fun update(jogo: JogoEntity)

    @Delete
    fun delete(jogo: JogoEntity)

    @Query("SELECT * FROM jogo WHERE id = :jogoId")
    fun getJogoComPlacares(jogoId: Int): JogoComPlacares
}