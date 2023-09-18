package com.blimas.smartsoccer.presentation.common.adapter

interface ListItem {

    val key: String

    fun areItemsTheSame(other: ListItem) = this.key == other.key
    fun areContentsTheSame(other: ListItem) = this == other

}