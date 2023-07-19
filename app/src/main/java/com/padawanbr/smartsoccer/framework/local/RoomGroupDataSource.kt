package com.padawanbr.smartsoccer.framework.local

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.framework.db.dao.GrupoDao
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import javax.inject.Inject

class RoomGroupDataSource @Inject constructor(
    private val grupoDao: GrupoDao
) : GroupLocalDataSource {

    override suspend fun saveGroup(grupo: Grupo) {
        return grupoDao.insert(grupo.toGrupoEntity())
    }

    private fun Grupo.toGrupoEntity() = GrupoEntity(
        id,
        nome,
        quantidadeMinimaJogadores,
        quantidadeMinimaJogadoresPorTime,
        quantidadeTimes
    )

}