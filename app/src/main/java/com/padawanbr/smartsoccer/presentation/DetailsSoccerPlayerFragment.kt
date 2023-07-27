package com.padawanbr.smartsoccer.presentation

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsSoccerPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsSoccerPlayerBinding? = null
    private val binding: FragmentDetailsSoccerPlayerBinding get() = _binding!!

    private val viewModel: DetailsSoccerPlayerViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

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
        initSpinnerDetailsPlayerAdapter()

        ratingBarAbilityBinding()

        binding.buttonSaveItem.setOnClickListener {

            val grupoId = arguments?.getInt("grupoId", -1) ?: -1

            val playerName = binding.editTextTextInputDetailsPlayerName.text.toString()
            val playerAge = binding.editTextTextInputDetailsPlayerAge.text.toString().toInt()

            val playerAbilitiesMap = createPlayerAbilitiesMap()

            val playerIsInDM = binding.switchDetailsPlayerDM.isChecked

            viewModel.createSoccer(
                grupoId,
                playerName,
                playerAge,
                playerAbilitiesMap,
                playerIsInDM
            )

            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }

        observeUiState()

    }

    private fun createPlayerAbilitiesMap(): Map<String, Float> {
        val playerAbilitiesMap = mutableMapOf<String, Float>()

        playerAbilitiesMap[binding.itemPlayerRatingSpeed.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingSpeed.ratingBarAbility.rating
        playerAbilitiesMap[binding.itemPlayerRatingKick.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingKick.ratingBarAbility.rating
        playerAbilitiesMap[binding.itemPlayerRatingPass.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingPass.ratingBarAbility.rating
        playerAbilitiesMap[binding.itemPlayerRatingMarking.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingMarking.ratingBarAbility.rating
        playerAbilitiesMap[binding.itemPlayerRatingDribble.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingDribble.ratingBarAbility.rating
        playerAbilitiesMap[binding.itemPlayerRatingRace.textViewSoccerPlayerAbility.text.toString()] =
            binding.itemPlayerRatingRace.ratingBarAbility.rating

        return playerAbilitiesMap
    }

    private fun ratingBarAbilityBinding() {
        // Criar uma lista com os IDs dos itens para facilitar o loop
        val itemBindings = listOf(
            binding.itemPlayerRatingSpeed,
            binding.itemPlayerRatingKick,
            binding.itemPlayerRatingPass,
            binding.itemPlayerRatingMarking,
            binding.itemPlayerRatingDribble,
            binding.itemPlayerRatingRace
        )

        // Criar uma lista com as habilidades correspondentes
        val abilities = listOf(
            "Velocidade",
            "Chute",
            "Passe",
            "Marcação",
            "Drible",
            "Raça"
        )

        // Loop para atribuir as labels e atualizar os valores dos TextViews
        for (i in itemBindings.indices) {
            val itemBinding = itemBindings[i]
            itemBinding.soccerPlayerAbility = abilities[i]

            val textViewScore = itemBinding.textViewSoccerPlayerScore
            textViewScore.text = String.format("%.1f", itemBinding.ratingBarAbility.rating)

            // Configurar o setOnRatingBarChangeListener para cada RatingBar individualmente
            itemBinding.ratingBarAbility.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                textViewScore.text = String.format("%.1f", rating)
            }
        }
    }

    private fun initSpinnerDetailsPlayerAdapter() {
        spinnerDetailsPlayerPosition = binding.spinnerDetailsPlayerPosition

        val detailsPlayerPositions =
            arrayOf("Selecione uma posição", "posição 1", "posição 2", "posição 3")

        adapterDetailsPlayerPosition =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsPlayerPositions)

        spinnerDetailsPlayerPosition.adapter = adapterDetailsPlayerPosition

        spinnerDetailsPlayerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
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

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                DetailsSoccerPlayerViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "Category GroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsSoccerPlayerViewModel.UiState.Loading -> {
                    Toast.makeText(
                        context,
                        "Category GroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                DetailsSoccerPlayerViewModel.UiState.ShowEmptySoccers -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.ShowEmptySoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is DetailsSoccerPlayerViewModel.UiState.ShowSoccers -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.ShowSoccers",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is DetailsSoccerPlayerViewModel.UiState.Success -> {
                    Toast.makeText(
                        context,
                        "DetailsSoccerPlayerViewModel.UiState.Success",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Atualize a lista de jogadores no SoccerPlayerFragment
                    sharedViewModel.updateSoccerPlayers(true)

                    this.dismiss()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
