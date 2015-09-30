package com.pasha.efebudak.popularmovies.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.pasha.efebudak.popularmovies.model.Result;

import retrofit.RestAdapter;


/**
 * Created by efebudak on 23/09/15.
 */
public class NetworkService extends IntentService {

    private static final String DEFAULT_WORKER_THREAD_NAME = "defaultWorkerThreadName";

    private static final String KEY_URL = "url";

    public static Intent newIntent(
            Context context,
            String url) {

        final Intent intent = new Intent(context, NetworkService.class);
        final Bundle bundle = new Bundle();

        bundle.putString(KEY_URL, url);

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

        RestAdapter retrofit
                = new RestAdapter.Builder().setEndpoint(url).build();
        TheMovieDbService theMovieDbService = retrofit.create(TheMovieDbService.class);

        Result resultCall
                = theMovieDbService.listMovies(
                "popularity.desc", "962b77a3c4dfa95e0e12b1655fdb620a");

        sendMessage(resultCall);

    }

    private void sendMessage(Result result) {

        Log.d("sender", "Broadcasting message");
        Intent intent = new Intent("custom-event-name");
        Bundle bundle = new Bundle();

        bundle.putParcelable("results", result);

        intent.putExtras(bundle);

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
