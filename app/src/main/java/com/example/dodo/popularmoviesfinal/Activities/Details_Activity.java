package com.example.dodo.popularmoviesfinal.Activities;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dodo.popularmoviesfinal.Adapters.Review_Adapter;
import com.example.dodo.popularmoviesfinal.Adapters.Trailer_Adapter;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.Models.ReviewModel;
import com.example.dodo.popularmoviesfinal.Models.ReviewResponse;
import com.example.dodo.popularmoviesfinal.Models.VideoModel;
import com.example.dodo.popularmoviesfinal.Models.VideoResponse;
import com.example.dodo.popularmoviesfinal.Network.ApiInterface;
import com.example.dodo.popularmoviesfinal.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dodo.popularmoviesfinal.Activities.MainActivity.retrofit;


public  class Details_Activity extends AppCompatActivity {
    private static final String TAG = Details_Activity.class.getSimpleName();

    public static final String API_KEY = "";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    private RecyclerView mRecyclerViewReviews;

    private ArrayList<ReviewModel> mReviewList = new ArrayList<>();
    public Review_Adapter mReviewAdapter;

    Trailer_Adapter mTrailer_Adapter;
    private RecyclerView Recycler_trailer;
    private MoviesData movieModel;
    private List<VideoModel> trailerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

        Intent intentGetMovieDetails = getIntent();
        movieModel = (MoviesData) intentGetMovieDetails.getSerializableExtra("movieModel");

        setupAdapter();
        fetchReviews();
        setupTrailers();
        fetchTrailer();

        TextView text_release_date = findViewById(R.id.text_release_date);

        text_release_date.setText(movieModel.getReleaseDate().substring(0, 4));
        // TextView text_vote_average = findViewById(R.id.text_vote_average);

        TextView text_overview = findViewById(R.id.text_overview);
        TextView text_original_title = findViewById(R.id.text_original_title);
        ImageView image_poster = findViewById(R.id.image_poster);


        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w500/" + movieModel.getPosterPath()).into(image_poster);

        RatingBar rating_bar = findViewById(R.id.rating_bar);

        text_original_title.setText(movieModel.getOriginalTitle()); //original title
        text_overview.setText(movieModel.getOverview());  //overview
        if (movieModel.getOverview() == "") {
            text_overview.setText("Overview:\n Overview not available !!");
        }


        double rate = movieModel.getVoteAverage();
        rate = rate / 2;
        rating_bar.setRating((float) rate);

    }


    private void setupTrailers() {
        Recycler_trailer = findViewById(R.id.Recycler_trailer);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        Recycler_trailer.setLayoutManager(mLayoutManager);
        mTrailer_Adapter = new Trailer_Adapter(this);
        Recycler_trailer.setAdapter(mTrailer_Adapter);
    }

    private void setupAdapter() {

        RecyclerView recyclerView = findViewById(R.id.Recycler_reviews);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(mLayoutManager);


        mReviewAdapter = new Review_Adapter(new ArrayList<ReviewModel>(), this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        recyclerView.setAdapter(mReviewAdapter);

        recyclerView.setHasFixedSize(true);
    }


    private void fetchReviews() {


        if (retrofit == null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }

        ApiInterface ReviewApiService = retrofit.create(ApiInterface.class);

        Call<ReviewResponse> call = ReviewApiService.getResults(movieModel.getId(), API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {

            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                assert response.body() != null;
                List<ReviewModel> mreviewList = response.body().getResults();
                mReviewAdapter.setItems(mreviewList);
            }

            @Override
            public void onFailure(@NonNull Call<ReviewResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void fetchTrailer() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ApiInterface TrailerApiService = retrofit.create(ApiInterface.class);

        Call<VideoResponse> call = TrailerApiService.getMovieTrailers(movieModel.getId(), API_KEY);
        call.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                assert response.body() != null;
                List<VideoModel> trailer = response.body().getResults();
                mTrailer_Adapter.updateTrailers(trailer);
                Recycler_trailer.smoothScrollToPosition(0);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(Details_Activity.this, "Error fetching trailer data", Toast.LENGTH_SHORT).show();

            }

        });
    }
}



/**
 * youtube app
 * https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
 *https://github.com/karenclaire/PopMovieSearchStage2/blob/master/app/src/main/java/com/example/android/popmoviesearchstage2/adapters/TrailerAdapter.java
 *
 *check for the right endpoint request for reviews??
 *
 * nested scroll view with recycler view layout:
 * https://github.com/pm48/PopularMovies/blob/master/app/src/main/res/layout/fragment_detail.xml
 *https://gist.github.com/eltonjhony/c2846684fd6bad24af6cf6014b2b7287
 *
 * setting detail activity with its adapter and retrofit like done with main activity
 * https://github.com/delaroy/MoviesApp/blob/master/app/src/main/java/com/delaroystudios/movieapp/DetailActivity.java
 * for refrence:
 * https://github.com/ddeleon92/MoviesAppStage2/tree/master/MoviesAppFinal/app/src/main/java/com/example/daou5____/moviesappstage1
 *https://github.com/nikosvaggalis/udacity-nanodegree-popular-movies/blob/master/app/src/main/java/moviedb/example/android/com/moviedb/adapters/ReviewsAdapter.java
 *checking on saved instance state:
 * https://gist.github.com/eltonjhony/273396fa8f748b0807daaa3343780082
 *
 */
//https://findusages.com/search/info.movito.themoviedbapi.model.MovieDb/getReleaseDate$0?offset=1
//for fav activity watch video:
        //https://www.youtube.com/watch?v=-R7qYjEfQO4&t=546s
//https://www.youtube.com/watch?v=JJqVPKrL2e8&t=102s