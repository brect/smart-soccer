package com.padawanbr.smartsoccer.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.padawanbr.smartsoccer.framework.db.dao.GrupoDao
import com.padawanbr.smartsoccer.framework.db.dao.JogadorDao
import com.padawanbr.smartsoccer.framework.db.dao.JogoDao
import com.padawanbr.smartsoccer.framework.db.dao.PlacarDao
import com.padawanbr.smartsoccer.framework.db.dao.PosicaoJogadorDao
import com.padawanbr.smartsoccer.framework.db.dao.TimeDao
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.JogadorPosicaoCrossRef
import com.padawanbr.smartsoccer.framework.db.entity.JogoEntity
import com.padawanbr.smartsoccer.framework.db.entity.PlacarEntity
import com.padawanbr.smartsoccer.framework.db.entity.PosicaoJogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity

@Database(
    entities = [
        JogadorEntity::class,
        GrupoEntity::class,
        PosicaoJogadorEntity::class,
        JogoEntity::class,
        TimeEntity::class,
        PlacarEntity::class,
        JogadorPosicaoCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jogadorDao(): JogadorDao
    abstract fun grupoDao(): GrupoDao
    abstract fun posicaoJogadorDao(): PosicaoJogadorDao
    abstract fun jogoDao(): JogoDao
    abstract fun timeDao(): TimeDao
    abstract fun placarDao(): PlacarDao
}
