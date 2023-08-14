package com.padawanbr.smartsoccer.presentation.ui.competition
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.padawanbr.smartsoccer.core.domain.model.Torneio
//import com.padawanbr.smartsoccer.databinding.ItemCompetitionBinding
//
//class DatailCompetitionAdapter(
//    val torneios: MutableList<Torneio>,
//    private val onItemClicked: (Torneio) -> Unit
//) : RecyclerView.Adapter<DatailCompetitionViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, type: Int): DatailCompetitionViewHolder {
//        val binding =
//            ItemCompetitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return DatailCompetitionViewHolder(binding) {
//            onItemClicked(torneios[it])
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return torneios.size
//    }
//
//    override fun onBindViewHolder(holder: DatailCompetitionViewHolder, position: Int) {
//        val item = torneios[position]
//        holder.bind(item)
//        holder.itemView.setOnClickListener { onItemClicked(item) }
//    }
//}
