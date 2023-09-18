package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.GroupRepository
import com.blimas.smartsoccer.core.domain.model.GrupoComJogadoresETorneios
import com.blimas.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase.Params
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.blimas.smartsoccer.core.usecase.base.ResultStatus
import com.blimas.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetGrupoComJogadoresETorneiosUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<GrupoComJogadoresETorneios>>

    data class Params(
        val groupId: String,
    )
}

class GetGrupoComJogadoresETorneiosUseCaseImpl @Inject constructor(
    private val repository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers
): UseCase<Params, GrupoComJogadoresETorneios>(), GetGrupoComJogadoresETorneiosUseCase {

    override suspend fun doWork(params: Params): ResultStatus<GrupoComJogadoresETorneios> {
        return withContext(dispatchers.io()) {
            val grupoComJogadoresETorneios = repository.getGrupoComJogadoresETorneiosById(params.groupId)
            ResultStatus.Success(grupoComJogadoresETorneios)
        }
    }
}