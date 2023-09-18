package com.blimas.smartsoccer.framework.di

import com.blimas.smartsoccer.core.data.repository.GroupLocalDataSource
import com.blimas.smartsoccer.core.data.repository.GroupRepository
import com.blimas.smartsoccer.framework.local.RoomGroupDataSource
import com.blimas.smartsoccer.framework.repository.GroupRepositoryImpl
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