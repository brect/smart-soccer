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
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreateGroupFragment : BottomSheetDialogFragment() {

    private var _binding: BottonsheetCreateGroupBinding? = null
    private val binding: BottonsheetCreateGroupBinding get() = _binding!!

    private val viewModel: CreateGroupViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private lateinit var spinnerGroupModality: Spinner
    private lateinit var adapterGroupModality: ArrayAdapter<String>

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

        binding.buttonCreateGroup.setOnClickListener {
            val textNameGroup = binding.editTextTextInputGroupName.text.toString()
            val qtdTeam = binding.editTextTextInputQtdTeam.text.toString().toInt()

            // Obtenha o enum PosicaoJogador selecionado no Spinner
            val selectedPosition = spinnerGroupModality.selectedItem as String
            val groupModalityPositionString = selectedPosition.substringBefore("(")
                .trim() // Obtém apenas a posição, ignorando a abreviação
            val groupModalityPosition =
                TipoEsporte.values().find { it.tipo == groupModalityPositionString }

            viewModel.createGroup(
                textNameGroup,
                qtdTeam,
                groupModalityPosition ?: TipoEsporte.UNDEFINED
            )

            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }

        observeUiState()

    }

    private fun initSpinnerGroupModalityAdapter() {
        spinnerGroupModality = binding.spinnerGroupModality

        val detailsSportType = TipoEsporte.values().map { esporte ->
            "${esporte.tipo} "
        }.toMutableList()

        adapterGroupModality =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsSportType)

        spinnerGroupModality.adapter = adapterGroupModality

        spinnerGroupModality.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // Obtenha o enum TipoEsporte selecionado no Spinner
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Lida com o caso em que nada foi selecionado
                }
            }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                CreateGroupViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "CreateGroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                CreateGroupViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "CreateGroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                CreateGroupViewModel.UiState.Success -> {
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
