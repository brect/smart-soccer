package com.padawanbr.smartsoccer.presentation.ui.competition

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.usecase.AddQuickCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val addQuickCompetitionUseCase: AddQuickCompetitionUseCase,
) : ViewModel() {


    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.CreateQuickCompetition -> {
                    addQuickCompetitionUseCase.invoke(
                        AddQuickCompetitionUseCase.Params(
                            it.groupId,
                            it.jogadores,
                            it.numeroTimes,
                            it.considerarPosicoes,
                            it.considerarOveralls,
                            it.considerarDepartamentoMedico,
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
            }
        }
    }

    fun createQuickCompetition(
        groupId: String,
        jogadores: MutableList<Jogador>,
        numeroTimes: Int,
        considerarPosicoes: Boolean,
        considerarOveralls: Boolean,
        considerarDepartamentoMedico: Boolean
    ) {
        action.value = Action.CreateQuickCompetition(
            groupId,
            jogadores,
            numeroTimes,
            considerarPosicoes,
            considerarOveralls,
            considerarDepartamentoMedico,
        )
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class CreateQuickCompetition(
            val groupId: String,
            val jogadores: MutableList<Jogador>,
            val numeroTimes: Int,
            val considerarPosicoes: Boolean,
            val considerarOveralls: Boolean,
            val considerarDepartamentoMedico: Boolean
        ) : Action()
    }

}