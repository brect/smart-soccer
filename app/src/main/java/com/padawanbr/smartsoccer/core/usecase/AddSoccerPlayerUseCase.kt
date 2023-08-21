package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.SoccerPlayerRepository
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


interface AddSoccerPlayerUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
        val playerName: String,
        val playerAge: Int,
        val playerPosition: PosicaoJogador?,
        val playerAbilitiesMap: Map<String, Float>,
        val playerIsInDM: Boolean,
        val groupId: String,
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
                    id = params.id,
                    nome = params.playerName,
                    idade = params.playerAge,
                    posicao = params.playerPosition,
                    habilidades = params.playerAbilitiesMap,
                    estaNoDepartamentoMedico = params.playerIsInDM,
                    grupoId =  params.groupId
                ),
            )
            ResultStatus.Success(Unit)
        }
    }
}

