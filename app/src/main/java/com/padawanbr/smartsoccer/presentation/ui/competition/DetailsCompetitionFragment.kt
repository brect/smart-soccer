package com.padawanbr.smartsoccer.presentation.ui.competition

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.databinding.BottonsheetSharedTeamBinding
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import com.padawanbr.smartsoccer.presentation.utils.PermissionsUtil
import com.padawanbr.smartsoccer.presentation.utils.PermissionsUtil.REQUEST_EXTERNAL_STORAGE_CODE
import com.padawanbr.smartsoccer.presentation.utils.PermissionsUtil.checkPermissions
import com.padawanbr.smartsoccer.presentation.utils.PermissionsUtil.requestPermissionsIfDanied
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.getBitmapsFromRecyclerView
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.saveBitmapsToGallery
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.shareBitmapList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCompetitionFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDetailsCompetitionBinding? = null
    private val binding: FragmentDetailsCompetitionBinding get() = _binding!!

    private val viewModel: DetailsCompetitionViewModel by viewModels()

    private val args by navArgs<DetailsCompetitionFragmentArgs>()

    private lateinit var bottomSheetDialogSharedTeam: BottomSheetDialog

    lateinit var torneio: Torneio

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

        bindingBottomSharedCompetitions()

        viewModel.getCompetition(args.competitionId)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is DetailsCompetitionViewModel.UiState.Error -> {
                    showShortToast("Erro ao carregar as informações")
                }

                is DetailsCompetitionViewModel.UiState.Loading -> {
                    showShortToast("Carregando...")
                }

                is DetailsCompetitionViewModel.UiState.Success -> {
                    binding.recyclerViewDetailsSoccerTeams.run {
                        setHasFixedSize(true)
                        torneio = uiState.torneio
                        adapter = CompetitionTeamParentAdapter(torneio.times)
                    }
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
        menu.findItem(R.id.action_share_teams).icon?.setTint(requireContext().getColor(R.color.md_theme_light_onPrimaryContainer))
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_share_teams -> {
                when (checkPermissions(
                    requireActivity(),
                    PermissionsUtil.permissionsExternalStorage
                )) {
                    true -> {
                        requestPermissionsIfDanied(
                            requireActivity(),
                            PermissionsUtil.permissionsExternalStorage,
                            REQUEST_EXTERNAL_STORAGE_CODE
                        )
                    }

                    false -> {
                        bottomSheetDialogSharedTeam.show()
                    }
                }
                true
            }

            else -> false
        }
    }

    private fun createBottomSheetDialog(binding: ViewBinding): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        return bottomSheetDialog
    }

    private fun setButtonClickListener(
        button: Button,
        bottomSheetDialog: BottomSheetDialog?,
        onClickListener: () -> Unit
    ) {
        button.setOnClickListener {
            onClickListener.invoke()
            bottomSheetDialog?.dismiss()
        }
    }

    private fun bindingBottomSharedCompetitions() {
        val recyclerView = binding.recyclerViewDetailsSoccerTeams

        val bottomSheetSharedTeamBinding = BottonsheetSharedTeamBinding.inflate(layoutInflater)
        bottomSheetDialogSharedTeam = createBottomSheetDialog(bottomSheetSharedTeamBinding)

        setButtonClickListener(
            bottomSheetSharedTeamBinding.buttonSharedTeams,
            bottomSheetDialogSharedTeam
        ) {
            onClickToShare(recyclerView)
        }

        setButtonClickListener(
            bottomSheetSharedTeamBinding.buttonSaveTeams,
            bottomSheetDialogSharedTeam
        ) {
            onClickToSave(recyclerView)
        }
    }

    private fun onClickToSave(recyclerView: RecyclerView) {
        val bitmapsFromRecyclerView = getBitmapsFromRecyclerView(recyclerView)
        saveBitmapsToGallery(requireContext(), bitmapsFromRecyclerView, torneio.id)
    }

    private fun onClickToShare(recyclerView: RecyclerView) {
        val bitmapsFromRecyclerView = getBitmapsFromRecyclerView(recyclerView)
        shareBitmapList(requireContext(), bitmapsFromRecyclerView)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_EXTERNAL_STORAGE_CODE) {
            // Checking whether user granted the permission or not.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showShortToast("Permissão de armazenamento concedida! 😊")
            } else {
                showShortToast("Permissão de armazenamento negada. 😢")
            }
        }
    }
}
