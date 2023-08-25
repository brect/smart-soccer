package com.padawanbr.smartsoccer.presentation.extensions

import java.math.BigDecimal
import java.math.RoundingMode

fun Float.roundToTwoDecimalPlaces(): Float {
    return BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toFloat()
}