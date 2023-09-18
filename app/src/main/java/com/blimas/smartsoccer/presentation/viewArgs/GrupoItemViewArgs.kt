package com.blimas.smartsoccer.presentation.viewArgs

import android.os.Parcelable
import androidx.annotation.Keep
import com.blimas.smartsoccer.core.domain.model.TipoEsporte
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

