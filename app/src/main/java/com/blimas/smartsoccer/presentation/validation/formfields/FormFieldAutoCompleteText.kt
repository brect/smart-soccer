package com.blimas.smartsoccer.presentation.validation.formfields

import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import reactivecircus.flowbinding.android.widget.textChanges

class FormFieldAutoCompleteText(
    scope: CoroutineScope,
    private val textInputLayout: TextInputLayout,
    private val autoCompleteTextView: AutoCompleteTextView,
    private val validation: suspend (String?) -> String? = { null },
) : FormField<String>() {

    var isEnabled: Boolean
        get() = textInputLayout.isEnabled
        set(value) {
            textInputLayout.isEnabled = value
        }

    var isVisible: Boolean
        get() = textInputLayout.isVisible
        set(value) {
            textInputLayout.isVisible = value
        }

    var value: String?
        get() = stateInternal.value
        set(value) {
            autoCompleteTextView.setText(value)
        }

    init {
        autoCompleteTextView.textChanges().skipInitialValue().onEach { text ->
            clearError()
            stateInternal.update { text.toString() }
        }.launchIn(scope)
    }

    override fun clearError() {
        if (textInputLayout.error != null) {
            textInputLayout.error = null
            textInputLayout.isErrorEnabled = false
        }
    }

    override fun clearFocus() {
        autoCompleteTextView.clearFocus()
    }

    override fun disable() {
        isEnabled = false
    }

    override fun enable() {
        isEnabled = true
    }

    override suspend fun validate(focusIfError: Boolean): Boolean {
        if (!isVisible) {
            return true
        }
        val errorValue = try {
            validation(stateInternal.value)
        } catch (error: Throwable) {
            error.message
        }
        val result = errorValue == null
        if (result) {
            clearError()
        } else {
            textInputLayout.error = errorValue
            if (focusIfError) {
                autoCompleteTextView.requestFocus()
            }
        }
        isValidInternal.update { result }
        return result
    }
}