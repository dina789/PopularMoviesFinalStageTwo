package com.example.dodo.popularmoviesfinal.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.R;
import com.squareup.picasso.Picasso;

public class Details_Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_);

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



}

//https://findusages.com/search/info.movito.themoviedbapi.model.MovieDb/getReleaseDate$0?offset=1