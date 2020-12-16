package com.lemol.cardflash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter(private val wList: List<WordPair>) : RecyclerView.Adapter<WordListAdapter.AdapterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout,
            parent, false)

        return AdapterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val currentItem = wList[position]

        holder.question.text = currentItem.word
        holder.answer.text = currentItem.meaning
    }

    override fun getItemCount() = wList.size

    class AdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val question: TextView = itemView.findViewById(R.id.qWord)
        val answer: TextView = itemView.findViewById(R.id.qAnswer)
    }
}