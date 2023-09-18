package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.GroupRepository
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadores
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase.Params
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGrupoComJogadoresByIdUseCase {
    suspend operator fun invoke(params: Params): Flow<GrupoComJogadores?>

    data class Params(
        val groupId: String?,
    )
}

class GetGrupoComJogadoresByIdUseCaseImpl @Inject constructor(
    private val repository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers,
) : GetGrupoComJogadoresByIdUseCase {

    override suspend fun invoke(params: Params): Flow<GrupoComJogadores?> {
        return withContext(dispatchers.io()) {
            repository.getGrupoComJogadoresById(params.groupId)
        }
    }
}
