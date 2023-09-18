package com.blimas.smartsoccer.framework.di

import com.blimas.smartsoccer.core.data.repository.SoccerPlayerLocalDataSource
import com.blimas.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.blimas.smartsoccer.framework.local.RoomSoccerPlayerDataSource
import com.blimas.smartsoccer.framework.repository.SoccerPlayerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface SoccerPlayerRepositoryModule {

    @Binds
    fun bindSoccerPlayerRepository(
        repositoryImpl: SoccerPlayerRepositoryImpl
    ): SoccerPlayerRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomSoccerPlayerDataSource,
    ): SoccerPlayerLocalDataSource

}