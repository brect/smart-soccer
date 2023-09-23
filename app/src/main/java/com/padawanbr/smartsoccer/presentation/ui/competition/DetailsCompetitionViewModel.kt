package com.padawanbr.smartsoccer.presentation.ui.competition

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.core.usecase.GetCompetitionUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailsCompetitionViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getCompetitionUseCase: GetCompetitionUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetCompetition -> {
                    getCompetitionUseCase.invoke(
                        GetCompetitionUseCase.Params(
                            it.competitionId
                        )
                    ).watchStatus(
                        loading = {
                            emit(UiState.Loading)
                        },
                        success = {

                            val uiState = if (it == null) {
                                UiState.Error
                            } else UiState.Success(it)

                            emit(uiState)

                        },
                        error = {
                            emit(UiState.Error)
                        }
                    )
                }

                else -> {
                    Log.i("DetailsCompetitionViewModel", "DetailsCompetitionViewModel: else")
                }
            }
        }
    }

    fun getCompetition(
        competitionId: String,
    ) {
        action.value = Action.GetCompetition(
            competitionId,
        )
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val torneio: Torneio) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetCompetition(
            val competitionId: String,
        ) : Action()
    }

}
