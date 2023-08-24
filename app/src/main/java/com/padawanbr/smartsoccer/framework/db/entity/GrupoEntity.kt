package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import java.util.UUID

@Entity(tableName = "grupo")
data class GrupoEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(), // Utilizando UUID como ID
    val nome: String,
    val endereco: String,
    @Embedded
    val configuracaoEsporte: ConfiguracaoEsporteEntity,
    val diaDoJogo: String,
    val horarioInicio: String,
    val quantidadeTimes: Int,
    @Embedded
    val rangeIdade: RangeIdadeEntity
)


fun List<GrupoEntity>.toListGroupModel() = map {
    Grupo(
        it.id,
        it.nome,
        it.endereco,
        it.configuracaoEsporte.toConfiguracaoEsporteModel(),
        it.diaDoJogo,
        it.horarioInicio,
        it.quantidadeTimes,
        it.rangeIdade.toRangeIdadeModel()
    )
}

fun GrupoEntity.toGrupoModel(): Grupo {
    return Grupo(
        id = id,
        nome = nome,
        endereco = endereco,
        configuracaoEsporte = configuracaoEsporte.toConfiguracaoEsporteModel(),
        diaDoJogo = diaDoJogo,
        horarioInicio = horarioInicio,
        quantidadeTimes = quantidadeTimes,
        rangeIdade = rangeIdade.toRangeIdadeModel(),
    )
}
