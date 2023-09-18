package com.blimas.smartsoccer.presentation.validation.managers

import com.blimas.smartsoccer.databinding.BottonsheetCreateGroupBinding
import com.blimas.smartsoccer.presentation.validation.formfields.FormFieldAutoCompleteText
import com.blimas.smartsoccer.presentation.validation.formfields.FormFieldRangeSlider
import com.blimas.smartsoccer.presentation.validation.formfields.FormFieldText
import kotlinx.coroutines.CoroutineScope

class DetailsGroupFormFieldManager(val binding: BottonsheetCreateGroupBinding, private val lifecycleScope: CoroutineScope) {

    val fieldNameGroup by lazy {
        FormFieldText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputLayoutGroupName,
            textInputEditText = binding.editTextTextInputGroupName,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Nome do grupo obrigatório"
                    else -> null
                }
            }
        )
    }

    val fieldPlaceGroup by lazy {
        FormFieldText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputPlace,
            textInputEditText = binding.editTextTextInputPlace,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Local/Endereço é obrigatório"
                    else -> null
                }
            }
        )
    }

    val fieldModalityGroup by lazy {
        FormFieldAutoCompleteText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputGroupModality,
            autoCompleteTextView = binding.editTextTextInputGroupModality,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Selecione uma modalidade"
                    else -> null
                }
            }
        )
    }

    val fieldGameDay by lazy {
        FormFieldAutoCompleteText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputGameDay,
            autoCompleteTextView = binding.editTextTextInputGameDay,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Selecione o dia do seu jogo"
                    else -> null
                }
            }
        )
    }

    val fieldBeginningOfTheGame by lazy {
        FormFieldAutoCompleteText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputBeginningOfTheGame,
            autoCompleteTextView = binding.textViewBeginningOfTheGame,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Selecione o horário de início do seu jogo"
                    else -> null
                }
            }
        )
    }

    val fieldNumberOfVacancies by lazy {
        FormFieldText(
            scope = lifecycleScope,
            textInputLayout = binding.textInputNumberOfVacancies,
            textInputEditText = binding.editTextNumberOfVacancies,
            validation = { value ->
                when {
                    value.isNullOrBlank() -> "Digite a quantidade minima de times"
                    else -> null
                }
            }
        )
    }

    val fieldSeekBarRatingAge by lazy {
        FormFieldRangeSlider(
            scope = lifecycleScope,
            rangeSlider = binding.seekBarRatingAge,
        )
    }

    val formFields by lazy {
        listOf(
            fieldNameGroup,
            fieldPlaceGroup,
            fieldModalityGroup,
            fieldGameDay,
            fieldBeginningOfTheGame,
            fieldNumberOfVacancies,
            fieldSeekBarRatingAge
        )
    }
}