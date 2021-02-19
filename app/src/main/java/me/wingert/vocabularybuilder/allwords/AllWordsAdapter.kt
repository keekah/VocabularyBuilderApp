package me.wingert.vocabularybuilder.allwords

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
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

        when (holder.definitionText.visibility) {
            View.VISIBLE -> slideUp(holder.definitionText)
            else -> slideDown(holder)
        }
    }

    private fun slideUp(view : View) {
//        val animation = TranslateAnimation(0F, 0F, 0F, -view.height.toFloat())
//        animation.duration = 300
//        view.startAnimation(animation)
        view.visibility = View.GONE
    }

    private fun slideDown(holder: ViewHolder) {
        val animationSet = AnimationSet(true)

        val height = holder.definitionText.height.toFloat()
        val bottom = holder.definitionText.height - height

        val translate = TranslateAnimation(0F, 0F, -height, 0F)
        translate.duration = 300

        holder.definitionText.startAnimation(translate)
        holder.definitionText.visibility = View.VISIBLE
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

            val resources = itemView.context.resources
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
