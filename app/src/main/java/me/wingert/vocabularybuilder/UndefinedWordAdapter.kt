package me.wingert.vocabularybuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.wingert.vocabularybuilder.database.VocabularyWord

class UndefinedWordAdapter : RecyclerView.Adapter<UndefinedWordAdapter.ViewHolder>() {

    var undefinedWords = listOf<VocabularyWord>()
        set (value) {
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.undefined_word_view, parent, false) as TextView

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = undefinedWords[position]

        holder.textView.text = item.word
    }

    override fun getItemCount(): Int {
        return undefinedWords.size
    }

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}