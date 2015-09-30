package com.pasha.efebudak.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pasha.efebudak.popularmovies.R;
import com.pasha.efebudak.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by efebudak on 30/09/15.
 */
public class MovieDetailFragment extends Fragment {

    private static final String KEY_MOVIE = "keyMovie";

    /**
     * view variables
     */

    @Bind(R.id.movie_detail_text_view_title)
    TextView textViewTitle;
    @Bind(R.id.movie_detail_image_view_poster)
    ImageView imageViewPoster;
    @Bind(R.id.movie_detail_text_view_release_date)
    TextView textViewReleaseDate;
    @Bind(R.id.movie_detail_text_view_vote_average)
    TextView textViewVoteAverage;
    @Bind(R.id.movie_detail_text_view_synopsis)
    TextView textViewSynopsis;

    private Movie movie;

    public static MovieDetailFragment newInstance(Movie movie) {

        final MovieDetailFragment movieDetailFragment
                = new MovieDetailFragment();
        final Bundle bundle = new Bundle();

        bundle.putParcelable(KEY_MOVIE, movie);
        movieDetailFragment.setArguments(bundle);

        return movieDetailFragment;

    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        movie = getArguments().getParcelable(KEY_MOVIE);

        final View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, view);

        setDetailData();

        return view;
    }

    private void setDetailData() {

        textViewTitle.setText(movie.getTitle());
        Picasso.with(getActivity())
                .load("http://image.tmdb.org/t/p/w500/"
                        + movie.getPosterPath())
                .placeholder(getActivity().getResources().getDrawable(R.drawable.movie_placeholder))
                .into(imageViewPoster);
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewVoteAverage.setText(movie.getVoteAverage() + "");
        textViewSynopsis.setText(movie.getOverview());

    }
}
