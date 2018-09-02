package com.example.rahulmalhotra.popularmovies.PopularMovieUtils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieExecutors {

    private static final Object LOCK = new Object();
    private static MovieExecutors movieExecutorsInstance;
    private final Executor diskIO;

    public MovieExecutors(Executor diskIO) {
        this.diskIO = diskIO;
    }

    public Executor getDiskIO() {
        return diskIO;
    }

    public static MovieExecutors getInstance() {
        if(movieExecutorsInstance == null) {
            synchronized (LOCK) {
                movieExecutorsInstance = new MovieExecutors(Executors.newSingleThreadExecutor());
            }
        }
        return movieExecutorsInstance;
    }
}
