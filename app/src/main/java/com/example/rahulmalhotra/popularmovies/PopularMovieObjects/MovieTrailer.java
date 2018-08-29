package com.example.rahulmalhotra.popularmovies.PopularMovieObjects;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieTrailer implements Parcelable {

    private String id;
    private String key;
    private String name;
    private int size;
    private String site;
    private String type;

    public MovieTrailer(String id, String key, String name, int size, String site, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.size = size;
        this.site = site;
        this.type = type;
    }

    protected MovieTrailer(Parcel parcel) {
        id = parcel.readString();
        key = parcel.readString();
        name = parcel.readString();
        size = parcel.readInt();
        site = parcel.readString();
        type = parcel.readString();
    }

    public static final Creator<MovieTrailer> CREATOR = new Creator<MovieTrailer>() {
        @Override
        public MovieTrailer createFromParcel(Parcel in) {
            return new MovieTrailer(in);
        }

        @Override
        public MovieTrailer[] newArray(int size) {
            return new MovieTrailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(key);
        parcel.writeString(name);
        parcel.writeInt(size);
        parcel.writeString(site);
        parcel.writeString(type);
    }
}
