package com.pasha.efebudak.popularmovies.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.pasha.efebudak.popularmovies.BuildConfig;
import com.pasha.efebudak.popularmovies.model.MovieReviewResult;

import retrofit.RestAdapter;

/**
 * Created by efebudak on 09/10/15.
 */
public class MovieReviewsNetworkService extends IntentService{

    private static final String KEY_MOVIE_ID = "movieId";

    public static Intent newIntent(Context context, String movieId) {

        final Intent intent = new Intent(context, MovieReviewsNetworkService.class);
        final Bundle bundle = new Bundle();

        bundle.putString(KEY_MOVIE_ID, movieId);

        intent.putExtras(bundle);

        return intent;

    }

    public MovieReviewsNetworkService(){
        super("default");
    }

    public MovieReviewsNetworkService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String movieId = intent.getExtras().getString(KEY_MOVIE_ID);

        RestAdapter retrofit
                = new RestAdapter.Builder().setEndpoint(BuildConfig.BUILD_URL).build();
        TheMovieDbService theMovieDbService = retrofit.create(TheMovieDbService.class);

        MovieReviewResult resultCall;

        try {
            resultCall = theMovieDbService.listMovieReviews(
                    movieId, BuildConfig.MOVIE_DB_API_KEY);
        } catch (Exception e) {
            resultCall = null;
        }

        sendMessage(resultCall);

    }

    private void sendMessage(MovieReviewResult movieReviewResult) {

        Intent intent = new Intent("movie-review-results");
        Bundle bundle = new Bundle();

        bundle.putParcelable("movieReviewResults", movieReviewResult);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
