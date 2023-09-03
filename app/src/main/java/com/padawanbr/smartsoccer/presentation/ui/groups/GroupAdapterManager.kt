package com.padawanbr.smartsoccer.presentation.ui.groups

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.databinding.BottonsheetExcludeCompetitionBinding
import com.padawanbr.smartsoccer.presentation.common.adapter.getCommonAdapterOf
import com.padawanbr.smartsoccer.presentation.modelView.CompetitionItem
import com.padawanbr.smartsoccer.presentation.ui.competition.ItemCompetitionViewHolder

class GroupAdapterManager(
    private val context: Context,
    private val navController: NavController,
    private val bottonsheetExcludeCompetitionBinding: BottonsheetExcludeCompetitionBinding,
    private val bottomSheetDialogExcludeCompetition: BottomSheetDialog,
    private var competitionItemClicked: CompetitionItem?
) {

    val competitionsAdapter by lazy {
        getCommonAdapterOf(
            { ItemCompetitionViewHolder.create(it) },
            { item ->
                val directions = GroupFragmentDirections.actionDetailsGroupFragmentToDetailsCompetitionFragment()
                directions.competitionId = item.id
                navController.navigate(directions)
            },
            { item ->
                competitionItemClicked = item
                bottonsheetExcludeCompetitionBinding.textExcludeCompetitionContent.text = context.getString(R.string.exclude_competition, item.nome)
                bottomSheetDialogExcludeCompetition.show()
            }
        )
    }

    val playersInfoAdapter by lazy {
        getCommonAdapterOf(
            { ItemGroupPlayersInfoViewHolder.create(it) },
            { item ->
                Toast.makeText(context, item.text, Toast.LENGTH_SHORT).show()
            },
            { item ->
                Toast.makeText(context, item.text, Toast.LENGTH_SHORT).show()
            }
        )
    }
}
