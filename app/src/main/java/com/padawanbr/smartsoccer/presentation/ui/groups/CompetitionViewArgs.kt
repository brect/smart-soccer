package com.padawanbr.smartsoccer.presentation.ui.groups

import android.os.Parcelable
import androidx.annotation.Keep
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.framework.db.entity.GrupoEntity
import com.padawanbr.smartsoccer.framework.db.entity.toConfiguracaoEsporteModel
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