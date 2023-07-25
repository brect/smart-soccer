package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import com.padawanbr.smartsoccer.databinding.FragmentSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

@AndroidEntryPoint
class SoccerPlayerFragment : Fragment() {

    private var _binding: FragmentSoccerPlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args by navArgs<SoccerPlayerFragmentArgs>()

    private val viewModel: SoccerPlayerViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

    private val soccerPlayersAdapter by lazy {
        getCommonAdapterOf(
            { SoccerPlayerViewHolder.create(it) },
            { item: JogadorItem ->
                Toast.makeText(context, "productsAdapter $item", Toast.LENGTH_SHORT).show()
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

        if (args.isEditing) {
            Toast.makeText(
                context,
                "Modo edição de grupos",
                Toast.LENGTH_SHORT
            ).show()

            binding.textViewSoccerPlayerGroupName.text = args.grupoItemViewArgs?.nome
        }

        observeUiState()
        observeSharedUiState()

        val grupoId = args.grupoItemViewArgs?.id

        binding.floatingActionButtonAddSoccer.setOnClickListener {
            val grupoId = args.grupoItemViewArgs?.id
            grupoId?.let {
                val detailsSoccerPlayerFragment = DetailsSoccerPlayerFragment()
                val bundle = Bundle()
                bundle.putInt("grupoId", grupoId)
                detailsSoccerPlayerFragment.arguments = bundle
                detailsSoccerPlayerFragment.show(
                    childFragmentManager,
                    "DetailsSoccerPlayerFragment"
                )
            }
        }

        getAllSoccers(grupoId)

    }

    private fun getAllSoccers(grupoId: Int?) {
        grupoId?.let {
            viewModel.getAllSoccerPlayers(grupoId)
        }
    }

    private fun observeSharedUiState() {
        sharedViewModel.updateSoccerPlayers.observe(viewLifecycleOwner) {
            // Atualize o adaptador com a nova lista de jogadores
            val grupoId = args.grupoItemViewArgs?.id
            getAllSoccers(grupoId)
        }
    }

    private fun initSoccerPlayersAdapter() {
        binding.recyclerSoccerPlayers.run {
            setHasFixedSize(true)
            adapter = soccerPlayersAdapter

            binding.recyclerSoccerPlayers.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    if (dy > 0 && binding.floatingActionButtonAddSoccer.isShown) {
                        // Scroll para baixo e o FloatingActionButton está visível, oculta o FAB.
                        binding.floatingActionButtonAddSoccer.hide()
                    } else if (dy < 0 && !binding.floatingActionButtonAddSoccer.isShown) {
                        // Scroll para cima e o FloatingActionButton está oculto, mostra o FAB.
                        binding.floatingActionButtonAddSoccer.show()
                    }
                }
            })
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                SoccerPlayerViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SoccerPlayerViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                SoccerPlayerViewModel.UiState.ShowEmptySoccers -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.ShowEmptySoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                    soccerPlayersAdapter.submitList(emptyList())
                }
                is SoccerPlayerViewModel.UiState.ShowSoccers -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.ShowSoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                    soccerPlayersAdapter.submitList(uiState.soccerPlayers)
                }
                SoccerPlayerViewModel.UiState.Success -> {
                    Toast.makeText(
                        context,
                        "SoccerPlayerViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}