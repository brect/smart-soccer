package com.padawanbr.smartsoccer.presentation.ui.groups

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentGroupBinding
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.init
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.rotateView
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showIn
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.showOut
import com.padawanbr.smartsoccer.presentation.common.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.ui.competition.ItemCompetitionViewHolder
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GroupFragment : Fragment(), MenuProvider {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GroupViewModel by viewModels()

    private val args by navArgs<GroupFragmentArgs>()

    private var isRotate: Boolean = false

    lateinit var grupo: GrupoComJogadoresItem

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
            {
                Toast.makeText(
                    context,
                    "competitionsAdapter itemLongClicked",
                    Toast.LENGTH_SHORT
                ).show()
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

        initRecyclerView(binding.recyclerViewItemCompetition, competitionsAdapter, LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
        initRecyclerView(binding.recyclerViewSoccerPlayersInfo, playersInfoAdapter, LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        args.detalheGrupoItemViewArgs?.nome?.let { setToolbarTitle(it) }

        initFabs()

        configureFabMoreOptions()
        fabAddSoccerPlayerOnClick()
        fabCreateQuickCompetitionOnClick()

        observeUiState()
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

                    if (grupo.torneios?.size!! > 0) {
                        binding.flipperItemCompetition.displayedChild = FLIPPER_CHILD_COMPETITION_SUCCESS
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
            }
        }
    }

    private fun setSoccerPlayersInfos(uiState: GroupViewModel.UiState.Success) {
        val jogadoresDisponiveis = uiState.grupo.jogadoresDisponiveis
        val jogadoresNoDM = uiState.grupo.jogadoresNoDM
        val mediaJogadores = uiState.grupo.mediaJogadores
        val jogadoresGrupo = jogadoresNoDM?.let { jogadoresDisponiveis?.plus(it) }

        val soccerPlayersInfo = createSoccerPlayerInfoList(jogadoresDisponiveis, jogadoresNoDM, mediaJogadores, jogadoresGrupo)

        playersInfoAdapter.submitList(soccerPlayersInfo)
    }

    private fun createSoccerPlayerInfoList(
        jogadoresDisponiveis: Int?,
        jogadoresNoDM: Int?,
        mediaJogadores: Float?,
        jogadoresGrupo: Int?
    ): List<GroupoJogadoresInfo> {
        return arrayListOf(
            createGroupJogadoresInfo(R.drawable.ic_round_access_time_filled_24, "$jogadoresDisponiveis jogadores disponíveis"),
            createGroupJogadoresInfo(R.drawable.ic_round_access_time_filled_24, "$jogadoresNoDM jogadores no departamento médico"),
            createGroupJogadoresInfo(R.drawable.ic_round_access_time_filled_24, "A média de habilidade do grupo é de $mediaJogadores"),
            createGroupJogadoresInfo(R.drawable.ic_round_access_time_filled_24, "Possui $jogadoresGrupo jogadores cadastrados no grupo")
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
            isRotate = rotateView(it, !isRotate)
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
        init(binding.fabCreateCompetition)
        init(binding.textViewCreateCompetition)
    }


    private fun showFabs() {
        showIn(binding.fabAddSoccerPlayer)
        showIn(binding.textViewAddSoccerPlayer)
        showIn(binding.fabCreateQuickCompetition)
        showIn(binding.textViewCreateQuickCompetition)
        showIn(binding.fabCreateCompetition)
        showIn(binding.textViewCreateCompetition)
    }

    private fun hideFabs() {
        showOut(binding.fabAddSoccerPlayer)
        showOut(binding.textViewAddSoccerPlayer)
        showOut(binding.fabCreateQuickCompetition)
        showOut(binding.textViewCreateQuickCompetition)
        showOut(binding.fabCreateCompetition)
        showOut(binding.textViewCreateCompetition)
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
    }


    override fun onResume() {
        super.onResume()

        var groupId = args.detalheGrupoItemViewArgs?.id
        if (groupId != null) {
            viewModel.getGroupById(groupId)
        }

        Log.i("GROUPFRAGMENT", "onResume")
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_edit -> {
                Toast.makeText(context, "Action edit", Toast.LENGTH_LONG).show()
                true
            }

            else -> false
        }
    }

    companion object {
        private const val FLIPPER_CHILD_COMPETITION_EMPTY = 0
        private const val FLIPPER_CHILD_COMPETITION_SUCCESS = 1
    }


}

