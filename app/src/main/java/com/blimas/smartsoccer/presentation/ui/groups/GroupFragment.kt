package com.blimas.smartsoccer.presentation.ui.groups

import ImagePickerUtils.PICK_IMAGE_REQUEST
import ImagePickerUtils.loadImageFromPreferences
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
import com.blimas.smartsoccer.R
import com.blimas.smartsoccer.databinding.BottonsheetExcludeCompetitionBinding
import com.blimas.smartsoccer.databinding.BottonsheetExcludeGroupBinding
import com.blimas.smartsoccer.databinding.FragmentGroupBinding
import com.blimas.smartsoccer.presentation.extensions.navControllerAndClearStack
import com.blimas.smartsoccer.presentation.extensions.roundToTwoDecimalPlaces
import com.blimas.smartsoccer.presentation.extensions.showShortToast
import com.blimas.smartsoccer.presentation.modelView.CompetitionItem
import com.blimas.smartsoccer.presentation.modelView.GroupoJogadoresInfo
import com.blimas.smartsoccer.presentation.modelView.GrupoComJogadoresItem
import com.blimas.smartsoccer.presentation.utils.ViewAnimationUtils.init
import com.blimas.smartsoccer.presentation.utils.ViewAnimationUtils.rotateView
import com.blimas.smartsoccer.presentation.utils.ViewAnimationUtils.showIn
import com.blimas.smartsoccer.presentation.utils.ViewAnimationUtils.showOut
import com.blimas.smartsoccer.presentation.viewArgs.CompetitionViewArgs
import com.blimas.smartsoccer.presentation.viewArgs.GrupoItemViewArgs
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

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private var _bottomSheetBinding: BottonsheetExcludeGroupBinding? = null
    private val bottomSheetBinding: BottonsheetExcludeGroupBinding get() = _bottomSheetBinding!!

    private var competitionItemClicked: CompetitionItem? = null

    private lateinit var adapterManager: GroupAdapterManager

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

        bindingBottomSharedCompetitions()
        bindingBottomSheetToExcludeGroup()

        initMenuProvider()
        initFabs()

        configureFabMoreOptions()
        fabAddSoccerPlayerOnClick()
        fabCreateQuickCompetitionOnClick()

        observeUiState()
        observeSharedUiState()

        adapterManager = GroupAdapterManager(
            requireContext(),
            findNavController(),
            bottonsheetExcludeCompetitionBinding,
            bottomSheetDialogExcludeCompetition,
        ) {
            competitionItemClicked = it
        }

        initRecyclerView()
        imageProfileOnListener()

        groupLocalOnClickListener()
    }

    private fun groupLocalOnClickListener() {
        binding.includeGroupViewState.includeGroupGameInfoView.containerGroupLocal.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${binding.includeGroupViewState.includeGroupGameInfoView.textViewGroupLocal.text}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun imageProfileOnListener() {
        binding.includeGroupViewState.imageViewGroupProfile.setOnClickListener {
            val directions =
                GroupFragmentDirections.actionDetailsGroupFragmentToSampleUsingImageViewFragment()

            if (args.detalheGrupoItemViewArgs != null) {
                directions.groupId = args.detalheGrupoItemViewArgs!!.id
            }

            findNavController().navigate(directions)
        }
    }

    private fun initRecyclerView() {
        initRecyclerView(
            binding.includeGroupViewState.recyclerViewItemCompetition,
            adapterManager.competitionsAdapter,
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )

        initRecyclerView(
            binding.includeGroupViewState.recyclerViewSoccerPlayersInfo,
            adapterManager.playersInfoAdapter,
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )
    }

    private fun initMenuProvider() {
        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    private fun observeUiState() {
        viewModel.state.observe(viewLifecycleOwner) { uiState ->
            binding.fliperGroup.displayedChild = when (uiState) {
                GroupViewModel.UiState.DeleteSuccess -> {
                    navControllerAndClearStack()
                    FLIPPER_CHILD_GROUP_SUCCESS
                }

                GroupViewModel.UiState.Error -> {
                    FLIPPER_CHILD_GROUP_ERROR
                }

                GroupViewModel.UiState.Loading -> {
                    binding.includeGroupViewState.flipperItemCompetition.displayedChild = FLIPPER_CHILD_COMPETITION_EMPTY
                    adapterManager.competitionsAdapter.submitList(emptyList())
                    FLIPPER_CHILD_GROUP_LOADING
                }

                is GroupViewModel.UiState.Success -> {
                    grupo = uiState.grupo

                    loadImageFromPreferences(
                        requireContext(),
                        grupo.id,
                        binding.includeGroupViewState.imageViewGroupProfile
                    )

                    if (grupo.torneios?.size!! > 0) {
                        binding.includeGroupViewState.flipperItemCompetition.displayedChild =
                            FLIPPER_CHILD_COMPETITION_SUCCESS
                        adapterManager.competitionsAdapter.submitList(grupo.torneios)
                    }

                    binding.includeGroupViewState.textViewGroupTeamName.text = uiState.grupo.nome

                    binding.includeGroupViewState.includeGroupGameInfoView.textViewGroupDate.text = "${grupo.diaDoJogo}"
                    binding.includeGroupViewState.includeGroupGameInfoView.textViewGroupLocal.text = "${grupo.endereco}"

                    val quantidadeMinimaPorTime = uiState.grupo.configuracaoEsporte.quantidadeMinimaPorTime
                    binding.includeGroupViewState.includeGroupInfoView.textViewGameInformationTypeOfCourt.text =  uiState.grupo.configuracaoEsporte.tipoEsporte.tipo
                    binding.includeGroupViewState.includeGroupInfoView.textViewGameInformationConfiguration.text = context?.getString(
                        R.string.game_information_configuration,
                        quantidadeMinimaPorTime.toString(),
                        quantidadeMinimaPorTime.toString()
                    )
                    binding.includeGroupViewState.includeGroupInfoView.textViewGameInformationRateAge.text = context?.getString(
                        R.string.game_information_rate_age,
                        grupo.rangeIdade?.minAge.toString(),
                        grupo.rangeIdade?.maxAge.toString(),
                    )

                    setSoccerPlayersInfos(uiState)
                    FLIPPER_CHILD_GROUP_SUCCESS
                }

                GroupViewModel.UiState.SuccessDeleteCompetition -> {
                    getGroupItemView()
                    showShortToast("Competição deletada com sucesso")
                    FLIPPER_CHILD_GROUP_SUCCESS
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

        adapterManager.playersInfoAdapter.submitList(soccerPlayersInfo)
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
        binding.includeGroupViewState.fabMoreOptions.setOnClickListener {
            isRotate = rotateView(it, !isRotate)

            if (isRotate) {
                showFabs()
            } else {
                hideFabs()
            }
        }
    }

    private fun fabAddSoccerPlayerOnClick() {
        binding.includeGroupViewState.fabAddSoccerPlayer.setOnClickListener {
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
        binding.includeGroupViewState.fabCreateQuickCompetition.setOnClickListener {
            isRotate = rotateView(binding.includeGroupViewState.fabMoreOptions, !isRotate)
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
        init(binding.includeGroupViewState.fabAddSoccerPlayer)
        init(binding.includeGroupViewState.textViewAddSoccerPlayer)
        init(binding.includeGroupViewState.fabCreateQuickCompetition)
        init(binding.includeGroupViewState.textViewCreateQuickCompetition)
    }


    private fun showFabs() {
        showIn(binding.includeGroupViewState.fabAddSoccerPlayer)
        showIn(binding.includeGroupViewState.textViewAddSoccerPlayer)
        showIn(binding.includeGroupViewState.fabCreateQuickCompetition)
        showIn(binding.includeGroupViewState.textViewCreateQuickCompetition)
    }

    private fun hideFabs() {
        showOut(binding.includeGroupViewState.fabAddSoccerPlayer)
        showOut(binding.includeGroupViewState.textViewAddSoccerPlayer)
        showOut(binding.includeGroupViewState.fabCreateQuickCompetition)
        showOut(binding.includeGroupViewState.textViewCreateQuickCompetition)
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
        menu.findItem(R.id.action_delete_group).icon?.setTint(requireContext().getColor(R.color.md_theme_light_onPrimaryContainer))
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

    fun bindingBottomSheetToExcludeGroup() {
        // Crie um novo BottomSheetDialog aqui
        bottomSheetDialog = BottomSheetDialog(requireContext())

        // Inflate your custom layout with ViewBinding
        _bottomSheetBinding = BottonsheetExcludeGroupBinding.inflate(layoutInflater)

        // Set the custom layout as the content view of the BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetBinding.root)

        bottomSheetBinding.buttonExcludeGroup.setOnClickListener {

            var groupId = args.detalheGrupoItemViewArgs?.id
            if (groupId != null) {
                viewModel.deleteGroup(groupId)
                bottomSheetDialog.dismiss()
            }
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

            R.id.action_delete_group -> {
                bottomSheetBinding.textExcludeGroupContent.text = context?.getString(R.string.exclude_groups, grupo.nome)
                bottomSheetDialog.show()
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
            binding.includeGroupViewState.imageViewGroupProfile.setImageURI(imageUri)
        }
    }

    companion object {
        private const val FLIPPER_CHILD_GROUP_LOADING = 0
        private const val FLIPPER_CHILD_GROUP_SUCCESS = 1
        private const val FLIPPER_CHILD_GROUP_EMPTY = 2
        private const val FLIPPER_CHILD_GROUP_ERROR = 3

        private const val FLIPPER_CHILD_COMPETITION_EMPTY = 0
        private const val FLIPPER_CHILD_COMPETITION_SUCCESS = 1
    }


}
