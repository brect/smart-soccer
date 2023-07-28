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
    private val textType: TextView = itemBinding.textViewGroupType

    override fun bind(data: GrupoItem) {
        textName.text = data.nome
        textType.text = data.configuracaoEsporte.tipoEsporte.tipo
    }

    companion object {
        fun create(parent: ViewGroup): GroupsViewHolder {
            val itemBinding = ItemGroupBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupsViewHolder(itemBinding, parent.context)
        }
    }
}