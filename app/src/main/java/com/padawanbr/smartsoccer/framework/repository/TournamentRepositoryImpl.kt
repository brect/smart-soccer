package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.data.repository.TorneioRepository
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadores
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.framework.local.RoomTournamentDataSource
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

    override suspend fun getTournamentById(torneioId: String): Torneio? {
        return tournamentDataSource.getTournamentById(torneioId)
    }

    override suspend fun deleteTournament(torneioId: String) {
        return tournamentDataSource.deleteTournament(torneioId)
    }
}