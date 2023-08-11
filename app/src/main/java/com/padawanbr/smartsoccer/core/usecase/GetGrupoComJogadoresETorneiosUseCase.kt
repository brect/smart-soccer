package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadores
import com.padawanbr.smartsoccer.core.domain.model.GrupoComJogadoresETorneios
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase.Params
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGrupoComJogadoresETorneiosUseCase {
    suspend operator fun invoke(params: Params): Flow<GrupoComJogadoresETorneios?>

    data class Params(
        val groupId: String?,
    )
}

class GetGrupoComJogadoresETorneiosUseCaseImpl @Inject constructor(
    private val repository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers,
) : GetGrupoComJogadoresETorneiosUseCase {

    override suspend fun invoke(params: Params): Flow<GrupoComJogadoresETorneios?> {
        return withContext(dispatchers.io()) {
            repository.getGrupoComJogadoresETorneiosById(params.groupId)
        }
    }
}
