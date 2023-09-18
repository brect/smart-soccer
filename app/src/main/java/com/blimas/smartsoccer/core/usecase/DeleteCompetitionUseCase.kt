package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.TorneioRepository
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.blimas.smartsoccer.core.usecase.base.ResultStatus
import com.blimas.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteCompetitionUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(
        val competitionId: String,
    )
}

class DeleteCompetitionUseCaseImpl @Inject constructor(
    private val repository: TorneioRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<DeleteCompetitionUseCase.Params, Unit>(), DeleteCompetitionUseCase {

    override suspend fun doWork(params: DeleteCompetitionUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.deleteTournament(params.competitionId)
            ResultStatus.Success(Unit)
        }
    }

}