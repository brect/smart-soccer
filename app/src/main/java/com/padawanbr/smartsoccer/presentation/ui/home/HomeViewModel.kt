package com.padawanbr.smartsoccer.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.DeleteGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.modelView.GrupoItem
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getGroupsUseCase: GetGroupsUseCase,
    private val deleteGroupUseCase: DeleteGroupUseCase
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
                            val items = it.map { grupo ->
                                GrupoItem(
                                    grupo.id,
                                    grupo.nome,
                                    grupo.endereco,
                                    grupo.configuracaoEsporte,
                                    grupo.diaDoJogo,
                                    grupo.horarioInicio,
                                    grupo.quantidadeTimes,
                                    grupo.rangeIdade,
                                )
                            }

                            val uiState = if (items.isEmpty()) {
                                UiState.ShowEmptyGroups
                            } else UiState.ShowGroups(items)

                            emit(uiState)
                        }
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

    fun getAll() {
        action.value = Action.GetAll
    }

    fun deleteGroup(groupId: String) {
        action.value = Action.DeleteGroup(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        object Error : UiState()
        data class ShowGroups(val groups: List<GrupoItem>) : UiState()
        object ShowEmptyGroups : UiState()
    }

    sealed class Action {
        data class DeleteGroup(val groupId: String) : Action()
        object GetAll : Action()
    }

}
