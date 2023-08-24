package com.padawanbr.smartsoccer.presentation.ui.groups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import com.padawanbr.smartsoccer.presentation.ui.competition.CompetitionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getGrupoComJogadoresById: GetGrupoComJogadoresETorneiosUseCase,
//    private val getGrupoComJogadoresById: GetGrupoComJogadoresByIdUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetGroupById -> {
                    getGrupoComJogadoresById.invoke(
                        GetGrupoComJogadoresETorneiosUseCase.Params(
                            it.groupId
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            val grupo = it?.grupo
                            val jogadores = it?.jogadores?.toMutableList()

                            val torneios = it?.torneios?.toMutableList()?.map { torneio ->
                                CompetitionItem(
                                    id = torneio.id,
                                    nome = torneio.nome,
                                    tipoTorneio = torneio.tipoTorneio,
                                    criteriosDesempate = torneio.criteriosDesempate ?: arrayListOf(),
                                    times = torneio.times,
                                    partidas = torneio.partidas,
                                    grupoId = torneio.grupoId

                                )
                            }

                            var grupoItem = GrupoComJogadoresItem()

                            if (grupo != null && jogadores != null) {
                                grupoItem = GrupoComJogadoresItem(
                                    grupo.id,
                                    grupo.nome,
                                    grupo.endereco,
                                    grupo.configuracaoEsporte,
                                    grupo.diaDoJogo,
                                    grupo.horarioInicio,
                                    grupo.quantidadeTimes,
                                    grupo.rangeIdade,
                                    jogadores,
                                    it.jogadoresDisponiveis,
                                    it.jogadoresNoDM,
                                    it.mediaHabilidades,
                                    torneios
                                )
                            }

                            val uiState = if (it == null) {
                                UiState.Error
                            } else UiState.Success(grupoItem)

                            emit(uiState)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }

                else -> {
                    Log.i("DetailsGroupViewModel", "DetailsGroupViewModel: else")
                }
            }
        }
    }


    fun getGroupById(groupId: String) {
        action.value = Action.GetGroupById(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val grupo: GrupoComJogadoresItem) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetGroupById(
            val groupId: String,
        ) : Action()
    }

}
