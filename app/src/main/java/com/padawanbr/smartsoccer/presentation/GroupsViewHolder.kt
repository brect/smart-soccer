package com.padawanbr.smartsoccer.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.ItemGroupBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder

class GroupsViewHolder(
    itemBinding: ItemGroupBinding,
    private val mContext: Context
): CommonViewHolder<GrupoItem>(itemBinding){

    private val textName: TextView = itemBinding.textViewGroupTitle
    private val textQtdMinPlayers: TextView = itemBinding.textViewMinPlayers
    private val textQtdNumberPlayersPerTeam: TextView = itemBinding.textViewNumberPlayersPerTeam
    private val textQtdTeam: TextView = itemBinding.textViewQtdTeam

    override fun bind(data: GrupoItem) {
        textName.text = data.nome
//        textQtdMinPlayers.text = mContext.getString(R.string.minimo_por_time, data.quantidadeMinimaJogadores)
//        textQtdNumberPlayersPerTeam.text = mContext.getString(R.string.minimo_por_time, data.quantidadeMinimaJogadoresPorTime.toString())
        textQtdTeam.text = mContext.getString(R.string.quantidade_de_times, data.quantidadeTimes.toString())
    }

    companion object {
        fun create(parent: ViewGroup): GroupsViewHolder {
            val itemBinding = ItemGroupBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupsViewHolder(itemBinding, parent.context)
        }
    }
}