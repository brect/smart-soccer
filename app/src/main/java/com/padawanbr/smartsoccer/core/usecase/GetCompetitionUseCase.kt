package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.TorneioRepository
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetCompetitionUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Torneio>>

    data class Params(
        val competitionId: String,
    )
}

class GetCompetitionUseCaseImpl @Inject constructor(
    private val repository: TorneioRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<GetCompetitionUseCase.Params, Torneio>(), GetCompetitionUseCase {

    override suspend fun doWork(params: GetCompetitionUseCase.Params): ResultStatus<Torneio> {
        return withContext(dispatchers.io()) {
            val tournament = repository.getTournamentById(params.competitionId)
            ResultStatus.Success(tournament)
        }
    }
}
