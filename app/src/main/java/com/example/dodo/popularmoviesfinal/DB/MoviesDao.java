package com.example.dodo.popularmoviesfinal.DB;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.dodo.popularmoviesfinal.Models.MoviesData;

import java.util.List;

    @Dao //data access object for our entities
  public interface MoviesDao

    {

        @Query("SELECT * FROM MoviesFavorite ORDER BY originalTitle")
        LiveData<List<MoviesData>> loadAllMovies();

        //the fact we can request objects back that what makes room an object relational mapping library.
        @Insert
        void insertMovie(MoviesData movieData);

        // rest of method take aMoviesData object as a parameter


        @Query("SELECT * FROM MoviesFavorite WHERE id = :id")
        LiveData<MoviesData> loadMovieById(String id);

        @Delete
        void deleteMovie(MoviesData movieData);
    }

//https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/3c3871cd-e3e7-4c6c-a845-a09f7fc83855/lessons/7ef37c82-7a52-40b5-b557-c8b7243980c4/concepts/85d2f6f4-e430-415e-847d-641c1cf83cb8