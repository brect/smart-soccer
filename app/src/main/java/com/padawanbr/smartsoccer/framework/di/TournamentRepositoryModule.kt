package com.padawanbr.smartsoccer.framework.di

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.data.repository.TorneioRepository
import com.padawanbr.smartsoccer.core.data.repository.TournamentLocalDataSource
import com.padawanbr.smartsoccer.framework.local.RoomGroupDataSource
import com.padawanbr.smartsoccer.framework.local.RoomTournamentDataSource
import com.padawanbr.smartsoccer.framework.repository.GroupRepositoryImpl
import com.padawanbr.smartsoccer.framework.repository.TournamentRepositoryImpl
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