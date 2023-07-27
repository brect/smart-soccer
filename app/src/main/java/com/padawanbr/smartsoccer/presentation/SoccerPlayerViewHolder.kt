package com.padawanbr.smartsoccer.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.ItemGroupBinding
import com.padawanbr.smartsoccer.databinding.ItemSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder

class SoccerPlayerViewHolder(
    itemBinding: ItemSoccerPlayerBinding,
    private val mContext: Context
) : CommonViewHolder<JogadorItem>(itemBinding) {

    private val textViewSoccerPlayerName: TextView = itemBinding.textViewSoccerPlayerName
    private val soccerPlayerPosition: TextView = itemBinding.soccerPlayerPosition
    private val textViewSoccerPlayerMedicalDepartment: ImageView = itemBinding.textViewSoccerPlayerMedicalDepartment
    private val ratingBarAverageSoccerPlayer = itemBinding.ratingBarAverageSoccerPlayer

    override fun bind(data: JogadorItem) {
        textViewSoccerPlayerName.text = data.nome
        soccerPlayerPosition.text = data.posicao

        if (data.estaNoDepartamentoMedico == true) {
            textViewSoccerPlayerMedicalDepartment.visibility = View.VISIBLE
        }

        ratingBarAverageSoccerPlayer.rating = data.calcularMediaHabilidades()
    }

    companion object {
        fun create(parent: ViewGroup): SoccerPlayerViewHolder {
            val itemBinding = ItemSoccerPlayerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return SoccerPlayerViewHolder(itemBinding, parent.context)
        }
    }
}