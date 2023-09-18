package com.blimas.smartsoccer.framework.local

import com.blimas.smartsoccer.core.data.repository.SoccerPlayerLocalDataSource
import com.blimas.smartsoccer.core.domain.model.Jogador
import com.blimas.smartsoccer.framework.db.dao.JogadorDao
import com.blimas.smartsoccer.framework.db.entity.JogadorEntity
import com.blimas.smartsoccer.framework.db.entity.toListSoccerPlayerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomSoccerPlayerDataSource @Inject constructor(
    private val jogadorDao: JogadorDao
) : SoccerPlayerLocalDataSource {

    override suspend fun getAllSoccerPlayers(grupoId: String): Flow<List<Jogador>> {
        return jogadorDao.getJogadoresByGrupo(grupoId).map {
            it.toListSoccerPlayerModel()
        }
    }

    override suspend fun saveSoccerPlayer(jogador: Jogador) {
        jogadorDao.insert(jogador.toJogadorEntity())
    }

    override suspend fun deleteSoccerPlayer(jogadorId: String) {
        jogadorDao.delete(jogadorId)
    }

    private fun Jogador.toJogadorEntity() = JogadorEntity(
        jogadorId = id,
        nome = nome,
        idade = idade,
        posicao = posicao,
        habilidades = habilidades ?: emptyMap(),
        estaNoDepartamentoMedico = estaNoDepartamentoMedico ?: false,
        grupoId = grupoId
    )
}

