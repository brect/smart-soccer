package com.blimas.smartsoccer.framework.repository

import com.blimas.smartsoccer.core.data.repository.GroupLocalDataSource
import com.blimas.smartsoccer.core.data.repository.GroupRepository
import com.blimas.smartsoccer.core.domain.model.Grupo
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadores
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadoresETorneios
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupLocalDataSource: GroupLocalDataSource,
) : GroupRepository {

    override fun getAll(): Flow<List<Grupo>> {
        return groupLocalDataSource.getAll()
    }

    override suspend fun saveGroup(grupo: Grupo) {
        return groupLocalDataSource.saveGroup(grupo)
    }

    override suspend fun deleteGroup(groupId: String) {
        groupLocalDataSource.deleteGroup(groupId)
    }

    override fun getGrupoComJogadoresById(grupoId: String?): Flow<GrupoComJogadores?> {
        return groupLocalDataSource.getGrupoComJogadoresById(grupoId)
    }

    override suspend fun getGrupoComJogadoresETorneiosById(grupoId: String): GrupoComJogadoresETorneios {
        return groupLocalDataSource.getGrupoComJogadoresETorneiosById(grupoId)
    }

}