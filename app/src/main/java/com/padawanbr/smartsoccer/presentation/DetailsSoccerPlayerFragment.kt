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
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
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

        val grupoId = arguments?.getInt("grupoId", -1) ?: -1
        val isEditing = arguments?.getBoolean("isEditing", false)

        binding.flipperExcludeItem.visibility = View.GONE

        if (isEditing == true) {
            binding.flipperExcludeItem.visibility = View.VISIBLE
            populatePlayerDetails()
        }

        binding.buttonSaveItem.setOnClickListener {

            val playerName = binding.editTextTextInputDetailsPlayerName.text.toString()
            val playerAge = binding.editTextTextInputDetailsPlayerAge.text.toString().toInt()

            // Obtenha o enum PosicaoJogador selecionado no Spinner
            val selectedPosition = spinnerDetailsPlayerPosition.selectedItem as String
            val playerPositionString = selectedPosition.substringBefore("(")
                .trim() // Obtém apenas a posição, ignorando a abreviação
            val playerPosition = PosicaoJogador.values().find { it.funcao == playerPositionString }

            val playerAbilitiesMap = createPlayerAbilitiesMap()

            val playerIsInDM = binding.switchDetailsPlayerDM.isChecked

            viewModel.createSoccer(
                grupoId,
                playerName,
                playerAge,
                playerPosition,
                playerAbilitiesMap,
                playerIsInDM
            )

            Toast.makeText(context, "buttonSaveItem", Toast.LENGTH_SHORT).show()
        }

        observeUiState()

    }

    private fun populatePlayerDetails() {
        val nome = arguments?.getString("nome")
        val idade = arguments?.getInt("idade")
        val estaNoDepartamentoMedico = arguments?.getBoolean("estaNoDepartamentoMedico", false) ?: false
        val selectedPosition = arguments?.getString("posicao")
        val habilidades = arguments?.getSerializable("habilidades") as? Map<String, Float>

        binding.editTextTextInputDetailsPlayerName.setText(nome)
        binding.editTextTextInputDetailsPlayerAge.setText(idade.toString())
        binding.switchDetailsPlayerDM.isChecked = estaNoDepartamentoMedico

        val posicaoJogador = PosicaoJogador.fromString(selectedPosition)
        val positionIndex = PosicaoJogador.values().indexOf(posicaoJogador)
        spinnerDetailsPlayerPosition.setSelection(positionIndex)

        setPlayerAbilities(habilidades)
    }

    private fun setPlayerAbilities(habilidades: Map<String, Float>?) {
        habilidades?.let {
            // Aqui, você precisa substituir os IDs de TextView e RatingBar pelos IDs reais do seu layout
            binding.itemPlayerRatingSpeed.textViewSoccerPlayerScore.text =
                it["Velocidade"]?.toString() ?: "0.0"
            binding.itemPlayerRatingSpeed.ratingBarAbility.rating =
                it["Velocidade"] ?: 0.0f

            binding.itemPlayerRatingKick.textViewSoccerPlayerScore.text =
                it["Chute"]?.toString() ?: "0.0"
            binding.itemPlayerRatingKick.ratingBarAbility.rating =
                it["Chute"] ?: 0.0f

            binding.itemPlayerRatingPass.textViewSoccerPlayerScore.text =
                it["Passe"]?.toString() ?: "0.0"
            binding.itemPlayerRatingPass.ratingBarAbility.rating =
                it["Passe"] ?: 0.0f

            binding.itemPlayerRatingMarking.textViewSoccerPlayerScore.text =
                it["Marcação"]?.toString() ?: "0.0"
            binding.itemPlayerRatingMarking.ratingBarAbility.rating =
                it["Marcação"] ?: 0.0f

            binding.itemPlayerRatingDribble.textViewSoccerPlayerScore.text =
                it["Drible"]?.toString() ?: "0.0"
            binding.itemPlayerRatingDribble.ratingBarAbility.rating =
                it["Drible"] ?: 0.0f

            binding.itemPlayerRatingRace.textViewSoccerPlayerScore.text =
                it["Raça"]?.toString() ?: "0.0"
            binding.itemPlayerRatingRace.ratingBarAbility.rating =
                it["Raça"] ?: 0.0f
        }
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

        val detailsPlayerPositions = PosicaoJogador.values().map { posicao ->
            "${posicao.funcao} (${posicao.abreviacao})"
        }.toMutableList()

        adapterDetailsPlayerPosition =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsPlayerPositions)

        spinnerDetailsPlayerPosition.adapter = adapterDetailsPlayerPosition

        spinnerDetailsPlayerPosition.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    // Obtenha o enum PosicaoJogador selecionado no Spinner
                    val selectedPosition = PosicaoJogador.values()[p2]
                    // Agora você pode usar o enum PosicaoJogador diretamente, por exemplo:
                    val posicao = selectedPosition.funcao // Nome da posição
                    val abreviacao = selectedPosition.abreviacao // Abreviação da posição
                    // Outras ações que você deseja realizar com o enum PosicaoJogador
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    // Lida com o caso em que nada foi selecionado
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
