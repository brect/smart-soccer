package com.padawanbr.smartsoccer.presentation.validation.managers

import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.validation.formfields.FormFieldAutoCompleteText
import com.padawanbr.smartsoccer.presentation.validation.formfields.FormFieldText
import kotlinx.coroutines.CoroutineScope

class DetailsSoccerPlayerFormFieldManager(
    val binding: FragmentDetailsSoccerPlayerBinding,
    private val lifecycleScope: CoroutineScope
) {

    val fieldPlayerName by lazy {
        FormFieldText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputDetailsPlayerName,
            textInputEditText = binding.editTextTextInputDetailsPlayerName,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Nome do jogador obrigatório"
                    else -> null
                }
            }
        )
    }
    val fieldPlayerAge by lazy {
        FormFieldText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputDetailsPlayerAge,
            textInputEditText = binding.editTextTextInputDetailsPlayerAge,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Idade do jogador obrigatória"
                    else -> null
                }
            }
        )
    }


    val fieldPlayerPosition by lazy {
        FormFieldAutoCompleteText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputDetailsPlayerPosition,
            autoCompleteTextView = binding.editTextTextInputDetailsPlayerPosition,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Selecione uma modalidade"
                    else -> null
                }
            }
        )
    }

    val formFields by lazy {
        listOf(
            fieldPlayerName,
            fieldPlayerAge,
            fieldPlayerPosition
        )
    }
}