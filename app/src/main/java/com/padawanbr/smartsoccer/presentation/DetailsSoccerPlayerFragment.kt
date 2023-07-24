package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSoccerPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsSoccerPlayerBinding? = null
    private val binding: FragmentDetailsSoccerPlayerBinding get() = _binding!!

    private lateinit var spinnerDetailsPlayerPosition: Spinner
    private lateinit var adapterDetailsPlayerPosition: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsSoccerPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        spinnerDetailsPlayerPosition = binding.spinnerDetailsPlayerPosition

        val detailsPlayerPositions = arrayOf("Selecione uma posição", "posição 1", "posição 2", "posição 3")

        adapterDetailsPlayerPosition = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, detailsPlayerPositions)

        spinnerDetailsPlayerPosition.adapter = adapterDetailsPlayerPosition

        spinnerDetailsPlayerPosition.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                // You can define you actions as you want
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                val selectedObject = spinnerDetailsPlayerPosition.selectedItem as String
                Toast.makeText(
                    requireContext(),
                    "ID: ${selectedObject} Name: ${selectedObject}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
