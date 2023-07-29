package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetExcludeGroupBinding
import com.padawanbr.smartsoccer.databinding.FragmentGroupsBinding
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetExcludeGroupBinding? = null
    private val bottomSheetBinding: BottonsheetExcludeGroupBinding get() = _bottomSheetBinding!!

    private var selectedItem: GrupoItem? = null

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
            },
            { item: GrupoItem ->

                selectedItem = item

                bottomSheetBinding.textExcludeGroupContent.text = context?.getString(R.string.exclude_groups, item.nome)

                bottomSheetDialog.show()

                Toast.makeText(
                    context,
                    "GroupsAdapter itemLongClicked $item",
                    Toast.LENGTH_SHORT
                ).show()
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
        bindingBottomSheetToExcludeGroup()

        binding.floatingActionButton.setOnClickListener {
            val createGroupFragment = CreateGroupFragment()
            createGroupFragment.show(
                childFragmentManager,
                "CreateGroupFragment"
            )
        }

        observeUiState()
        observeSharedUiState()

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
                    viewModel.getAll()
                    Toast.makeText(
                        context,
                        "Groups GroupViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
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

    fun bindingBottomSheetToExcludeGroup() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetExcludeGroupBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.buttonExcludeGroup.setOnClickListener {
            // Verifique se existe um item selecionado
            selectedItem?.let { selectedGroup ->
                // Faça o que você precisa fazer com o item selecionado
                // Por exemplo, chame o método para excluir o grupo
                viewModel.deleteGroup(selectedGroup.id)
                // Limpe o item selecionado após a ação
                selectedItem = null
                // Feche o BottomSheetDialog
                bottomSheetDialog.dismiss()
            }
        }
    }

    private fun observeSharedUiState() {
        sharedViewModel.updateGroups.observe(viewLifecycleOwner) {
            viewModel.getAll()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}