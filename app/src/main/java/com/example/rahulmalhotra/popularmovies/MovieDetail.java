package com.example.rahulmalhotra.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rahulmalhotra.popularmovies.PopularMovieAdapters.MovieReviewAdapter;
import com.example.rahulmalhotra.popularmovies.PopularMovieAdapters.MovieTrailerAdapter;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieReview;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieTrailer;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.MovieExecutors;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.MovieViewModel;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.MovieViewModelFactory;
import com.example.rahulmalhotra.popularmovies.PopularMoviesDB.MovieDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

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

    @BindView(R.id.movieReviews)
    RecyclerView movieReviewsRV;

    @BindView(R.id.movieTrailers)
    RecyclerView movieTrailersRV;

    private MovieDatabase movieDatabaseInstance;
    private String TAG = MovieDetail.class.getSimpleName();
    private Movie movie;
    private Boolean movieBookmarked;
    private MovieReviewAdapter movieReviewAdapter;
    private MovieTrailerAdapter movieTrailerAdapter;
    private ArrayList<MovieReview> movieReviewArrayList;
    private ArrayList<MovieTrailer> movieTrailerArrayList;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bookmark:
                if(!movieBookmarked) {
                    insertMovie();
                    item.setIcon(R.drawable.bookmark);
                } else {
                    deleteMovie();
                    item.setIcon(R.drawable.bookmarkempty);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertMovie() {
        MovieExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
//            movie.setMovieId(null);
                movieDatabaseInstance.movieDao().insertFavoriteMovie(movie);
            }
        });
    }

    private void deleteMovie() {
        MovieExecutors.getInstance().getDiskIO().execute(new Runnable() {
            @Override
            public void run() {
            movieDatabaseInstance.movieDao().deleteFavoriteMovie(movie);
//            movie.setMovieId(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        if(movie!= null && movie.getMovieId() != null) {
            menu.findItem(R.id.bookmark).setIcon(R.drawable.bookmark);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = getIntent().getParcelableExtra("movie");
        ButterKnife.bind(this);
        setMovieReviewAdapter(null);
        fetchTrailersAndReviews(movie);
        movieTitle.setText(movie.getOriginalTitle());
        movieDate.setText("Release Date: \n" + movie.getReleaseDate());
        Picasso.with(this).load(movie.getPosterPath()).into(movieImage);
        movieRating.setText("Rating: \n" + String.valueOf(movie.getVoteAverage()));
        movieDescription.setText(movie.getOverview());
        movieDatabaseInstance = MovieDatabase.getInstance(getApplicationContext());
        initializeMovieViewModel();
    }

    private void initializeMovieViewModel() {
        MovieViewModelFactory movieViewModelFactory = new MovieViewModelFactory(movieDatabaseInstance, movie.getId());
        MovieViewModel movieViewModel = ViewModelProviders.of(this, movieViewModelFactory).get(MovieViewModel.class);
        movieViewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie bookmarkedMovie) {
                Log.d("bmk", String.valueOf(bookmarkedMovie));
                if(bookmarkedMovie!=null) {
                    movie = bookmarkedMovie;
                    movieBookmarked = true;
                } else {
                    movieBookmarked = false;
                }
            }
        });
    }

    private void setMovieReviewAdapter(ArrayList<MovieReview> movieReviewArrayList) {
        if(movieReviewArrayList==null)
            movieReviewArrayList = new ArrayList<MovieReview>();
        movieReviewsRV.removeAllViewsInLayout();
        movieReviewAdapter = new MovieReviewAdapter(movieReviewArrayList);
        movieReviewsRV.setAdapter(movieReviewAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        movieReviewsRV.setLayoutManager(manager);
        movieReviewsRV.setHasFixedSize(true);
    }

    private void setMovieTrailerAdapter(ArrayList<MovieTrailer> movieTrailerArrayList) {
        if(movieTrailerArrayList==null)
            movieTrailerArrayList = new ArrayList<MovieTrailer>();
        movieTrailersRV.removeAllViewsInLayout();
        movieTrailerAdapter = new MovieTrailerAdapter(movieTrailerArrayList);
        movieTrailersRV.setAdapter(movieTrailerAdapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        movieTrailersRV.setLayoutManager(manager);
        movieTrailersRV.setHasFixedSize(true);
    }

    private void fetchTrailersAndReviews(Movie movie) {
        String movieId = String.valueOf(movie.getId());
        FetchMoviesTask fetchMoviesTask1 = new FetchMoviesTask(null, this);
        fetchMoviesTask1.execute(movieId, "reviews");
        FetchMoviesTask fetchMoviesTask2 = new FetchMoviesTask(null, this);
        fetchMoviesTask2.execute(movieId, "videos");
    }

    @Override
    public void getMovieReviews(ArrayList<MovieReview> movieReviews) {
        setMovieReviewAdapter(movieReviews);
    }

    @Override
    public void getMovieTrailers(ArrayList<MovieTrailer> movieTrailers) {
        setMovieTrailerAdapter(movieTrailers);
    }
}