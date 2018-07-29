package com.example.rahulmalhotra.popularmovies.PopularMovieUtils;

import android.net.Uri;

import com.example.rahulmalhotra.popularmovies.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String MOVIE_DB_API_URL = "https://api.themoviedb.org/3/movie/";
    private static final String API_KEY = BuildConfig.API_KEY;

    public static URL buildUrl(String sortBy) {

        Uri builtUri = Uri.parse(MOVIE_DB_API_URL).buildUpon()
                .appendPath(sortBy)
                .appendQueryParameter("api_key", API_KEY)
                .build();

        URL url = null;

        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            if(scanner.hasNext()) {
                return scanner.next();
            } else {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }
    }

}
