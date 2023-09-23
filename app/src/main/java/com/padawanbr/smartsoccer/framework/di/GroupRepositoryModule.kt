package com.padawanbr.smartsoccer.framework.di

import com.padawanbr.smartsoccer.core.data.repository.GroupLocalDataSource
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.framework.local.RoomGroupDataSource
import com.padawanbr.smartsoccer.framework.repository.GroupRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GroupRepositoryModule {

    @Binds
    fun bindGroupRepository(
        repositoryImpl: GroupRepositoryImpl
    ): GroupRepository

    @Binds
    fun bindLocalDataSource(
        dataSource: RoomGroupDataSource,
    ): GroupLocalDataSource

}