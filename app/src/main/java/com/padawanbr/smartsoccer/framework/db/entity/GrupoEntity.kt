package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.ColumnInfo
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
    val quantidadeTimes: Int,
    @Embedded
    val configuracaoEsporte: ConfiguracaoEsporteEntity
)


fun List<GrupoEntity>.toListGroupModel() = map {
    Grupo(
        it.id,
        it.nome,
        it.quantidadeTimes,
        it.configuracaoEsporte.toConfiguracaoEsporteModel(),
    )
}

fun GrupoEntity.toGrupoModel(): Grupo {
    return Grupo(
        id = id,
        nome = nome,
        quantidadeTimes = quantidadeTimes,
        configuracaoEsporte = configuracaoEsporte.toConfiguracaoEsporteModel(),
    )
}
