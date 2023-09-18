package com.blimas.smartsoccer.framework.db.entity

import com.blimas.smartsoccer.core.domain.model.RangeIdade

data class RangeIdadeEntity(
    val minAge: Int,
    val maxAge: Int
)

fun RangeIdadeEntity.toRangeIdadeModel(): RangeIdade {
    return RangeIdade(
        minAge = minAge,
        maxAge =  maxAge
    )
}