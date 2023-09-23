package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.padawanbr.smartsoccer.framework.db.entity.PosicaoJogadorEntity

@Dao
interface PosicaoJogadorDao {
    @Query("SELECT * FROM posicao_jogador")
    fun getAll(): List<PosicaoJogadorEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posicao: PosicaoJogadorEntity)

    @Update
    fun update(posicao: PosicaoJogadorEntity)

    @Delete
    fun delete(posicao: PosicaoJogadorEntity)
}