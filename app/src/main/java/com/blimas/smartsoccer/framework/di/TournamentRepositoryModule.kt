package com.blimas.smartsoccer.framework.di

import com.blimas.smartsoccer.core.data.repository.TorneioRepository
import com.blimas.smartsoccer.core.data.repository.TournamentLocalDataSource
import com.blimas.smartsoccer.framework.local.RoomTournamentDataSource
import com.blimas.smartsoccer.framework.repository.TournamentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TournamentRepositoryModule {

    @Binds
    fun bindTorneioRepository(
        repositoryImpl: TournamentRepositoryImpl
    ): TorneioRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomTournamentDataSource,
    ): TournamentLocalDataSource

}