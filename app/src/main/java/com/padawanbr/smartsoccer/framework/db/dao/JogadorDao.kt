package com.padawanbr.smartsoccer.framework.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.OnConflictStrategy
import androidx.room.Transaction
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.PartidaEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeComJogadoresEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeJogadorCrossRef
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JogadorDao {

    @Query("SELECT * FROM jogador")
    fun getAll(): Flow<List<JogadorEntity>>

    @Query("SELECT * FROM jogador WHERE grupoId = :grupoId")
    fun getJogadoresByGrupo(grupoId: String): Flow<List<JogadorEntity>>

    @Query("SELECT * FROM jogador WHERE jogadorId = :jogadorId")
    fun getJogadorById(jogadorId: Int): JogadorEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(jogador: JogadorEntity)

    @Transaction
    suspend fun insertAll(
        jogadores: List<JogadorEntity>,
    ) {
        jogadores.forEach{
            insert(it)
        }
    }

    @Update
    fun update(jogador: JogadorEntity)

    @Query("DELETE FROM jogador WHERE jogadorId = :jogadorId")
    fun delete(jogadorId: String)

}