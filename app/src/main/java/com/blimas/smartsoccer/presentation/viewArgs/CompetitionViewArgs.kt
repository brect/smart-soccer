package com.blimas.smartsoccer.presentation.viewArgs

import android.os.Parcelable
import androidx.annotation.Keep
import com.blimas.smartsoccer.core.domain.model.Jogador
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class CompetitionViewArgs(
    val grupoId: String,
    val jogadores: MutableList<Jogador>?,
) : Parcelable

@Keep
@Parcelize
data class JogadorViewArgs(
    val id: String,
    val nome: String?,
    val idade: Int?,
    val posicao: String?,
    val habilidades: Map<String, Float>?,
    var estaNoDepartamentoMedico: Boolean?,
) : Parcelable


fun List<Jogador>.toListJogadorViewArgs() = map {
    JogadorViewArgs(
        it.id,
        it.nome,
        it.idade,
        it.posicao?.abreviacao,
        it.habilidades,
        it.estaNoDepartamentoMedico
    )
}