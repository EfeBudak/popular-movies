package com.pasha.efebudak.popularmovies.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.pasha.efebudak.popularmovies.BuildConfig;
import com.pasha.efebudak.popularmovies.model.MovieVideoResult;

import retrofit.RestAdapter;

/**
 * Created by efebudak on 09/10/15.
 */
public class MovieVideosNetworkService extends IntentService {

    private static final String DEFAULT_WORKER_THREAD_NAME = "defaultWorkerThreadName";
    private static final String KEY_MOVIE_ID = "movieId";

    public static Intent newIntent(Context context, String movieId) {

        final Intent intent = new Intent(context, MovieVideosNetworkService.class);
        final Bundle bundle = new Bundle();

        bundle.putString(KEY_MOVIE_ID, movieId);

        intent.putExtras(bundle);

        return intent;

    }

    public MovieVideosNetworkService() {
        super(DEFAULT_WORKER_THREAD_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String movieId = intent.getExtras().getString(KEY_MOVIE_ID);

        RestAdapter retrofit
                = new RestAdapter.Builder().setEndpoint(BuildConfig.BUILD_URL).build();
        TheMovieDbService theMovieDbService = retrofit.create(TheMovieDbService.class);

        MovieVideoResult resultCall;

        try {
            resultCall = theMovieDbService.listMovieTrailers(
                    movieId, BuildConfig.MOVIE_DB_API_KEY);
        } catch (Exception e) {
            resultCall = null;
        }

        sendMessage(resultCall);

    }

    private void sendMessage(MovieVideoResult movieVideoResult) {

        Intent intent = new Intent("movie-videos-results");
        Bundle bundle = new Bundle();

        bundle.putParcelable("movieVideosResults", movieVideoResult);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
