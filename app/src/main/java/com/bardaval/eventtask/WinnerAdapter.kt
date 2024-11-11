package com.bardaval.eventtask

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WinnerAdapter(private val winners: List<Winner>) : RecyclerView.Adapter<WinnerAdapter.WinnerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinnerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.winner_item, parent, false)
        return WinnerViewHolder(view)
    }

    override fun onBindViewHolder(holder: WinnerViewHolder, position: Int) {
        val winner = winners[position]
        holder.bind(winner)
    }

    override fun getItemCount(): Int {
        return winners.size
    }

    class WinnerViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
        private val winnerName: TextView = itemView.findViewById(R.id.winnerName)
        private val timeTaken: TextView = itemView.findViewById(R.id.timeTaken)

        fun bind(winner: Winner) {
            winnerName.text = winner.name
            timeTaken.text = "Time: ${winner.timeTaken} seconds"
        }
    }
}
