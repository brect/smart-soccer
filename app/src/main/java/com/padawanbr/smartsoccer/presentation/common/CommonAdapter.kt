package com.example.marvelapp.presentation.common

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.padawanbr.smartsoccer.presentation.common.CommonDiffCallback
import com.padawanbr.smartsoccer.presentation.common.CommonViewHolder
import com.padawanbr.smartsoccer.presentation.common.ListItem


inline fun <T: ListItem, VH: CommonViewHolder<T>> getCommonAdapterOf(
    crossinline createViewHolder: (ViewGroup) -> VH
): ListAdapter<T, VH> {
    val diffCallback = CommonDiffCallback<T>()

    return object : ListAdapter<T, VH>(diffCallback){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
            return createViewHolder(parent)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            holder.bind(getItem(position))
        }
    }
}