package com.example.rahulmalhotra.popularmovies.PopularMoviesDB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static MovieDatabase movieDatabaseInstance;
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviedb";

    public static MovieDatabase getInstance(Context context) {
        if(movieDatabaseInstance == null) {
            synchronized (LOCK) {
                movieDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class, MovieDatabase.DATABASE_NAME).build();
            }
        }
        return movieDatabaseInstance;
    }

    public abstract MovieDao movieDao();

}
