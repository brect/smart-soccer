package com.padawanbr.smartsoccer.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.framework.db.dao.GrupoDao
import com.padawanbr.smartsoccer.framework.db.dao.JogadorDao
import com.padawanbr.smartsoccer.framework.db.dao.JogoDao
import com.padawanbr.smartsoccer.framework.db.dao.PlacarDao
import com.padawanbr.smartsoccer.framework.db.dao.PosicaoJogadorDao
import com.padawanbr.smartsoccer.framework.db.dao.TimeDao
import com.padawanbr.smartsoccer.framework.db.dao.TorneioDao
import com.padawanbr.smartsoccer.framework.db.entity.ClassificacoesConverter
import com.padawanbr.smartsoccer.framework.db.entity.Converters
import com.padawanbr.smartsoccer.framework.db.entity.CriterioDesempateItemEntity
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.JogadorPosicaoCrossRef
import com.padawanbr.smartsoccer.framework.db.entity.JogoEntity
import com.padawanbr.smartsoccer.framework.db.entity.PartidaEntity
import com.padawanbr.smartsoccer.framework.db.entity.PlacarEntity
import com.padawanbr.smartsoccer.framework.db.entity.PosicaoJogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.PosicoesConverter
import com.padawanbr.smartsoccer.framework.db.entity.TimeEntity
import com.padawanbr.smartsoccer.framework.db.entity.TimeJogadorCrossRef
import com.padawanbr.smartsoccer.framework.db.entity.TipoTorneioConverter
import com.padawanbr.smartsoccer.framework.db.entity.TorneioEntity
import com.padawanbr.smartsoccer.framework.db.entity.UUIDTypeConverter

@Database(
    entities = [
        JogadorEntity::class,
        GrupoEntity::class,
        PosicaoJogadorEntity::class,
        JogoEntity::class,
        PlacarEntity::class,
        JogadorPosicaoCrossRef::class,
        TimeJogadorCrossRef::class,
        TimeEntity::class,
        TorneioEntity::class,
        PartidaEntity::class,
        CriterioDesempateItemEntity::class
    ],
    version = 1,
    exportSchema = false
)

@TypeConverters(
    Converters::class,
    PosicoesConverter::class,
    ClassificacoesConverter::class,
    UUIDTypeConverter::class,
    TipoTorneioConverter::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jogadorDao(): JogadorDao
    abstract fun grupoDao(): GrupoDao
    abstract fun posicaoJogadorDao(): PosicaoJogadorDao
    abstract fun jogoDao(): JogoDao
    abstract fun timeDao(): TimeDao
    abstract fun placarDao(): PlacarDao
    abstract fun torneioDao(): TorneioDao

}