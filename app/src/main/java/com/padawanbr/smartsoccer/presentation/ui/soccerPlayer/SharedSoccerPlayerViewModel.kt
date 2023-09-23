package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedSoccerPlayerViewModel @Inject constructor() : ViewModel() {

    // LiveData para sinalizar a necessidade de atualizar a lista de jogadores
    private val _updateSoccerPlayers = MutableLiveData<Boolean>()
    val updateSoccerPlayers: LiveData<Boolean> get() = _updateSoccerPlayers

    // Função para sinalizar a necessidade de atualizar a lista de jogadores
    fun updateSoccerPlayers(updateList: Boolean) {
        _updateSoccerPlayers.value = updateList
    }
}
