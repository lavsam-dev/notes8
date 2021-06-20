package com.geekbrains.lavsam.notes8.domain;

import java.util.ArrayList;
import java.util.List;

public class NotesRepositoryImpl implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesRepositoryImpl();

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> notes = new ArrayList<>();

        notes.add(new Note("id1", "Title Number One", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg"));
        notes.add(new Note("id2", "Title Number Two", "https://cdn.pixabay.com/photo/2020/04/17/16/48/marguerite-5056063_1280.jpg"));
        notes.add(new Note("id3", "Title Number Three", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg"));
        notes.add(new Note("id4", "Title Number Four", "https://cdn.pixabay.com/photo/2021/03/17/09/06/snowdrop-6101818_1280.jpg"));
        notes.add(new Note("id5", "Title Number Five", "https://cdn.pixabay.com/photo/2020/06/22/10/40/castle-5328719_1280.jpg"));
        notes.add(new Note("id6", "Title Number Six", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg"));
        notes.add(new Note("id7", "Title Number Seven", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg"));
        notes.add(new Note("id8", "Title Number Eight", "https://cdn.pixabay.com/photo/2020/06/22/10/40/castle-5328719_1280.jpg"));
        notes.add(new Note("id9", "Title Number Nine", "https://cdn.pixabay.com/photo/2021/03/17/09/06/snowdrop-6101818_1280.jpg"));
        notes.add(new Note("id10", "Title Number Ten", "https://cdn.pixabay.com/photo/2020/04/17/16/48/marguerite-5056063_1280.jpg"));
        notes.add(new Note("id11", "Title Number Eleven", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg"));
        notes.add(new Note("id12", "Title Number Twelve", "https://cdn.pixabay.com/photo/2020/12/25/11/57/flamingos-5859192_1280.jpg"));

        int itemCount = 1000;

        for (int i = 0; i < itemCount; i++) {
            notes.add(new Note("id12", "Title Number Twelve", "https://cdn.pixabay.com/photo/2020/07/06/01/33/sky-5375005_1280.jpg"));
        }

        return notes;
    }
}
