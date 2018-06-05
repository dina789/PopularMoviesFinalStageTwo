package com.example.dodo.popularmoviesfinal.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dodo.popularmoviesfinal.Adapters.Review_Adapter;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.Models.ReviewModel;
import com.example.dodo.popularmoviesfinal.Models.ReviewResponse;
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
import static com.example.dodo.popularmoviesfinal.Adapters.Review_Adapter.*;


public class Details_Activity extends AppCompatActivity {
    private static final String TAG = Details_Activity.class.getSimpleName();

    public static final String  API_KEY = "90cfeb2390166bcd501adabe6f68e59a";
    public static final String REVIEW_PATH ="http://api.themoviedb.org/3/movie/" + "id" + "?&append_to_response=reviews";
    private RecyclerView mRecyclerViewReviews;

    private ArrayList<ReviewModel> mReviewList = new ArrayList<>();
    public Review_Adapter mReviewAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);
        setupAdapter();
        fetchReviews();
        Intent intentGetMovieDetails  = getIntent();

        MoviesData movieModel = (MoviesData) intentGetMovieDetails .getSerializableExtra("movieModel");


        TextView text_release_date = findViewById(R.id.text_release_date);

       text_release_date.setText(movieModel.getReleaseDate().substring(0, 4));
       // TextView text_vote_average = findViewById(R.id.text_vote_average);

        TextView text_overview = findViewById(R.id.text_overview);
        TextView text_original_title = findViewById(R.id.text_original_title);
        ImageView image_poster = findViewById(R.id.image_poster);


        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w500/" + movieModel.getPosterPath()).into(image_poster);

        RatingBar rating_bar= findViewById(R.id.rating_bar);

        text_original_title.setText(movieModel.getOriginalTitle()); //original title
        text_overview .setText(movieModel.getOverview());  //overview
        if (movieModel.getOverview() == "") {
            text_overview.setText("Overview:\n Overview not available !!");
        }


        double rate = movieModel.getVoteAverage();
        rate = rate /2;
       rating_bar.setRating((float) rate);

    }

    private void setupAdapter() {

        RecyclerView recyclerView = findViewById(R.id.Recycler_reviews);

    LinearLayoutManager    mLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(   mLayoutManager );




        mReviewAdapter = new Review_Adapter(new ArrayList<ReviewModel>(),this);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        recyclerView.setAdapter(mReviewAdapter);

        recyclerView.setHasFixedSize(true);
    }



    private void fetchReviews() {


        if (retrofit == null) {

            retrofit = new Retrofit.Builder()

                    .baseUrl(REVIEW_PATH)

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();

        }

        ApiInterface ReviewApiService = retrofit.create(ApiInterface.class);

        Call<ReviewResponse> call = ReviewApiService.getResults(API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {

            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {

                assert response.body() != null;
                List<ReviewModel> mreviewList = response.body().getResults();
                Review_Adapter.setItems(mreviewList);
            }

            @Override
            public void onFailure(@NonNull Call< ReviewResponse> call, @NonNull Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

}



/**
 *check for the right endpoint request for reviews??
 *
 * nested scroll view with recycler view layout:
 * https://github.com/pm48/PopularMovies/blob/master/app/src/main/res/layout/fragment_detail.xml
 *https://gist.github.com/eltonjhony/c2846684fd6bad24af6cf6014b2b7287
 *
 * setting detail activity with its adapter and retrofit like done with main activity
 * https://github.com/delaroy/MoviesApp/blob/master/app/src/main/java/com/delaroystudios/movieapp/DetailActivity.java
 *
 *checking on saved instance state:
 * https://gist.github.com/eltonjhony/273396fa8f748b0807daaa3343780082
 *
 */
//https://findusages.com/search/info.movito.themoviedbapi.model.MovieDb/getReleaseDate$0?offset=1