package com.padawanbr.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.core.domain.model.Time
import com.padawanbr.smartsoccer.databinding.ItemParentCompetitionTeamBinding

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
        itemBinding: ItemParentCompetitionTeamBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textCompetitionTeam: TextView = itemBinding.textViewItemCompetitionTeam
        private val recyclerViewChildCompetitionTeam: RecyclerView = itemBinding.recyclerViewChildCompetitionTeam

        fun bind(time: Time) {
            textCompetitionTeam.text = time.nome

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

    }

}