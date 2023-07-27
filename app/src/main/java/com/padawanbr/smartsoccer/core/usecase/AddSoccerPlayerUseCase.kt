package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface AddSoccerPlayerUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val grupoId: Int,
        val nome: String,
        val idade: Int,
        val dm: Boolean
    )
}

class AddSoccerPlayerUseCaseImpl @Inject constructor(
    private val repository: SoccerPlayerRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<AddSoccerPlayerUseCase.Params, Unit>(), AddSoccerPlayerUseCase {

    override suspend fun doWork(params: AddSoccerPlayerUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.saveSoccerPlayer(
                Jogador(
                    nome = params.nome,
                    idade = params.idade,
                    posicoes = emptyList(),
                    classificacoes = emptyMap(),
                    estaNoDepartamentoMedico = params.dm
                ),
                params.grupoId,
            )
            ResultStatus.Success(Unit)
        }
    }
}

