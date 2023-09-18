package com.blimas.smartsoccer.presentation.modelView

import androidx.annotation.DrawableRes
import com.blimas.smartsoccer.presentation.common.adapter.ListItem

data class GroupoJogadoresInfo(

    @DrawableRes
    val icon: Int,
    val text: String = "",

    override val key: String = icon.toString()
) : ListItem