package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Jogador
import kotlinx.coroutines.flow.Flow

interface SoccerPlayerRepository {

    suspend fun getAllSoccerPlayers(grupoId: Int): Flow<List<Jogador>>

    suspend fun saveSoccerPlayer(jogador: Jogador, grupoId: Int)

}
