package com.padawanbr.smartsoccer

import android.app.Application
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.framework.db.AppDatabase
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SmartSoccerApp : Application() {}