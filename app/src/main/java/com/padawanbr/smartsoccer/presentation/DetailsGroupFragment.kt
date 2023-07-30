package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.padawanbr.smartsoccer.databinding.FragmentDetailsGroupBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsGroupFragment : Fragment() {

    private var _binding: FragmentDetailsGroupBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsGroupFragmentArgs>()

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


        binding.textViewSoccerPlayerGroupName.text = args.detailsGroupViewArgs?.nome


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}