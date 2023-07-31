package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.framework.db.entity.GrupoComJogadores
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

    override suspend fun deleteGroup(groupId: Int) {
        groupLocalDataSource.deleteGroup(groupId)
    }

    override fun getGrupoComJogadoresById(grupoId: Int?): Flow<GrupoComJogadores?> {
        return groupLocalDataSource.getGrupoComJogadoresById(grupoId)
    }

}