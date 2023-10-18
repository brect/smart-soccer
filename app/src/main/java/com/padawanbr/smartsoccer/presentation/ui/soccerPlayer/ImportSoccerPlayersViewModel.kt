package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.padawanbr.smartsoccer.core.domain.model.Jogador
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.usecase.AddSoccersPlayerUseCase
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import com.padawanbr.smartsoccer.presentation.extensions.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import java.util.regex.Pattern
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
                    val jogadoresResult = extractJogadoresFromInput(it.text, it.groupId)

                    jogadoresResult.onSuccess { jogadores ->
                        addSoccerPlayersUseCase.invoke(
                            AddSoccersPlayerUseCase.Params(jogadores)
                        ).watchStatus(
                            loading = {
                                emit(UiState.Loading)
                            },
                            success = {
                                emit(UiState.Success)
                            },
                            error = { exception ->
                                emit(UiState.Error(exception.message ?: "Erro desconhecido"))
                            }
                        )
                    }

                    jogadoresResult.onFailure { exception ->
                        emit(UiState.Error(exception.message ?: "Erro desconhecido"))
                    }
                }

                else -> {
                    Log.i("DetailsSoccerPlayerViewModel", "DetailsSoccerPlayerViewModel: else")
                }
            }
        }
    }

    private fun extractJogadoresFromInput(text: String, grupoId: String): Result<List<Jogador>> {
        val pattern = Pattern.compile("^([^|]+)\\|(\\d{1,3})\\|(GK|ZAG|LAT|MEI|ATA)(?:\\|(S|N)\\|([0-9]{1}\\.[0-9]{1})\\|([0-9]{1}\\.[0-9]{1})\\|([0-9]{1}\\.[0-9]{1})\\|([0-9]{1}\\.[0-9]{1})\\|([0-9]{1}\\.[0-9]{1})\\|([0-9]{1}\\.[0-9]{1}))?;$") // ... substituído por outras abreviações

        val lines = text.split("\n")
        val jogadores = mutableListOf<Jogador>()

        for (line in lines) {
            val matcher = pattern.matcher(line)

            if (matcher.find()) {
                val nome = matcher.group(1)
                val idade = matcher.group(2).toInt()
                val posicao = PosicaoJogador.values().find { it.abreviacao == matcher.group(3) }

                val estaNoDM = matcher.group(4)?.let { it == "S" } ?: false

                val habilidades = mutableMapOf<String, Float>()
                listOf(
                    "Velocidade",
                    "Chute",
                    "Passe",
                    "Marcação",
                    "Drible",
                    "Raça"
                ).forEachIndexed { index, habilidade ->
                    matcher.group(5 + index)?.toFloat()?.let {
                        habilidades[habilidade] = it
                    }
                }

                val jogador = Jogador(
                    id = UUID.randomUUID().toString(),
                    nome = nome,
                    idade = idade,
                    posicao = posicao,
                    habilidades = habilidades,
                    estaNoDepartamentoMedico = estaNoDM,
                    grupoId = grupoId
                )

                jogadores.add(jogador)
            } else {
                return Result.failure(Exception("Formato de entrada inválido. Preencha os valores seguindo os padrões acima."))
            }
        }

        return Result.success(jogadores)
    }

    fun saveSoccerPlayers(
        text: String,
        groupId: String,
    ) {
        action.value = Action.CreateSoccerPlayers(
            text,
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
            val text: String,
            val groupId: String,
        ) : Action()
    }
}
