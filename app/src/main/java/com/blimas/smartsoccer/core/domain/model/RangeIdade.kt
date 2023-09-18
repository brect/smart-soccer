package com.blimas.smartsoccer.core.domain.model

import com.blimas.smartsoccer.framework.db.entity.RangeIdadeEntity

class RangeIdade(
    var minAge: Int,
    var maxAge: Int
)

fun RangeIdade.toRangeIdadeEntity(): RangeIdadeEntity {
    return RangeIdadeEntity(
        minAge = minAge,
        maxAge =  maxAge
    )
}