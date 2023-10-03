package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.usecase.AddSoccersPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ImportSoccerPlayersViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val addSoccerPlayersUseCase: AddSoccersPlayerUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.CreateSoccerPlayers -> {
                   val id =  it.id.takeIf { !it.isNullOrEmpty() } ?: UUID.randomUUID().toString()
                    addSoccerPlayersUseCase.invoke(
                        AddSoccersPlayerUseCase.Params(
                            id,
                            it.playerName,
                            it.playerAge,
                            it.playerPosition,
                            it.playerAbilitiesMap,
                            it.playerIsInDM,
                            it.groupId,
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {
                            emit(UiState.Success)
                        },
                        error = {
                            emit(UiState.Error("Erro ao adicionar jogador"))
                        }
                    )
                }

                else -> {
                    Log.i("DetailsSoccerPlayerViewModel", "DetailsSoccerPlayerViewModel: else")
                }
            }
        }
    }

    fun saveSoccerPlayers(
        soccerId: String?,
        playerName: String,
        playerAge: Int,
        playerPosition: PosicaoJogador?,
        playerAbilitiesMap: Map<String, Float>,
        playerIsInDM: Boolean,
        groupId: String,
    ) {
        action.value = Action.CreateSoccerPlayers(
            soccerId,
            playerName,
            playerAge,
            playerPosition,
            playerAbilitiesMap,
            playerIsInDM,
            groupId,
        )
    }


    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }

    sealed class Action {
        data class CreateSoccerPlayers(
            val id: String?,
            val playerName: String,
            val playerAge: Int,
            val playerPosition: PosicaoJogador?,
            val playerAbilitiesMap: Map<String, Float>,
            val playerIsInDM: Boolean,
            val groupId: String,
        ) : Action()

    }

}
