package com.blimas.smartsoccer.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.blimas.smartsoccer.databinding.ItemHomeBinding
import com.blimas.smartsoccer.presentation.modelView.GrupoItem
import com.blimas.smartsoccer.presentation.common.adapter.CommonViewHolder

class HomeItemViewHolder(
    itemBinding: ItemHomeBinding,
) : CommonViewHolder<GrupoItem>(itemBinding) {

    private val textName: TextView = itemBinding.textViewGroupTitle
    private val textType: TextView = itemBinding.textViewGroupType

    override fun bind(data: GrupoItem) {
        textName.text = data.nome
        textType.text = data.configuracaoEsporte.tipoEsporte.modalidade
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): HomeItemViewHolder {
            val itemBinding = ItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return HomeItemViewHolder(itemBinding)
        }
    }
}