package com.example.tictactoe.viewAndAdapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.tictactoe.R
import com.example.tictactoe.Database.matchListInfo
import android.content.Context


class MatchFragCycleAdapter internal constructor(context: Context) : RecyclerView.Adapter<MatchFragCycleAdapter.ViewHolder>() {


   inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val matchWinner = itemView.findViewById(R.id.match_winner) as TextView
        val matchStats = itemView.findViewById(R.id.match_stats) as TextView
    }
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var matches = emptyList<matchListInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val textView = inflater.inflate(R.layout.match_list_item, parent, false)
        return ViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val match : matchListInfo = matches[position]
        holder.matchWinner.text = match.winner
        holder.matchStats.text = match.stats
    }
    internal fun setMatch(words: List<matchListInfo>) {
        this.matches = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = matches.size
}
