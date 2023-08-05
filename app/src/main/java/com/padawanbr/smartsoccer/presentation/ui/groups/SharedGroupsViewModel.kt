package com.padawanbr.smartsoccer.presentation.ui.groups

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class SharedGroupsViewModel @Inject constructor() : ViewModel() {

    // LiveData para sinalizar a necessidade de atualizar a lista de jogadores
    private val _updateGroups = MutableLiveData<Boolean>()
    val updateGroups: LiveData<Boolean> get() = _updateGroups

    // Função para sinalizar a necessidade de atualizar a lista de jogadores
    fun updateGroups(updateList: Boolean) {
        _updateGroups.value = updateList
    }
}
