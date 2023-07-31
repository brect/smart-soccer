package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.framework.db.entity.GrupoComJogadores
import kotlinx.coroutines.flow.Flow

interface GroupRepository {

    fun getAll(): Flow<List<Grupo>>

    suspend fun saveGroup(grupo: Grupo)

    suspend fun deleteGroup(groupId: Int)

    fun getGrupoComJogadoresById(grupoId: Int?): Flow<GrupoComJogadores?>
}
