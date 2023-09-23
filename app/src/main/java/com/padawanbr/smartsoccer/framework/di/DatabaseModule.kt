package com.padawanbr.smartsoccer.framework.di

import android.content.Context
import androidx.room.Room
import com.padawanbr.smartsoccer.core.data.DbConstants.APP_DATABASE_NAME
import com.padawanbr.smartsoccer.framework.db.AppDatabase
import com.padawanbr.smartsoccer.framework.db.LoggingQueryCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ) .setQueryCallback(LoggingQueryCallback(), Executors.newSingleThreadExecutor())
        .build()

    @Provides
    fun providerJogadorDao(appDatabase: AppDatabase) = appDatabase.jogadorDao()

    @Provides
    fun providerGrupoDao(appDatabase: AppDatabase) = appDatabase.grupoDao()

    @Provides
    fun providerPosicaoDao(appDatabase: AppDatabase) = appDatabase.posicaoJogadorDao()

    @Provides
    fun providerJogoDao(appDatabase: AppDatabase) = appDatabase.jogoDao()

    @Provides
    fun providerTimeDao(appDatabase: AppDatabase) = appDatabase.timeDao()

    @Provides
    fun providerPlacarDao(appDatabase: AppDatabase) = appDatabase.placarDao()

    @Provides
    fun providerTorneioDao(appDatabase: AppDatabase) = appDatabase.torneioDao()

}