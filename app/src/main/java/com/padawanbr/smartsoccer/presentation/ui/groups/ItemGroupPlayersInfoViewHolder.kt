package com.padawanbr.smartsoccer.presentation.ui.groups

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.padawanbr.smartsoccer.databinding.ItemSoccerPlayersInfoBinding
import com.padawanbr.smartsoccer.presentation.common.adapter.CommonViewHolder
import com.padawanbr.smartsoccer.presentation.modelView.GroupoJogadoresInfo

class ItemGroupPlayersInfoViewHolder(
    itemBinding: ItemSoccerPlayersInfoBinding,
) : CommonViewHolder<GroupoJogadoresInfo>(itemBinding) {

    private val soccerPlayerGroupInfo: TextView = itemBinding.textViewSoccerPlayerGroupInfo
    private val icSoccerPlayerGroupInfo: ImageView = itemBinding.imageViewSoccerPlayerGroupInfo

    override fun bind(item: GroupoJogadoresInfo) {
        soccerPlayerGroupInfo.text = item.text
        icSoccerPlayerGroupInfo.setImageResource(item.icon)
    }


    companion object {
        fun create(
            parent: ViewGroup
        ): ItemGroupPlayersInfoViewHolder {
            val itemBinding = ItemSoccerPlayersInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ItemGroupPlayersInfoViewHolder(itemBinding)
        }
    }
}