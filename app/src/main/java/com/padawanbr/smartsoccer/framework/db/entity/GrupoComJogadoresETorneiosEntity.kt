package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadoresETorneios

data class GrupoComJogadoresETorneiosEntity(
    @Embedded
    val grupo: GrupoEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "grupoId"
    )
    val jogadores: List<JogadorEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "grupoId"
    )
    val torneios: List<TorneioEntity>
)

fun GrupoComJogadoresETorneiosEntity.toGrupoComJogadoresETorneiosModel(): GrupoComJogadoresETorneios {
    return this.let {
        GrupoComJogadoresETorneios(
            grupo = it.grupo.toGrupoModel(),
            jogadores = it.jogadores.toListSoccerPlayerModel(),
            torneios = it.torneios.toListCompetitionModel()
        )
    }
}

fun List<GrupoComJogadoresETorneiosEntity>.toListGrupoComJogadoresModel(): List<GrupoComJogadoresETorneios> {
    return map { it.toGrupoComJogadoresETorneiosModel() }
}