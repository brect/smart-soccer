package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity

@Dao
interface TorneioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTorneio(torneio: TorneioEntity)

    @Query("SELECT * FROM torneio WHERE grupoId = :grupoId")
    suspend fun getTorneiosByGrupo(grupoId: String): List<TorneioEntity>

    @Query("SELECT * FROM torneio WHERE id = :torneioId")
    suspend fun getTorneioById(torneioId: String): TorneioEntity?

    @Query("DELETE FROM torneio WHERE id = :torneioId")
    suspend fun deleteTorneio(torneioId: String)
}