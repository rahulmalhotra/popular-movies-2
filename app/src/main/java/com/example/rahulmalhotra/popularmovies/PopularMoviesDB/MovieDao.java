package com.example.rahulmalhotra.popularmovies.PopularMoviesDB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadFavoriteMovies();

    @Insert
    void insertFavoriteMovie(Movie movie);

    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT * FROM movie WHERE id =:id")
    LiveData<Movie> loadSingleMovie(Integer id);

}
