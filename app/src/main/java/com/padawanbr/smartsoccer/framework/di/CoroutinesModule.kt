package com.padawanbr.smartsoccer.framework.di

import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface CoroutinesModule {
    @Binds
    fun provideDispatchers(dispatchers: AppCoroutinesDispatchers): CoroutinesDispatchers
}