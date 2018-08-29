package com.example.rahulmalhotra.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieReview;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity implements MoviesDetailInterface {

    @BindView(R.id.movieTitle)
    TextView movieTitle;

    @BindView(R.id.movieImage)
    ImageView movieImage;

    @BindView(R.id.movieDate)
    TextView movieDate;

    @BindView(R.id.movieRating)
    TextView movieRating;

    @BindView(R.id.movieDescription)
    TextView movieDescription;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = getIntent().getParcelableExtra("movie");
        fetchTrailersAndReviews(movie);
        ButterKnife.bind(this);
        movieTitle.setText(movie.getOriginalTitle());
        movieDate.setText("Release Date: \n" + movie.getReleaseDate());
        Picasso.with(this).load(movie.getPosterPath()).into(movieImage);
        movieRating.setText("Rating: \n" + String.valueOf(movie.getVoteAverage()));
        movieDescription.setText(movie.getOverview());
    }

    private void fetchTrailersAndReviews(Movie movie) {
        String movieId = String.valueOf(movie.getId());
        FetchMoviesTask fetchMoviesTask1 = new FetchMoviesTask(null, this);
        fetchMoviesTask1.execute(movieId + "/reviews");
        FetchMoviesTask fetchMoviesTask2 = new FetchMoviesTask(null, this);
        fetchMoviesTask2.execute(movieId + "/videos");
    }

    @Override
    public void getMovieReviews(ArrayList<MovieReview> movieReviews) {
        // Set the movie reviews
    }

    @Override
    public void getMovieTrailers(ArrayList<MovieTrailer> movieTrailers) {
        // Set the movie trailers
    }
}