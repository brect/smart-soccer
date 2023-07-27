package com.padawanbr.smartsoccer.core.domain.model

data class Jogador(
    val id: Int? = 0,
    val nome: String,
    val idade: Int,
    val posicoes: List<PosicaoJogador>? = null,
    val habilidades: Map<String, Float>? = null,
    var estaNoDepartamentoMedico: Boolean? = null
)
