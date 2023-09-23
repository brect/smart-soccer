package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import kotlinx.coroutines.flow.Flow

interface TournamentLocalDataSource {

    suspend fun insertTorneioWithTimesAndPartidas(torneio: Torneio, times: List<Time>, partidas: List<Partida>)

    suspend fun saveTournament(torneio: Torneio)

    suspend fun getTournamentsByGroup(grupoId: String): Flow<List<Torneio>>

    suspend fun getTournamentById(torneioId: String): Torneio?

    suspend fun deleteTournament(torneioId: String)

}