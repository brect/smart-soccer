package com.padawanbr.smartsoccer.presentation.ui.home

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
import com.padawanbr.smartsoccer.databinding.FragmentHomeBinding
import com.padawanbr.smartsoccer.presentation.ui.groups.DetailsGroupFragment
import com.padawanbr.smartsoccer.presentation.ui.groups.GrupoItem
import com.padawanbr.smartsoccer.presentation.ui.groups.SharedGroupsViewModel
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.ui.groups.DetalheGrupoItemViewArgs
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetExcludeGroupBinding? = null
    private val bottomSheetBinding: BottonsheetExcludeGroupBinding get() = _bottomSheetBinding!!

    private var selectedItem: GrupoItem? = null

    private val groupsAdapter by lazy {
        getCommonAdapterOf(
            { HomeItemViewHolder.create(it) },
            { item: GrupoItem ->
                val directions =
                    HomeFragmentDirections.actionGroupsFragmentToDetailsGroupFragment()

                directions.detalheGrupoItemViewArgs = DetalheGrupoItemViewArgs(
                    item.id,
                    item.nome,
                    item.quantidadeTimes,
                    item.configuracaoEsporte.tipoEsporte
                )

                findNavController().navigate(directions)
            },
            { item: GrupoItem ->

                selectedItem = item

                bottomSheetBinding.textExcludeGroupContent.text =
                    context?.getString(R.string.exclude_groups, item.nome)

                bottomSheetDialog.show()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(
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
            val directions =
                HomeFragmentDirections.actionGroupsFragmentToCreateDetailsGroupFragment()
            findNavController().navigate(directions)
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
            binding.fliperGroup.displayedChild = when (uiState) {
                is HomeViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "Groups GroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                    FLIPPER_CHILD_LOADING
                }

                is HomeViewModel.UiState.ShowGroups -> {
                    groupsAdapter.submitList(uiState.groups)
                    FLIPPER_CHILD_SUCCESS
                }

                is HomeViewModel.UiState.Success -> {
                    viewModel.getAll()
                    Toast.makeText(
                        context,
                        "Groups GroupViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    FLIPPER_CHILD_SUCCESS
                }

                is HomeViewModel.UiState.ShowEmptyGroups -> {
                    groupsAdapter.submitList(emptyList())
                    FLIPPER_CHILD_EMPTY
                }

                is HomeViewModel.UiState.Error -> {
                    FLIPPER_CHILD_ERROR
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

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_SUCCESS = 1
        private const val FLIPPER_CHILD_EMPTY = 2
        private const val FLIPPER_CHILD_ERROR = 3
    }

}