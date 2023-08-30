package com.padawanbr.smartsoccer.core.domain.model

import com.padawanbr.smartsoccer.framework.db.entity.RangeIdadeEntity

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