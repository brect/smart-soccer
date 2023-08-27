package com.padawanbr.smartsoccer.presentation.ui.competition

import android.Manifest.permission.READ_MEDIA_IMAGES
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.LruCache
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil
import com.padawanbr.smartsoccer.presentation.common.PermissionsUtil.Companion.REQUEST_WRITE_EXTERNAL_STORAGE_CODE
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.getScreenshotFromRecyclerView
import com.padawanbr.smartsoccer.presentation.utils.ImageUtils.shareImageAndText
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream


@AndroidEntryPoint
class DetailsCompetitionFragment : Fragment(), MenuProvider {

    private var _binding: FragmentDetailsCompetitionBinding? = null
    private val binding: FragmentDetailsCompetitionBinding get() = _binding!!

    private val viewModel: DetailsCompetitionViewModel by viewModels()

    private val args by navArgs<DetailsCompetitionFragmentArgs>()

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
                        adapter = CompetitionTeamParentAdapter(uiState.torneio.times)
                    }

//                    Toast.makeText(
//                        context,
//                        "DetailsCompetitionViewModel.UiState.Success",
//                        Toast.LENGTH_SHORT
//                    ).show()
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


                activity?.let {
                    if (it != null) {
                        val permissionsUtil = PermissionsUtil()
                        if (permissionsUtil.checkSelfPermission(it, READ_MEDIA_IMAGES)
                        ) {
                            // Requesting the permission
                            permissionsUtil.requestPermissionIfDanied(
                                it,
                                READ_MEDIA_IMAGES,
                                REQUEST_WRITE_EXTERNAL_STORAGE_CODE
                            )
                        } else {

                            val recyclerView = binding.recyclerViewDetailsSoccerTeams

                            val createBitmapFromView = getScreenshotFromRecyclerView(recyclerView)
                            shareImageAndText(requireContext(), createBitmapFromView)
                        }
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

        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE_CODE) {

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