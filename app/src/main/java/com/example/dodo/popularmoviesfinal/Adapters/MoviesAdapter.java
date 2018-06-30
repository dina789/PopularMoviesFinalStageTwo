package com.example.dodo.popularmoviesfinal.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dodo.popularmoviesfinal.Activities.Details_Activity;
import com.example.dodo.popularmoviesfinal.Models.MoviesData;
import com.example.dodo.popularmoviesfinal.R;
import com.squareup.picasso.Picasso;

import java.util.List;


//da yextend el view holder
public class MoviesAdapter extends RecyclerView.Adapter < MoviesAdapter.MovieViewHolder>
{
    private List<MoviesData> mMovieList;
    private final Context mContext;

    public void setItems(List<MoviesData> mMovieList) {
        this.mMovieList = mMovieList;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface MoviesAdapterOnClickHandler {

        void onClick(MoviesData moviesData);
    }

    /**
     * @param context      Used to talk to the UI and app resources
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MoviesAdapter(List<MoviesData> mMovieList, Context context, MoviesAdapterOnClickHandler clickHandler) {
        this.mContext = context;
        this.mMovieList = mMovieList;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        MoviesData movie = mMovieList.get(position);

        // This is how we use Picasso to load images from the internet.
        Picasso.with(mContext)
                .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                .placeholder(R.color.colorAccent)
                .into(holder.image_poster);
    }

    @Override
    public int getItemCount() {
        if (mMovieList == null){
            return -1;
        }
        return mMovieList.size();
    }






    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView image_poster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            image_poster = itemView.findViewById(R.id.image_poster);
           /* It's also a convenient place to set an
                     OnClickListener, since it has access to the adapter and the views.*/
            itemView.setOnClickListener(this);
        }
        /**
         * This gets called by the child views during a click. We fetch the date that has been
         * selected, and then call the onClick handler registered with this adapter, passing that
         * date.
         *
         * @param v the View that was clicked
         */
        @Override
        public void onClick(View v) {

            getAdapterPosition();

            Intent intent = new Intent(v.getContext(), Details_Activity.class);
            MoviesData currentMovie = mMovieList.get(getAdapterPosition());
            intent.putExtra("movieModel", currentMovie);
            v.getContext().startActivity(intent);

        }
    }
}

    //void swapCursor(Cursor newCursor) {
     //   mCursor = newCursor;
     //   notifyDataSetChanged();
    //}

    //  public void setMovieList(List<Movie> movieList)
    // {
    //  this.mMovieList.clear();
    //  this.mMovieList.addAll( mMovieList);

    // The adapter needs to know that the data has changed. If we don't call this, app will crash.


    //   notifyDataSetChanged();
    // }

//done yet implement on click listener

//https://www.youtube.com/watch?v=OOLFhtyCspA&t=3625s

//https://android.googlesource.com/platform/frameworks/base/+/master/core/java/android/widget/ArrayAdapter.java

// http://mateoj.com/2015/10/06/creating-movies-app-retrofit-picasso-android/
/*  https://classroom.udacity.com/nanodegrees/nd801/parts/9bb83157-0407-47dc-b0c4-c3d4d7dc66df/modules/418d7086-8596-4c73-8d1b-8bddef80c116/lessons/c81cb722-d20a-495a-83c6-6890a6142aac/concepts/ae70fe56-dbd3-446c-be43-b8da0f076ea6
https://discussions.udacity.com/t/let-s-share-popular-movies-stage-1-code-review-comments/635604
https://www.androidhive.info/2017/09/android-recyclerview-swipe-delete-undo-using-itemtouchhelper/
//sunshine forecastadapter
for notify data set changed
//https://discussions.udacity.com/t/adapter-notifydatasetchanged-is-not-working/630771/6
//using cursor:
https://developer.android.com/reference/android/database/Cursor.html
Imp refrence:
https://discussions.udacity.com/t/popular-movies-stage-1-json/601025/37
https://discussions.udacity.com/t/popular-movies-stage-1-json/601025/11
// why recycler view don't need a scroll view:
https://discussions.udacity.com/t/database-implementation/623950/9
https://discussions.udacity.com/t/the-constructor-in-recycle-view-class/243441
for recycler view implementaion
https://willowtreeapps.com/ideas/android-fundamentals-working-with-the-recyclerview-adapter-and-viewholder-pattern
postpone using data binding!:
better use recycler view and data binding
https://discussions.udacity.com/t/data-binding-recycler-view-item/6071
https://blog.jayway.com/2015/12/08/recyclerview-and-databinding/
 *///https://github.com/laramartin/android_movies/blob/master/app/src/main/java/eu/laramartin/popularmovies/ui/MoviesAdapter.java
//https://github.com/schordas/RetroStack/blob/master/app/src/main/java/com/android/chordas/retrostack/QuestionsAdapter.java

//setting on click listener, should i do it on bind view ?https://stackoverflow.com/questions/26682277/how-do-i-get-the-position-selected-in-a-recyclerview