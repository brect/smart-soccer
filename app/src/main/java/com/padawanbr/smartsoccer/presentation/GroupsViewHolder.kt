package com.padawanbr.smartsoccer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.padawanbr.smartsoccer.databinding.ItemGroupBinding
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder

class GroupsViewHolder(
    itemBinding: ItemGroupBinding,
): CommonViewHolder<GrupoItem>(itemBinding){

    private val textName: TextView = itemBinding.textViewGroupTitle

    override fun bind(data: GrupoItem) {
        textName.text = data.nome
    }

    companion object {
        fun create(parent: ViewGroup): GroupsViewHolder {
            val itemBinding = ItemGroupBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            return GroupsViewHolder(itemBinding)
        }
    }
}