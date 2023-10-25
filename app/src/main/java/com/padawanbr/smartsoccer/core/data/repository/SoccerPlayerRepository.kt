package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Jogador
import kotlinx.coroutines.flow.Flow
interface SoccerPlayerRepository {

    suspend fun getAllSoccerPlayers(grupoId: String): Flow<List<Jogador>>

    suspend fun saveSoccerPlayer(jogador: Jogador)

    suspend fun saveSoccerPlayers(jogadores: List<Jogador>)

    suspend fun deleteSoccerPlayer(jogadorId: String)

    suspend fun deleteSoccerPlayersByGroup(grupoId: String)
}
