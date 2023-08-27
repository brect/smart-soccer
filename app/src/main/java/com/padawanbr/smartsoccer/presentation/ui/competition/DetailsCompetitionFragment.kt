package com.padawanbr.smartsoccer.presentation.ui.competition

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.databinding.BottonsheetSharedTeamBinding
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil.REQUEST_EXTERNAL_STORAGE_CODE
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil.checkPermissions
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil.requestPermissionsIfDanied
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.getBitmapsFromRecyclerView
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.getScreenshotFromRecyclerView
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.saveBitmapsToGallery
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.shareBitmapList
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.shareImageAndText
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsCompetitionFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDetailsCompetitionBinding? = null
    private val binding: FragmentDetailsCompetitionBinding get() = _binding!!

    private val viewModel: DetailsCompetitionViewModel by viewModels()

    private val args by navArgs<DetailsCompetitionFragmentArgs>()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetSharedTeamBinding? = null
    private val bottomSheetBinding: BottonsheetSharedTeamBinding get() = _bottomSheetBinding!!

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
//                    Toast.makeText(
//                        context,
//                        "DetailsCompetitionViewModel.UiState.Error",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }

                is DetailsCompetitionViewModel.UiState.Loading -> {
//                    Toast.makeText(
//                        context,
//                        "DetailsCompetitionViewModel.UiState.Loading",
//                        Toast.LENGTH_SHORT
//                    ).show()
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

    fun bindingBottomSharedCompetitions() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetSharedTeamBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        val recyclerView = binding.recyclerViewDetailsSoccerTeams

        bottomSheetBinding.buttonSharedTeams.setOnClickListener {
//            val createBitmapFromView = getScreenshotFromRecyclerView(recyclerView)
//            shareImageAndText(requireContext(), createBitmapFromView)
            val bitmapsFromRecyclerView = getBitmapsFromRecyclerView(recyclerView)
            shareBitmapList(requireContext(), bitmapsFromRecyclerView)

            bottomSheetDialog.dismiss()
        }

        bottomSheetBinding.buttonSaveTeams.setOnClickListener {
            val bitmapsFromRecyclerView = getBitmapsFromRecyclerView(recyclerView)
            saveBitmapsToGallery(requireContext(), bitmapsFromRecyclerView, torneio.id)

            bottomSheetDialog.dismiss()
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_share_teams -> {
                when (checkPermissions(requireActivity(), PermissionsUtil.permissionsExternalStorage)) {
                    true -> {
                        requestPermissionsIfDanied(
                            requireActivity(),
                            PermissionsUtil.permissionsExternalStorage,
                            REQUEST_EXTERNAL_STORAGE_CODE
                        )
                    }

                    false -> {
                        bottomSheetDialog.show()
                    }
                }
                true
            }
            else -> false
        }
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

                // Showing the toast message
                Toast.makeText(
                    requireContext(),
                    "EXTERNAL_STORAGE Permission Granted",
                    Toast.LENGTH_SHORT
                )
                    .show();
            } else {
                Toast.makeText(
                    requireContext(),
                    "EXTERNAL_STORAGE Permission Denied",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

}