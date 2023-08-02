package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadores
import kotlinx.coroutines.flow.Flow

interface GroupLocalDataSource {
    fun getAll(): Flow<List<Grupo>>
    fun getGrupoComJogadoresById(grupoId: String?): Flow<GrupoComJogadores?>
    suspend fun saveGroup(grupo: Grupo)
    suspend fun deleteGroup(groupId: String)
}