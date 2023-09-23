package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGroupsUseCase {
    suspend operator fun invoke(
        params: Unit = Unit
    ): Flow<List<Grupo>>
}

class GetGroupsUseCaseImpl @Inject constructor(
    private val groupRepository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers
) : FlowUseCase<Unit, List<Grupo>>(), GetGroupsUseCase {


    override suspend fun createFlowObservable(params: Unit): Flow<List<Grupo>> {
        return withContext(dispatchers.io()) {
            groupRepository.getAll()
        }
    }
}