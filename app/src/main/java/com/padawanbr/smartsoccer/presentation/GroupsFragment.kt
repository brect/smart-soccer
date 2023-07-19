package com.padawanbr.smartsoccer.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetCreateGroupBinding
import androidx.navigation.fragment.findNavController
import com.padawanbr.smartsoccer.databinding.FragmentGroupsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GroupsFragment : Fragment() {

    private var _binding: FragmentGroupsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetCreateGroupBinding? = null
    private val bottomSheetBinding: BottonsheetCreateGroupBinding get() = _bottomSheetBinding!!

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

        bindingBottomSheetToCreateGroup()

        binding.floatingActionButton.setOnClickListener {
            bottomSheetDialog.show()
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.floatingActionButton)
                .setAction("Action", null).show()
        }

        observeUiState()
    }

    private fun observeUiState() {
//        viewModel.run {
//
//        }
    }

        private fun bindingBottomSheetToCreateGroup() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetCreateGroupBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        // Access elements in the layout using ViewBinding
        bottomSheetBinding.buttonCreateGroup.setOnClickListener {
            Toast.makeText(context, "Category buttonSaveCategory", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_GroupsFragment_to_SoccerPlayerFragment)
            bottomSheetDialog.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}