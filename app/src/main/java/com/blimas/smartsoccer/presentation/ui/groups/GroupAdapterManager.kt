package com.blimas.smartsoccer.presentation.ui.groups

import android.content.Context
import android.widget.Toast
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.blimas.smartsoccer.R
import com.blimas.smartsoccer.databinding.BottonsheetExcludeCompetitionBinding
import com.blimas.smartsoccer.presentation.common.adapter.getCommonAdapterOf
import com.blimas.smartsoccer.presentation.modelView.CompetitionItem
import com.blimas.smartsoccer.presentation.ui.competition.ItemCompetitionViewHolder

class GroupAdapterManager(
    private val context: Context,
    private val navController: NavController,
    private val bottonsheetExcludeCompetitionBinding: BottonsheetExcludeCompetitionBinding,
    private val bottomSheetDialogExcludeCompetition: BottomSheetDialog,
    private val onCompetitionItemClicked: (CompetitionItem?) -> Unit
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
                onCompetitionItemClicked(item)
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
