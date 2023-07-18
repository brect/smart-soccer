package com.padawanbr.smartsoccer.core.domain.model

data class Jogador(
    val nome: String,
    val idade: Int,
    val posicoes: List<PosicaoJogador>,
    val classificacoes: Map<String, Double>,
    var estaNoDepartamentoMedico: Boolean
)
