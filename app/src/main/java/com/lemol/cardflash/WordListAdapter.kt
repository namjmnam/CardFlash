package com.lemol.cardflash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class WordListAdapter(
    private val wList: List<WordPair>,
    private val listener : OnItemClickListener) :
    RecyclerView.Adapter<WordListAdapter.AdapterViewHolder>() {

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

    inner class AdapterViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val question: TextView = itemView.findViewById(R.id.qWord)
        val answer: TextView = itemView.findViewById(R.id.qAnswer)
        private val deleteItem : ImageView = itemView.findViewById(R.id.deleteItem)

        init {
            //itemView.setOnClickListener(this) this code is for clicking on the whole thing
            deleteItem.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            //below is my attempt to detect clicking on image
            //if (position != RecyclerView.NO_POSITION){
                //if (vid = uid){} why doesn't it work
            /*
                if (v != null) {
                    when (v.id) {R.id.deleteItem -> {listener.onItemClick(position)}}
                }
             */
                listener.onItemClick(position)
            //}
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

}