package com.padawanbr.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.padawanbr.smartsoccer.databinding.ItemCompetitionBinding
import com.padawanbr.smartsoccer.databinding.ItemHomeBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder
import com.padawanbr.smartsoccer.presentation.ui.groups.GrupoItem

class DatailCompetitionViewHolder(
    itemBinding: ItemCompetitionBinding,
) : CommonViewHolder<CompetitionItem>(itemBinding) {

    private val textNameCompetition: TextView = itemBinding.textViewNameCompetition
    private val textTypeCompetition: TextView = itemBinding.textViewTypeCompetition

    override fun bind(item: CompetitionItem) {
        textNameCompetition.text = item.nome
        textTypeCompetition.text = item.tipoTorneio?.descricao
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): DatailCompetitionViewHolder {
            val itemBinding = ItemCompetitionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return DatailCompetitionViewHolder(itemBinding)
        }
    }
}