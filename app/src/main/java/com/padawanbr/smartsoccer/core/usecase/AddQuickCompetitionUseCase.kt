package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

interface AddQuickCompetitionUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,

    )
}