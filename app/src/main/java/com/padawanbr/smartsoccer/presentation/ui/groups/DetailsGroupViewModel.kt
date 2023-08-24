package com.padawanbr.smartsoccer.presentation.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class DetailsGroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val addGroupUseCase: AddGroupUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.CreateGroup -> {
                    addGroupUseCase.invoke(
                        AddGroupUseCase.Params(
                            UUID.randomUUID().toString(),
                            it.groupName,
                            it.quantidadeTimes,
                            it.tipoEsporte,
                            arrayListOf(),
                            arrayListOf()
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
                else -> {}
            }
        }
    }

    fun createGroup(
        groupName: String,
        textPlaceGroup: String,
        tipoEsporte: TipoEsporte,
        textGameDay: String,
        textBeginningOfTheGame: String,
        quantidadeTimes: Int,
        rangeIdade: RangeIdade
    ) {
        action.value = Action.CreateGroup(
            groupName,
            textPlaceGroup,
            tipoEsporte,
            textGameDay,
            textBeginningOfTheGame,
            quantidadeTimes,
            rangeIdade
        )
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class CreateGroup(
            val groupName: String,
            val textPlaceGroup: String,
            val tipoEsporte: TipoEsporte,
            val textGameDay: String,
            val textBeginningOfTheGame: String,
            val quantidadeTimes: Int,
            val rangeIdade: RangeIdade
        ) : Action()
    }

}
