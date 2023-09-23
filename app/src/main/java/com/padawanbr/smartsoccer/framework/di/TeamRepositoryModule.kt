package com.padawanbr.smartsoccer.framework.di

import com.padawanbr.smartsoccer.core.data.repository.TeamLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.TeamRepository
import com.padawanbr.smartsoccer.framework.local.RoomTeamDataSource
import com.padawanbr.smartsoccer.framework.repository.TeamRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TeamRepositoryModule {

    @Binds
    fun bindTeamRepository(
        repositoryImpl: TeamRepositoryImpl
    ): TeamRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomTeamDataSource,
    ): TeamLocalDataSource

}