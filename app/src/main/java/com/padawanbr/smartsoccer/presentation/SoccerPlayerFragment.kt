package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import com.padawanbr.smartsoccer.databinding.FragmentSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SoccerPlayerFragment : Fragment() {

    private var _binding: FragmentSoccerPlayerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args by navArgs<SoccerPlayerFragmentArgs>()

    private val soccerPlayersAdapter by lazy {
        getCommonAdapterOf(
            { SoccerPlayerViewHolder.create(it) },
            { item: JogadorItem ->
                Toast.makeText(context, "productsAdapter $item", Toast.LENGTH_SHORT).show()
            }
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSoccerPlayerBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (args.isEditing) {
            Toast.makeText(
                context,
                "Modo edição de grupos",
                Toast.LENGTH_SHORT
            ).show()

            binding.textViewSoccerPlayerGroupName.text = args.grupoItemViewArgs?.nome
        }

        binding.floatingActionButtonAddSoccer.setOnClickListener {
            val detailsSoccerPlayerFragment = DetailsSoccerPlayerFragment()
            detailsSoccerPlayerFragment.show(childFragmentManager, "DetailsSoccerPlayerFragment")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}