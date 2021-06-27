package com.geekbrains.lavsam.notes8.domain;

import java.util.List;

public interface NotesRepository {
    List<Note> getNotes();

    void clear();

    Note add(String title, String text, String imageUrl);
}
