package com.padawanbr.smartsoccer.presentation

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class GrupoItemViewArgs(
    val id: Int,
    val nome: String,
    val quantidadeMinimaJogadores: Int,
    val quantidadeMinimaJogadoresPorTime: Int,
    val quantidadeTimes: Int,
) : Parcelable
