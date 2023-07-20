package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.padawanbr.smartsoccer.core.domain.model.Grupo

@Entity(tableName = "grupo")
data class GrupoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    val nome: String,
    val quantidadeMinimaJogadores: Int,
    val quantidadeMinimaJogadoresPorTime: Int,
    val quantidadeTimes: Int
)

fun List<GrupoEntity>.toGroupModel() = map {
    Grupo(
        it.id,
        it.nome,
        it.quantidadeMinimaJogadores,
        it.quantidadeMinimaJogadoresPorTime,
        it.quantidadeTimes,
        arrayListOf(),
        arrayListOf()
    )
}