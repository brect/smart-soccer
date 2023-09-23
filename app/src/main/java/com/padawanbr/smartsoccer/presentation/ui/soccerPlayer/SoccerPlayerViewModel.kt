package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.GetSoccerPlayersByGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.modelView.JogadorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class SoccerPlayerViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getSoccerPlayersByGroupUseCase: GetSoccerPlayersByGroupUseCase,
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

                else -> {}
            }
        }
    }

    fun getAllSoccerPlayers(grupoId: String) {
        action.value = Action.GetAllSoccerPlayers(grupoId)
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()

        data class ShowSoccers(val soccerPlayers: List<JogadorItem>) : UiState()
        object ShowEmptySoccers : UiState()
    }

    sealed class Action {
        data class GetAllSoccerPlayers(val grupoId: String) : Action()
    }

}
