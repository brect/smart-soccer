package com.blimas.smartsoccer.framework.repository

import com.blimas.smartsoccer.core.data.repository.TorneioRepository
import com.blimas.smartsoccer.core.domain.model.Torneio
import com.blimas.smartsoccer.framework.local.RoomTournamentDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TournamentRepositoryImpl @Inject constructor(
    private val tournamentDataSource: RoomTournamentDataSource,
) : TorneioRepository {

    override suspend fun insertTorneioWithTimesAndPartidas(
        torneio: Torneio
    ) {
        tournamentDataSource.insertTorneioWithTimesAndPartidas(torneio, torneio.times, torneio.partidas)
    }

    override suspend fun saveTournament(torneio: Torneio) {
        tournamentDataSource.saveTournament(torneio)
    }

    override suspend fun getTournamentsByGroup(grupoId: String): Flow<List<Torneio>> {
        return tournamentDataSource.getTournamentsByGroup(grupoId)
    }

    override suspend fun getTournamentById(torneioId: String): Torneio {
        return tournamentDataSource.getTournamentById(torneioId)
    }

    override suspend fun deleteTournament(torneioId: String) {
        return tournamentDataSource.deleteTournament(torneioId)
    }
}