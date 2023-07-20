package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Grupo
import kotlinx.coroutines.flow.Flow

interface GroupLocalDataSource {

    suspend fun saveGroup(grupo: Grupo)

    fun getAll(): Flow<List<Grupo>>

}