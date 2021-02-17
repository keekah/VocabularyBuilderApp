package me.wingert.vocabularybuilder.undefinedwords

import android.util.Log
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
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.undefined_word_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = undefinedWords[position]

        holder.itemView.undefined_word_text.text = item.word
        holder.itemView.setOnClickListener { itemClicked(item, holder) }
    }

    override fun getItemCount(): Int {
        return undefinedWords.size
    }

    private fun itemClicked(vocab: VocabularyWord, holder: ViewHolder) {
        onClickListener.onClick(vocab)
        Log.i("UndefWordsAdapter", "item clicked: $vocab")
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class OnClickListener(val clickListener: (vocab: VocabularyWord) -> Unit) {
        fun onClick(vocab: VocabularyWord) {
            clickListener(vocab)
        }
    }
}