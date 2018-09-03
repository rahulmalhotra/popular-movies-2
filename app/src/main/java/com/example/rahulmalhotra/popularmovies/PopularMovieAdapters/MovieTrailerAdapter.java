package com.example.rahulmalhotra.popularmovies.PopularMovieAdapters;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rahulmalhotra.popularmovies.MainActivity;
import com.example.rahulmalhotra.popularmovies.PopularMovieObjects.MovieTrailer;
import com.example.rahulmalhotra.popularmovies.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.MovieViewHolder> {

    ArrayList<MovieTrailer> movieTrailerArrayList;

    public MovieTrailerAdapter(ArrayList<MovieTrailer> movieTrailerArrayList) {
        this.movieTrailerArrayList = movieTrailerArrayList;
    }

    public void setMovieTrailerList(ArrayList<MovieTrailer> movieTrailerArrayList) {
        this.movieTrailerArrayList = movieTrailerArrayList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movietrailer, parent, false);
        return new MovieViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.trailerTitle.setText(movieTrailerArrayList.get(position).getName());
        holder.trailerType.setText(movieTrailerArrayList.get(position).getType());
        holder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movieTrailerArrayList.get(position).getKey()));
                view.getContext().startActivity(intent);
            }
        });
        holder.shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TITLE, "Share Trailer");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, movieTrailerArrayList.get(position).getName());
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, watch this amazing movie trailer:- https://www.youtube.com/watch?v=" + movieTrailerArrayList.get(position).getKey());
                view.getContext().startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
        });
        holder.trailerSize.setText(String.valueOf(movieTrailerArrayList.get(position).getSize()) + "px");
    }

    @Override
    public int getItemCount() {
        if(movieTrailerArrayList==null)
            return 0;
        else
            return movieTrailerArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.trailerTitle) TextView trailerTitle;
        @BindView(R.id.trailerType) TextView trailerType;
        @BindView(R.id.playButton)
        ImageButton playButton;
        @BindView(R.id.trailerSize) TextView trailerSize;
        @BindView(R.id.shareButton)
        ImageButton shareButton;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}