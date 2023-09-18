package com.blimas.smartsoccer.presentation.viewArgs

import android.os.Parcelable
import androidx.annotation.Keep
import com.blimas.smartsoccer.core.domain.model.TipoEsporte
import kotlinx.parcelize.Parcelize


@Keep
@Parcelize
data class DetalheGrupoItemViewArgs(
    val id: String,
    val nome: String,
    val quantidadeTimes: Int,
    val tipoEsporte: TipoEsporte
) : Parcelable
