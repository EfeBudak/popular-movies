package com.pasha.efebudak.popularmovies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
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
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements
        MovieListFragment.MovieListItemListener {

    private static final String TAG_MOVIE_LIST_FRAGMENT = "tagMovieListFragment";

    private Result result;

    @Bind(R.id.main_frame_layout_container)
    FrameLayout frameLayoutContainer;

    private MovieListFragment movieListFragment;
    private MovieDetailFragment movieDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {

            addMovieListFragment();

        } else {

            result = savedInstanceState.getParcelable("result");

        }

    }

    public void callMovieService() {

        if (result == null) {

            startService(
                    NetworkService.newIntent(
                            this,
                            "https://api.themoviedb.org/3"));

            final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    // Get extra data included in the Intent

                    Bundle bundle = intent.getExtras();
                    result = bundle.getParcelable("results");

                    if (result == null) {
                        frameLayoutContainer.setForeground(
                                getResources().getDrawable(R.drawable.movie_placeholder));
                    } else {
                        movieListFragment.updateResult(result);
                    }

                }
            };

            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                    new IntentFilter("custom-event-name"));
        } else {

            movieListFragment
                    = (MovieListFragment) getSupportFragmentManager()
                    .findFragmentByTag(TAG_MOVIE_LIST_FRAGMENT);
            movieListFragment.updateResult(result);
        }

    }

    private void addMovieListFragment() {

        movieListFragment = MovieListFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(
                R.id.main_frame_layout_container, movieListFragment, TAG_MOVIE_LIST_FRAGMENT);

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

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.sort_order)
                    .setItems(R.array.sort_order_types, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int position) {

                            startService(
                                    NetworkService.newIntent(
                                            MainActivity.this,
                                            "https://api.themoviedb.org/3",
                                            position));

                        }
                    });
            builder.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * called when an item of the movie list is clicked
     *
     * @param position
     */
    @Override
    public void onItemClicked(int position) {

        openDetailFragment(result.getMovieArrayList().get(position));
    }

    private void openDetailFragment(Movie movie) {

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        movieDetailFragment = MovieDetailFragment.newInstance(movie);
        fragmentTransaction.replace(R.id.main_frame_layout_container, movieDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("result", result);

    }
}
