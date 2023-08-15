package com.padawanbr.smartsoccer.presentation.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import com.padawanbr.smartsoccer.databinding.FragmentGroupBinding
import com.padawanbr.smartsoccer.presentation.ui.groups.GroupFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCompetitionFragment : Fragment() {

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



        observeUiState()

        viewModel.getCompetition(args.competitionId)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                DetailsCompetitionViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "DetailsCompetitionViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                DetailsCompetitionViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "DetailsCompetitionViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is DetailsCompetitionViewModel.UiState.Success -> {

                    binding.textDetailsSoccerTeams.text = uiState.torneio.times.toString()
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

}