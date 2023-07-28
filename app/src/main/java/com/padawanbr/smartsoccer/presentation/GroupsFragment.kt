package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.core.domain.model.TipoEsporte
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import com.padawanbr.smartsoccer.databinding.FragmentGroupsBinding
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetCreateGroupBinding? = null
    private val bottomSheetBinding: BottonsheetCreateGroupBinding get() = _bottomSheetBinding!!

    private val groupsAdapter by lazy {
        getCommonAdapterOf(
            { GroupsViewHolder.create(it) },
            { item: GrupoItem ->
                val directions = GroupsFragmentDirections.actionGroupsFragmentToSoccerPlayerFragment()

                directions.grupoItemViewArgs = GrupoItemViewArgs(
                    item.id,
                    item.nome,
                    item.quantidadeTimes,
                    item.configuracaoEsporte.tipoEsporte
                )

                directions.isEditing = true

                findNavController().navigate(directions)
//                Toast.makeText(context, "productsAdapter $item", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGroupsBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initGroupsAdapter()

        bindingBottomSheetToCreateGroup()

        binding.floatingActionButton.setOnClickListener {
            bottomSheetDialog.show()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.floatingActionButton)
                .setAction("Action", null).show()
        }

        observeUiState()

        viewModel.getAll()
    }

    private fun initGroupsAdapter() {
        binding.recyclerGroupItens.run {
            setHasFixedSize(true)
            adapter = groupsAdapter

            binding.recyclerGroupItens.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0 && binding.floatingActionButton.isShown) {
                        // Scroll para baixo e o FloatingActionButton está visível, oculta o FAB.
                        binding.floatingActionButton.hide()
                    } else if (dy < 0 && !binding.floatingActionButton.isShown) {
                        // Scroll para cima e o FloatingActionButton está oculto, mostra o FAB.
                        binding.floatingActionButton.show()
                    }
                }
            })
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                GroupViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "Groups GroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                GroupViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "Groups GroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                GroupViewModel.UiState.Success -> {
                    bottomSheetDialog.hide()
                }

                GroupViewModel.UiState.ShowEmptyGroups -> {
                    groupsAdapter.submitList(emptyList())
                }

                is GroupViewModel.UiState.ShowGroups -> {
                    groupsAdapter.submitList(uiState.groups)
                }
            }
        }
    }

    private fun bindingBottomSheetToCreateGroup() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetCreateGroupBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // Access elements in the layout using ViewBinding
        bottomSheetBinding.buttonCreateGroup.setOnClickListener {
            val textNameGroup = bottomSheetBinding.editTextTextInputGroupName.text.toString()
            val qtdMinPlayers =
                bottomSheetBinding.editTextTextInputQtdMinPlayers.text.toString().toInt()
            val qtdNumberPlayersPerTeam =
                bottomSheetBinding.editTextTextInputQtdNumberPlayersPerTeam.text.toString().toInt()
            val qtdTeam = bottomSheetBinding.editTextTextInputQtdTeam.text.toString().toInt()

            viewModel.createGroup(
                textNameGroup,
                qtdTeam,
                TipoEsporte.FUTEBOL
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}