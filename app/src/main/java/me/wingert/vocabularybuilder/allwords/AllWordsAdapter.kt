package me.wingert.vocabularybuilder.allwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.wingert.vocabularybuilder.R
import me.wingert.vocabularybuilder.VocabWord

const val NOORVIK_ZIP = "99763"

class AllWordsAdapter(private val deleteClickListener: DeleteClickListener, private val onClickListener: OnClickListener) : RecyclerView.Adapter<AllWordsAdapter.ViewHolder>() {

    var words: List<VocabWord> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = words.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = words[position]

        holder.bearsLogo.visibility = View.GONE

        holder.wordText.bringToFront()
        holder.deleteIcon.bringToFront()

        holder.deleteIcon.visibility = View.INVISIBLE
        holder.definitionText.visibility = View.GONE

        holder.deleteIcon.setOnClickListener { deleteClickListener.onClick(item) }
        holder.itemView.setOnClickListener { itemClicked(item, holder) }

        holder.bind(item)
    }

    private fun itemClicked(vocab: VocabWord, holder: ViewHolder) {
        onClickListener.onClick(vocab)
        holder.deleteIcon.visibility = when (holder.deleteIcon.visibility) {
            View.VISIBLE -> View.INVISIBLE
            else -> View.VISIBLE
        }

        holder.definitionText.visibility = when (holder.definitionText.visibility) {
            View.VISIBLE -> View.GONE
            else -> View.VISIBLE
        }

        holder.bearsLogo.visibility = when (holder.bearsLogo.visibility) {
            View.VISIBLE -> View.GONE
            else -> {
                if (holder.wordText.text == NOORVIK_ZIP)
                    View.VISIBLE
                else
                    View.GONE
            }
        }

    }


    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wordText : TextView = itemView.findViewById(R.id.word_text)
        val deleteIcon : ImageView = itemView.findViewById(R.id.delete_icon)
        val definitionText : TextView = itemView.findViewById(R.id.definition_text)
        val bearsLogo : ImageView = itemView.findViewById(R.id.bears_logo)

        companion object {

            fun from(parent: ViewGroup) : ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.word_view, parent, false)
                return ViewHolder(view)
            }
        }

        fun bind(item: VocabWord) {
            wordText.text = item.word
            definitionText.text = item.definition
        }
    }

    // Click on a delete icon to remove the word from the list.
    class DeleteClickListener(val deleteClickListener: (vocab: VocabWord) -> Unit) {
        fun onClick(vocab: VocabWord) = deleteClickListener(vocab)
    }

    // Click on a word to expand the view, showing definition and delete icon.
    class OnClickListener(val clickListener: (vocab: VocabWord) -> Unit) {
        fun onClick(vocab: VocabWord) {
            clickListener(vocab)
        }
    }
}

class VocabularyDiffCallback : DiffUtil.ItemCallback<VocabWord>() {

    override fun areItemsTheSame(oldItem: VocabWord, newItem: VocabWord): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VocabWord, newItem: VocabWord): Boolean {
        return oldItem == newItem
    }
}
