package com.padawanbr.smartsoccer.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentDetailsGroupBinding
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.init
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.rotateFab
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showIn
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showOut
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsGroupFragment : Fragment() {

    private var _binding: FragmentDetailsGroupBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsGroupFragmentArgs>()

    var isRotate: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDetailsGroupBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val groupName = args.detailsGroupViewArgs?.nome
        groupName?.let { setToolbarTitle(it) }

        binding.textViewSoccerPlayerGroupName.text = groupName

        configureFabMoreOptions()
    }

    private fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    private fun configureFabMoreOptions() {

        init(binding.fabAddSoccerPlayer)
        init(binding.textViewAddSoccerPlayer)

        binding.fabMoreOptions.setOnClickListener {

            isRotate = rotateFab(it, !isRotate)

            if(isRotate){
                showIn(binding.fabAddSoccerPlayer)
                showIn(binding.textViewAddSoccerPlayer)
            } else {
                showOut(binding.fabAddSoccerPlayer)
                showOut(binding.textViewAddSoccerPlayer)
            }
        }

        binding.fabAddSoccerPlayer.setOnClickListener {
            val directions = DetailsGroupFragmentDirections.actionDetailsGroupFragmentToSoccerPlayerFragment()

            if (args.detailsGroupViewArgs != null) {
                directions.groupId = it.id
            }

            findNavController().navigate(directions)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}