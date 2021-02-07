package me.wingert.vocabularybuilder

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    var data = listOf<String>()
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
        Log.i("WordAdapter bind", "data . value = $data.value")
        val item = data[position]
        holder.textView.text = item
    }

    class WordViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}
