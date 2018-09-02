package com.example.rahulmalhotra.popularmovies.PopularMovieUtils;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.rahulmalhotra.popularmovies.PopularMoviesDB.MovieDatabase;

public class MovieViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private MovieDatabase movieDatabase;
    private Integer id;

    public MovieViewModelFactory(MovieDatabase movieDatabase, Integer id) {
        this.movieDatabase = movieDatabase;
        this.id = id;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MovieViewModel(movieDatabase, id);
    }
}
