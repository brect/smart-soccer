package com.padawanbr.smartsoccer.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsSoccerPlayerViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val addSoccerPlayerUseCase: AddSoccerPlayerUseCase,
    private val deleteSoccerPlayerUseCase: DeleteSoccerPlayerUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.CreateSoccerPlayer -> {
                    addSoccerPlayerUseCase.invoke(
                        AddSoccerPlayerUseCase.Params(
                            it.groupId,
                            it.playerName,
                            it.playerAge,
                            it.playerPosition,
                            it.playerAbilitiesMap,
                            it.playerIsInDM
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.Success)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }

                is Action.ExcludeSoccerPlayer -> {
                    deleteSoccerPlayerUseCase.invoke(
                        DeleteSoccerPlayerUseCase.Params(
                            it.playerId
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.Delete)
                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }
                else -> {
                    Log.i("DetailsSoccerPlayerViewModel", "DetailsSoccerPlayerViewModel: else")
                }
            }
        }
    }

    fun createSoccer(
        groupId: Int,
        playerName: String,
        playerAge: Int,
        playerPosition: PosicaoJogador?,
        playerAbilitiesMap: Map<String, Float>,
        playerIsInDM: Boolean
    ) {
        action.value = Action.CreateSoccerPlayer(
            groupId,
            playerName,
            playerAge,
            playerPosition,
            playerAbilitiesMap,
            playerIsInDM
        )
    }

    fun deletePlayer(playerId: Int) {
        action.value = Action.ExcludeSoccerPlayer(playerId)
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
        object Delete : UiState()
    }

    sealed class Action {
        data class CreateSoccerPlayer(
            val groupId: Int,
            val playerName: String,
            val playerAge: Int,
            val playerPosition: PosicaoJogador?,
            val playerAbilitiesMap: Map<String, Float>,
            val playerIsInDM: Boolean
        ) : Action()

        data class ExcludeSoccerPlayer(val playerId: Int) : Action()
    }

}
