package com.blimas.smartsoccer.core.data.repository

import com.blimas.smartsoccer.core.domain.model.Jogador
import kotlinx.coroutines.flow.Flow
interface SoccerPlayerRepository {

    suspend fun getAllSoccerPlayers(grupoId: String): Flow<List<Jogador>>

    suspend fun saveSoccerPlayer(jogador: Jogador)

    suspend fun deleteSoccerPlayer(jogadorId: String)
}
