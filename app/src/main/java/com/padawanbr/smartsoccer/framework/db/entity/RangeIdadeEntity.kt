package com.padawanbr.smartsoccer.framework.db.entity

import com.padawanbr.smartsoccer.core.domain.model.RangeIdade

data class RangeIdadeEntity(
    val minAge: Int,
    val maxAge: Int
)

fun RangeIdadeEntity.toRangeIdadeModel(): RangeIdade {
    return RangeIdade(
        minAge = minAge,
        maxAge =  minAge
    )
}