package me.wingert.vocabularybuilder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.wingert.vocabularybuilder.database.VocabularyWord

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var data = listOf<VocabularyWord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.word_view, parent, false) as TextView
        return WordViewHolder(view)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = item.word
    }

    class WordViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}
