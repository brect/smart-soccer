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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.padawanbr.smartsoccer.core.domain.model.DiaDaSemana
import com.padawanbr.smartsoccer.core.domain.model.RangeIdade
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import dagger.hilt.android.AndroidEntryPoint
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

    private var rangeIdade = RangeIdade(10, 70)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottonsheetCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = false

        initSpinnerGroupModalityAdapter()
        initSpinnerGroupGameDayAdapter()

        beginningOfTheGameTimerPickerListener()
        seekBarRatingAgeListener()
        buttonCreateGroupListener()

        observeUiState()

    }

    private fun buttonCreateGroupListener() {
        binding.buttonCreateGroup.setOnClickListener {
            val textNameGroup = binding.editTextTextInputGroupName.text.toString()
            val textPlaceGroup = binding.editTextTextInputPlace.text.toString()

            // Obtenha o enum PosicaoJogador selecionado no Spinner
            val selectedPosition = binding.editTextTextInputGroupModality.text.toString()
            val groupModalityPositionString = selectedPosition.substringBefore("(").trim()
            val groupModalityPosition = TipoEsporte.values().find { it.modalidade == groupModalityPositionString }

            val textGameDay = binding.editTextTextInputGameDay.text.toString()
            val textBeginningOfTheGame = binding.textViewBeginningOfTheGame.text.toString()

            val qtdTeam = binding.editTextNumberOfVacancies.text.toString().toInt()

            viewModel.createGroup(
                textNameGroup,
                textPlaceGroup,
                groupModalityPosition ?: TipoEsporte.FUTEBOL_CAMPO,
                textGameDay,
                textBeginningOfTheGame,
                qtdTeam,
                rangeIdade
            )

            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }
    }

    private fun seekBarRatingAgeListener() {
        binding.seekBarRatingAge.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->
            val values = binding.seekBarRatingAge.values

            val minAge = values[0].roundToInt()
            val maxAge = values[1].roundToInt()

            binding.textViewAgeValueFromLbl.text = "$minAge - anos"
            binding.textViewAgeValueToLbl.text = "$maxAge - anos"

            rangeIdade.minAge
            rangeIdade.maxAge

            binding.textViewAgeValueFromLbl.text

            Log.d("From", values[0].toString())
            Log.d("T0", values[1].toString())

            Log.d("slider", "$slider")
            Log.d("value", "$value")
            Log.d("fromUser", "$fromUser")
        })
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
                    this.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
