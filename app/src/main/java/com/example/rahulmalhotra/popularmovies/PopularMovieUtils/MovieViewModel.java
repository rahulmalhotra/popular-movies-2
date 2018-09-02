package com.example.rahulmalhotra.popularmovies.PopularMovieUtils;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMoviesDB.MovieDatabase;

public class MovieViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;

    public LiveData<Movie> getMovieLiveData() {
        return movieLiveData;
    }

    public  MovieViewModel(MovieDatabase movieDatabase, Integer id) {
        movieLiveData = movieDatabase.movieDao().loadSingleMovie(id);
    }
}