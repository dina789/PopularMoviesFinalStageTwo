package com.example.dodo.popularmoviesfinal.DB;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.dodo.popularmoviesfinal.Models.MoviesData;

@Database(entities = {MoviesData.class}, version = 1, exportSchema = false)
public abstract class MoviesDataBase extends RoomDatabase

{  private static final String LOG_TAG = MoviesDataBase.class.getSimpleName();
    private static final Object LOCK = new Object();
private static final String DATABASE_NAME = "favorites";

    private static MoviesDataBase sInstance;

     //will return a moviesdatabse using singleton pattern
    //is a softaware design pattern that restrict the installization of a class to one object
    //this is useful when we want to make sure only one obect of a class is created
    public static MoviesDataBase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {


                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesDataBase.class, MoviesDataBase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }
    public abstract MoviesDao movieDao();

}


    /*
    // to add MoviesDao we use an abstract method and then retirns it.
    public abstract MoviesDao movieDao();


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final MoviesDao mDao;

        PopulateDbAsync(MoviesDataBase db) {
            mDao = db.movieDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteMovie();
            MoviesData movie = new MoviesData();
            mDao.insertMovie(movie);
        movie = new MoviesData();
            mDao.insertMovie(movie);
            return null;
        }
    }
.addCallback(sRoomDatabaseCallback).






}
*/
//https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/3c3871cd-e3e7-4c6c-a845-a09f7fc83855/lessons/7ef37c82-7a52-40b5-b557-c8b7243980c4/concepts/7ec9cf7f-c5ea-48d6-a448-cba13f52db82