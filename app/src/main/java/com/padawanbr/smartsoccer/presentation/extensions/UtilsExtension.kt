package com.padawanbr.smartsoccer.presentation.extensions

import java.math.BigDecimal
import java.math.RoundingMode


fun Float?.roundToTwoDecimalPlaces(): Float {
    if (this == null || this.isNaN()) {
        return 0.00f
    }

    return BigDecimal(this.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toFloat()
}
