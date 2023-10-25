package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetExcludeGroupBinding
import com.padawanbr.smartsoccer.databinding.BottonsheetExcludeSocceerPlayersBinding
import com.padawanbr.smartsoccer.databinding.FragmentSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.adapter.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.extensions.attachHideShowFab
import com.padawanbr.smartsoccer.presentation.extensions.showLoadingToast
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.presentation.modelView.JogadorItem
import com.padawanbr.smartsoccer.presentation.ui.groups.GroupFragmentDirections
import com.padawanbr.smartsoccer.presentation.utils.ViewAnimationUtils
import com.padawanbr.smartsoccer.presentation.viewArgs.GrupoItemViewArgs
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class SoccerPlayerFragment : Fragment(), MenuProvider {

    private var _binding: FragmentSoccerPlayerBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<SoccerPlayerFragmentArgs>()

    private val viewModel: SoccerPlayerViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

    private var isRotate: Boolean = false

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetExcludeSocceerPlayersBinding? = null
    private val bottomSheetBinding: BottonsheetExcludeSocceerPlayersBinding get() = _bottomSheetBinding!!

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

        bindingBottomSheetToExcludeSoccerPlayers()

        initMenuProvider()

        initSoccerPlayersAdapter()

        initFabs()
        configureFabMoreOptions()

        observeUiState()
        observeSharedUiState()

        val grupoId = args.groupId

        fabAddSoccerPlayerOnClick(grupoId)
        fabImportPlayersOnClick(grupoId)

        getAllSoccers(grupoId)

    }

    private fun initMenuProvider() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    fun bindingBottomSheetToExcludeSoccerPlayers() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetExcludeSocceerPlayersBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.buttonExcludeGroup.setOnClickListener {
            var groupId = args.groupId
            if (groupId != null) {
                viewModel.deleteSoccerPlayers(groupId)
                bottomSheetDialog.dismiss()
            }
        }
    }


    private fun fabImportPlayersOnClick(grupoId: String) {
        binding.fabImportPlayers.setOnClickListener {
            resetFabs()

            val directions =
                SoccerPlayerFragmentDirections.actionSoccerPlayerFragmentToImportSoccerPlayersFragment()

            directions.groupId = grupoId
            findNavController().navigate(directions)

        }
    }

    private fun fabAddSoccerPlayerOnClick(grupoId: String) {
        binding.fabAddSoccerPlayer.setOnClickListener {
            resetFabs()
            showDetailsSoccerPlayerFragment(grupoId)
        }
    }

    private fun resetFabs() {
        isRotate = ViewAnimationUtils.rotateView(
            binding.fabSoccerAddOptions,
            !isRotate
        )
        hideFabs()
    }

    private fun initFabs() {
        ViewAnimationUtils.init(binding.fabImportPlayers)
        ViewAnimationUtils.init(binding.textViewImportPlayers)
        ViewAnimationUtils.init(binding.fabAddSoccerPlayer)
        ViewAnimationUtils.init(binding.textViewAddSoccerPlayer)
    }

    private fun configureFabMoreOptions() {
        binding.fabSoccerAddOptions.setOnClickListener {
            isRotate = ViewAnimationUtils.rotateView(it, !isRotate)

            if (isRotate) {
                showFabs()
            } else {
                hideFabs()
            }
        }
    }

    private fun showFabs() {
        ViewAnimationUtils.showIn(binding.fabImportPlayers)
        ViewAnimationUtils.showIn(binding.textViewImportPlayers)
        ViewAnimationUtils.showIn(binding.fabAddSoccerPlayer)
        ViewAnimationUtils.showIn(binding.textViewAddSoccerPlayer)
    }

    private fun hideFabs() {
        ViewAnimationUtils.showOut(binding.fabImportPlayers)
        ViewAnimationUtils.showOut(binding.textViewImportPlayers)
        ViewAnimationUtils.showOut(binding.fabAddSoccerPlayer)
        ViewAnimationUtils.showOut(binding.textViewAddSoccerPlayer)
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

            binding.recyclerSoccerPlayers.attachHideShowFab(binding.fabSoccerAddOptions)
        }
    }

    private fun observeSharedUiState() {
        sharedViewModel.updateSoccerPlayers.observe(viewLifecycleOwner) {
            // Atualize o adaptador com a nova lista de jogadores
            getAllSoccerPlayers()
        }
    }

    private fun getAllSoccerPlayers() {
        val grupoId = args.groupId
        getAllSoccers(grupoId)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.fliperSoccerPlayers.displayedChild = when (uiState) {
                is SoccerPlayerViewModel.UiState.Error -> {
                    showShortToast(uiState.message)
                    FLIPPER_CHILD_SOCCER_PLAYER_ERROR
                }

                SoccerPlayerViewModel.UiState.Loading -> {
                    showLoadingToast()
                    FLIPPER_CHILD_SOCCER_PLAYER_LOADING
                }

                SoccerPlayerViewModel.UiState.ShowEmptySoccers -> {
                    soccerPlayersAdapter.submitList(emptyList())
                    FLIPPER_CHILD_SOCCER_PLAYER_EMPTY
                }

                is SoccerPlayerViewModel.UiState.ShowSoccers -> {
                    soccerPlayersAdapter.submitList(uiState.soccerPlayers)
                    FLIPPER_CHILD_SOCCER_PLAYER_SUCCESS
                }

                SoccerPlayerViewModel.UiState.Success -> {
                    FLIPPER_CHILD_SOCCER_PLAYER_SUCCESS
                }

                SoccerPlayerViewModel.UiState.SuccessClearPlayers -> {
                    getAllSoccerPlayers()
                    FLIPPER_CHILD_SOCCER_PLAYER_LOADING
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

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_soccer_player, menu)
        menu.findItem(R.id.action_delete_soccer_players).icon?.setTint(requireContext().getColor(R.color.md_theme_light_onPrimaryContainer))
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {

            R.id.action_delete_soccer_players -> {
                if (soccerPlayersAdapter.itemCount == 0) {
                    showShortToast("Você não possui jogadores cadastrados para serem excluídos")
                    false
                } else {
                    bottomSheetBinding.textExcludePlayersContent.text =
                        context?.getString(R.string.exclude_soccer_players)
                    bottomSheetDialog.show()
                    true
                }
            }

            else -> false
        }
    }
}