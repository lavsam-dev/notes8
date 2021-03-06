package com.geekbrains.lavsam.notes8.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class NotesFirestoreRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesFirestoreRepository();
    private final static String NOTES = "notes";
    private final static String DATE = "date";
    private final static String TITLE = "title";
    private final static String TEXT = "text";
    private final static String IMAGE = "url";
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Callback<List<Note>> callback;

    @Override
    public void getNotes(Callback<List<Note>> callback) {
        this.callback = callback;
        firebaseFirestore.collection(NOTES)
                .orderBy(DATE, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            ArrayList<Note> result = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentID = (String) document.getId();
                                String title = (String) document.get(TITLE);
                                String text = (String) document.get(TEXT);
                                String image = (String) document.get(IMAGE);
                                Date date = ((Timestamp) document.get(DATE)).toDate();

                                result.add(new Note(document.getId(), title, text, image, date));
                            }

                            callback.onSuccess(result);

                        } else {
                            task.getException();
                        }
                    }
                });
    }

    @Override
    public void clear() {
        ArrayList<String> result = new ArrayList<>();

        firebaseFirestore.collection(NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add((String) document.getId());
                            }

//                            callback.onSuccess(result);

                        } else {
                            task.getException();
                        }
                    }
                });

        for (String docId: result) {
            firebaseFirestore.collection(NOTES)
                    .document(docId)
                    .delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                callback.onSuccess(note);
                            }
                        }
                    });
        }
    }

    @Override
    public void restore() {
        ArrayList<Note> notesArray = new ArrayList<>();

        notesArray.add(new Note("id1", "?????????????????????? ?????????? (Accipiter brevipes)",
                "?????????????????????? ?????????? ???????????????? ???????????? ???????????? ???????????? ?? ???????????????????? ??????????, ?? ???????????????????????? ???????? ?????? ???????????????? ???????????????????? ?? ?????????????? ???????? ?? ???????????????????????? ?? ???????????? ????????. ?????????????? ???????????? ???????????????????????? ??????????????, ?????????? ???????? ?????????????????? 40 ????, ?????????????? ???????????? ?????????????? ?????????? 70 ????, ?????? ???? 200 ??. ???????????????? ?????????? ???????? ?????????? ????????????-??????????, ?????????????????????? ?????????? ???????????????? ?????????????? ??????????????, ???????????? ??????????. ?????????? ???????????????? ?? ????????-???????????????????? ???????? ???? ????????????, ?????????? ?????????? ???????????????? ???????????? ???????????????????? ????????????, ?????? ???????? ?????????????? ?? ?????????????????????? ????????????????????. ?????????????????????? ?????????? ?????????????????? ?? ?????????????? ?????????????? ???????????????? ?? ?????????? ?????????? ???? ??????????????????????, ????????????????, ???????? ???????????? ???????????????? ?? ???? ?????????????? ??????????????????. ???????????? ?????????? ?? ??????????????.",
                "https://www.niasam.ru/allimages/109815.jpg", new Date()));
        notesArray.add(new Note("id2", "?????????????????????? ?????? (Gyps fulvus)",
                "???? ???????????????? ?????????????????? ?????????????????? ?????????????? ?????????????????????? ?????????? ?????????????? ????????????-??????????????????, ?????? ?????????????????????? ??????. ?????? ???????????????? ????????????, ???????????? ?? ?????????????? ??????????, ?? ?????? ?????????? ???????????????? ?? ?????????????????? ???????????????????????? ???????????????????????? ????????????????????. ?????????? ?????????? ?????????????????? ?????????????????????? ?????? ???????????????????? ???????????????????? ???????????????? ??????????????????, ?? ?????????? ?? ???????? ?????????????? ???????????? ?? ??????????. ?? ?????????? ?????????????????????????? ???????? ?????????? 1 ??, ???????????? ?????????????? ?????????????????? 2,5 ??. ???????????????????? ?????????????? ???????????? ?????????? ?????????????????? ?????????? ??????, ???? ?????? ?????????????? ?????????? ???????????????????????? ?? ????????????????. ???????? ???????? ???????????? ??????????, ?????? ???????????????? ?? ??????????????. ???????????? ????????????. ???????????? ???????????????????????? ???????? ?????????????? ?????????????????????????? ???? ????????????, ???????????? ??????????????????????????, ?? ?????????????? ?????????????? ?????????? ?????????? ?????? ?? ???????? ???????????? ?????????????????? ???????????? ?????? ????????.",
                "https://www.niasam.ru/allimages/109816.jpg", new Date()));
        notesArray.add(new Note("id3", "????????-???????????? (Aquila pennata)",
                "????????-?????????????? ??? ???????????????? ?????????????? ?????????????????? ????????????????, ?????????????????? ?? ??????????. ?? ?????????? ?????????????????? 50 ????, ???????????? ?????????????? ?????????????????? 1 ??, ?????? ?????????? 1 ????. ?????????? ???????????? ???? ???????????????? ?????????????? ????????????, ?????? ??????????. ?? ?????? ?? ???????????? ?????????????????????????? ?????????? ?????????? ??????????????????. ?????? ??????????-???????????????? ?????????????????? ?????? ??????????????: ?????????????? ?? ????????????. ?? ?????????????? ???????? ???????? ???????? ??????????, ?? ?????? ????????????????. ???????????? ?????????? ?????????? ?????????????????? ??????????????????-?????????? ????????????????. ???????????????????????? ?????????????? ???? ?????????????????? ?????????? ?????????????? ???????????????? ?????????????? ????????????, ?????????????? ???????????????? ?????????? ???????? ?? ?????????????? ????????, ?????????????????? ???????????? ???? ??????????????. ?????? ?????????????????????? ?????????? ???????? ????????-?????????????? ?????????????????????? ?????????????????? ???? ???????????? ???????????? ????????????????, ?? ?????? ?????????? ?? ???? ?????????????????? ?? ?????????????? ??????????????????????????. ?????? ?? ??????????, ?? ??????????????????????????, ?? ????????????????.",
                "https://www.niasam.ru/allimages/109817.jpg", new Date()));
        notesArray.add(new Note("id4", "?????????????????????? ?????????? (Anthus cervinus)",
                "???????? ???? ???????????? ???????? ?????????????????? ?????????????? ??? ?????????????????????? ?????????? ??? ?????????????????? ?????????? 15 ????. ???????????? ???? ???????????????? ?? ?????????? ???????? ?? ?????????????? ??????????????????. ???????????????????? ???????????????? ?????????? ???????????????????? ?????????? ??????????????, ?? ???????????? ?? ???????? ???????????????? ???????????????????????? ?????????????????? ?? ?????????????? ????????????????????. ???????????????????????? ?????? ?????????? ???????? ????????????????, ?? ?????? ??????????, ?? ??????????, ?????????? ?????? ???????????????? ?? ?????????? ?????????????? ????????????????, ????-???? ???????? ?????? ?? ?????????????? ???????? ????????????????. ?????????????? ??????????????????-???????????? ??????????. ???????????????? ???????????????????? ???????????? ???????????? ??????????. ???? ?????????????????????? ?????????? ???????????????????????? ???????????? ???????????????? ?????????? ??????????????????, ?????????? ???????????????????????? ???????????? ?? ?????????????? ?????????????? ?????????? ??????????. ?????????? ???????? ?????? ?????????? ?????????????????? ???????????? ???? ????????, ???????????????????????? ?????????? ????????????-??????????-??????????-????????-????????-????????-??????????-????????-????????-?????????????? ?????????????????????????? ???????????????? ????????????.",
                "https://www.niasam.ru/allimages/109818.jpg", new Date()));
        notesArray.add(new Note("id5", "???????????????????? ?????????????????? (Lanius minor)",
                "???????????????????? ???????????? ?????????? ???????????????????? ???????????????????? ?????????????????????? ?? ????????????????????, ?????? ???? ???????????????? ?????????????????? ???????? ?????????????????? ?????????????? ?????? ?????????????? ??????????????????????, ???? ???????????? ?????????????? ?? ??????????????, ???????????????? ??????????, ?? ?????????????????????? ?? ???????????? ???????????????? ??????????????????????. ?????????????? ???????????????????????????? ?????????? ???????? ??????????????, ???????????? ?????????? ???????????????? ??????????, ?????????? ????????????????, ???????? ?????????????????? ????????????. ?????????? ???????? ?????????????????????? ???????????????????? ?????????? 25 ????, ???????????? ?????????????? ?????????????????? 40 ????, ?????? ???? 60 ??. ?????????????? ???????????????? ???????????? ?? ??????????, ???????????? ?????????????? ?????????? ???????????? ?????????????? ??????????????. ???????? ????????????, ???????????? ???????????? ?? ?????????? ???????????????? ???????????? ??????????????. ???? ?????????? ???????????? ???????? ???????????????????? ?????????????? ??????????????-???????????? ??????????????. ?????????????????????? ?? ???????????????????? ?????????????????????? ????????????????????, ?????????? ???????????????????? ?? ??????????????, ?????????????? ???? ?????????????????? ????????????.",
                "https://www.niasam.ru/allimages/109819.jpg", new Date()));
        notesArray.add(new Note("id6", "???????????????????????? ?????????????? (Sturnus vulgaris)",
                "???????????????????????? ??????????????, ?????????????? ???????????????????? ???????????????????? ?????????????? ????????????, ???????????????? ???????????? ???????????????????????????????? ?? ???????????? ???????????? ????????????, ?????????????? ?????????? ?????? ??????????????, ?????? ?? ???????????????? ?? ???????? ?????????????????????? ???????????? ??????????. ?? ?????????? ???? ?????????????????? 22 ??, ???????????? ?????????????? ?????????? 40 ????, ?????????? ???? 80 ??. ???????????????????????????? ?????????????? ???????????????? ?????????????? ????????????????????????, ???????????????? ??????, ?????????????? ?????????????????? ???????????? ????????, ?????????????? ?????????????? ???? ?????????? ???????????????? ??????????????. ?????????????????? ???????????????????? ?????????????? ???? ?????????? ?????????? ?????????????????? ???????????????????????? ????????????????. ??????????, ?????????? ?? ?????? ?????????? ????????????, ??????????????????, ?? ????????????????????, ??????????, ???????????????????? ?????? ???????????????????? ????????????????. ?????????? ????????????????. ?????????? ??????????. ?? ???????????? ???????????? ???????????????? ???????????????????? ?????????? ?? ?????????? ????????????????. ?????????????????????? ?????????? ??????????????????????????, ???????????????? ?????????????????? ????????????, ?? ?????? ?????????? ?? ?????????????????????? ???????????? ?????????? ????????.",
                "https://www.niasam.ru/allimages/109820.jpg", new Date()));
        notesArray.add(new Note("id7", "?????????????????? (Troglodytes troglodytes)",
                "?? ?????????????? ?? ?????????????????? ???????????? ?????????? ?????????? ?????????????? ?????????? ?????? ?????????????????? ??????????????????. ???????????? ?????????????? ?? ???????????????? ?????????????? ???? ?????? ?????????? ?????????????????? ????????????????????, ?? ???? ???????????????? ?????????????????? ?????? ???? ?????????????????????? ??????????????. ?????????????????? ?? ???????????????????? ???????????????? ????????????????????. ???????????? ???????????? ?????????? 10 ????, ?? ???????????? ?????????????? ???? 20 ???? ?? ?????????? ???????????????? 10 ?? ????????????????, ?????????? ???????????????? ?????????? ?? ??????????????, ?? ?????? ???????????? ?????? ?? ?????? ???????????????? ?????? ?? ?????????????? ????????????. ?????????????? ?????????? ???????? ????????????????-??????????, ?????????????? ????????-???????????? ??????????. ?????? ???????? ?????????????????? ?????????????? ?????????????????????? ??????????????????, ???????????????? ???????????? ?? ?????????????? ?????????????? ?? ????????????. ?????????????????? ?????????????????????? ????????????????, ???????????? ?? ?????????????? ????????. ?????????? ?? ???????? ??????????????, ?????????????? ???? ???????????????? ?? ???????????????? ???????????? ?? ?????????????? ???????????? ???? ?????????? ????????????????.",
                "https://www.niasam.ru/allimages/109821.jpg", new Date()));
        notesArray.add(new Note("id8", "?????????????????? ?????????????? (Carduelis hornemanni)",
                "?? ?????????? ?????????????????? ?????????????? ?????????? 15 ????, ???????????? ?????????? ???????????????????? ?????????? ?????????????????? ?????????????????????? ???????????????????????? ??????????????, ???? ?????????????? ???????????????????? ?????????? ?????????????? ??????????????????, ?? ?????????? ??????????????????????, ?? ???? ?????????????????? ??????????????????????. ???????????? ?? ?????????? ???????????? ??????????, ???????????? ??????????????, ?????????????? ?????????? ??????????????, ??????????????????. ?? ???????????? ?????????????? ?????????????????? ?????????? ???? ??????????, ?? ?????????????? ?????? ??????. ???????? ?? ???????????????????????????? ?????????? ?????????? ?????????? ????????. ?????????????????????? ?????????????? ???? ?????????? ???????????? ????????????. ?????????????????? ?????????????? ?????????????????????? ?? ???????????? ???????????????? ???????????? ????????. ?????????????? ?????? ?? ???????????????? ?? ?????????????? ??????????, ?? ????????????, ?????????? ???????????????? ???????????? ??????????????????????. ???????????????? ?????? ????????????????????????, ?????? ?? ???????????????? ??????????. ?? ?? ?????????????????????????? ?????????????????? ?????????? ?????????? ???????????????? ?????????????????? ????????.",
                "https://www.niasam.ru/allimages/109822.jpg", new Date()));
        notesArray.add(new Note("id9", "???????????????????? ?????????? (Loxia leucoptera)",
                "?????? ???????????? ???????????????? ?????????????? ?????????????? ?????????????????? ?????????????? ???????????????? ???????????????????? ????????????, ?????????????? ?? ???? ???? ?????????? ???????????????? ?? ?????????????????? ?????????? ???????????????????????? ?????????????????? ????????????. ???????? ???????????? ?????????? ?????????? ???????? ?? ???????????? ??????????????????????. ?? ?????? ?????????? ?????? ?????????? ???????????????? ???????????? ??????????????, ?????????????? ?????????? ??????????-??-??-??-??-??-???? ?? ???????????? ???????????????? ??????????????. ?????????? ???????? ???????????????????????????? ???????? ?????????? 16 ????. ???????? ??????????????, ???????????????? ?????????? ?????????????????????? ?????????? ?????????????? ?????????? ???????????????? ?? ???????????????? ????????????, ???????????????? ?????? ?????????????????????????????? ????????????????. ???????????????? ?? ???????????? ?? ?????????? ?????????? ???????? ???????????????????? ??????????????????????. ?????????????? ???????????????? ?? ???????????????????? ????????, ?????????? ??? ????????????????-?????????????? ?????? ????????????????-??????????????. ???????????? ???????????? ?? ?????????? ???????????????????????? ????????????????.",
                "https://www.niasam.ru/allimages/109823.jpg", new Date()));
        notesArray.add(new Note("id10", "???????????? ?????????????? (Cuculus optatus)",
                "???????????? ?????????????? ?????????????? ???????????????? ?? ???????????????? ?????????????? ????????????????????????, ???? ?? ?????????????????? ?????????????????? ???????? ???????? ???????? ???????????????? ????????????, ???????? ?????? ???????????? ???????????? ?? ???? ???????????? ??????????. ?????? ???????? ???????????????? ????????????????, ???? ???????????? ?????????????? ?????? ???? ?????????? ???????????????? ?? ?????????????????????? ?? ???????????????? ???? ???????????????????? ???????????? ?????????????? ??????????. ?????????? ????????, ?????????? ???????? ???????? ?????????? ?????????? ???????????? ????????????????. ?????? ???? ???????????????????????? ?????????? ??????-??????, ?? ???????????? ???????? ????????-????-???? ??????-???? ??????-??????. ?????????????? ???????? ?? ???????????? ?????????????? ??????????????. ?? ?????????? ?????? ?????????????????? 45 ????, ???????????? ?????????????? ?????????? 60 ????, ?????? ???? 100 ??. ???????????? ?????????? ???????????? ??????????, ?????????? ????????????, ???????????? ????????????-??????????. ???????????? ?????????? ???????? ?????????? ?? ?????????????? ??????????????????. ???????? ???????????????????? ??????????. ???????????????? ?????????? ???????????????? ?? ???????????? ????????.",
                "https://www.niasam.ru/allimages/109824.jpg", new Date()));
        notesArray.add(new Note("id11", "?????????? ???????????????????? (Motacilla alba)",
                "?? ?????????? ?????????? ???????????????????? ?????????????????? 20 ????, ?????? ?????????? 25 ??. ?????????????????????? ???????????? ???? ?????????????????? ?????????? ???????????????? ?????????????? ??????????, ?????????????? ?????? ?????????????????? ?????????????????? ???????????????????? ????????????????. ???????????????? ?????????? ???????? ???????????? ??????????, ?????????? ??? ????????????. ???????????? ??????????????????????, ???????????????? ???????????? ????????????????. ?????????? ?????????? ????????????. ?????????? ???????????????????? ?????????? ?????????????? ?????????? ?????????? ???? ???????????????????? ???????????? ?? ?????????? ?????????????????? ??????????????, ???? ???????????? ?????????? ?? ?????????????? ???????????????? ?????????????????????????? ????????????????. ???????? ???????????? ?????? ?????????? ???????????? ?????????? ?????????????????? ?????? ???????????? ???????????? ?????? ?????????? ????????????, ?????????? ???????? ???????????????? ????????????, ?????????????????? ???????????? ?????? ???????????? ?????????? ????????. ???????????????? ?????????? ???????????????????? ???????????????? ??????????, ????????????????????, ???? ???????????????????????? ???????? ?????????????????? ?????????? ??????????.",
                "https://www.niasam.ru/allimages/109825.jpg", new Date()));
        notesArray.add(new Note("id12", "???????????????????????? ???????????????? (Carpodacus erythrinus)",
                "?? ?????????? ?????????????????? ?????????????? ?????????? ?? ?????????????????? ???????????????????? ?????????? ???????????? ???????????????????????? ???????????????? ??? ????????????, ???? ???????????????? ????????????????, ?????? ?????????????? ??????????????. ?????????????????? ???? ?????????? ?? ???????????????? ???????????????????? ???? ???????????? ?????????????? ?????? ?????????? ?? ????????????????. ?? ?????? ?????? ?????????????? ?????????? ?????????????? ?????? ?????????? ?????????????? ???????????????????? ???? ?????????????????????? ????????, ???? ???? ?????????????????????? ???????????? ???????????? ???????????????? ???????????????????????? ?????????????????? ??????????, ?????????????????? ???? ???????????? ?????????????????????? ????????????, ???????????????????????????? ?? ?????????? ????????????-??????????????. ???????? ???????? ?????????? ???????????????????????? ???????????????? ????????????????????-??????????, ???????????? ?? ???????????? ?????????? ????????, ?????????????? ????????-??????????????. ???????????????? ?? ?????????????? ????????????????, ????????????????-???????????? ?????????? ???? ?????????????? ??????????????????.",
                "https://www.niasam.ru/allimages/109826.jpg", new Date()));

        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        for (Note note: notesArray) {
            data.put(TITLE, note.getTitle());
            data.put(TEXT, note.getText());
            data.put(IMAGE, note.getUrl());
            data.put(DATE, date);
            firebaseFirestore.collection(NOTES)
                    .add(data)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()) {
                                Note noteAdd = new Note(task.getResult().getId(),
                                        note.getTitle(), note.getText(), note.getUrl(), date);

                                callback.onSuccess(Collections.singletonList(noteAdd));
                            }
                        }
                    });

        }
    }

    @Override
    public void add(String title, String text, String imageUrl, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();

        Date date = new Date();

        data.put(TITLE, title);
        data.put(TEXT, text);
        data.put(IMAGE, imageUrl);
        data.put(DATE, date);

        firebaseFirestore.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Note note = new Note(task.getResult().getId(), title, text, imageUrl, date);

                            callback.onSuccess(note);
                        }
                    }
                });
    }

    @Override
    public void remove(Note note, Callback<Object> callback) {
        firebaseFirestore.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(note);
                        }
                    }
                });

    }

    @Override
    public Note update(@NonNull Note note, @Nullable String title, @Nullable String text, @Nullable Date date) {
        return null;
    }
}
