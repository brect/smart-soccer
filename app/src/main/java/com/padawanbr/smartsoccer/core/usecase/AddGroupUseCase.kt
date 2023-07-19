package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.Sorteio
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddGroupUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: Int,
        val nome: String,
        val quantidadeMinimaJogadores: Int,
        val quantidadeMinimaJogadoresPorTime: Int,
        val quantidadeTimes: Int,
        val jogadores: MutableList<Jogador>,
        val sorteios: MutableList<Sorteio>
    )
}

class AddGroupUseCaseImpl @Inject constructor(
    private val respository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<AddGroupUseCase.Params, Unit>(), AddGroupUseCase {

    override suspend fun doWork(params: AddGroupUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            respository.saveGroup(
                Grupo(
                    params.id,
                    params.nome,
                    params.quantidadeMinimaJogadores,
                    params.quantidadeMinimaJogadoresPorTime,
                    params.quantidadeTimes,
                    params.jogadores,
                    params.sorteios
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}
