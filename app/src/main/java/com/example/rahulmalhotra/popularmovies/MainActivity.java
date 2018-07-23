package com.example.rahulmalhotra.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.rahulmalhotra.popularmovies.PopularMovieAdapters.ImageAdapter;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesInterface{

    private ArrayList<Movie> moviesList;

    @BindView(R.id.moviesView) GridView moviesView;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", moviesList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        moviesList = new ArrayList<>();
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            getApiResponse("popular.desc");
        } else {
            moviesList.clear();
            moviesList.addAll(savedInstanceState.<Movie>getParcelableArrayList("movies"));
            setMoviesView();
        }
        moviesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), MovieDetail.class);
            intent.putExtra("movie", moviesList.get(i));
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
                getApiResponse("popular.desc");
                return true;
            case R.id.sortByRated:
                getApiResponse("vote_average.desc");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getApiResponse(String sortyBy){
        Log.d("sorting by:- ", sortyBy);
        new FetchMoviesTask(this).execute(sortyBy);
    }

    private void setMoviesView() {
        ImageAdapter imageAdapter = new ImageAdapter(this, moviesList);
        imageAdapter.notifyDataSetChanged();
        moviesView.setAdapter(imageAdapter);
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
