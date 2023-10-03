package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import com.padawanbr.smartsoccer.databinding.FragmentImportSoccerPlayersBinding
import com.padawanbr.smartsoccer.databinding.FragmentSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.presentation.validation.formfields.disable
import com.padawanbr.smartsoccer.presentation.validation.formfields.enable
import com.padawanbr.smartsoccer.presentation.validation.formfields.validate
import com.padawanbr.smartsoccer.presentation.validation.managers.DetailsSoccerPlayerFormFieldManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class ImportSoccerPlayersFragment : BottomSheetDialogFragment() {

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

        binding.textDetailsSoccersTitle.tooltipText = "xurupita"
        observeUiState()
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
        this.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
