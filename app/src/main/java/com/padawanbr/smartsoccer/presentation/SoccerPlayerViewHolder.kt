package com.padawanbr.smartsoccer.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.ItemGroupBinding
import com.padawanbr.smartsoccer.databinding.ItemSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder

class SoccerPlayerViewHolder(
    itemBinding: ItemSoccerPlayerBinding,
    private val mContext: Context
): CommonViewHolder<JogadorItem>(itemBinding){

    private val textViewSoccerPlayerName: TextView = itemBinding.textViewSoccerPlayerName
    private val textViewSoccerPlayerAge: TextView = itemBinding.textViewSoccerPlayerAge
    private val textViewSoccerPlayerAbility: TextView = itemBinding.textViewSoccerPlayerAbility
    private val soccerPlayerPosition: TextView = itemBinding.soccerPlayerPosition
    private val textViewSoccerPlayerMedicalDepartment: TextView = itemBinding.textViewSoccerPlayerMedicalDepartment

    override fun bind(data: JogadorItem) {
        textViewSoccerPlayerName.text = data.nome
        textViewSoccerPlayerAge.text = data.idade.toString()
        textViewSoccerPlayerAbility.text = data.classificacoes
        soccerPlayerPosition.text = data.posicoes
        textViewSoccerPlayerMedicalDepartment.text = data.estaNoDepartamentoMedico.toString()
    }

    companion object {
        fun create(parent: ViewGroup): SoccerPlayerViewHolder {
            val itemBinding = ItemSoccerPlayerBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return SoccerPlayerViewHolder(itemBinding, parent.context)
        }
    }
}