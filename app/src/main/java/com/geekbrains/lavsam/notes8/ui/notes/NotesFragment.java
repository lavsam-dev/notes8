package com.geekbrains.lavsam.notes8.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.lavsam.notes8.R;
import com.geekbrains.lavsam.notes8.domain.Note;
import com.geekbrains.lavsam.notes8.domain.NotesRepository;
import com.geekbrains.lavsam.notes8.domain.NotesRepositoryImpl;
import com.geekbrains.lavsam.notes8.ui.MainActivity;

import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesRepository repository = NotesRepositoryImpl.INSTANCE;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NotesAdapter notesAdapter = new NotesAdapter();

        RecyclerView notesList = view.findViewById(R.id.notes_list);

        notesList.setLayoutManager(new LinearLayoutManager(requireContext()));
//        notesList.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        List<Note> notes = repository.getNotes();

        notesAdapter.setData(notes);
        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {

                if (requireActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) requireActivity();

                    mainActivity.getRouter().showNoteDetail(note);
                }
//                Snackbar.make(view, note.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });

//        notesAdapter.notifyDataSetChanged();
        notesList.setAdapter(notesAdapter);

    }
}
