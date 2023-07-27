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
        val groupId: Int,
        val playerName: String,
        val playerAge: Int,
        val playerAbilitiesMap: Map<String, Float>,
        val playerIsInDM: Boolean
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
                    nome = params.playerName,
                    idade = params.playerAge,
                    posicoes = emptyList(),
                    habilidades = params.playerAbilitiesMap,
                    estaNoDepartamentoMedico = params.playerIsInDM
                ),
                params.groupId,
            )
            ResultStatus.Success(Unit)
        }
    }
}

