package com.geekbrains.lavsam.notes8.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

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
    private String title;
    private final String text;
    private final String url;
    private Date date;

    public Note(String id, String title, String text, String url, Date date) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.url = url;
        this.date = date;
    }

    protected Note(Parcel in) {
        id = in.readString();
        title = in.readString();
        text = in.readString();
        url = in.readString();
        date = new Date(in.readLong());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(url);
        dest.writeLong(date.getTime());
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

    public Date getDate() { return date; }

    public void setTitle(String title) { this.title = title; }

    public void setDate(Date date) { this.date = date; }
}
