package me.wingert.vocabularybuilder.undefinedwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.undefined_word_view.view.*
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.database.VocabularyWord

class UndefinedWordsAdapter(private val onClickListener: OnClickListener) : RecyclerView.Adapter<UndefinedWordsAdapter.ViewHolder>() {

    var undefinedWords = listOf<VocabularyWord>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = undefinedWords[position]

        holder.itemView.setOnClickListener { onClickListener.onClick(item) }

        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return undefinedWords.size
    }

    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val undefinedWordText : TextView = itemView.findViewById(R.id.undefined_word_text)

        companion object {

            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.undefined_word_view, parent, false)

                return ViewHolder(view)
            }
        }

        fun bind(item: VocabularyWord) {
            undefinedWordText.text = item.word
        }

    }

    class OnClickListener(val clickListener: (vocab: VocabularyWord) -> Unit) {
        fun onClick(vocab: VocabularyWord) {
            clickListener(vocab)
        }
    }
}