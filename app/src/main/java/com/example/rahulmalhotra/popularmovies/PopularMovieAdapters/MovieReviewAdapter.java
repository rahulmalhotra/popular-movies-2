package com.example.rahulmalhotra.popularmovies.PopularMovieAdapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieReview;
import com.example.rahulmalhotra.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.MovieViewHolder> {

    ArrayList<MovieReview> movieReviewList;

    public MovieReviewAdapter(ArrayList<MovieReview> movieReviewList) {
        this.movieReviewList = movieReviewList;
    }

    public void setMovieReviewList(ArrayList<MovieReview> movieReviewList) {
        this.movieReviewList = movieReviewList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.moviereview, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.authorTV.setText(movieReviewList.get(position).getAuthor());
        holder.contentTV.setText(movieReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if(movieReviewList==null)
            return 0;
        else
            return movieReviewList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.author) TextView authorTV;
        @BindView(R.id.content) TextView contentTV;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}