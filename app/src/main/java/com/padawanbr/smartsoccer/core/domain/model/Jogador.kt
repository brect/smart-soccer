package com.padawanbr.smartsoccer.core.domain.model

import java.util.UUID

data class Jogador(
    val id: String = UUID.randomUUID().toString(),
    val nome: String,
    val idade: Int,
    val posicao: PosicaoJogador? = null,
    val habilidades: Map<String, Float> = emptyMap(),
    var estaNoDepartamentoMedico: Boolean = false,
    val grupoId: String
)

fun Jogador.calcularMediaHabilidades(): Float {
    return habilidades.values.average().toFloat()
}