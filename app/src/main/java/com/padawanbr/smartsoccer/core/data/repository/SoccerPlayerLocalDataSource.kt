package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Jogador
import kotlinx.coroutines.flow.Flow

interface SoccerPlayerLocalDataSource {

    suspend fun getAllSoccerPlayers(grupoId: String): Flow<List<Jogador>>

    suspend fun saveSoccerPlayer(jogador: Jogador)

    suspend fun deleteSoccerPlayer(jogadorId: String)

}