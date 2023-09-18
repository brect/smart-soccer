package com.blimas.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.blimas.smartsoccer.framework.db.entity.GrupoComJogadoresETorneiosEntity
import com.blimas.smartsoccer.framework.db.entity.GrupoComJogadoresEntity
import com.blimas.smartsoccer.framework.db.entity.GrupoEntity
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
    suspend fun delete(groupId: String)

    @Transaction
    @Query("SELECT * FROM grupo")
    fun getGruposComJogadores(): List<GrupoComJogadoresEntity>

    @Transaction
    @Query("SELECT * FROM grupo WHERE id = :grupoId")
    fun getGrupoComJogadoresById(grupoId: String?): Flow<GrupoComJogadoresEntity?>

    @Transaction
    @Query("SELECT * FROM grupo WHERE id = :grupoId")
    fun getGrupoComJogadoresETorneiosById(grupoId: String?): GrupoComJogadoresETorneiosEntity

}