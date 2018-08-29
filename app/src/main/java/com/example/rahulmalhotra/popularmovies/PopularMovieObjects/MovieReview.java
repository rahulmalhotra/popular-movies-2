package com.example.rahulmalhotra.popularmovies.PopularMovieObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieReview implements Parcelable {

    private String author;
    private String content;
    private String id;
    private String url;

    public MovieReview(String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    private MovieReview(Parcel parcel) {
        author = parcel.readString();
        content = parcel.readString();
        id = parcel.readString();
        url = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(id);
        parcel.writeString(url);
    }

    public static final Creator<MovieReview> CREATOR = new Creator<MovieReview>() {
        @Override
        public MovieReview createFromParcel(Parcel parcel) {
            return new MovieReview(parcel);
        }

        @Override
        public MovieReview[] newArray(int size) {
            return new MovieReview[size];
        }
    };
}
