package com.padawanbr.smartsoccer.presentation.ui.groups

import androidx.annotation.DrawableRes
import com.padawanbr.smartsoccer.presentation.common.ListItem

data class GroupoJogadoresInfo(

    @DrawableRes
    val icon: Int,
    val text: String = "",

    override val key: String = icon.toString()
) : ListItem