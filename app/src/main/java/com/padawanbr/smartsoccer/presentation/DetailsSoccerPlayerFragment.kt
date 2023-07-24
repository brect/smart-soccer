package com.padawanbr.smartsoccer.presentation

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSoccerPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsSoccerPlayerBinding? = null
    private val binding: FragmentDetailsSoccerPlayerBinding get() = _binding!!

    private val viewModel: DetailsSoccerPlayerViewModel by viewModels()

    private lateinit var spinnerDetailsPlayerPosition: Spinner
    private lateinit var adapterDetailsPlayerPosition: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsSoccerPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSpinnerDetailsPlayerAdapter()

        binding.buttonSaveItem.setOnClickListener {

            val grupoId = arguments?.getInt("grupoId", -1) ?: -1
            val textInputDetailsPlayerName =
                binding.editTextTextInputDetailsPlayerName.text.toString()
            val textInputDetailsPlayerAge =
                binding.editTextTextInputDetailsPlayerAge.text.toString().toInt()

            viewModel.createSoccer(
                grupoId,
                textInputDetailsPlayerName,
                textInputDetailsPlayerAge
            )
            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }

        observeUiState()

    }

    private fun initSpinnerDetailsPlayerAdapter() {
        spinnerDetailsPlayerPosition = binding.spinnerDetailsPlayerPosition

        val detailsPlayerPositions =
            arrayOf("Selecione uma posição", "posição 1", "posição 2", "posição 3")

        adapterDetailsPlayerPosition =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsPlayerPositions)

        spinnerDetailsPlayerPosition.adapter = adapterDetailsPlayerPosition

        spinnerDetailsPlayerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // You can define you actions as you want
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    val selectedObject = spinnerDetailsPlayerPosition.selectedItem as String
                    Toast.makeText(
                        requireContext(),
                        "ID: ${selectedObject} Name: ${selectedObject}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                DetailsSoccerPlayerViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "Category GroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsSoccerPlayerViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "Category GroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsSoccerPlayerViewModel.UiState.ShowEmptySoccers -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.ShowEmptySoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DetailsSoccerPlayerViewModel.UiState.ShowSoccers -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.ShowSoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                DetailsSoccerPlayerViewModel.UiState.Success -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
