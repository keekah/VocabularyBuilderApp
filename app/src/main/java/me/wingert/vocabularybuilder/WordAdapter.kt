package me.wingert.vocabularybuilder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.wingert.vocabularybuilder.database.VocabularyWord

class WordAdapter : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    var data = listOf<VocabularyWord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wordText : TextView = itemView.findViewById(R.id.word_text)
        val deleteIcon : ImageView = itemView.findViewById(R.id.delete_icon)

        companion object {

            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.word_view, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: VocabularyWord) {
            val res = itemView.context.resources

            wordText.text = item.word
//            deleteIcon.setImageResource(R.drawable.ic_baseline_delete_24)
        }
    }
}
