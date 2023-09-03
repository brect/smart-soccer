package com.padawanbr.smartsoccer.presentation.ui.groups

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.padawanbr.smartsoccer.core.domain.model.DiaDaSemana
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.presentation.validation.formfields.FormFieldAutoCompleteText
import com.padawanbr.smartsoccer.presentation.validation.formfields.FormFieldRangeSlider
import com.padawanbr.smartsoccer.presentation.validation.formfields.FormFieldText
import com.padawanbr.smartsoccer.presentation.validation.formfields.disable
import com.padawanbr.smartsoccer.presentation.validation.formfields.enable
import com.padawanbr.smartsoccer.presentation.validation.formfields.validate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks
import java.text.MessageFormat
import java.util.Locale
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailsGroupFragment : BottomSheetDialogFragment() {

    private var _binding: BottonsheetCreateGroupBinding? = null
    private val binding: BottonsheetCreateGroupBinding get() = _binding!!

    private val viewModel: DetailsGroupViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private lateinit var timePicker: TimePicker

    private val args by navArgs<DetailsGroupFragmentArgs>()

    private var rangeIdade = RangeIdade(10, 70)

    private lateinit var formFieldManager: FormFieldManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottonsheetCreateGroupBinding.inflate(inflater, container, false)
        formFieldManager = FormFieldManager(binding, lifecycleScope)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        beginningOfTheGameTimerPickerListener()
        seekBarRatingAgeListener()

        if (isEditingMode()) {
            populateGroupDetails(args.grupoItemViewArgs)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false

        initSpinnerGroupModalityAdapter()
        initSpinnerGroupGameDayAdapter()

        observeUiState()

        initFormFields()

        binding.buttonSaveGroup.clicks().onEach {
            submit()
        }.launchIn(lifecycleScope)

    }

    private fun initFormFields() {
        formFieldManager.formFields
    }

    private fun submit() = lifecycleScope.launch {
        binding.buttonSaveGroup.isEnabled = false

        formFieldManager.formFields.disable()
        if (formFieldManager.formFields.validate(validateAll = true)) {

            viewModel.saveGroup(
                args.grupoItemViewArgs?.id,
                formFieldManager.fieldNameGroup.value.toString(),
                formFieldManager.fieldPlaceGroup.value.toString(),
                tipoEsporte(formFieldManager.fieldModalityGroup.value) ?: TipoEsporte.FUTEBOL_CAMPO,
                formFieldManager.fieldGameDay.value.toString(),
                formFieldManager.fieldBeginningOfTheGame.value.toString(),
                formFieldManager.fieldNumberOfVacancies.value.toString().toInt(),
                RangeIdade(
                    formFieldManager.fieldSeekBarRatingAge.value?.get(0)?.toInt() ?: 0,
                    formFieldManager.fieldSeekBarRatingAge.value?.get(1)?.toInt() ?: 70,
                )
            )

            showShortToast("Novo grupo criado com sucesso!")
        }

        formFieldManager.formFields.enable()
        binding.buttonSaveGroup.isEnabled = true
    }

    private fun isEditingMode() = args.grupoItemViewArgs != null

    private fun populateGroupDetails(viewArgs: GrupoItemViewArgs?) {
        binding.editTextTextInputGroupName.setText(viewArgs?.nome)
        binding.editTextTextInputPlace.setText(viewArgs?.endereco)
        binding.editTextTextInputGroupModality.setText(viewArgs?.tipoEsporte?.modalidade)
        binding.editTextTextInputGameDay.setText(viewArgs?.diaDoJogo)
        binding.textViewBeginningOfTheGame.setText(viewArgs?.horarioInicio)
        setTextAgeValueLabel(viewArgs?.minAge?.toInt(), viewArgs?.maxAge?.toInt())
        binding.seekBarRatingAge.setValues(viewArgs?.minAge, viewArgs?.maxAge)
        binding.editTextNumberOfVacancies.setText(viewArgs?.quantidadeTimes.toString())
    }

    private fun tipoEsporte(selectedPosition: String?): TipoEsporte? {
        val groupModalityPositionString = selectedPosition?.substringBefore("(")?.trim()
        return TipoEsporte.values().find { it.modalidade == groupModalityPositionString }
    }

    private fun seekBarRatingAgeListener() {
        binding.seekBarRatingAge.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->
            val values = binding.seekBarRatingAge.values

            rangeIdade.minAge = values[0].roundToInt()
            rangeIdade.maxAge = values[1].roundToInt()

            setTextAgeValueLabel(rangeIdade.minAge, rangeIdade.maxAge)

            Log.d("From", values[0].toString())
            Log.d("T0", values[1].toString())

            Log.d("slider", "$slider")
            Log.d("value", "$value")
            Log.d("fromUser", "$fromUser")
        })
    }

    private fun setTextAgeValueLabel(minAge: Int?, maxAge: Int?) {
        binding.textViewAgeValueFromLbl.text = "$minAge - anos"
        binding.textViewAgeValueToLbl.text = "$maxAge - anos"
    }

    private fun beginningOfTheGameTimerPickerListener() {
        binding.textViewBeginningOfTheGame.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                .setTitleText("Horário do jogo")
                .build()
            timePicker.addOnPositiveButtonClickListener {
                binding.textViewBeginningOfTheGame.setText(
                    MessageFormat.format(
                        "{0}:{1}",
                        String.format(
                            Locale.getDefault(),
                            "%02d",
                            timePicker.hour
                        ),
                        String.format(
                            Locale.getDefault(),
                            "%02d",
                            timePicker.minute
                        )
                    )
                )
            }
            timePicker.show(requireFragmentManager(), "tag")
        }
    }

    private fun initSpinnerGroupGameDayAdapter() {
        val gameDay = DiaDaSemana.values().map { semana -> "${semana.dia}" }.toMutableList()

        val adapterGameDay = ArrayAdapter(requireContext(), R.layout.simple_list_item_1, gameDay)
        binding.editTextTextInputGameDay.setAdapter(adapterGameDay)
    }

    private fun initSpinnerGroupModalityAdapter() {
        val detailsSportType = TipoEsporte.values().map { esporte ->
            "${esporte.modalidade}"
        }.toMutableList()

        val adapterGroupModality =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsSportType)
        binding.editTextTextInputGroupModality.setAdapter(adapterGroupModality)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                DetailsGroupViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "CreateGroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsGroupViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "CreateGroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsGroupViewModel.UiState.Success -> {
                    sharedViewModel.updateGroups(true)
                    dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}