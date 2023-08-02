package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerLocalDataSource
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.framework.db.dao.JogadorDao
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.toListSoccerPlayerModel
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

    override suspend fun deleteSoccerPlayer(jogadorId: Int) {
        jogadorDao.delete(jogadorId)
    }

    private fun Jogador.toJogadorEntity() = JogadorEntity(
        nome = nome,
        idade = idade,
        posicao = posicao,
        habilidades = habilidades ?: emptyMap(),
        estaNoDepartamentoMedico = estaNoDepartamentoMedico ?: false,
        grupoId = grupoId
    )
}

