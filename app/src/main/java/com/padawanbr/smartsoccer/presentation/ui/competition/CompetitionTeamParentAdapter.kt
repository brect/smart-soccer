package com.padawanbr.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.R
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.databinding.ItemParentCompetitionTeamBinding
import com.padawanbr.smartsoccer.presentation.common.ViewAnimation

class CompetitionTeamParentAdapter(
    private val times: List<Time>
) : RecyclerView.Adapter<CompetitionTeamParentAdapter.CompetitionTeamViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionTeamViewHolder {
        return CompetitionTeamViewHolder.create(parent)
    }

    override fun getItemCount() = times.size

    override fun onBindViewHolder(holder: CompetitionTeamViewHolder, position: Int) {
        holder.bind(times[position])
    }

    class CompetitionTeamViewHolder(
        val itemBinding: ItemParentCompetitionTeamBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root), OnClickListener {

        var isRotate: Boolean = false

        private val containerCompetitionTeam: View = itemBinding.containerCompetitionTeam
        private val competitionTeam: TextView = itemBinding.textViewItemCompetitionTeam
        private val visibilityCompetitionTeam: ImageView = itemBinding.imageViewArrowCompetitionTeam

        private val teamCompetitionTeamQtdPlayers: TextView =
            itemBinding.txtTeamCompetitionTeamQtdPlayers
        private val teamCompetitionTeamAvgAbilityTeam: TextView =
            itemBinding.txtTeamCompetitionTeamAvgAbilityTeam
        private val teamCompetitionTeamAvgAge: TextView = itemBinding.txtTeamCompetitionTeamAvgAge
        private val teamCompetitionTeamTeamForce: TextView =
            itemBinding.txtTeamCompetitionTeamTeamForce

        private val recyclerViewChildCompetitionTeam: RecyclerView =
            itemBinding.recyclerViewChildCompetitionTeam

        fun bind(time: Time) {
//            ViewAnimation.init(visibilityCompetitionTeam)

            containerCompetitionTeam.setOnClickListener(this)
            visibilityCompetitionTeam.setOnClickListener(this)

            competitionTeam.text = time.nome

            teamCompetitionTeamQtdPlayers.text = time.jogadores.size.toString()
            teamCompetitionTeamAvgAbilityTeam.text = time.mediaHabilidades.toString()
            teamCompetitionTeamAvgAge.text = "${time.mediaIdades} anos"
            teamCompetitionTeamTeamForce.text = time.forcaTime.toString()

            recyclerViewChildCompetitionTeam.run {
                setHasFixedSize(true)
                adapter = CompetitionTeamChildAdapter(time.jogadores)
            }
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): CompetitionTeamViewHolder {
                val itemBinding = ItemParentCompetitionTeamBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CompetitionTeamViewHolder(itemBinding)
            }
        }

        override fun onClick(p0: View?) {
            when (p0?.id) {
                R.id.container_competitionTeam, R.id.imageView_arrow_competitionTeam -> {
                    recyclerViewChildCompetitionTeam.let {

                        val rotationValue = if (isRotate) 0f else 180f

                        isRotate = ViewAnimation.rotateView(visibilityCompetitionTeam, !isRotate, rotationValue)

                        recyclerViewChildCompetitionTeam.isVisible = !recyclerViewChildCompetitionTeam.isVisible
                        itemBinding.viewDividerBottom.isVisible = !itemBinding.viewDividerBottom.isVisible
                    }
                }

            }
        }

    }


}