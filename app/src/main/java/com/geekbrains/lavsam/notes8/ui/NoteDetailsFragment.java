package com.geekbrains.lavsam.notes8.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.geekbrains.lavsam.notes8.R;
import com.geekbrains.lavsam.notes8.domain.Note;

public class NoteDetailsFragment extends Fragment {

    public static final String TAG = "NoteDetailsFragment";
    private static final String ARG_NOTE = "ARG_NOTE";

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        args.putParcelable(ARG_NOTE, note);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView title = view.findViewById(R.id.title);
        TextView text = view.findViewById(R.id.text);
        ImageView image = view.findViewById(R.id.image);

        Note note = getArguments().getParcelable(ARG_NOTE);

        title.setText(note.getTitle());
        Glide.with(image).load(note.getUrl()).into(image);
        text.setText(note.getText());
    }
}
