package com.padawanbr.smartsoccer.presentation.validation.formfields

import android.view.View
import androidx.core.view.isVisible
import com.google.android.material.slider.RangeSlider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import reactivecircus.flowbinding.material.changeEvents

class FormFieldRangeSlider(
    scope: CoroutineScope,
    private val rangeSlider: RangeSlider,
) : FormField<List<Float>>() {

    var isEnabled: Boolean
        get() = rangeSlider.isEnabled
        set(value) {
            rangeSlider.isEnabled = value
        }

    var isVisible: Boolean
        get() = rangeSlider.isVisible
        set(value) {
            rangeSlider.isVisible = value
        }

    var value: List<Float>?
        get() = stateInternal.value
        set(value) {
            rangeSlider.values = value.orEmpty()
        }

    init {
        rangeSlider.changeEvents().onEach {
            clearError()
            stateInternal.update { rangeSlider.values }
        }.launchIn(scope)
    }

    override fun clearError() {
    }

    override fun clearFocus() {
        rangeSlider.clearFocus()
    }

    override fun disable() {
        isEnabled = false
    }

    override fun enable() {
        isEnabled = true
    }

    override suspend fun validate(focusIfError: Boolean) = true

}
