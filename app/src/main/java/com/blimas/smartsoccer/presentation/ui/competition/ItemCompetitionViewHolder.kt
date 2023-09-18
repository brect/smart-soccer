package com.blimas.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blimas.smartsoccer.databinding.ItemCompetitionBinding
import com.blimas.smartsoccer.presentation.common.adapter.CommonViewHolder
import com.blimas.smartsoccer.presentation.modelView.CompetitionItem

class ItemCompetitionViewHolder(
    itemBinding: ItemCompetitionBinding,
) : CommonViewHolder<CompetitionItem>(itemBinding) {

    private val textNameCompetition: TextView = itemBinding.textViewNameCompetition
    private val textTypeCompetition: TextView = itemBinding.textViewTypeCompetition
    private val textViewQtdTeamsCompetition: TextView = itemBinding.textViewQtdTeamsCompetition
    private val textViewQtdPlayersCompetition: TextView = itemBinding.textViewQtdPlayersCompetition

    override fun bind(item: CompetitionItem) {
        textNameCompetition.text = item.nome
        textTypeCompetition.text = item.tipoTorneio?.descricao

        textViewQtdTeamsCompetition.visibility = View.INVISIBLE
        textViewQtdPlayersCompetition.visibility = View.GONE
    }

    companion object {
        fun create(parent: ViewGroup): ItemCompetitionViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemCompetitionBinding.inflate(inflater, parent, false)
            return ItemCompetitionViewHolder(binding)
        }
    }
}