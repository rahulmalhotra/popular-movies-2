package com.example.rahulmalhotra.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.rahulmalhotra.popularmovies.PopularMovieAdapters.ImageAdapter;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MoviesInterface{

    @BindView(R.id.moviesView) GridView moviesView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getApiResponse("popular.desc");
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

    private void setMoviesView(String[] movies) {
        ImageAdapter imageAdapter = new ImageAdapter(this, movies);
        imageAdapter.notifyDataSetChanged();
        moviesView.setAdapter(imageAdapter);
    }

    @Override
    public void getMovies(String[] movies) {
        if(movies==null) {
            Toast.makeText(this, "No response from server", Toast.LENGTH_SHORT).show();
        }
        setMoviesView(movies);
    }
}
