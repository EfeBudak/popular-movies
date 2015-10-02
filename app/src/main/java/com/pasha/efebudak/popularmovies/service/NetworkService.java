package com.pasha.efebudak.popularmovies.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.pasha.efebudak.popularmovies.model.Result;

import retrofit.RestAdapter;


/**
 * Created by efebudak on 23/09/15.
 */
public class NetworkService extends IntentService {

    public static final int MOST_POPULAR = 0;
    public static final int HIGHEST_RATED = 1;

    private static final String DEFAULT_WORKER_THREAD_NAME = "defaultWorkerThreadName";
    //todo keep the API key at gradle.properties
    private static final String MOVIE_DB_API_KEY = "962b77a3c4dfa95e0e12b1655fdb620a";

    private static final String KEY_URL = "url";
    private static final String KEY_ORDER_TYPE = "orderType";

    public static Intent newIntent(Context context, String url) {


        return newIntent(context, url, MOST_POPULAR);

    }

    public static Intent newIntent(
            Context context,
            String url,
            int orderType) {

        final Intent intent = new Intent(context, NetworkService.class);
        final Bundle bundle = new Bundle();

        bundle.putString(KEY_URL, url);
        bundle.putInt(KEY_ORDER_TYPE, orderType);

        intent.putExtras(bundle);

        return intent;

    }

    public NetworkService() {
        super(DEFAULT_WORKER_THREAD_NAME);
    }

    public NetworkService(String workerThreadName) {
        super(workerThreadName);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final Bundle bundle = intent.getExtras();
        final String url = bundle.getString(KEY_URL);
        final int orderType = bundle.getInt(KEY_ORDER_TYPE);

        RestAdapter retrofit
                = new RestAdapter.Builder().setEndpoint(url).build();
        TheMovieDbService theMovieDbService = retrofit.create(TheMovieDbService.class);

        Result resultCall;

        try {
            resultCall
                    = theMovieDbService.listMovies(
                    findOrderType(orderType), MOVIE_DB_API_KEY);
        } catch (Exception e) {
            resultCall = null;
        }


        sendMessage(resultCall);

    }

    private String findOrderType(int orderType) {

        switch (orderType) {

            case MOST_POPULAR:
                return "popularity.desc";
            case HIGHEST_RATED:
                return "vote_average.desc";
            default:
                //just to be interesting
                return "release_date.desc";

        }

    }

    private void sendMessage(Result result) {

        Intent intent = new Intent("custom-event-name");
        Bundle bundle = new Bundle();

        bundle.putParcelable("results", result);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
