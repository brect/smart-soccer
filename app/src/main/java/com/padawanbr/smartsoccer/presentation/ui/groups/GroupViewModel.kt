package com.padawanbr.smartsoccer.presentation.ui.groups

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers,
    private val getGrupoComJogadoresById: GetGrupoComJogadoresByIdUseCase,
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap {
        liveData(coroutinesDispatchers.main()) {
            when (it) {
                is Action.GetGroupById -> {
                    getGrupoComJogadoresById.invoke(
                        GetGrupoComJogadoresByIdUseCase.Params(
                            it.groupId
                        )
                    ).catch {
                        emit(UiState.Error)
                    }.collect {
                        val grupo = it?.grupo
                        val jogadores = it?.jogadores?.toMutableList()

                        var grupoItem = GrupoItem()

                        if (grupo != null && jogadores != null){
                            grupoItem =  GrupoItem(
                                grupo.id,
                                grupo.nome,
                                grupo.quantidadeTimes,
                                grupo.configuracaoEsporte,
                                jogadores,
                                it.jogadoresDisponiveis,
                                it.jogadoresNoDM,
                                it.mediaJogadores
                            )
                        }

                        val uiState = if (it == null) {
                            UiState.Error
                        } else UiState.Success(grupoItem)

                        emit(uiState)
                    }
                }

                else -> {
                    Log.i("DetailsGroupViewModel", "DetailsGroupViewModel: else")
                }
            }
        }
    }


    fun getGroupById(groupId: String?) {
        action.value = Action.GetGroupById(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val grupo: GrupoItem) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetGroupById(
            val groupId: String?,
        ) : Action()
    }

}
