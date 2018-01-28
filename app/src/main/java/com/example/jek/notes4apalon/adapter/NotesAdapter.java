package com.example.jek.notes4apalon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jek.notes4apalon.model.Note;
import com.example.jek.notes4apalon.R;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {
    private List<Note> listNotes = new ArrayList<>();
    private static OnItemClickListener onItemClickListener;

    public void setData(List<Note> listNotes) {
        this.listNotes = listNotes;
    }

    public static void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        NotesAdapter.onItemClickListener = onItemClickListener;
    }

    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NotesViewHolder holder, int position) {
        Note note = listNotes.get(position);
        holder.noteTitle.setText(note.getTitle());
        holder.noteBody.setText(note.getBody());
    }

    @Override
    public int getItemCount() {
        return this.listNotes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView noteTitle;
        TextView noteBody;


        public NotesViewHolder(View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteBody = itemView.findViewById(R.id.noteBody);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View v);
    }
}
