package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.padawanbr.smartsoccer.framework.db.entity.GrupoComJogadores
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GrupoDao {
    @Query("SELECT * FROM grupo")
    fun getAll(): Flow<List<GrupoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(grupo: GrupoEntity)

    @Update
    fun update(grupo: GrupoEntity)

    @Query("DELETE FROM grupo WHERE id = :groupId")
    suspend fun delete(groupId: Int)

    @Transaction
    @Query("SELECT * FROM grupo")
    fun getGruposComJogadores(): List<GrupoComJogadores>

    @Transaction
    @Query("SELECT * FROM grupo WHERE id = :grupoId")
    fun getGrupoComJogadoresById(grupoId: Int?): Flow<GrupoComJogadores?>
}