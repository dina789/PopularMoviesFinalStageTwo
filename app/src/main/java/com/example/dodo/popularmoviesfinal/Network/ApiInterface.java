package com.example.dodo.popularmoviesfinal.Network;


import com.example.dodo.popularmoviesfinal.Models.MovieResponse;
import com.example.dodo.popularmoviesfinal.Models.ReviewResponse;
import com.example.dodo.popularmoviesfinal.Models.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {

    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter


  @GET("/3/movie/top_rated")

  Call<MovieResponse> getTop_rated(@Query("api_key") String API_KEY);

  @GET("/3/movie/popular")
  Call<MovieResponse> getPopular(@Query("api_key") String API_KEY);


  @GET("./{id}/reviews")
  Call<ReviewResponse> getResults(@Path("id") String id, @Query("api_key") String API_KEY);
 // @GET("movie/{movie_id}/videos")
 // Call<VideoResponse> getMovieTrailer(@Path("movie_id") int id, @Query("api_key") String apiKey);

  @GET("3/movie/{id}/videos?")
  Call<VideoResponse>   getMovieTrailers(@Path("id") String id );
}



/*

I have also noticed there are these 2 endpoints:
https://api.themoviedb.org/3/movie/popular?api_key=
and
https://api.themoviedb.org/3/movie/top_rated?api_key=

https://android.jlelse.eu/consuming-rest-api-using-retrofit-library-in-android-ed47aef01ecb
on endpoint from the forum;
https://discussions.udacity.com/t/confusion-with-api-endpoint-for-sorting-by-popular-or-top-rated/222344/2

https://discussions.udacity.com/t/using-retrofit-firsttime/366768/9


https://discussions.udacity.com/t/retrofit-help-building-movie-id-reviews-endpoint/385052/3
http://square.github.io/retrofit/

https://stackoverflow.com/questions/27816507/retrofit-multiple-endpoints-with-same-restadapter/27981993#27981993

Path – variable substitution for the API endpoint. For example movie id will be swapped for{id} in the URL endpoint.

@Query – specifies the query key name with the value of the annotated parameter.

@Body – payload for the POST call

@Header – specifies the header with the value of the annotated parameter
 */