package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.padawanbr.smartsoccer.databinding.FragmentImportSoccerPlayersBinding
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImportSoccerPlayersFragment : Fragment() {

    private var _binding: FragmentImportSoccerPlayersBinding? = null
    private val binding: FragmentImportSoccerPlayersBinding get() = _binding!!

    private val viewModel: ImportSoccerPlayersViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImportSoccerPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSaveItem.setOnClickListener {
            save()
        }

        observeUiState()
    }

    private fun save() {
        val text = binding.editTextTextInputDetailsPlayersName.text.toString()
        val grupoId = arguments?.getString("groupId", "") ?: ""

        if (text.isNullOrBlank()) {
            showShortToast("Erro de input em branco")
        } else {
            viewModel.saveSoccerPlayers(text, grupoId)
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is ImportSoccerPlayersViewModel.UiState.Error -> {
                    showShortToast(uiState.message)
                }

                ImportSoccerPlayersViewModel.UiState.Loading -> {
                    showShortToast("Carregando...")
                }

                is ImportSoccerPlayersViewModel.UiState.Success -> {
                    atualizaListaDeJogadores()
                    showShortToast("Jogadores adicionados com sucesso!")
                }

                is ImportSoccerPlayersViewModel.UiState.Error -> TODO()
            }
        }
    }


    private fun atualizaListaDeJogadores() {
        sharedViewModel.updateSoccerPlayers(true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
