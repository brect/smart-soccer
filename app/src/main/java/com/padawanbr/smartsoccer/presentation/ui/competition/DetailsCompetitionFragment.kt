package com.padawanbr.smartsoccer.presentation.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCompetitionFragment : Fragment()  , MenuProvider {

    private var _binding: FragmentDetailsCompetitionBinding? = null
    private val binding: FragmentDetailsCompetitionBinding get() = _binding!!

    private val viewModel: DetailsCompetitionViewModel by viewModels()

    private val args by navArgs<DetailsCompetitionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailsCompetitionBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        observeUiState()

        viewModel.getCompetition(args.competitionId)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is DetailsCompetitionViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "DetailsCompetitionViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is DetailsCompetitionViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "DetailsCompetitionViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is DetailsCompetitionViewModel.UiState.Success -> {
                    binding.recyclerViewDetailsSoccerTeams.run {
                        setHasFixedSize(true)
                        adapter =  CompetitionTeamParentAdapter(uiState.torneio.times)
                    }

                    Toast.makeText(
                        context,
                        "DetailsCompetitionViewModel.UiState.Success",
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_details_competition, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_share_teams -> {
                Toast.makeText(context, "Action compartilhar", Toast.LENGTH_LONG).show()
                true
            }

            else -> false
        }
    }

}