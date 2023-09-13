package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.padawanbr.smartsoccer.databinding.FragmentSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.adapter.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.extensions.attachHideShowFab
import com.padawanbr.smartsoccer.presentation.extensions.showLoadingToast
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.presentation.modelView.JogadorItem
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class SoccerPlayerFragment : Fragment() {

    private var _binding: FragmentSoccerPlayerBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SoccerPlayerFragmentArgs>()

    private val viewModel: SoccerPlayerViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

    private val soccerPlayersAdapter by lazy {
        getCommonAdapterOf(
            { SoccerPlayerViewHolder.create(it) },
            { item: JogadorItem ->
                val grupoId = args.groupId
                showDetailsSoccerPlayerFragment(grupoId, item, true)
            },
            { item: JogadorItem ->
                showShortToast("Jogador ${item.nome}")
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSoccerPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSoccerPlayersAdapter()

        observeUiState()
        observeSharedUiState()

        val grupoId = args.groupId

        binding.floatingActionButtonAddSoccer.setOnClickListener {
            showDetailsSoccerPlayerFragment(grupoId)
        }

        getAllSoccers(grupoId)

    }

    private fun showDetailsSoccerPlayerFragment(
        grupoId: String,
        jogador: JogadorItem? = null,
        isEditing: Boolean = false
    ) {
        grupoId?.let {
            val detailsSoccerPlayerFragment = DetailsSoccerPlayerFragment()
            val bundle = Bundle()

            bundle.putString("grupoId", grupoId)
            bundle.putBoolean("isEditing", isEditing)

            // Verifica se o jogador não é nulo e, em seguida, adiciona seus atributos individualmente no Bundle
            jogador?.let {
                bundle.putString("id", it.id)
                bundle.putString("nome", it.nome)
                bundle.putInt("idade", it.idade ?: 0)
                bundle.putString("posicao", it.posicao)
                bundle.putSerializable("habilidades", it.habilidades as Serializable)
                bundle.putBoolean("estaNoDepartamentoMedico", it.estaNoDepartamentoMedico ?: false)
            }

            detailsSoccerPlayerFragment.arguments = bundle
            detailsSoccerPlayerFragment.show(
                childFragmentManager,
                "DetailsSoccerPlayerFragment"
            )
        }
    }

    private fun getAllSoccers(grupoId: String?) {
        grupoId?.let {
            viewModel.getAllSoccerPlayers(grupoId)
        }
    }

    private fun initSoccerPlayersAdapter() {
        binding.recyclerSoccerPlayers.run {
            setHasFixedSize(true)
            adapter = soccerPlayersAdapter

            binding.recyclerSoccerPlayers.attachHideShowFab(binding.floatingActionButtonAddSoccer)
        }
    }

    private fun observeSharedUiState() {
        sharedViewModel.updateSoccerPlayers.observe(viewLifecycleOwner) {
            // Atualize o adaptador com a nova lista de jogadores
            val grupoId = args.groupId
            getAllSoccers(grupoId)
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.fliperSoccerPlayers.displayedChild = when (uiState) {
                SoccerPlayerViewModel.UiState.Error -> {
                    showShortToast("Erro ao carregar sua lista")
                    FLIPPER_CHILD_SOCCER_PLAYER_ERROR
                }

                SoccerPlayerViewModel.UiState.Loading -> {
                    showLoadingToast()
                    FLIPPER_CHILD_SOCCER_PLAYER_LOADING
                }

                SoccerPlayerViewModel.UiState.ShowEmptySoccers -> {
                    showShortToast("Você não possui jogadores cadastrados")
                    soccerPlayersAdapter.submitList(emptyList())
                    FLIPPER_CHILD_SOCCER_PLAYER_EMPTY
                }

                is SoccerPlayerViewModel.UiState.ShowSoccers -> {
                    soccerPlayersAdapter.submitList(uiState.soccerPlayers)
                    FLIPPER_CHILD_SOCCER_PLAYER_SUCCESS
                }

                SoccerPlayerViewModel.UiState.Success -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    FLIPPER_CHILD_SOCCER_PLAYER_SUCCESS
                }
            }

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val FLIPPER_CHILD_SOCCER_PLAYER_LOADING = 0
        private const val FLIPPER_CHILD_SOCCER_PLAYER_SUCCESS = 1
        private const val FLIPPER_CHILD_SOCCER_PLAYER_EMPTY = 2
        private const val FLIPPER_CHILD_SOCCER_PLAYER_ERROR = 3

    }
}