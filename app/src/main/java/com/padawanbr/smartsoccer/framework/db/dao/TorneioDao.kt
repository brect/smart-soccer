package com.padawanbr.smartsoccer.framework.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.padawanbr.smartsoccer.framework.db.entity.PartidaEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeComJogadoresEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeJogadorCrossRef
import com.padawanbr.smartsoccer.framework.db.entity.TorneioComTimesAndJogadores
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity


@Dao
interface TorneioDao {

    @Transaction
    @Query("SELECT * FROM torneio WHERE id = :torneioId")
    suspend fun getTorneioWithTimesAndJogadoresById(torneioId: String): TorneioComTimesAndJogadores

    @Transaction
    suspend fun insertTorneioWithTimesAndPartidas(
        torneio: TorneioEntity,
        times: List<TimeComJogadoresEntity>,
        partidas: List<PartidaEntity>
    ) {
        insertTorneio(torneio)
        times.forEach { insertTime(it.time) }
        partidas.forEach { insertPartida(it) }

        times.forEach { timeComJogadores ->
            timeComJogadores.jogadores.forEach { jogador ->
                insertTimeJogadorCrossRefs(
                    TimeJogadorCrossRef(
                        timeId = timeComJogadores.time.timeId,
                        jogadorId = jogador.jogadorId
                    )
                )
            }
        }

    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTorneio(torneio: TorneioEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTime(times: TimeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPartida(partida: PartidaEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeJogadorCrossRefs(time: TimeJogadorCrossRef)

    @Query("DELETE FROM torneio WHERE id = :torneioId")
    suspend fun deleteTorneio(torneioId: String)
}

