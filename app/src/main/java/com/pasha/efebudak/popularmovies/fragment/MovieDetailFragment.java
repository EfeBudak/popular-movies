package com.pasha.efebudak.popularmovies.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pasha.efebudak.popularmovies.R;
import com.pasha.efebudak.popularmovies.model.Movie;
import com.pasha.efebudak.popularmovies.model.MovieReviewResult;
import com.pasha.efebudak.popularmovies.model.MovieVideoResult;
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
    @Bind(R.id.movie_detail_linear_layout_videos)
    LinearLayout linearLayoutVideos;
    @Bind(R.id.movie_detail_text_view_reviews)
    TextView textViewReviews;

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

    public void updateVideoList(final MovieVideoResult movieVideoResult) {

        for (int i = 0; i < movieVideoResult.getMovieVideoArrayList().size(); i++) {

            final String movieName = movieVideoResult.getMovieVideoArrayList().get(i).getName();
            final String movieKey = movieVideoResult.getMovieVideoArrayList().get(i).getKey();

            TextView textView = new TextView(getActivity());
            textView.setText(movieName);
            textView.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Intent intent
                            = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://youtu.be/" + movieKey));
                    final Intent browserIntentChooser = Intent.createChooser(intent, "Choooose please: ");

                    startActivity(browserIntentChooser);

                }
            });

            try {

                linearLayoutVideos.addView(textView);
            }catch (Exception e){
                Log.d("Add textview exception", e.getMessage());
            }

        }

    }

    public void updateReviewList(final MovieReviewResult movieReviewResult) {

        String reviewString = "";

        for (int i = 0; i < movieReviewResult.getMovieReviewArrayList().size(); i++) {

            final String reviewAuthor = movieReviewResult.getMovieReviewArrayList().get(i).getAuthor();
            final String reviewContent = movieReviewResult.getMovieReviewArrayList().get(i).getContent();

            reviewString = reviewAuthor + "\n" + reviewContent + "\n";

        }

        textViewReviews.setText(reviewString);

    }

}
