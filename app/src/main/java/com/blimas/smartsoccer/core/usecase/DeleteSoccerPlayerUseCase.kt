package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.blimas.smartsoccer.core.usecase.base.ResultStatus
import com.blimas.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteSoccerPlayerUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(
        val soccerPlayerId: String,
    )
}

class DeleteSoccerPlayerUseCaseImpl @Inject constructor(
    private val repository: SoccerPlayerRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<DeleteSoccerPlayerUseCase.Params, Unit>(), DeleteSoccerPlayerUseCase {

    override suspend fun doWork(params: DeleteSoccerPlayerUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.deleteSoccerPlayer(params.soccerPlayerId)
            ResultStatus.Success(Unit)
        }
    }

}