package com.padawanbr.smartsoccer.presentation.ui.soccerPlayer

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.padawanbr.smartsoccer.core.domain.model.PosicaoJogador
import com.padawanbr.smartsoccer.databinding.FragmentDetailsSoccerPlayerBinding
import com.padawanbr.smartsoccer.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.presentation.validation.formfields.disable
import com.padawanbr.smartsoccer.presentation.validation.formfields.enable
import com.padawanbr.smartsoccer.presentation.validation.formfields.validate
import com.padawanbr.smartsoccer.presentation.validation.managers.DetailsSoccerPlayerFormFieldManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks

@AndroidEntryPoint
class DetailsSoccerPlayerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentDetailsSoccerPlayerBinding? = null
    private val binding: FragmentDetailsSoccerPlayerBinding get() = _binding!!

    private val viewModel: DetailsSoccerPlayerViewModel by viewModels()
    private val sharedViewModel: SharedSoccerPlayerViewModel by activityViewModels()

    private lateinit var formFieldManager: DetailsSoccerPlayerFormFieldManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsSoccerPlayerBinding.inflate(inflater, container, false)
        formFieldManager = DetailsSoccerPlayerFormFieldManager(binding, lifecycleScope)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFormFields()

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.root.parent as View)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.isDraggable = true

        initSpinnerDetailsPlayerAdapter()

        ratingBarAbilityBinding()

        val isEditing = arguments?.getBoolean("isEditing", false)

        binding.flipperExcludeItem.visibility = View.GONE

        if (isEditing == true) {
            binding.flipperExcludeItem.visibility = View.VISIBLE
            populatePlayerDetails()
        }

        binding.buttonSaveItem.clicks().onEach {
            submit()
        }.launchIn(lifecycleScope)

        binding.buttonExcludeItem.setOnClickListener {
            deleteSoccerPlayer()
        }

        observeUiState()
    }

    private fun deleteSoccerPlayer() {
        val playerId = arguments?.getString("id")
        if (playerId.isNullOrBlank()) {
            showShortToast("Jogador inválido ou não encontrado")
        } else {
            viewModel.deleteSoccerPlayer(playerId)
        }
    }

    private fun posicaoJogador(selectedPosition: String): PosicaoJogador? {
        val playerPositionString = selectedPosition.substringBefore("(").trim()
        return PosicaoJogador.values().find { it.funcao == playerPositionString }
    }

    private fun initFormFields() {
        formFieldManager.formFields
    }

    private fun submit() = lifecycleScope.launch {
        binding.buttonSaveItem.isEnabled = false

        formFieldManager.formFields.disable()
        if (formFieldManager.formFields.validate(validateAll = true)) {

            val grupoId = arguments?.getString("grupoId", "") ?: ""
            val soccerId = arguments?.getString("id", "")
            val playerAbilitiesMap = createPlayerAbilitiesMap()
            val playerIsInDM = binding.switchDetailsPlayerDM.isChecked

            viewModel.saveSoccerPlayer(
                soccerId,
                formFieldManager.fieldPlayerName.value.toString(),
                formFieldManager.fieldPlayerAge.value.toString().toInt(),
                posicaoJogador(formFieldManager.fieldPlayerPosition.value.toString()),
                playerAbilitiesMap,
                playerIsInDM,
                grupoId,
            )
        }

        formFieldManager.formFields.enable()
        binding.buttonSaveItem.isEnabled = true
    }

    private fun populatePlayerDetails() {
        val nome = arguments?.getString("nome")
        val idade = arguments?.getInt("idade")
        val estaNoDepartamentoMedico =
            arguments?.getBoolean("estaNoDepartamentoMedico", false) ?: false
        val selectedPosition = arguments?.getString("posicao")
        val habilidades = arguments?.getSerializable("habilidades") as? Map<String, Float>

        binding.editTextTextInputDetailsPlayerName.setText(nome)
        binding.editTextTextInputDetailsPlayerAge.setText(idade.toString())
        binding.switchDetailsPlayerDM.isChecked = estaNoDepartamentoMedico

        val posicaoJogador = PosicaoJogador.fromString(selectedPosition)
        binding.editTextTextInputDetailsPlayerPosition.setText(
            "${posicaoJogador?.funcao} (${posicaoJogador?.abreviacao})",
            false
        )

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

    private fun initAbilityViewBindings(): List<AbilityViewBinding> {
        return listOf(
            AbilityViewBinding(
                binding.itemPlayerRatingSpeed.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingSpeed.ratingBarAbility
            ),
            AbilityViewBinding(
                binding.itemPlayerRatingKick.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingKick.ratingBarAbility
            ),
            AbilityViewBinding(
                binding.itemPlayerRatingPass.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingPass.ratingBarAbility
            ),
            AbilityViewBinding(
                binding.itemPlayerRatingMarking.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingMarking.ratingBarAbility
            ),
            AbilityViewBinding(
                binding.itemPlayerRatingDribble.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingDribble.ratingBarAbility
            ),
            AbilityViewBinding(
                binding.itemPlayerRatingRace.textViewSoccerPlayerAbility,
                binding.itemPlayerRatingRace.ratingBarAbility
            )
        )
    }

    private fun createPlayerAbilitiesMap(): Map<String, Float> {
        val playerAbilitiesMap = mutableMapOf<String, Float>()

        val abilityViewBindings = initAbilityViewBindings()

        abilityViewBindings.forEach { abilityViewBinding ->
            val abilityName = abilityViewBinding.textView.text.toString()
            val abilityRating = abilityViewBinding.ratingBar.rating.takeIf { it > 0 } ?: 0f
            playerAbilitiesMap[abilityName] = abilityRating
        }

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
        val detailsPlayerPositions = PosicaoJogador.values().map { posicao ->
            "${posicao.funcao} (${posicao.abreviacao})"
        }.toMutableList()

        val adapterDetailsPlayerPositions =
            ArrayAdapter(requireContext(), R.layout.simple_list_item_1, detailsPlayerPositions)
        binding.editTextTextInputDetailsPlayerPosition.setAdapter(adapterDetailsPlayerPositions)
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is DetailsSoccerPlayerViewModel.UiState.Error -> {
                    showShortToast(uiState.message)
                }

                DetailsSoccerPlayerViewModel.UiState.Loading -> {
                    showShortToast("Carregando...")
                }

                is DetailsSoccerPlayerViewModel.UiState.Success -> {
                    atualizaListaDeJogadores()
                    showShortToast("Jogador adicionado com sucesso!")
                }

                DetailsSoccerPlayerViewModel.UiState.Delete -> {
                    atualizaListaDeJogadores()
                    showShortToast("Jogador deletado com sucesso!")
                }

                is DetailsSoccerPlayerViewModel.UiState.Error -> TODO()
            }
        }
    }

    private fun atualizaListaDeJogadores() {
        sharedViewModel.updateSoccerPlayers(true)
        this.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
