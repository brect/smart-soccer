package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.Grupo
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddGroupUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
        val nome: String,
        val endereco: String,
        val tipoEsporte: TipoEsporte,
        val diaDoJogo: String,
        val horarioInicio: String,
        val quantidadeTimes: Int,
        val rangeIdade: RangeIdade
    )
}

class AddGroupUseCaseImpl @Inject constructor(
    private val repository: GroupRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<AddGroupUseCase.Params, Unit>(), AddGroupUseCase {

    override suspend fun doWork(params: AddGroupUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveGroup(
                Grupo(
                    params.id,
                    params.nome,
                    params.endereco,
                    ConfiguracaoEsporte(params.tipoEsporte, params.tipoEsporte.quantidadeMinimaPorTime),
                    params.diaDoJogo,
                    params.horarioInicio,
                    params.quantidadeTimes,
                    params.rangeIdade
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}

