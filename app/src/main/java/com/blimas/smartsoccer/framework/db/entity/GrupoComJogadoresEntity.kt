package com.blimas.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadores

data class GrupoComJogadoresEntity(
    @Embedded
    val grupo: GrupoEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "grupoId"
    )
    val jogadores: List<JogadorEntity>
)


fun GrupoComJogadoresEntity?.toGrupoComJogadoresModel(): GrupoComJogadores? {
    return this?.let {
        GrupoComJogadores(
            grupo = it.grupo.toGrupoModel(),
            jogadores = it.jogadores.toListSoccerPlayerModel()
        )
    }
}

fun List<GrupoComJogadoresEntity>.toListGrupoComJogadoresModel(): List<GrupoComJogadores?> {
    return map { it.toGrupoComJogadoresModel() }
}