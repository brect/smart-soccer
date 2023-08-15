package com.padawanbr.smartsoccer.presentation.ui.competition

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.padawanbr.smartsoccer.databinding.FragmentDetailsCompetitionBinding
import com.padawanbr.smartsoccer.databinding.FragmentGroupBinding

class DetailsCompetitionFragment : Fragment() {

    private var _binding: FragmentDetailsCompetitionBinding? = null
    private val binding: FragmentDetailsCompetitionBinding get() = _binding!!


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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}