package com.padawanbr.smartsoccer.presentation

import android.os.Parcelable
import androidx.annotation.Keep
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class DetalheGrupoItemViewArgs(
    val id: Int,
    val nome: String,
    val quantidadeTimes: Int,
    val tipoEsporte: TipoEsporte
) : Parcelable
