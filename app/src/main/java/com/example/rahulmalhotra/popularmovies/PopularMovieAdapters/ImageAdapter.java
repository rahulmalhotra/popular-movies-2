package com.example.rahulmalhotra.popularmovies.PopularMovieAdapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private String[] movies;

    public ImageAdapter(Context context, String[] moviesArray) {
        mContext = context;
        movies = moviesArray;
    }

    @Override
    public int getCount() {
        return movies.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView;
        if(view==null) {
            imageView  = new ImageView(mContext);
            imageView.setPadding(8,8,8,8);
            imageView.setAdjustViewBounds(true);
        }
        else {
            imageView = (ImageView) view;
        }
        Picasso.with(mContext).load(movies[i]).into(imageView);
        return imageView;
    }
}
