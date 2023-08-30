package com.padawanbr.smartsoccer.presentation.ui.groups

import android.os.Parcelable
import androidx.annotation.Keep
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
class GrupoItemViewArgs(
    val id: String,
    val nome: String,
    val endereco: String,
    val tipoEsporte: TipoEsporte,
    val diaDoJogo: String?,
    val horarioInicio: String?,
    val quantidadeTimes: Int?,
    val minAge: Float? = 0F,
    val maxAge: Float? = 0F
) : Parcelable

