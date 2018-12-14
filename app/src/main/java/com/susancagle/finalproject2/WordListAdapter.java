package com.susancagle.finalproject2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.WordHolder> {

    private List<Word> words = new ArrayList<>();
    private OnItemClickListener listener;


    @Override
    public WordHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new WordHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WordHolder holder, int position) {
        if (words != null) {
            Word current = words.get(position);
            holder.textViewWord.setText(current.getWord());
            holder.textViewDefinition.setText(current.getDefinition());
        } else {
            // Covers the case of data not being ready yet.
            holder.textViewWord.setText("No Word");
            holder.textViewDefinition.setText("No Definition");
        }
    }

    public void setWords(List<Word> words) {
        this.words = words;
        notifyDataSetChanged();
    }

    public Word getWordAt(int position) {
        return words.get(position);
    }


    // getItemCount() is called many times, and when it's first called,
    // words has not been updated (means initially, it's null and we can't return null)
    @Override
    public int getItemCount() {
        if (words != null)
            return words.size();
        else return 0;
    }

    class WordHolder extends RecyclerView.ViewHolder {
        private TextView textViewWord;
        private TextView textViewDefinition;


        public WordHolder(View itemView) {
            super(itemView);
            textViewWord = itemView.findViewById(R.id.textViewWord);
            textViewDefinition = itemView.findViewById(R.id.textViewDefinition);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(words.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Word word);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
