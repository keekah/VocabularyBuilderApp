package me.wingert.vocabularybuilder.allwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.database.VocabularyWord

class AllWordsAdapter(private val deleteClickListener: DeleteClickListener, private val onClickListener: OnClickListener) : RecyclerView.Adapter<AllWordsAdapter.ViewHolder>() {

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

        holder.wordText.bringToFront()
        holder.deleteIcon.bringToFront()

        holder.deleteIcon.visibility = View.INVISIBLE
        holder.definitionText.visibility = View.GONE

        holder.deleteIcon.setOnClickListener { deleteClickListener.onClick(item) }
        holder.itemView.setOnClickListener { itemClicked(item, holder) }

        holder.bind(item)
    }

    private fun itemClicked(vocab: VocabularyWord, holder: ViewHolder) {
        onClickListener.onClick(vocab)
        holder.deleteIcon.visibility = when (holder.deleteIcon.visibility) {
            View.VISIBLE -> View.INVISIBLE
            else -> View.VISIBLE
        }

        holder.definitionText.visibility = when (holder.definitionText.visibility) {
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }

    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wordText : TextView = itemView.findViewById(R.id.word_text)
        val deleteIcon : ImageView = itemView.findViewById(R.id.delete_icon)
        val definitionText : TextView = itemView.findViewById(R.id.definition_text)

        companion object {

            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.word_view, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: VocabularyWord) {
            wordText.text = item.word
            definitionText.text = item.definition
        }
    }

    // Click on a delete icon to remove the word from the list.
    class DeleteClickListener(val deleteClickListener: (vocab: VocabularyWord) -> Unit) {
        fun onClick(vocab: VocabularyWord) = deleteClickListener(vocab)
    }

    // Click on a word to expand the view, showing definition and delete icon.
    class OnClickListener(val clickListener: (vocab: VocabularyWord) -> Unit) {
        fun onClick(vocab: VocabularyWord) {
            clickListener(vocab)
        }
    }
}