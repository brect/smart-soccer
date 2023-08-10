package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadores
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import java.util.UUID

@Entity(tableName = "torneio")
data class TorneioEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    @TypeConverters(TipoTorneio::class)
    val tipoTorneio: TipoTorneio? = null,// Converter o enum TipoTorneio para String
    val grupoId: String
)

//fun List<TorneioEntity>.toListTorneioEntityModel(): List<Torneio?> {
//    return map { it.to() }
//}

//
//
//fun TorneioEntity?.toTorneioModel(): Torneio? {
//    return this?.let {
//        Torneio(
//            id,
//            nome,
//            tipoTorneio,
//            grupoId
//        )
//    }
//}