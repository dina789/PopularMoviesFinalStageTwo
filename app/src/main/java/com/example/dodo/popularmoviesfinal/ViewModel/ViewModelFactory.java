package com.example.dodo.popularmoviesfinal.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.example.dodo.popularmoviesfinal.DB.MoviesDataBase;

public class ViewModelFactory  extends ViewModelProvider.NewInstanceFactory {

    MoviesDataBase db;
    String movieid;

    public ViewModelFactory(MoviesDataBase db, String movieid) {
        this.db = db;
        this.movieid = movieid;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddMovieViewModel(db, movieid);
    }
}



/*
Architecture Components: LiveData and Lifecycle:
https://www.youtube.com/watch?v=jCw5ib0r9wg


//https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/3c3871cd-e3e7-4c6c-a845-a09f7fc83855/lessons/7ef37c82-7a52-40b5-b557-c8b7243980c4/concepts/afb9448e-0f1a-4565-aa48-3b52ec8ef52e
*/