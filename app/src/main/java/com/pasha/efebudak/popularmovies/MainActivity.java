package com.pasha.efebudak.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.pasha.efebudak.popularmovies.fragment.MovieDetailFragment;
import com.pasha.efebudak.popularmovies.fragment.MovieListFragment;
import com.pasha.efebudak.popularmovies.model.Movie;
import com.pasha.efebudak.popularmovies.model.Result;
import com.pasha.efebudak.popularmovies.service.NetworkService;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity implements
        MovieListFragment.MovieListItemListener {

    private Result result;

    @Bind(R.id.main_frame_layout_container)
    FrameLayout frameLayoutContainer;

    private FragmentManager fragmentManager;

    private MovieListFragment movieListFragment;
    private MovieDetailFragment movieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Get extra data included in the Intent

                Bundle bundle = intent.getExtras();
                result = bundle.getParcelable("results");
                movieListFragment.updateResult(result);

            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-event-name"));


        startService(
                NetworkService.newIntent(
                        this,
                        "https://api.themoviedb.org/3"));

        movieListFragment = MovieListFragment.newInstance();

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.main_frame_layout_container, movieListFragment)
                .addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClicked(int position) {

        openDetailFragment(result.getMovieArrayList().get(position));
    }

    private void openDetailFragment(Movie movie) {

        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        movieDetailFragment = MovieDetailFragment.newInstance(movie);
        fragmentTransaction.replace(R.id.main_frame_layout_container, movieDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
