package com.example.dodo.popularmoviesfinal.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.dodo.popularmoviesfinal.Adapters.MoviesAdapter;
import com.example.dodo.popularmoviesfinal.Models.MovieResponse;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.Network.ApiInterface;
import com.example.dodo.popularmoviesfinal.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//Notes:
/**The correct would be create a object that contains a list of movies.
 *  Then, you can create the API interface returns that responde object. */


public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler

{


    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    private static Retrofit retrofit = null;

    private MoviesAdapter moviesAdapter;

    public static final String  API_KEY = "90cfeb2390166bcd501adabe6f68e59a";

  //  private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupAdapter();
        fetchMostPopular();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        //set menu item title based on sort key


        return super.onCreateOptionsMenu(menu);


    }


    @Override public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.most_popular) {
            fetchMostPopular();
            return true;
        }

        if (id == R.id.top_rated) {
            fetchTopRated();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override

    public void onClick(MoviesData moviesData) {
        Intent intent = new Intent(this, Details_Activity.class);
        intent.putExtra("movieModel", moviesData);
        startActivity(intent);
    }

    private void setupAdapter() {
        // Initialize recycler view
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

     moviesAdapter = new MoviesAdapter(new ArrayList<MoviesData>(),this, this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        recyclerView.setAdapter(moviesAdapter);


        // grid layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        recyclerView.setHasFixedSize(true);
    }


    private void fetchMostPopular() {


        if (retrofit == null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }

        ApiInterface movieApiService = retrofit.create(ApiInterface.class);

        Call<MovieResponse> call = movieApiService.getPopular(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                List<MoviesData> mMovieList = response.body().getResults();
                moviesAdapter.setItems(mMovieList);
            }

            @Override
            public void onFailure(@NonNull Call< MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    private void fetchTopRated() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        ApiInterface movieApiService = retrofit.create(ApiInterface.class);
        Call<MovieResponse> call = movieApiService.getTop_rated(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {

                List<MoviesData> mMovieList = response.body().getResults();
                moviesAdapter.setItems(mMovieList);
            }

            @Override
            public void onFailure(@NonNull Call<MovieResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }


    public boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}












//https://github.com/schordas/RetroStack/blob/master/app/src/main/java/com/android/chordas/retrostack/MainActivity.java
/*
//https://www.youtube.com/watch?v=OOLFhtyCspA&t=3625s
        //https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/418d7086-8596-4c73-8d1b-8bddef80c116/lessons/5a9d75c2-eb50-4a06-b1ed-b30645f27fdf/concepts/73e97b9e-4ca1-4520-baa1-1f475f5b7bfb

a guide:

https://discussions.udacity.com/t/popular-movies-stage1-help/618976/17

to implement and check:
https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/418d7086-8596-4c73-8d1b-8bddef80c116/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/3da2dca7-50a2-413b-958c-987080988ae1

for swiperefresh

> SwipeRefreshLayout:
In order to refresh the inbox, SwipeRefreshLayout is wrapped around the RecyclerView.
This article doesn’t explains the persistence of the data.
So the inbox will be reset to initial state up on refresh.



https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
for setting and menu:

https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%204/92_p_adding_settings_to_an_app.html
https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%202/42_p_use_an_options_menu_and_radio_buttons.htmlhttps://developer.android.com/guide/topics/ui/menus.html#groups
https://material.io/guidelines/patterns/settings.html#settings-usage


how to use retrofit double check:
https://android.jlelse.eu/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb

if use retrofit check:

https://discussions.udacity.com/t/retrofit-webcast/45754/2
https://plus.google.com/events/ctnno460ac50g2c6h3r99ph0r5k?authkey=CJy1hpmurNfU6gE
https://inthecheesefactory.com/blog/say-goodbye-to-findviewbyid-with-data-binding-library/en

Wiring up recycler view:
https://stackoverflow.com/questions/29579811/changing-number-of-columns-with-gridlayoutmanager-and-recyclerview
https://classroom.udacity.
com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/418d7086-8596-4c73-8d1b-8bddef80c116/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/5ae0f3eb-e0ee-41ec-bfe4-bfa77a2a6d0d

Retrofit pb forum checked:
https://discussions.udacity.com/t/unknownhostexception-retrofit/619700
https://discussions.udacity.com/t/butterknife-where-to-bind-viewholder-views-in-recyclerview-adapter/642159/8

*/
//https://api.themoviedb.org/3/movie/343611?api_key={api_key}&append_to_response=videos

/*
https://github.com/codepath/android_guides/wiki/Consuming-APIs-with-Retrofit
https://stackoverflow.com/questions/40012341/how-to-define-api-endpoint-for-retrofit
https://futurestud.io/tutorials/retrofit-optional-query-parameters
https://inthecheesefactory.com/blog/retrofit-2.0/en
https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
https://www.androidhive.info/2016/05/android-working-with-retrofit-http-library/
 */