package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.TournamentLocalDataSource
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.framework.db.dao.TorneioDao
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomTournamentDataSource @Inject constructor(
    private val torneioDao: TorneioDao
) : TournamentLocalDataSource {

    override suspend fun insertTorneioWithTimesAndPartidas(
        torneio: Torneio,
        times: List<Time>,
        partidas: List<Partida>
    ) {
        torneioDao.insertTorneioWithTimesAndPartidas(torneio, times, partidas)
    }

    override suspend fun saveTournament(torneio: Torneio) {
        torneioDao.insertTorneio(torneio.toTorneioEntity())
    }

    override suspend fun getTournamentsByGroup(grupoId: String): Flow<List<Torneio>> {
        return flowOf()
    }

    override suspend fun getTournamentById(torneioId: String): Torneio? {
        return null
    }

    override suspend fun deleteTournament(torneioId: String) {
        torneioDao.deleteTorneio(torneioId)
    }

    fun Torneio.toTorneioEntity() = TorneioEntity (
        nome = nome,
        tipoTorneio = tipoTorneio,
        grupoId = grupoId
    )

}


