package com.blimas.smartsoccer.presentation.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.blimas.smartsoccer.core.domain.model.Jogador
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.blimas.smartsoccer.databinding.BottonsheetCreateCompetitionBinding
import com.blimas.smartsoccer.presentation.extensions.showShortToast
import com.blimas.smartsoccer.presentation.ui.groups.SharedGroupsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuickCompetitionFragment : BottomSheetDialogFragment() {

    private var _binding: BottonsheetCreateCompetitionBinding? = null
    private val binding: BottonsheetCreateCompetitionBinding get() = _binding!!

    private val viewModel: QuickCompetitionViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private val args by navArgs<QuickCompetitionFragmentArgs>()


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

        createTeamOnClickListener(jogadores, grupoId)

        observeUiState()
    }

    private fun createTeamOnClickListener(
        jogadores: MutableList<Jogador>,
        grupoId: String
    ) {
        binding.buttonCreateTeam.setOnClickListener {
            val qtdTeams = binding.editTextCreateTeamQtdTeams.text.toString().toInt()

            // Verificar se o número de times é pelo menos igual ao número mínimo de jogadores
            if (jogadores.size < qtdTeams) {
                val jogadorOuJogadores = if (jogadores.size == 1) "jogador" else "jogadores"
                showShortToast("Não é possível criar $qtdTeams times com apenas ${jogadores.size} $jogadorOuJogadores.")
                findNavController().popBackStack()
                return@setOnClickListener
            }

            val teamPositionsChecked = binding.switchCreateTeamPositions.isChecked
            val teamAbilitiesChecked = binding.switchCreateTeamAbilities.isChecked
            val teamDmChecked = binding.switchCreateTeamDm.isChecked

            viewModel.createQuickCompetition(
                grupoId,
                jogadores,
                qtdTeams,
                teamPositionsChecked,
                teamAbilitiesChecked,
                teamDmChecked
            )
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                QuickCompetitionViewModel.UiState.Error -> {
                    showShortToast("Erro ao criar a competição")
                }
                QuickCompetitionViewModel.UiState.Loading -> {
                    showShortToast("Carregando...")
                }
                QuickCompetitionViewModel.UiState.Success -> {
                    sharedViewModel.updateGroups(true)
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