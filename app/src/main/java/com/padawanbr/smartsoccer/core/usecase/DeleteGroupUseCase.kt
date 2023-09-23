package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DeleteGroupUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
    )
}

class DeleteGroupUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<DeleteGroupUseCase.Params, Unit>(), DeleteGroupUseCase {


    override suspend fun doWork(params: DeleteGroupUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            groupRepository.deleteGroup(params.id)
            ResultStatus.Success(Unit)
        }
    }
}