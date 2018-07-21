package com.example.rahulmalhotra.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetail extends AppCompatActivity {

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

    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = getIntent().getParcelableExtra("movie");
        ButterKnife.bind(this);
        movieTitle.setText(movie.getOriginalTitle());
        movieDate.setText("Release Date: \n" + movie.getReleaseDate());
        Picasso.with(this).load(movie.getPosterPath()).into(movieImage);
        movieRating.setText("Rating: \n" + String.valueOf(movie.getVoteAverage()));
        movieDescription.setText(movie.getOverview());
    }
}
