package com.padawanbr.smartsoccer.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.ConfiguracaoEsporte
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.Sorteio
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.core.usecase.AddGroupUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGroupsUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val addGroupUseCase: AddGroupUseCase,
    private val getGroupsUseCase: GetGroupsUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.CreateGroup -> {
                    addGroupUseCase.invoke(
                        AddGroupUseCase.Params(
                            0,
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

    fun createGroup(
        groupName: String,
        quantidadeTimes: Int,
        tipoEsporte: TipoEsporte
    ) {
        action.value = Action.CreateGroup(
            groupName,
            quantidadeTimes,
            tipoEsporte
        )
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
        data class CreateGroup(
            val groupName: String,
            val quantidadeTimes: Int,
            val tipoEsporte: TipoEsporte
        ) : Action()

        object GetAll : Action()
    }

}
