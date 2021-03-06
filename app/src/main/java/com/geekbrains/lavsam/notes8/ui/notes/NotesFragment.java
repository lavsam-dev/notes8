package com.geekbrains.lavsam.notes8.ui.notes;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geekbrains.lavsam.notes8.R;
import com.geekbrains.lavsam.notes8.RouterHolder;
import com.geekbrains.lavsam.notes8.domain.Callback;
import com.geekbrains.lavsam.notes8.domain.Note;
import com.geekbrains.lavsam.notes8.domain.NotesFirestoreRepository;
import com.geekbrains.lavsam.notes8.domain.NotesRepository;
import com.geekbrains.lavsam.notes8.domain.NotesRepositoryImpl;
import com.geekbrains.lavsam.notes8.ui.MainActivity;
import com.geekbrains.lavsam.notes8.ui.MainRouter;

import java.util.Collections;
import java.util.List;

public class NotesFragment extends Fragment {

    public static final String TAG = "NotesFragment";
    private NotesRepository repository = NotesFirestoreRepository.INSTANCE;
    private NotesAdapter notesAdapter;

    private int longClickedIndex;
    private Note longClickedNote;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notesAdapter = new NotesAdapter(this);

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> result) {
                notesAdapter.setData(result);
                notesAdapter.notifyDataSetChanged();

            }
        });

        notesAdapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClickedListener(@NonNull Note note) {

                if (requireActivity() instanceof MainActivity) {
                    MainRouter router = ((RouterHolder) requireActivity()).getMainRouter();

                    router.showNoteDetail(note);
                }
//                Snackbar.make(view, note.getTitle(), Snackbar.LENGTH_SHORT).show();
            }
        });

        notesAdapter.setLongClickedListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClickedListener(@NonNull Note note, int index) {
                longClickedIndex = index;
                longClickedNote = note;
            }
        });
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

                    repository.add("???????????????????????????? ???????????????? (Carpodacus erythrinus)",
                            "?? ?????????? ?????????????????? ?????????????? ?????????? ?? ?????????????????? ???????????????????? ?????????? ???????????? ???????????????????????????? ????????????????!",
                            "https://www.niasam.ru/allimages/109826.jpg",
                            new Callback<Note>() {
                        @Override
                        public void onSuccess(Note result) {

                            int index = notesAdapter.add(result);

                            notesAdapter.notifyItemInserted(index);

                            notesList.scrollToPosition(index);
                        }
                    });

                    return true;
                }
                if (item.getItemId() == R.id.action_clear) {
                    repository.clear();
                    notesAdapter.setData(Collections.emptyList());
                    notesAdapter.notifyDataSetChanged();
                    return true;
                }
                if (item.getItemId() == R.id.action_restore) {
                    repository.restore();
                    notesAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());

        notesList.setLayoutManager(linearLayoutManager);

        notesList.setAdapter(notesAdapter);

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        requireActivity().getMenuInflater().inflate(R.menu.menu_notes_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_update) {

            if (requireActivity() instanceof RouterHolder) {
                MainRouter router = ((RouterHolder) requireActivity()).getMainRouter();

                router.showEditNote(longClickedNote);
            }

            return true;
        }

        if (item.getItemId() == R.id.action_delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.alert_remove_title)
                    .setMessage(longClickedNote.getTitle())
                    .setIcon(R.drawable.ic_remove_dialog)
                    .setPositiveButton(R.string.alert_remove_btn_positive, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            repository.remove(longClickedNote, new Callback<Object>() {
                                @Override
                                public void onSuccess(Object result) {
                                    notesAdapter.remove(longClickedNote);

                                    notesAdapter.notifyItemRemoved(longClickedIndex);
                                }
                            });
                        }
                    })
                    .setNegativeButton(R.string.alert_remove_btn_negative, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.show();

            return true;
        }
        return super.onContextItemSelected(item);

    }
}
