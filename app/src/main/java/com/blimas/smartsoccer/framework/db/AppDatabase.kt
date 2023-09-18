package com.blimas.smartsoccer.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blimas.smartsoccer.framework.db.dao.GrupoDao
import com.blimas.smartsoccer.framework.db.dao.JogadorDao
import com.blimas.smartsoccer.framework.db.dao.JogoDao
import com.blimas.smartsoccer.framework.db.dao.PlacarDao
import com.blimas.smartsoccer.framework.db.dao.PosicaoJogadorDao
import com.blimas.smartsoccer.framework.db.dao.TimeDao
import com.blimas.smartsoccer.framework.db.dao.TorneioDao
import com.blimas.smartsoccer.framework.db.entity.ClassificacoesConverter
import com.blimas.smartsoccer.framework.db.entity.Converters
import com.blimas.smartsoccer.framework.db.entity.CriterioDesempateItemEntity
import com.blimas.smartsoccer.framework.db.entity.GrupoEntity
import com.blimas.smartsoccer.framework.db.entity.JogadorEntity
import com.blimas.smartsoccer.framework.db.entity.JogadorPosicaoCrossRef
import com.blimas.smartsoccer.framework.db.entity.JogoEntity
import com.blimas.smartsoccer.framework.db.entity.PartidaEntity
import com.blimas.smartsoccer.framework.db.entity.PlacarEntity
import com.blimas.smartsoccer.framework.db.entity.PosicaoJogadorEntity
import com.blimas.smartsoccer.framework.db.entity.PosicoesConverter
import com.blimas.smartsoccer.framework.db.entity.TimeEntity
import com.blimas.smartsoccer.framework.db.entity.TimeJogadorCrossRef
import com.blimas.smartsoccer.framework.db.entity.TipoTorneioConverter
import com.blimas.smartsoccer.framework.db.entity.TorneioEntity
import com.blimas.smartsoccer.framework.db.entity.UUIDTypeConverter

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