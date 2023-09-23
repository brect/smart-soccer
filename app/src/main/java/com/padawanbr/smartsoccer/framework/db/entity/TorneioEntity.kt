package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.core.domain.model.Partida
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import java.util.UUID

@Entity(tableName = "torneio")
data class TorneioEntity(
    @PrimaryKey
    val torneioId: String = UUID.randomUUID().toString(),
    val nome: String,
    @TypeConverters(TipoTorneio::class)
    val tipoTorneio: TipoTorneio? = null,// Converter o enum TipoTorneio para String
    val grupoId: String
)

fun List<TorneioEntity>.toListCompetitionModel() = map {
    Torneio(
        it.torneioId,
        it.nome,
        it.tipoTorneio,
        listOf(),
        listOf(),
        listOf(),
        it.grupoId
    )
}

fun TorneioEntity.toCompetitionModel(): Torneio {
    return Torneio(
        id = torneioId,
        nome = nome,
        tipoTorneio = tipoTorneio,
        listOf(),
        listOf(),
        listOf(),
        grupoId = grupoId
    )
}

fun TorneioEntity.toCompetitionModel(times: List<Time>, partidas: List<Partida> = emptyList()): Torneio {
    return Torneio(
        id = this.torneioId,
        nome = this.nome,
        tipoTorneio = this.tipoTorneio,
        criteriosDesempate = null, // Estou assumindo que TorneioEntity não tem essa informação
        times = times,
        partidas = partidas, // Você pode adicionar a lógica para as partidas também se elas estiverem disponíveis
        grupoId = this.grupoId
    )
}