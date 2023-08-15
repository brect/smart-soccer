package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.padawanbr.smartsoccer.framework.db.entity.PartidaEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity
import com.padawanbr.smartsoccer.framework.db.entity.TorneioComTimesEntity
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity

@Dao
interface TorneioDao {

    @Transaction
    suspend fun insertTorneioWithTimesAndPartidas(torneio: TorneioEntity, times: List<TimeEntity>, partidas: List<PartidaEntity>) {
        insertTorneio(torneio)
        insertTimes(times)
        insertPartidas(partidas)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTorneio(torneio: TorneioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimes(times: List<TimeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartidas(partidas: List<PartidaEntity>)

    @Query("SELECT * FROM torneio WHERE grupoId = :grupoId")
    suspend fun getTorneiosByGrupo(grupoId: String): List<TorneioEntity>

    @Query("SELECT * FROM torneio WHERE id = :torneioId")
    suspend fun getTorneioById(torneioId: String): TorneioComTimesEntity

    @Query("DELETE FROM torneio WHERE id = :torneioId")
    suspend fun deleteTorneio(torneioId: String)
}
