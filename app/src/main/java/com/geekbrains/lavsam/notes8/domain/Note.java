package com.geekbrains.lavsam.notes8.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private final String id;
    private final String title;
    private final String text;
    private final String url;

    public Note(String id, String title, String text, String url) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.url = url;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        text = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}
