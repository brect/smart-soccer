package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import com.padawanbr.smartsoccer.presentation.modelView.JogadorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SoccerPlayerViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getSoccerPlayersByGroupUseCase: GetSoccerPlayersByGroupUseCase,
    private val deleteSoccerPlayersByGroupUseCase: DeleteSoccerPlayersByGroupUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetAllSoccerPlayers -> {
                    getSoccerPlayersByGroupUseCase.invoke(it.grupoId)
                        .catch {
                            it.cause
                            it.message
                            emit(UiState.ShowEmptySoccers)
                        }
                        .collect {
                            val items = it.map { soccers ->
                                JogadorItem(
                                    soccers.id,
                                    soccers.nome,
                                    soccers.idade,
                                    "${soccers.posicao?.funcao} (${soccers.posicao?.abreviacao})",
                                    soccers.habilidades,
                                    soccers.estaNoDepartamentoMedico
                                )
                            }

                            val uiState = if (items.isEmpty()) {
                                UiState.ShowEmptySoccers
                            } else UiState.ShowSoccers(items)

                            emit(uiState)
                        }
                }

                is Action.DeleteAllSoccerPlayers -> {
                    deleteSoccerPlayersByGroupUseCase.invoke(
                        DeleteSoccerPlayersByGroupUseCase.Params(
                            it.grupoId
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.SuccessClearPlayers)
                        },
                        error = {
                            emit(UiState.Error("Erro ao excluir jogadores"))
                        }
                    )
                }

                else -> {}
            }
        }
    }

    fun getAllSoccerPlayers(grupoId: String) {
        action.value = Action.GetAllSoccerPlayers(grupoId)
    }

    fun deleteSoccerPlayers(groupId: String) {
        action.value = Action.DeleteAllSoccerPlayers(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()

        data class Error(val message: String = "Erro ao processar solicitaçao") : UiState()
        data class ShowSoccers(val soccerPlayers: List<JogadorItem>) : UiState()
        object SuccessClearPlayers : UiState()
        object ShowEmptySoccers : UiState()
    }

    sealed class Action {
        data class GetAllSoccerPlayers(val grupoId: String) : Action()
        data class DeleteAllSoccerPlayers(val grupoId: String) : Action()
    }

}
