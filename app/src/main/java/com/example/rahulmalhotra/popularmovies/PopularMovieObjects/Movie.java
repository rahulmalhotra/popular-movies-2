package com.example.rahulmalhotra.popularmovies.PopularMovieObjects;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

@Entity(tableName = "movie")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer movieId;
    private Integer id;
    private String originalTitle;
    private String posterPath;
    private String overview;
    private double voteAverage;
    private String releaseDate;

    public Integer getMovieId() {
        return movieId;
    }

    public Integer getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Movie(Integer movieId, Integer id, String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate) {
        Log.d("kkk", String.valueOf(movieId));
        this.movieId = movieId;
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Ignore
    public Movie(Integer id, String originalTitle, String posterPath, String overview, double voteAverage, String releaseDate) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.releaseDate = releaseDate;
    }

    @Ignore
    private Movie(Parcel parcel) {
        id = parcel.readInt();
        originalTitle = parcel.readString();
        posterPath = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readDouble();
        releaseDate = parcel.readString();
        movieId = parcel.readInt();
        if(movieId==0) {
            movieId = null;
        }
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(originalTitle);
        parcel.writeString(posterPath);
        parcel.writeString(overview);
        parcel.writeDouble(voteAverage);
        parcel.writeString(releaseDate);
        if(movieId!=null)
            parcel.writeInt(movieId);
    }

}
