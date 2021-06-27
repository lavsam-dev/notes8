package com.geekbrains.lavsam.notes8.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Date;
import java.util.List;

public interface NotesRepository {
    List<Note> getNotes();

    void clear();

    Note add(String title, String text, String imageUrl);

    void remove(Note note);

    Note update(@NonNull Note note, @Nullable String title, @Nullable Date date);
}
