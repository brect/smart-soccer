package com.padawanbr.smartsoccer.presentation.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateCompetitionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompetitionFragment : BottomSheetDialogFragment() {

    private var _binding: BottonsheetCreateCompetitionBinding? = null
    private val binding: BottonsheetCreateCompetitionBinding get() = _binding!!

    private val viewModel: CompetitionViewModel by viewModels()

    private val args by navArgs<CompetitionFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottonsheetCreateCompetitionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val grupoId = args.competitionViewArgs?.grupoId ?: ""
        val jogadores = args.competitionViewArgs?.jogadores ?: mutableListOf()

        binding.buttonCreateTeam.setOnClickListener {
            val qtdTeams = binding.editTextCreateTeamQtdTeams.text.toString().toInt()
            val teamPositionsChecked = binding.switchCreateTeamPositions.isChecked
            val teamAbilitiesChecked = binding.switchCreateTeamAbilities.isChecked
            val teamDmChecked =  binding.switchCreateTeamDm.isChecked

            viewModel.createQuickCompetition(
                grupoId,
                jogadores,
                qtdTeams,
                teamPositionsChecked,
                teamAbilitiesChecked,
                teamDmChecked
            )
        }

        observeUiState()
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                CompetitionViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "CompetitionViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                CompetitionViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "CompetitionViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                CompetitionViewModel.UiState.Success -> {
                    Toast.makeText(
                        context,
                        "CompetitionViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}