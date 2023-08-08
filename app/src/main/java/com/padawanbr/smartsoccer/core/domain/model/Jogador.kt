package com.padawanbr.smartsoccer.core.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Keep
@Parcelize
data class Jogador(
    val id: String = UUID.randomUUID().toString(),
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

