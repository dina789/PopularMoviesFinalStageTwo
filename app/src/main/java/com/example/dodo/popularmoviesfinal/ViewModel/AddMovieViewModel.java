package com.example.dodo.popularmoviesfinal.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.dodo.popularmoviesfinal.DB.MoviesDataBase;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;

public class AddMovieViewModel  extends ViewModel {

    //private mmem variable for the livedata and public getter
    private LiveData<MoviesData> moviesList;


   //initialize constructor with a relative call to the Db:
     public AddMovieViewModel(MoviesDataBase db, String movieid) {
         moviesList = db.movieDao().loadMovieById(movieid);
     }


    public LiveData<MoviesData> getMoviesList() {
        return moviesList;








    }
}




//https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/3c3871cd-e3e7-4c6c-a845-a09f7fc83855/lessons/7ef37c82-7a52-40b5-b557-c8b7243980c4/concepts/afb9448e-0f1a-4565-aa48-3b52ec8ef52e