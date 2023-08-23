package com.padawanbr.smartsoccer.presentation.ui.groups

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.padawanbr.smartsoccer.core.domain.model.DiaDaSemana
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailsGroupFragment : BottomSheetDialogFragment() {

    private var _binding: BottonsheetCreateGroupBinding? = null
    private val binding: BottonsheetCreateGroupBinding get() = _binding!!

    private val viewModel: DetailsGroupViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottonsheetCreateGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinnerGroupModalityAdapter()
        initSpinnerGroupGameDayAdapter()

        binding.seekBarRatingAge.addOnChangeListener(RangeSlider.OnChangeListener { slider, value, fromUser ->
            val values = binding.seekBarRatingAge.values

            binding.textViewAgeValueFromLbl.text = "${values[0].roundToInt()} - anos"
            binding.textViewAgeValueToLbl.text = "${values[1].roundToInt()} - anos"

            binding.textViewAgeValueFromLbl.text

            Log.d("From", values[0].toString())
            Log.d("T0", values[1].toString())

            Log.d("slider", "$slider")
            Log.d("value", "$value")
            Log.d("fromUser", "$fromUser")
        })


        binding.buttonCreateGroup.setOnClickListener {
            val textNameGroup = binding.editTextTextInputGroupName.text.toString()
            val qtdTeam = binding.editTextNumberOfVacancies.text.toString().toInt()

            // Obtenha o enum PosicaoJogador selecionado no Spinner
            val selectedPosition = binding.editTextTextInputGroupModality.text.toString()
            val groupModalityPositionString = selectedPosition.substringBefore("(").trim()
            val groupModalityPosition =
                TipoEsporte.values().find { it.modalidade == groupModalityPositionString }

            viewModel.createGroup(
                textNameGroup,
                qtdTeam,
                groupModalityPosition ?: TipoEsporte.FUTEBOL_CAMPO
            )

            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }

        observeUiState()

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
