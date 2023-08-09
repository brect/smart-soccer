package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Torneio

interface TorneioRepository {

    suspend fun saveTournament(torneio: Torneio)

    suspend fun getTournamentsByGrupo(grupoId: String): List<Torneio>

    suspend fun getTournamentById(torneioId: String): Torneio?

    suspend fun deleteTournament(torneioId: String)

}