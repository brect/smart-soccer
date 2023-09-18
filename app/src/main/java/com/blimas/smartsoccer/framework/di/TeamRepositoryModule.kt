package com.blimas.smartsoccer.framework.di

import com.blimas.smartsoccer.core.data.repository.TeamLocalDataSource
import com.blimas.smartsoccer.core.data.repository.TeamRepository
import com.blimas.smartsoccer.framework.local.RoomTeamDataSource
import com.blimas.smartsoccer.framework.repository.TeamRepositoryImpl
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