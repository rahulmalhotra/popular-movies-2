package com.example.rahulmalhotra.popularmovies;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieReview;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieTrailer;

import java.util.ArrayList;

public interface MoviesDetailInterface {
    void getMovieReviews(ArrayList<MovieReview> movieReviews);
    void getMovieTrailers(ArrayList<MovieTrailer> movieTrailers);
}