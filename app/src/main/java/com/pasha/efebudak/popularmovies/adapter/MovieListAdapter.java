package com.pasha.efebudak.popularmovies.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.pasha.efebudak.popularmovies.R;
import com.pasha.efebudak.popularmovies.model.Result;
import com.squareup.picasso.Picasso;

/**
 * Created by efebudak on 23/09/15.
 */
public class MovieListAdapter extends BaseAdapter {

    private Context mContext;
    private Result result;

    public MovieListAdapter(Context c, Result result) {
        mContext = c;
        this.result = result;
    }

    public int getCount() {
        return result.getMovieArrayList().size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = (ImageView) convertView;

        if (convertView == null){

            imageView = new ImageView(mContext);

        }

        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w185/"
                        + result.getMovieArrayList().get(position).getPosterPath())
                .placeholder(mContext.getResources().getDrawable(R.drawable.movie_placeholder))
                .into(imageView);

        return imageView;
    }



}