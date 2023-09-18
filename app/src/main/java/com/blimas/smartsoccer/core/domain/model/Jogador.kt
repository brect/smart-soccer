package com.blimas.smartsoccer.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Jogador(
    val id: String,
    val nome: String,
    val idade: Int,
    val posicao: PosicaoJogador? = null,
    val habilidades: Map<String, Float> = emptyMap(),
    var estaNoDepartamentoMedico: Boolean = false,
    val grupoId: String
) : Parcelable

fun Jogador.calcularMediaHabilidades(): Float {
    return habilidades.values.average().toFloat()
}


