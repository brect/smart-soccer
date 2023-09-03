package com.padawanbr.smartsoccer.presentation.ui.groups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.DeleteCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresETorneiosUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import com.padawanbr.smartsoccer.presentation.modelView.GrupoComJogadoresItem
import com.padawanbr.smartsoccer.presentation.modelView.CompetitionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getGrupoComJogadoresById: GetGrupoComJogadoresETorneiosUseCase,
    private val deleteCompetitionUseCase: DeleteCompetitionUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
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
                                    criteriosDesempate = torneio.criteriosDesempate
                                        ?: arrayListOf(),
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

                is Action.DeleteCompetitionById -> {
                    deleteCompetitionUseCase.invoke(
                        DeleteCompetitionUseCase.Params(
                            it.idTorneio
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.SuccessDeleteCompetition)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }

                is Action.DeleteGroup -> {
                    deleteGroupUseCase.invoke(
                        DeleteGroupUseCase.Params(
                            it.groupId
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.DeleteSuccess)
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

    fun deleteCompetition(idTorneio: String) {
        action.value = Action.DeleteCompetitionById(idTorneio)
    }

    fun deleteGroup(groupId: String) {
        action.value = Action.DeleteGroup(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val grupo: GrupoComJogadoresItem) : UiState()
        object SuccessDeleteCompetition : UiState()
        object Error : UiState()
        object DeleteSuccess : UiState()
    }

    sealed class Action {
        data class GetGroupById(
            val groupId: String,
        ) : Action()
        data class DeleteGroup(val groupId: String) : Action()
        data class DeleteCompetitionById(
            val idTorneio: String
        ) : Action()
    }

}
