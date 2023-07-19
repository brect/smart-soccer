package com.padawanbr.smartsoccer.framework.repository

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupRepositoryImpl @Inject constructor(
    private val groupLocalDataSource: GroupLocalDataSource,
) : GroupRepository {

    override fun getAll(): Flow<List<Grupo>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveGroup(grupo: Grupo) {
        return groupLocalDataSource.saveGroup(grupo)
    }

}