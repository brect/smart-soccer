package com.blimas.smartsoccer.framework.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.blimas.smartsoccer.core.domain.model.PosicaoJogador
import com.blimas.smartsoccer.core.domain.model.TipoTorneio
import java.util.UUID


class Converters {
    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun fromStringToList(string: String): List<String> {
        return string.split(",")
    }
}

class PosicoesConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Int> {
        val mapType = object : TypeToken<Map<String, Int>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, Int>): String {
        return Gson().toJson(map)
    }
}

class PosicaoJogadorConverter {
    @TypeConverter
    fun fromPosicaoJogador(tipoPosicao: PosicaoJogador?): String? {
        return tipoPosicao?.name
    }

    @TypeConverter
    fun toPosicaoJogador(tipoPosicao: String?): PosicaoJogador? {
        return tipoPosicao?.let { enumValueOf<PosicaoJogador>(it) }
    }
}

class TipoTorneioConverter {
    @TypeConverter
    fun fromTipoTorneio(tipoTorneio: TipoTorneio?): String? {
        return tipoTorneio?.tipo
    }

    @TypeConverter
    fun toTipoTorneio(tipoTorneio: String?): TipoTorneio? {
        return tipoTorneio?.let { enumValueOf<TipoTorneio>(it) }
    }
}


class ClassificacoesConverter {
    @TypeConverter
    fun fromString(value: String): Map<String, Float> {
        val mapType = object : TypeToken<Map<String, Float>>() {}.type
        return Gson().fromJson(value, mapType)
    }

    @TypeConverter
    fun fromMap(map: Map<String, Float>): String {
        return Gson().toJson(map)
    }
}

class UUIDTypeConverter {
    @TypeConverter
    fun fromString(value: String?): UUID? {
        return value?.let { UUID.fromString(it) }
    }

    @TypeConverter
    fun toString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}
