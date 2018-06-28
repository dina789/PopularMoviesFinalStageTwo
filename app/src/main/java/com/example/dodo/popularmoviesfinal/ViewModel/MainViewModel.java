package com.example.dodo.popularmoviesfinal.ViewModel;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.dodo.popularmoviesfinal.DB.MoviesDataBase;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;

import java.util.List;

import static android.content.ContentValues.TAG;

//to cache our   list of entry wraps in a live data object


public class MainViewModel  extends AndroidViewModel

{
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<MoviesData>> mAllMovies;
   // private MovieRepo mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        //initialize member variable in a constructor
        //we will get the instance of a database
        //and call loadalltask method in our dao

       // MovieRepo  mRepository = new MovieRepo(application);
       // mAllMovies = mRepository.loadAllMovies();

       MoviesDataBase database = MoviesDataBase.getInstance(this.getApplication());
       Log.d(TAG, "Actively retrieving task from database");
      mAllMovies = database.movieDao().loadAllMovies();

    }


    //public getter:

    public LiveData<List<MoviesData>> getMovies() {
        return mAllMovies;
    }


  //  public static void insertMovie(MoviesData moviesData) {
       // mRepository.insert(moviesData);
    }


