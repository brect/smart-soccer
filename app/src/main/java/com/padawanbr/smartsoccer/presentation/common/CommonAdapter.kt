package com.padawanbr.smartsoccer.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter


inline fun <T: ListItem, VH: CommonViewHolder<T>> getCommonAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH,
    crossinline itemClicked: (T) -> Unit
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
        }
    }
}
