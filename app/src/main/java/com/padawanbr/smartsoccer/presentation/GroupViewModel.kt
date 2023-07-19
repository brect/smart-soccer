package com.padawanbr.smartsoccer.presentation

import androidx.lifecycle.ViewModel
import com.padawanbr.smartsoccer.core.usecase.base.AppCoroutinesDispatchers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GroupViewModel @Inject constructor(
    private val coroutinesDispatchers: AppCoroutinesDispatchers
) : ViewModel() {}
