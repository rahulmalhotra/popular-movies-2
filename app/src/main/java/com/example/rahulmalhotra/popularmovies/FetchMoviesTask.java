package com.example.rahulmalhotra.popularmovies;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.Movie;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieReview;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieTrailer;
import com.example.rahulmalhotra.popularmovies.PopularMovieUtils.NetworkUtils;

public class FetchMoviesTask extends AsyncTask<String, Void, String> {

    private MoviesInterface moviesInterface;
    private MoviesDetailInterface moviesDetailInterface;
    private URL url;

    public FetchMoviesTask(MoviesInterface moviesInterface, MoviesDetailInterface moviesDetailInterface) {
        this.moviesInterface = moviesInterface;
        this.moviesDetailInterface = moviesDetailInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings.length==0) {
            return null;
        }

        String sortBy = strings[0];
        url = NetworkUtils.buildUrl(sortBy);
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
        ArrayList<Movie> movieList = new ArrayList<>();
        ArrayList<MovieReview> movieReviewList = new ArrayList<>();
        ArrayList<MovieTrailer> movieTrailerList = new ArrayList<>();
        try {
            if(jsonResponse!=null && url!=null) {
                JSONObject baseObject = new JSONObject(jsonResponse);
                if(url.toString().contains("top_rated") || url.toString().contains("popular")) {
                    JSONArray moviesArray = baseObject.getJSONArray("results");
                    for (int i = 0; i <moviesArray.length() ; i++) {
                        JSONObject movieObject = (JSONObject) moviesArray.get(i);
                        String imageURL = "http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
                        movieList.add(
                                i,
                                new Movie(
                                        movieObject.getInt("id"),
                                        movieObject.getString("original_title"),
                                        imageURL,
                                        movieObject.getString("overview"),
                                        movieObject.getDouble("vote_average"),
                                        movieObject.getString("release_date")
                                )
                        );
                    }
                    moviesInterface.getMovies(movieList);
                }
                else if(url.toString().contains("reviews")) {
                    JSONArray movieReviewsArray = baseObject.getJSONArray("results");
                    for (int i = 0; i <movieReviewsArray.length() ; i++) {
                        JSONObject movieReviewObject = (JSONObject) movieReviewsArray.get(i);
                        movieReviewList.add(
                                i,
                                new MovieReview(
                                        movieReviewObject.getString("author"),
                                        movieReviewObject.getString("content"),
                                        movieReviewObject.getString("id"),
                                        movieReviewObject.getString("url")
                                )
                        );
                    }
                    moviesDetailInterface.getMovieReviews(movieReviewList);
                }
                else if(url.toString().contains("videos")) {
                    JSONArray movieTrailersArray = baseObject.getJSONArray("results");
                    for (int i = 0; i < movieTrailersArray.length(); i++) {
                        JSONObject movieTrailerObject = (JSONObject) movieTrailersArray.get(i);
                        movieTrailerList.add(
                                i,
                                new MovieTrailer(
                                        movieTrailerObject.getString("id"),
                                        movieTrailerObject.getString("key"),
                                        movieTrailerObject.getString("name"),
                                        movieTrailerObject.getInt("size"),
                                        movieTrailerObject.getString("site"),
                                        movieTrailerObject.getString("type")
                                )
                        );
                    }
                    moviesDetailInterface.getMovieTrailers(movieTrailerList);
                }
            } else {
                if(url.toString().contains("top_rated") || url.toString().contains("popular")) {
                    moviesInterface.getMovies(null);
                }
                else if(url.toString().contains("reviews")) {
                    moviesDetailInterface.getMovieReviews(null);
                }
                else if(url.toString().contains("videos")) {
                    moviesDetailInterface.getMovieTrailers(null);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.onPostExecute(jsonResponse);
    }
}
