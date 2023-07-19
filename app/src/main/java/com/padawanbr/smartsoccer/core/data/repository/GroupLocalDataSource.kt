package com.padawanbr.smartsoccer.core.data.repository

import com.padawanbr.smartsoccer.core.domain.model.Grupo

interface GroupLocalDataSource {

    suspend fun saveGroup(grupo: Grupo)
}