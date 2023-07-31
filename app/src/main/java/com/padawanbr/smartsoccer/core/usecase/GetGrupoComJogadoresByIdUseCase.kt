package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase.Params
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.framework.db.entity.GrupoComJogadores
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGrupoComJogadoresByIdUseCase {
    suspend operator fun invoke(params: Params): Flow<GrupoComJogadores?>

    data class Params(
        val groupId: Int?,
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
