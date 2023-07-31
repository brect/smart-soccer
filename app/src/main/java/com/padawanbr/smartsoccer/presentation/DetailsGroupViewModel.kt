package com.padawanbr.smartsoccer.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.usecase.AddSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.DeleteSoccerPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.GetGrupoComJogadoresByIdUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.framework.db.entity.JogadorEntity
import com.padawanbr.smartsoccer.framework.db.entity.toConfiguracaoEsporteModel
import com.padawanbr.smartsoccer.framework.db.entity.toSoccerPlayerModel
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DetailsGroupViewModel @Inject constructor(
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
                    }
                        .collect {
                            var grupoComJogadores: GrupoItem? = null
                            var jogadores: List<Jogador> = mutableListOf()
                            if (it != null) {

                                jogadores = it.jogadores.toSoccerPlayerModel()

                                grupoComJogadores = GrupoItem(
                                    it.grupo.id,
                                    it.grupo.nome,
                                    it.grupo.quantidadeTimes,
                                    it.grupo.configuracaoEsporte.toConfiguracaoEsporteModel(),
                                    jogadores.toMutableList(),
                                    mutableListOf()
                                )
                            }


                            val uiState = if (grupoComJogadores == null) {
                                UiState.Error
                            } else UiState.Success(grupoComJogadores)

                            emit(uiState)
                        }
                }

                else -> {
                    Log.i("DetailsGroupViewModel", "DetailsGroupViewModel: else")
                }
            }
        }
    }


    fun getGroupById(groupId: Int?) {
        action.value = Action.GetGroupById(groupId)
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val grupo: GrupoItem) : UiState()
        object Error : UiState()
    }

    sealed class Action {
        data class GetGroupById(
            val groupId: Int?,
        ) : Action()
    }

}
