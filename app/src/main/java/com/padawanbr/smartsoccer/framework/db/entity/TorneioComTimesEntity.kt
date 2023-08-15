package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import java.util.UUID

data class TorneioComTimesEntity(
    @Embedded
    val torneio: TorneioEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "torneioId"
    )
    val times: List<TimeEntity>
)

fun TorneioComTimesEntity.toTorneioModel(): Torneio {
    return Torneio(
        torneio.id,
        torneio.nome,
        torneio.tipoTorneio,
        arrayListOf(),
        times.toListTimeModel(),
        arrayListOf(),
        torneio.grupoId
    )
}
