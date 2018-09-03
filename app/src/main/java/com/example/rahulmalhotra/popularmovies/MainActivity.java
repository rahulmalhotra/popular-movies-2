package com.example.rahulmalhotra.popularmovies;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.example.rahulmalhotra.popularmovies.PopularMovieAdapters.ImageAdapter;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.MoviesViewModel;
import com.example.rahulmalhotra.popularmovies.PopularMoviesDB.MovieDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesInterface{

    private ArrayList<Movie> moviesList;
    private ArrayList<Movie> bookmarkedMoviesList;

    private MovieDatabase movieDatabase;
    private ImageAdapter imageAdapter;
    private Boolean bookmarkedMoviesShown;

    @BindView(R.id.moviesView) GridView moviesView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if(bookmarkedMoviesShown)
            outState.putParcelableArrayList("movies", bookmarkedMoviesList);
        else
            outState.putParcelableArrayList("movies", moviesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        bookmarkedMoviesShown = false;
        initializeViewModel();
        movieDatabase = MovieDatabase.getInstance(getApplicationContext());
        moviesList = new ArrayList<>();
        imageAdapter = new ImageAdapter(this, moviesList);
        moviesView.setAdapter(imageAdapter);
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            if(checkNetworkConnection())
                getApiResponse("popular");
            else
                Toast.makeText(this, "Network Connection not available", Toast.LENGTH_SHORT).show();
        } else {
            moviesList.clear();
            moviesList.addAll(savedInstanceState.<Movie>getParcelableArrayList("movies"));
            setMoviesView();
        }
        moviesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), MovieDetail.class);
            if(bookmarkedMoviesShown) {
                intent.putExtra("movie", bookmarkedMoviesList.get(i));
                Log.d("test1", String.valueOf(bookmarkedMoviesList.get(i).getMovieId()));
            } else {
                intent.putExtra("movie", moviesList.get(i));
            }
            startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByPopular:
                bookmarkedMoviesShown = false;
                getApiResponse("popular");
                return true;
            case R.id.sortByRated:
                bookmarkedMoviesShown = false;
                getApiResponse("top_rated");
                return true;
            case R.id.bookmarkedMovies:
                bookmarkedMoviesShown = true;
                getBookMarkedMovies();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
    }

    private void getBookMarkedMovies() {
        imageAdapter.setMovies(bookmarkedMoviesList);
    }

    private void initializeViewModel() {
        MoviesViewModel movieViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        LiveData<List<Movie>> moviesLiveData = movieViewModel.getMovies();
        moviesLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                bookmarkedMoviesList = new ArrayList<>(movies);
                if(bookmarkedMoviesShown) {
                    imageAdapter.setMovies(bookmarkedMoviesList);
                }
            }
        });
    }

    private void getApiResponse(String sortyBy){
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(this, null);
        fetchMoviesTask.execute(sortyBy);
    }

    private void setMoviesView() {
        imageAdapter.setMovies(moviesList);
    }

    @Override
    public void getMovies(ArrayList<Movie> movies) {
        if(movies==null) {
            Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
        } else {
            moviesList.clear();
            moviesList.addAll(movies);
            setMoviesView();
        }
    }
}
