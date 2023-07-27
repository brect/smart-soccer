package com.padawanbr.smartsoccer.framework.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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
