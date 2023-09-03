package com.padawanbr.smartsoccer.presentation.common.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


inline fun <T: ListItem, VH: CommonViewHolder<T>> getCommonAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH,
    crossinline itemClicked: (T) -> Unit,
    crossinline itemLongClicked: (T) -> Unit
): ListAdapter<T, VH> {
    val diffCallback = CommonDiffCallback<T>()

    return object : ListAdapter<T, VH>(diffCallback){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = getItem(position)
            holder.bind(item)
            holder.itemView.setOnClickListener { itemClicked(item) }
            holder.itemView.setOnLongClickListener {
                itemLongClicked(item)
                true // Retorne "true" para indicar que o clique longo foi tratado
            }
        }
    }
}
