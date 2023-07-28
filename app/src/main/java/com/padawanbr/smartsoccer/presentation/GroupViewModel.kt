package com.padawanbr.smartsoccer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getGroupsUseCase: GetGroupsUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetAll -> {
                    getGroupsUseCase.invoke()
                        .catch {
                            emit(UiState.ShowEmptyGroups)
                        }
                        .collect {
                            val items = it.map { groups ->
                                GrupoItem(
                                    groups.id,
                                    groups.nome,
                                    groups.quantidadeTimes,
                                    groups.configuracaoEsporte,
                                    arrayListOf(),
                                    arrayListOf()
                                )
                            }

                            val uiState = if (items.isEmpty()) {
                                UiState.ShowEmptyGroups
                            } else UiState.ShowGroups(items)

                            emit(uiState)
                        }
                }
            }
        }
    }

    fun getAll() {
        action.value = Action.GetAll
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()

        data class ShowGroups(val groups: List<GrupoItem>) : UiState()
        object ShowEmptyGroups : UiState()
    }

    sealed class Action {
        object GetAll : Action()
    }

}
