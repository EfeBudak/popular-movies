package com.pasha.efebudak.popularmovies.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.pasha.efebudak.popularmovies.R;
import com.pasha.efebudak.popularmovies.adapter.MovieListAdapter;
import com.pasha.efebudak.popularmovies.model.Result;
import com.pasha.efebudak.popularmovies.util.Utils;

/**
 * Created by efebudak on 23/09/15.
 */
public class MovieListFragment extends Fragment {

    public interface MovieListItemListener {

        void onItemClicked(int position);
        void callMovieService();

    }

    private MovieListItemListener movieListItemListener;
    private GridView gridViewMovieList;
    private Result result;

    public static MovieListFragment newInstance() {

        return new MovieListFragment();

    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        gridViewMovieList = (GridView) view.findViewById(R.id.movie_list_grid_view);
        gridViewMovieList.setNumColumns(Utils.calculateNumberOfColumns(getActivity()));

        if(savedInstanceState != null){
            result = savedInstanceState.getParcelable("result");
        }

        if(result == null){
            movieListItemListener.callMovieService();
        }

        if (result != null) {
            gridViewMovieList.setAdapter(new MovieListAdapter(getActivity(), result));
        }

        gridViewMovieList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        movieListItemListener.onItemClicked(i);

                    }
                });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable("result", result);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        movieListItemListener = (MovieListItemListener) activity;
    }

    @Override
    public void onDetach() {
        movieListItemListener = null;
        super.onDetach();
    }

    public void updateResult(final Result result) {

        this.result = result;
        gridViewMovieList.setAdapter(new MovieListAdapter(getActivity(), result));

    }

}
