package com.example.dodo.popularmoviesfinal.Activities;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dodo.popularmoviesfinal.Adapters.Review_Adapter;
import com.example.dodo.popularmoviesfinal.Adapters.Trailer_Adapter;
import com.example.dodo.popularmoviesfinal.DB.AppExecutors;
import com.example.dodo.popularmoviesfinal.DB.MoviesDataBase;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.Models.ReviewModel;
import com.example.dodo.popularmoviesfinal.Models.ReviewResponse;
import com.example.dodo.popularmoviesfinal.Models.VideoModel;
import com.example.dodo.popularmoviesfinal.Models.VideoResponse;
import com.example.dodo.popularmoviesfinal.Network.ApiInterface;
import com.example.dodo.popularmoviesfinal.R;
import com.example.dodo.popularmoviesfinal.ViewModel.AddMovieViewModel;
import com.example.dodo.popularmoviesfinal.ViewModel.ViewModelFactory;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.dodo.popularmoviesfinal.Activities.MainActivity.retrofit;


public class Details_Activity extends AppCompatActivity {
    private static final String TAG = Details_Activity.class.getSimpleName();
    Context context;


    public static final String API_KEY = "90cfeb2390166bcd501adabe6f68e59a";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";

    public Review_Adapter mReviewAdapter;
    Trailer_Adapter mTrailer_Adapter;
    private RecyclerView Recycler_trailer;
    private MoviesData movieModel;
    private MoviesDataBase mDb;
    MaterialFavoriteButton materialFavoriteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);
        context = getApplicationContext();

        Intent intentGetMovieDetails = getIntent();
        movieModel = (MoviesData) intentGetMovieDetails.getSerializableExtra("movieModel");
        setupAdapter();
        fetchReviews();
        setupTrailers();
        fetchTrailer();
        //  setupViewModel();
        TextView text_release_date = findViewById(R.id.text_release_date);

        text_release_date.setText(movieModel.getReleaseDate().substring(0, 4));
        // TextView text_vote_average = findViewById(R.id.text_vote_average);

        TextView text_overview = findViewById(R.id.text_overview);
        TextView text_original_title = findViewById(R.id.text_original_title);
        ImageView image_poster = findViewById(R.id.image_poster);


        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w500/" + movieModel.getPosterPath()).into(image_poster);

        RatingBar rating_bar = findViewById(R.id.rating_bar);

        text_original_title.setText(movieModel.getTitle()); //original title
        text_overview.setText(movieModel.getOverview());  //overview
        if (movieModel.getOverview() == "") {
            text_overview.setText("Overview:\n Overview not available !!");
        }


        double rate = movieModel.getVoteAverage();
        rate = rate / 2;
        rating_bar.setRating((float) rate);


        materialFavoriteButton =
                findViewById(R.id.Button_fav);


        materialFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //query the DB as it contains all the movies which are stored as Favorites.

                mDb = MoviesDataBase.getInstance(getApplicationContext());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //create a method in the DAO to insert the movie on click of the favourite button and the insert operation will be performed for that particular movie.
//To add things in the database we now need to invoke
                        mDb.movieDao().insertMovie(movieModel);


                        // Create a new intent to start an AddTaskActivity
                        //  which is not possible as it is not an Activity. So here you should just insert the Movie in the Favourite DB on click of this Button.
                        //  Intent addTaskIntent = new Intent(Details_Activity.this, AddMovieViewModel.class);
                        //  startActivity(addTaskIntent);
                    }
                });

                setupViewModel();
            }
        });
    }


    private void setupViewModel() {
        ViewModelFactory modelFactory = new ViewModelFactory(mDb, movieModel.getId());
        final AddMovieViewModel movieViewModel = ViewModelProviders.of(this, modelFactory).get(AddMovieViewModel.class);
        movieViewModel.getMoviesList().observe(Details_Activity.this, new Observer<MoviesData>() {

            @Override
            public void onChanged(@Nullable MoviesData moviesData) {

                if (movieModel == null) {
                    movieViewModel.getMoviesList().removeObserver(this);
                    materialFavoriteButton.setFavorite(false);
                    // deselect the favorite button
                } else {
                    movieViewModel.getMoviesList().removeObserver(this);
                    materialFavoriteButton.setFavorite(true);
                    // select the button
                }
            }
        });
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
 * https://github.com/karenclaire/PopMovieSearchStage2/blob/master/app/src/main/java/com/example/android/popmoviesearchstage2/adapters/TrailerAdapter.java
 * <p>
 * check for the right endpoint request for reviews??
 * <p>
 * nested scroll view with recycler view layout:
 * https://github.com/pm48/PopularMovies/blob/master/app/src/main/res/layout/fragment_detail.xml
 * https://gist.github.com/eltonjhony/c2846684fd6bad24af6cf6014b2b7287
 * <p>
 * setting detail activity with its adapter and retrofit like done with main activity
 * https://github.com/delaroy/MoviesApp/blob/master/app/src/main/java/com/delaroystudios/movieapp/DetailActivity.java
 * for refrence:
 * https://github.com/ddeleon92/MoviesAppStage2/tree/master/MoviesAppFinal/app/src/main/java/com/example/daou5____/moviesappstage1
 * https://github.com/nikosvaggalis/udacity-nanodegree-popular-movies/blob/master/app/src/main/java/moviedb/example/android/com/moviedb/adapters/ReviewsAdapter.java
 * checking on saved instance state:
 * https://gist.github.com/eltonjhony/273396fa8f748b0807daaa3343780082
 */
//https://findusages.com/search/info.movito.themoviedbapi.model.MovieDb/getReleaseDate$0?offset=1
//for fav activity watch video:
//https://www.youtube.com/watch?v=-R7qYjEfQO4&t=546s
//https://www.youtube.com/watch?v=JJqVPKrL2e8&t=102s

//https://android.jlelse.eu/room-store-your-data-c6d49b4d53a3
//http://blog.iamsuleiman.com/android-architecture-components-tutorial-room-livedata-viewmodel/
//https://www.pluralsight.com/guides/making-a-notes-app-using-room-database
//https://javalibs.com/artifact/com.github.ivbaranov/materialfavoritebutton?className=com.github.ivbaranov.mfb.MaterialFavoriteButton.Builder&source
/*


//for reference :
    private void setupViewModel () {

                            MainViewModel movieViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
                            movieViewModel.getMovies().observe(Details_Activity.this, new Observer<List<MoviesData>>()


                            {
                                public void onChanged(@Nullable List<MoviesData> moviesEntries) {

                                    Log.d(TAG, "Updating list of tasks from LiveData in ViewModel");
                                    MoviesAdapter mAdapter.setMovies(moviesEntries);
// select the button

//set tasks m3 adapter


                                }

                                      MoviesDataBase mDb = MoviesDataBase.getInstance(getApplicationContext());
        setupViewModel();

                            }}




  SAVING DATA USING ROOM LIBRARY:
  https://en.proft.me/2017/11/15/saving-data-using-room-android/
  https://discussions.udacity.com/t/using-room-library/810899/2




 */

/*
 private void deleteFavorite() {


        final MoviesData favoriteMovie = new MoviesData(
                moviesData.getId(),
                moviesData.getVoteAverage(),
                moviesData.getTitle(),
                moviesData.getPosterPath(),
                moviesData.getBackdropPath(),
                moviesData.getOverview(),
                moviesData.getReleaseDate()

        );


        final MoviesDataBase database = MoviesDataBase .getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                //if (!(movie.getId().equals(database.movieDao().loadMovieById(movie.getId()).getValue().getId()))) {
                database.movieDao().insertMovie(favoriteMovie);
                //}
            }
        });
    }


    private void saveFavorite() {



        final MoviesData favoriteMovie = new MoviesData(
                moviesData.getId(),
                moviesData.getVoteAverage(),
                moviesData.getTitle(),
                moviesData.getPosterPath(),
                moviesData.getBackdropPath(),
                moviesData.getOverview(),
                moviesData.getReleaseDate()
        );
        final MoviesDataBase database = MoviesDataBase .getInstance(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
        @Override
        public void run() {
            //if (!(movie.getId().equals(database.movieDao().loadMovieById(movie.getId()).getValue().getId()))) {
            database.movieDao().insertMovie(favoriteMovie);
            //}
        }
    });
}
 setMaterialFavoriteButtonOnLoad();
  private void setMaterialFavoriteButtonOnLoad() {


        mDb = MoviesDataBase.getInstance(getApplicationContext());

     ViewModelFactory modelFactory = new   ViewModelFactory(  mDb, moviesData.getId());
        final AddMovieViewModel movieViewModel = ViewModelProviders.of(this, modelFactory).get(AddMovieViewModel.class);
        movieViewModel. getMoviesList().observe(Details_Activity.this, new Observer<MoviesData>() {
            @Override
            public void onChanged(@Nullable MoviesData movie) {
                favoriteMovie = movie;
                if (movie == null) {
                    materialFavoriteButton.setFavorite(false);
                    Log.d(TAG, "onChanged: favorite not found");
                    // deselect the favorite button
                } else {
                    materialFavoriteButton.setFavorite(true);
                    Log.d(TAG, "onChanged: favorite found");
                    // select the button
                }
            }
        });

    }


    if (favorite) {
                            saveFavorite();

                            Snackbar.make(buttonView, "Added to Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }
                        else{
                            deleteFavorite();
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();


 */