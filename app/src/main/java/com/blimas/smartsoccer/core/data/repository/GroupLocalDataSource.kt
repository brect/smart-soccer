package com.blimas.smartsoccer.core.data.repository

import com.blimas.smartsoccer.core.domain.model.Grupo
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadores
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadoresETorneios
import kotlinx.coroutines.flow.Flow

interface GroupLocalDataSource {
    fun getAll(): Flow<List<Grupo>>
    fun getGrupoComJogadoresById(grupoId: String?): Flow<GrupoComJogadores?>
    suspend fun getGrupoComJogadoresETorneiosById(grupoId: String?): GrupoComJogadoresETorneios
    suspend fun saveGroup(grupo: Grupo)
    suspend fun deleteGroup(groupId: String)
}