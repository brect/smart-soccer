package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SoccerPlayerRepositoryImpl @Inject constructor(
    private val soccerPlayerLocalDataSource: SoccerPlayerLocalDataSource,
) : SoccerPlayerRepository {
    override suspend fun getAllSoccerPlayers(grupoId: String): Flow<List<Jogador>> {
        return soccerPlayerLocalDataSource.getAllSoccerPlayers(grupoId)
    }

    override suspend fun saveSoccerPlayer(jogador: Jogador) {
        soccerPlayerLocalDataSource.saveSoccerPlayer(jogador)
    }

    override suspend fun deleteSoccerPlayer(jogadorId: Int) {
        soccerPlayerLocalDataSource.deleteSoccerPlayer(jogadorId)
    }

}
