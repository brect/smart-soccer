package com.blimas.smartsoccer.core.usecase

import com.blimas.smartsoccer.core.data.repository.TorneioRepository
import com.blimas.smartsoccer.core.domain.model.Jogador
import com.blimas.smartsoccer.core.domain.model.TipoTorneio
import com.blimas.smartsoccer.core.domain.model.Torneio
import com.blimas.smartsoccer.core.domain.model.sortearTimes
import com.blimas.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.blimas.smartsoccer.core.usecase.base.ResultStatus
import com.blimas.smartsoccer.core.usecase.base.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AddQuickCompetitionUseCase {

    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>

    data class Params(
        val id: String,
        val jogadores: MutableList<Jogador>,
        val numeroTimes: Int,
        val considerarPosicoes: Boolean,
        val considerarOveralls: Boolean,
        val considerarDepartamentoMedico: Boolean,
        val groupId: String,
    )
}


class AddQuickCompetitionUseCaseImpl @Inject constructor(
    private val repository: TorneioRepository,
    private val dispatchers: CoroutinesDispatchers,
) : UseCase<AddQuickCompetitionUseCase.Params, Unit>(), AddQuickCompetitionUseCase {

    override suspend fun doWork(params: AddQuickCompetitionUseCase.Params): ResultStatus<Unit> {

        val timesSorteados = sortearTimes(
            jogadores = params.jogadores,
            numeroTimes = params.numeroTimes,
            considerarPosicoes = params.considerarPosicoes,
            considerarOveralls = params.considerarOveralls,
            considerarDepartamentoMedico = params.considerarDepartamentoMedico
        )

        return withContext(dispatchers.io()) {
            repository.insertTorneioWithTimesAndPartidas(
                Torneio(
                    params.id,
                    "Competição Rápida",
                    TipoTorneio.JOGO_UNICO,
                    listOf(),
                    timesSorteados,
                    listOf(),
                    params.groupId
                )
            )
            ResultStatus.Success(Unit)
        }
    }
}
