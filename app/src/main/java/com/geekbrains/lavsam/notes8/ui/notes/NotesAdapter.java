package com.geekbrains.lavsam.notes8.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geekbrains.lavsam.notes8.R;
import com.geekbrains.lavsam.notes8.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private final Fragment fragment;
    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteClickedListener listener;

    public NotesAdapter(Fragment fragment) { this.fragment = fragment; }

    public void setData(List<Note> toSet) {
        notes.clear();
        notes.addAll(toSet);
    }

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NoteViewHolder holder, int position) {

        Note note = notes.get(position);
        holder.bind(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public int add(Note addedNote) {
        notes.add(addedNote);
        return notes.size() - 1;
    }

    public interface OnNoteClickedListener {
        void onNoteClickedListener(@NonNull Note note);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getListener() != null) {
                        getListener().onNoteClickedListener(notes.get(getAdapterPosition()));
                    }
                }
            });

            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }

        public void bind(Note note) {
            title.setText(note.getTitle());

            Glide.with(image)
                    .load(note.getUrl())
                    .centerCrop()
                    .into(image);
        }
    }
}
