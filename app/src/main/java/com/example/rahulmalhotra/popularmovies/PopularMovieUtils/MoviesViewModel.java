package com.example.rahulmalhotra.popularmovies.PopularMovieUtils;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMoviesDB.MovieDatabase;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private LiveData<List<Movie>> movies;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        MovieDatabase movieDatabase = MovieDatabase.getInstance(getApplication());
        movies = movieDatabase.movieDao().loadFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
