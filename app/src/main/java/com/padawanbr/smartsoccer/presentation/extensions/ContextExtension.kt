package com.padawanbr.smartsoccer.presentation.extensions

import android.content.Context
import android.util.TypedValue
import androidx.annotation.AttrRes

fun Context.getAttrColor(@AttrRes attrResId: Int): Int {
    val typedValue = TypedValue()
    val typedArray = this.obtainStyledAttributes(typedValue.data, intArrayOf(attrResId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}