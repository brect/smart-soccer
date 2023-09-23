package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.padawanbr.smartsoccer.databinding.ItemSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.adapter.CommonViewHolder
import com.padawanbr.smartsoccer.presentation.modelView.JogadorItem

class SoccerPlayerViewHolder(
    itemBinding: ItemSoccerPlayerBinding,
    private val mContext: Context
) : CommonViewHolder<JogadorItem>(itemBinding) {

    private val textViewSoccerPlayerName: TextView = itemBinding.textViewSoccerPlayerName
    private val soccerPlayerPosition: TextView = itemBinding.soccerPlayerPosition
    private val textViewSoccerPlayerMedicalDepartment: ImageView = itemBinding.imageViewSoccerPlayerMedicalDepartment
    private val ratingBarAverageSoccerPlayer = itemBinding.ratingBarAverageSoccerPlayer
    private val textViewAverageSoccerPlayer = itemBinding.textViewAverageSoccerPlayerScore

    override fun bind(data: JogadorItem) {
        textViewSoccerPlayerName.text = data.nome
        soccerPlayerPosition.text = data.posicao

        if (data.estaNoDepartamentoMedico == true) {
            textViewSoccerPlayerMedicalDepartment.visibility = View.VISIBLE
        }

        val mediaHabilidades = data.calcularMediaHabilidades()
        ratingBarAverageSoccerPlayer.rating = mediaHabilidades
        textViewAverageSoccerPlayer.text = String.format("%.2f", mediaHabilidades)
    }

    companion object {
        fun create(parent: ViewGroup): SoccerPlayerViewHolder {
            val itemBinding = ItemSoccerPlayerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return SoccerPlayerViewHolder(itemBinding, parent.context)
        }
    }
}