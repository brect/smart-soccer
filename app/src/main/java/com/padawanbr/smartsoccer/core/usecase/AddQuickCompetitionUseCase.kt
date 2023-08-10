package com.padawanbr.smartsoccer.core.usecase

import com.padawanbr.smartsoccer.core.data.repository.GroupRepository
import com.padawanbr.smartsoccer.core.data.repository.TorneioRepository
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.TipoTorneio
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.core.domain.model.calcularMediaHabilidades
import com.padawanbr.smartsoccer.core.domain.model.sortearTimes
import com.padawanbr.smartsoccer.core.usecase.base.CoroutinesDispatchers
import com.padawanbr.smartsoccer.core.usecase.base.ResultStatus
import com.padawanbr.smartsoccer.core.usecase.base.UseCase
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

//        println("----- Times Sorteados -----")
//        timesSorteados.forEachIndexed { index, time ->
//            println("Time ${index + 1}")
//            println("Nome: ${time.nome}")
//            println("Media time: ${time.mediaHabilidades}")
//            println("Jogadores:")
//            time.jogadores.forEach { jogador ->
//                println(" - ${jogador.nome} (Media Habilidades: ${jogador.calcularMediaHabilidades()}) (Posição: ${jogador.posicao}) (DM: ${jogador.estaNoDepartamentoMedico})")
//            }
//            println("--------------------------")
//        }
//
        return withContext(dispatchers.io()) {
            repository.insertTorneioWithTimesAndPartidas(
                Torneio(
                    params.id,
                    "QuickCompetition",
                    TipoTorneio.ELIMINATORIAS,
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
