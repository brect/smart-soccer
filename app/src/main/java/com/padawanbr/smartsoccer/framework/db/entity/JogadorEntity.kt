package com.padawanbr.smartsoccer.framework.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jogador")
data class JogadorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nome: String,
    val idade: Int,
    val posicoes: String,  // Map<String, Int> convertido para String
    val classificacoes: String,  // Map<String, Double> convertido para String
    var estaNoDepartamentoMedico: Boolean
)