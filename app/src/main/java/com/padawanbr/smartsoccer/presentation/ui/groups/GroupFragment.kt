package com.padawanbr.smartsoccer.presentation.ui.groups

import ImagePickerUtils.PICK_IMAGE_REQUEST
import ImagePickerUtils.loadImageFromPreferences
import ImagePickerUtils.openGallery
import ImagePickerUtils.saveImageUri
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.alfred.app.presentation.extensions.showShortToast
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetExcludeCompetitionBinding
import com.padawanbr.smartsoccer.databinding.FragmentGroupBinding
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.init
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.rotateView
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showIn
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showOut
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.extensions.roundToTwoDecimalPlaces
import com.padawanbr.smartsoccer.presentation.ui.competition.CompetitionItem
import com.padawanbr.smartsoccer.presentation.ui.competition.ItemCompetitionViewHolder
import com.padawanbr.smartsoccer.presentation.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupFragment : Fragment(), MenuProvider {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()
    private val sharedViewModel: SharedGroupsViewModel by activityViewModels()

    private val args by navArgs<GroupFragmentArgs>()

    private var isRotate: Boolean = false

    lateinit var grupo: GrupoComJogadoresItem

    private lateinit var bottomSheetDialogExcludeCompetition: BottomSheetDialog
    private var _bottonsheetExcludeCompetitionBinding: BottonsheetExcludeCompetitionBinding? = null
    private val bottonsheetExcludeCompetitionBinding: BottonsheetExcludeCompetitionBinding get() = _bottonsheetExcludeCompetitionBinding!!

    private var competitionItemClicked: CompetitionItem? = null

    private val competitionsAdapter by lazy {
        getCommonAdapterOf(
            { ItemCompetitionViewHolder.create(it) },
            {
                val directions =
                    GroupFragmentDirections.actionDetailsGroupFragmentToDetailsCompetitionFragment()

                directions.competitionId = it.id

                findNavController().navigate(directions)

                Toast.makeText(
                    context,
                    "competitionsAdapter itemClicked",
                    Toast.LENGTH_SHORT
                ).show()
            },
            { item ->

                competitionItemClicked = item
                bottonsheetExcludeCompetitionBinding.textExcludeCompetitionContent.text =
                    context?.getString(R.string.exclude_competition, item.nome)

                bottomSheetDialogExcludeCompetition.show()
            }
        )
    }

    private val playersInfoAdapter by lazy {
        getCommonAdapterOf(
            { ItemGroupPlayersInfoViewHolder.create(it) },
            {
                Toast.makeText(
                    context,
                    "competitionsAdapter itemClicked",
                    Toast.LENGTH_SHORT
                ).show()
            },
            {
                Toast.makeText(
                    context,
                    "competitionsAdapter itemLongClicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentGroupBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(
            binding.recyclerViewItemCompetition,
            competitionsAdapter,
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )

        initRecyclerView(
            binding.recyclerViewSoccerPlayersInfo,
            playersInfoAdapter,
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        args.detalheGrupoItemViewArgs?.nome?.let { setToolbarTitle(it) }

        initFabs()

        configureFabMoreOptions()
        fabAddSoccerPlayerOnClick()
        fabCreateQuickCompetitionOnClick()

        bindingBottomSharedCompetitions()

        observeUiState()
        observeSharedUiState()

        binding.imageViewGroupProfile.setOnClickListener {
            openGallery(this)
        }
    }

    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                GroupViewModel.UiState.Error -> {
                    Toast.makeText(
                        context,
                        "DetailsGroupViewModel.UiState.Error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                GroupViewModel.UiState.Loading -> {
                    binding.flipperItemCompetition.displayedChild = FLIPPER_CHILD_COMPETITION_EMPTY
                    competitionsAdapter.submitList(emptyList())
                }

                is GroupViewModel.UiState.Success -> {
                    grupo = uiState.grupo

                    loadImageFromPreferences(
                        requireContext(),
                        grupo.id,
                        binding.imageViewGroupProfile
                    )

                    if (grupo.torneios?.size!! > 0) {
                        binding.flipperItemCompetition.displayedChild =
                            FLIPPER_CHILD_COMPETITION_SUCCESS
                        competitionsAdapter.submitList(grupo.torneios)
                    }

                    binding.textViewGroupTeamName.text = uiState.grupo.nome
                    binding.textViewGroupDate.text = "${grupo.diaDoJogo}"
                    binding.textViewGroupLocal.text = "${grupo.endereco}"

                    binding.textViewGameInformationTypeOfCourt.text =
                        uiState.grupo.configuracaoEsporte.tipoEsporte.tipo
                    val quantidadeMinimaPorTime =
                        uiState.grupo.configuracaoEsporte.quantidadeMinimaPorTime

                    binding.textViewGameInformationConfiguration.text = context?.getString(
                        R.string.game_information_configuration,
                        quantidadeMinimaPorTime.toString(),
                        quantidadeMinimaPorTime.toString()
                    )

                    binding.textViewGameInformationMonthlyPrice.text = context?.getString(
                        R.string.game_information_monthly_price,
                        00.00.toString()
                    )

                    binding.textViewGameInformationRateAge.text = context?.getString(
                        R.string.game_information_rate_age,
                        grupo.rangeIdade?.minAge.toString(),
                        grupo.rangeIdade?.maxAge.toString(),
                    )

                    setSoccerPlayersInfos(uiState)

                }

                GroupViewModel.UiState.SuccessDeleteCompetition -> {
                    getGroupItemView()
                    showShortToast("Competição deletada com sucesso")
                }
            }
        }
    }

    private fun observeSharedUiState() {
        sharedViewModel.updateGroups.observe(viewLifecycleOwner) {
            getGroupItemView()
        }
    }

    private fun setSoccerPlayersInfos(uiState: GroupViewModel.UiState.Success) {
        val jogadoresDisponiveis = uiState.grupo.jogadoresDisponiveis
        val jogadoresNoDM = uiState.grupo.jogadoresNoDM
        val mediaJogadores = uiState.grupo.mediaJogadores
        val jogadoresGrupo = jogadoresNoDM?.let { jogadoresDisponiveis?.plus(it) }

        val soccerPlayersInfo = createSoccerPlayerInfoList(
            jogadoresDisponiveis,
            jogadoresNoDM,
            mediaJogadores,
            jogadoresGrupo
        )

        playersInfoAdapter.submitList(soccerPlayersInfo)
    }

    private fun createSoccerPlayerInfoList(
        jogadoresDisponiveis: Int?,
        jogadoresNoDM: Int?,
        mediaJogadores: Float?,
        jogadoresGrupo: Int?
    ): List<GroupoJogadoresInfo> {
        return arrayListOf(
            createGroupJogadoresInfo(
                R.drawable.ic_round_check_box_24,
                "$jogadoresDisponiveis jogadores disponíveis"
            ),
            createGroupJogadoresInfo(
                R.drawable.ic_round_healing_24,
                "$jogadoresNoDM jogadores no departamento médico"
            ),
            createGroupJogadoresInfo(
                R.drawable.ic_round_ssid_chart_24,
                "A média de habilidade do grupo é de ${mediaJogadores?.roundToTwoDecimalPlaces()}"
            ),
            createGroupJogadoresInfo(
                R.drawable.ic_sharp_groups_24,
                "Possui $jogadoresGrupo jogadores cadastrados no grupo"
            )
        )
    }

    private fun createGroupJogadoresInfo(icon: Int, description: String): GroupoJogadoresInfo {
        return GroupoJogadoresInfo(icon, description)
    }

    private fun initRecyclerView(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<*>,
        layoutManager: RecyclerView.LayoutManager? = null
    ) {
        recyclerView.run {
            this.layoutManager = layoutManager ?: this.layoutManager
            this.adapter = adapter
        }
    }

    private fun configureFabMoreOptions() {
        binding.fabMoreOptions.setOnClickListener {
            isRotate = rotateView(it, !isRotate)

            if (isRotate) {
                showFabs()
            } else {
                hideFabs()
            }
        }
    }

    private fun fabAddSoccerPlayerOnClick() {
        binding.fabAddSoccerPlayer.setOnClickListener {
            isRotate = rotateView(it, !isRotate)
            hideFabs()
            val directions =
                GroupFragmentDirections.actionDetailsGroupFragmentToSoccerPlayerFragment()
            if (args.detalheGrupoItemViewArgs != null) {
                directions.groupId = args.detalheGrupoItemViewArgs!!.id
            }
            findNavController().navigate(directions)
        }
    }

    private fun fabCreateQuickCompetitionOnClick() {
        binding.fabCreateQuickCompetition.setOnClickListener {
            isRotate = rotateView(binding.fabMoreOptions, !isRotate)
            hideFabs()
            val directions =
                GroupFragmentDirections.actionDetailsGroupFragmentToCompetitionFragment()

            if (args.detalheGrupoItemViewArgs != null) {
                directions.competitionViewArgs = CompetitionViewArgs(
                    grupo.id,
                    grupo.jogadores
                )
            }

            findNavController().navigate(directions)
        }
    }

    private fun initFabs() {
        init(binding.fabAddSoccerPlayer)
        init(binding.textViewAddSoccerPlayer)
        init(binding.fabCreateQuickCompetition)
        init(binding.textViewCreateQuickCompetition)
    }


    private fun showFabs() {
        showIn(binding.fabAddSoccerPlayer)
        showIn(binding.textViewAddSoccerPlayer)
        showIn(binding.fabCreateQuickCompetition)
        showIn(binding.textViewCreateQuickCompetition)
    }

    private fun hideFabs() {
        showOut(binding.fabAddSoccerPlayer)
        showOut(binding.textViewAddSoccerPlayer)
        showOut(binding.fabCreateQuickCompetition)
        showOut(binding.textViewCreateQuickCompetition)
    }


    private fun bindingBottomSharedCompetitions() {
        _bottonsheetExcludeCompetitionBinding =
            BottonsheetExcludeCompetitionBinding.inflate(layoutInflater)

        bottomSheetDialogExcludeCompetition =
            createBottomSheetDialog(bottonsheetExcludeCompetitionBinding)

        setButtonClickListener(
            bottonsheetExcludeCompetitionBinding.buttonExcludeCompetition,
            bottomSheetDialogExcludeCompetition,
            competitionItemClicked?.id
        ) {
            onClickToExcludeCompetition(competitionItemClicked?.id)
        }
    }

    private fun onClickToExcludeCompetition(idTorneio: String?) {
        idTorneio?.let { viewModel.deleteCompetition(it) }
    }

    private fun createBottomSheetDialog(binding: ViewBinding): BottomSheetDialog {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        return bottomSheetDialog
    }

    private fun <T> setButtonClickListener(
        button: Button,
        bottomSheetDialog: BottomSheetDialog?,
        param: T?,
        onClickListener: (param: T?) -> Unit
    ) {
        button.setOnClickListener {
            onClickListener.invoke(param)
            bottomSheetDialog?.dismiss()
        }
    }

    private fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_details_group, menu)
        menu.findItem(R.id.action_edit_group).icon?.setTint(requireContext().getColor(R.color.md_theme_light_onPrimaryContainer))
    }


    override fun onResume() {
        super.onResume()

        getGroupItemView()

        Log.i("GROUPFRAGMENT", "onResume")
    }

    private fun getGroupItemView() {
        var groupId = args.detalheGrupoItemViewArgs?.id
        if (groupId != null) {
            viewModel.getGroupById(groupId)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_edit_group -> {
                val directions =
                    GroupFragmentDirections.actionDetailsGroupFragmentToCreateDetailsGroupFragment()

                directions.grupoItemViewArgs = GrupoItemViewArgs(
                    grupo.id,
                    grupo.nome,
                    grupo.endereco,
                    grupo.configuracaoEsporte.tipoEsporte,
                    grupo.diaDoJogo,
                    grupo.horarioInicio,
                    grupo.quantidadeTimes,
                    grupo.rangeIdade?.minAge?.toFloat(),
                    grupo.rangeIdade?.maxAge?.toFloat(),
                )

                findNavController().navigate(directions)
                true
            }

            else -> false
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri = data.data ?: return

            saveImageUri(requireContext(), imageUri, grupo.id)
            binding.imageViewGroupProfile.setImageURI(imageUri)
        }
    }

    companion object {
        private const val FLIPPER_CHILD_COMPETITION_EMPTY = 0
        private const val FLIPPER_CHILD_COMPETITION_SUCCESS = 1
    }


}
