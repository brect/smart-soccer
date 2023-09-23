package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.TournamentLocalDataSource
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.framework.db.dao.TorneioDao
import com.padawanbr.smartsoccer.framework.db.entity.PartidaEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeComJogadoresEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity
import com.padawanbr.smartsoccer.framework.db.entity.toCompetitionModel
import com.padawanbr.smartsoccer.framework.db.entity.toListJogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.toTimeModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RoomTournamentDataSource @Inject constructor(
    private val torneioDao: TorneioDao
) : TournamentLocalDataSource {

    override suspend fun insertTorneioWithTimesAndPartidas(
        torneio: Torneio,
        times: List<Time>,
        partidas: List<Partida>
    ) {
        val torneioEntity = torneio.toTorneioEntity()
        val timesEntities = times.map { it.toTimeComJogadoresEntity(torneioEntity.torneioId) }
        val partidasEntities = partidas.map { it.toPartidaEntity(torneioEntity.torneioId) }

        torneioDao.insertTorneioWithTimesAndPartidas(
            torneioEntity,
            timesEntities,
            partidasEntities
        )
    }

    override suspend fun saveTournament(torneio: Torneio) {
        torneioDao.insertTorneio(torneio.toTorneioEntity())
    }

    override suspend fun getTournamentsByGroup(grupoId: String): Flow<List<Torneio>> {
        return flowOf()
    }

    override suspend fun getTournamentById(torneioId: String): Torneio {
        val torneioWithTimesAndJogadores = torneioDao.getTorneioWithTimesAndJogadoresById(torneioId)

        val times = torneioWithTimesAndJogadores.times.map { it.toTimeModel() }

        return torneioWithTimesAndJogadores.torneio.toCompetitionModel(times)
    }

    override suspend fun deleteTournament(torneioId: String) {
        torneioDao.deleteTorneio(torneioId)
    }

    private fun Torneio.toTorneioEntity() = TorneioEntity(
        torneioId = id,
        nome = nome,
        tipoTorneio = tipoTorneio,
        grupoId = grupoId
    )

    private fun Time.toTimeEntity(torneioId: String) = TimeEntity(
        timeId = id,
        nome = nome,
        mediaHabilidades = mediaHabilidades,
        torneioId = torneioId,
    )


    private fun Partida.toPartidaEntity(torneioId: String) = PartidaEntity(
        id = id,
        timeCasaId = timeCasa.id,
        timeVisitanteId = timeVisitante.id,
        placarCasa = placarCasa,
        placarVisitante = placarVisitante,
        torneioId = torneioId
    )

}

private fun Time.toTimeComJogadoresEntity(torneioId: String) = TimeComJogadoresEntity(
    TimeEntity(nome = nome, mediaHabilidades = mediaHabilidades, torneioId = torneioId),
    jogadores.toListJogadorEntity()
)


