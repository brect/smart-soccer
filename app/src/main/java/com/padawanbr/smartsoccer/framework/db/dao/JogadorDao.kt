package com.padawanbr.smartsoccer.framework.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity

@Dao
interface JogadorDao {
    @Query("SELECT * FROM jogador")
    fun getAll(): List<JogadorEntity>

    @Insert
    fun insert(jogador: JogadorEntity)

    @Update
    fun update(jogador: JogadorEntity)

    @Delete
    fun delete(jogador: JogadorEntity)
}