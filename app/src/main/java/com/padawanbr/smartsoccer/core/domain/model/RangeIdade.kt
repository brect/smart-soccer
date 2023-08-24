package com.padawanbr.smartsoccer.core.domain.model

import com.padawanbr.smartsoccer.framework.db.entity.RangeIdadeEntity

class RangeIdade(
    val minAge: Int,
    val maxAge: Int
)

fun RangeIdade.toRangeIdadeEntity(): RangeIdadeEntity {
    return RangeIdadeEntity(
        minAge = minAge,
        maxAge =  minAge
    )
}