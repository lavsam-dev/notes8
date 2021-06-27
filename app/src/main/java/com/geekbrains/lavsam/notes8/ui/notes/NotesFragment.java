package com.geekbrains.lavsam.notes8.ui.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.lavsam.notes8.R;
import com.geekbrains.lavsam.notes8.domain.Note;
import com.geekbrains.lavsam.notes8.domain.NotesRepository;
import com.geekbrains.lavsam.notes8.domain.NotesRepositoryImpl;
import com.geekbrains.lavsam.notes8.ui.MainActivity;

import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesRepository repository = NotesRepositoryImpl.INSTANCE;
    private NotesAdapter notesAdapter;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesAdapter = new NotesAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        RecyclerView notesList = view.findViewById(R.id.notes_list);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_add) {
                    Note addedNote = repository.add("НЕобыкновенная чечевица (Carpodacus erythrinus)",
                            "В лесах Самарской области рядом с открытыми просторами живет оседло НЕобыкновенная чечевица!",
                            "https://www.niasam.ru/allimages/109826.jpg");

                    int index = notesAdapter.add(addedNote);

                    notesAdapter.notifyItemInserted(index);

                    notesList.scrollToPosition(index);

                    return true;
                }
                if (item.getItemId() == R.id.action_clear) {
                    repository.clear();
                    notesAdapter.setData(Collections.emptyList());
                    notesAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

        notesList.setLayoutManager(linearLayoutManager);

        notesList.setAdapter(notesAdapter);

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

    }
}
