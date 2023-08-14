package com.padawanbr.smartsoccer.presentation.ui.competition

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.padawanbr.smartsoccer.core.domain.model.Torneio
import com.padawanbr.smartsoccer.databinding.ItemCompetitionBinding

class DatailCompetitionAdapter(val torneios: MutableList<Torneio>) :
    RecyclerView.Adapter<DatailCompetitionAdapter.DatailCompetitionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DatailCompetitionViewHolder {
        return DatailCompetitionViewHolder.create(parent)
    }

    override fun getItemCount(): Int {
        return torneios.size
    }

    override fun onBindViewHolder(holder: DatailCompetitionViewHolder, position: Int) {
        holder.bind(torneios[position])
    }

    class DatailCompetitionViewHolder(
        itemBinding: ItemCompetitionBinding,
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private val textNameCompetition: TextView = itemBinding.textViewNameCompetition
        private val textTypeCompetition: TextView = itemBinding.textViewTypeCompetition

        fun bind(item: Torneio) {
            textNameCompetition.text = item.nome
            textTypeCompetition.text = item.tipoTorneio?.descricao
        }

        companion object {
            fun create(
                parent: ViewGroup,
            ): DatailCompetitionViewHolder {
                val itemBinding = ItemCompetitionBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DatailCompetitionViewHolder(itemBinding)
            }
        }
    }

}
