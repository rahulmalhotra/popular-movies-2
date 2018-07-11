package com.example.rahulmalhotra.popularmovies;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.NetworkUtils;

public class FetchMoviesTask extends AsyncTask<String, Void, String> {

    private MoviesInterface moviesInterface;

    public FetchMoviesTask(MoviesInterface moviesInterface) {
        this.moviesInterface = moviesInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings.length==0) {
            return null;
        }

        String sortBy = strings[0];
        URL url = NetworkUtils.buildUrl(sortBy);
        try {
            String apiResponse = NetworkUtils.getResponseFromHttpUrl(url);
            return apiResponse;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String jsonResponse) {
        List<String> movieURLs = new ArrayList<String>();
        try {
            if(jsonResponse!=null) {
                JSONObject baseObject = new JSONObject(jsonResponse);
                JSONArray moviesArray = baseObject.getJSONArray("results");
                for (int i = 0; i <moviesArray.length() ; i++) {
                    JSONObject movieObject = (JSONObject) moviesArray.get(i);
                    String imageURL = "http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
                    movieURLs.add(imageURL);
                }
                moviesInterface.getMovies(movieURLs.toArray(new String[movieURLs.size()]));
            } else {
                moviesInterface.getMovies(null);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(jsonResponse);
    }
}
