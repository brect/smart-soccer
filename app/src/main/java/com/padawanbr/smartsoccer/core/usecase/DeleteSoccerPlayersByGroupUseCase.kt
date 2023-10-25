package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteSoccerPlayersByGroupUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(
        val groupId: String,
    )
}

class DeleteSoccerPlayersByGroupUseCaseImpl @Inject constructor(
    private val repository: SoccerPlayerRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<DeleteSoccerPlayersByGroupUseCase.Params, Unit>(), DeleteSoccerPlayersByGroupUseCase {

    override suspend fun doWork(params: DeleteSoccerPlayersByGroupUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.deleteSoccerPlayersByGroup(params.groupId)
            ResultStatus.Success(Unit)
        }
    }

}