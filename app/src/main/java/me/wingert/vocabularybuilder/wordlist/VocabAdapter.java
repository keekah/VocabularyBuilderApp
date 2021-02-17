package me.wingert.vocabularybuilder.wordlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import me.wingert.vocabularybuilder.R;
import me.wingert.vocabularybuilder.database.VocabularyWord;

public class VocabAdapter extends RecyclerView.Adapter<VocabAdapter.ViewHolder>
{

    LiveData<List<VocabularyWord>> data;
    private WordAdapter.DeleteClickListener deleteClickListener;
    private WordAdapter.OnClickListener onClickListener;

    public VocabAdapter(LiveData<List<VocabularyWord>> dataSet, WordAdapter.DeleteClickListener deleteClickListener, WordAdapter.OnClickListener onClickListener)
    {
        data = dataSet;
        this.deleteClickListener = deleteClickListener;
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.word_view, viewGroup, false);
        Log.i("VocabAdapter", "view group is " + viewGroup);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.getWordText().setText(data.getValue().get(position).getWord());
        holder.getDeleteIcon().setImageResource(R.drawable.ic_baseline_delete_24);
        holder.getDefinitionText().setText(data.getValue().get(position).getDefinition());
    }

    @Override
    public int getItemCount()
    {
        return data.getValue().size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView wordText;
        private final ImageView deleteIcon;
        private final TextView definitionText;

        public ViewHolder(View itemView)
        {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                }
            });

            wordText = itemView.findViewById(R.id.word_text);
            deleteIcon = itemView.findViewById(R.id.delete_icon);
            definitionText = itemView.findViewById(R.id.definition_text);

        }

        public TextView getWordText() { return wordText; }
        public ImageView getDeleteIcon() { return deleteIcon; }
        public TextView getDefinitionText() { return definitionText; }
    }

}
