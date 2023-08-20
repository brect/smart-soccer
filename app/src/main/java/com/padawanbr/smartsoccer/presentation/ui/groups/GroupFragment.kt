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
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.FragmentGroupBinding
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.init
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation.rotateFab
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

    var isRotate: Boolean = false

    lateinit var grupo: GrupoItem

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

        initPlayersInfoAdapter()
        initCompetitionAdapter()

        val menuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        args.detailsGroupViewArgs?.nome?.let { setToolbarTitle(it) }

        initFabs()

        configureFabMoreOptions()
        fabAddSoccerPlayerOnClick()
        fabCreateQuickCompetitionOnClick()


//        binding.imageViewCompetitionsArrow.setOnClickListener {
//            Toast.makeText(
//                context,
//                "imageViewCompetitionsArrow",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

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
                    competitionsAdapter.submitList(emptyList())

                    Toast.makeText(
                        context,
                        "CreateGroupViewModel.UiState.Loading",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is GroupViewModel.UiState.Success -> {
                    grupo = uiState.grupo

                    competitionsAdapter.submitList(grupo.torneios)

                    binding.textViewGroupTeamName.text = uiState.grupo.nome
                    binding.textViewGroupDate.text = "dd/mm/yyyy"
                    binding.textViewGroupLocal.text = "-"

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
                        80.00.toString()
                    )

                    binding.textViewGameInformationRateAge.text = context?.getString(
                        R.string.game_information_rate_age,
                        20.toString(),
                        60.toString()
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
        val jogadoresGrupo = (jogadoresNoDM?.let {
            jogadoresDisponiveis?.plus(
                it
            )
        })

        val soccerPlayersInfo = arrayListOf<GroupoJogadoresInfo>()

        soccerPlayersInfo.add(
            GroupoJogadoresInfo(
                R.drawable.ic_round_access_time_filled_24,
                "$jogadoresDisponiveis jogadores dispoíveis"
            )
        )

        soccerPlayersInfo.add(
            GroupoJogadoresInfo(
                R.drawable.ic_round_access_time_filled_24,
                "$jogadoresNoDM jogadores no departamento médico"
            )
        )

        soccerPlayersInfo.add(
            GroupoJogadoresInfo(
                R.drawable.ic_round_access_time_filled_24,
                "A média de habilidade do grupo é de $mediaJogadores"
            )
        )

        soccerPlayersInfo.add(
            GroupoJogadoresInfo(
                R.drawable.ic_round_access_time_filled_24,
                "Possui $jogadoresGrupo cadastrados no grupo"
            )
        )

        playersInfoAdapter.submitList(soccerPlayersInfo)
    }

    private fun initPlayersInfoAdapter() {
        binding.recyclerViewSoccerPlayersInfo.run {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = playersInfoAdapter
        }
    }

    private fun initCompetitionAdapter() {
        binding.recyclerViewItemCompetition.run {
            adapter = competitionsAdapter
        }
    }

    private fun configureFabMoreOptions() {
        binding.fabMoreOptions.setOnClickListener {
            isRotate = rotateFab(it, !isRotate)

            if (isRotate) {
                showFabs()
            } else {
                hideFabs()
            }
        }
    }

    private fun fabAddSoccerPlayerOnClick() {
        binding.fabAddSoccerPlayer.setOnClickListener {
            isRotate = rotateFab(it, !isRotate)
            hideFabs()
            val directions =
                GroupFragmentDirections.actionDetailsGroupFragmentToSoccerPlayerFragment()
            if (args.detailsGroupViewArgs != null) {
                directions.groupId = args.detailsGroupViewArgs!!.id
            }
            findNavController().navigate(directions)
        }
    }

    private fun fabCreateQuickCompetitionOnClick() {
        binding.fabCreateQuickCompetition.setOnClickListener {
            isRotate = rotateFab(it, !isRotate)
            hideFabs()
            val directions =
                GroupFragmentDirections.actionDetailsGroupFragmentToCompetitionFragment()

            if (args.detailsGroupViewArgs != null) {
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

    private fun setToolbarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_details_group, menu)
    }


    override fun onResume() {
        super.onResume()

        var groupId = args.detailsGroupViewArgs?.id
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

}