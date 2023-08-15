package com.padawanbr.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.padawanbr.smartsoccer.databinding.ItemCompetitionBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder

class ItemCompetitionViewHolder(
    itemBinding: ItemCompetitionBinding,
) : CommonViewHolder<CompetitionItem>(itemBinding) {

    private val textNameCompetition: TextView = itemBinding.textViewNameCompetition
    private val textTypeCompetition: TextView = itemBinding.textViewTypeCompetition

    override fun bind(item: CompetitionItem) {
        textNameCompetition.text = item.nome
        textTypeCompetition.text = item.tipoTorneio?.descricao
    }

    companion object {
        fun create(parent: ViewGroup): ItemCompetitionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCompetitionBinding.inflate(inflater, parent, false)
            return ItemCompetitionViewHolder(binding)
        }
    }
}