package com.padawanbr.smartsoccer.presentation.common

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class CommonViewHolder<T> (
    itemBinding: ViewBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    abstract fun bind(data: T)

}