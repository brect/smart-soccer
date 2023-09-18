package com.blimas.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blimas.smartsoccer.core.domain.model.Jogador
import com.blimas.smartsoccer.core.domain.model.calcularMediaHabilidades
import com.blimas.smartsoccer.databinding.ItemChildCompetitionTeamBinding

class CompetitionTeamChildAdapter(val jogadores: MutableList<Jogador>) : RecyclerView.Adapter<CompetitionTeamChildAdapter.CompetitionTeamChildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompetitionTeamChildViewHolder {
        return CompetitionTeamChildViewHolder.create(parent)
    }

    override fun getItemCount() = jogadores.size

    override fun onBindViewHolder(holder: CompetitionTeamChildViewHolder, position: Int) {
        holder.bind(jogadores[position])
    }

    class CompetitionTeamChildViewHolder(
        itemBinding: ItemChildCompetitionTeamBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textViewCompetitionPlayerName: TextView = itemBinding.textViewCompetitionPlayerName
        private val ratingBarCompetitionAverageSoccerPlayer: RatingBar = itemBinding.ratingBarCompetitionAverageSoccerPlayer
        private val textViewCompetitionPosition: TextView = itemBinding.textViewCompetitionPosition

        fun bind(jogador: Jogador) {
            textViewCompetitionPlayerName.text = jogador.nome
            ratingBarCompetitionAverageSoccerPlayer.rating = jogador.calcularMediaHabilidades()
            textViewCompetitionPosition.text = jogador.posicao?.funcao
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): CompetitionTeamChildViewHolder {
                val itemBinding = ItemChildCompetitionTeamBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return CompetitionTeamChildViewHolder(itemBinding)
            }
        }

    }

}