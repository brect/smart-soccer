package com.padawanbr.smartsoccer.framework.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JogadorDao {

    @Query("SELECT * FROM jogador")
    fun getAll(): Flow<List<JogadorEntity>>

    @Query("SELECT * FROM jogador WHERE grupoId = :grupoId")
    fun getJogadoresByGrupo(grupoId: String): Flow<List<JogadorEntity>>

    @Query("SELECT * FROM jogador WHERE id = :jogadorId")
    fun getJogadorById(jogadorId: Int): JogadorEntity?

    @Insert
    fun insert(jogador: JogadorEntity)

    @Update
    fun update(jogador: JogadorEntity)

    @Query("DELETE FROM jogador WHERE id = :jogadorId")
    fun delete(jogadorId: Int)

}