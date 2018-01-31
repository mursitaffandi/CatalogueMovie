package com.mursitaffandi.cataloguemovie.network;

import com.mursitaffandi.cataloguemovie.model.MMovie;
import com.mursitaffandi.cataloguemovie.model.movie_detail.MDetail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UrlEndpoint {
    @GET("movie/now_playing")
    Call<MMovie> getNowPlayingMovie();

    @GET("movie/{movie_id}")
    Call<MDetail> getDetailMovie(@Path("movie_id") String movie_id);

    @GET("movie/upcoming")
    Call<MMovie> getUpcomingMovie();

    @GET("search/movie")
    Call<MMovie> getSearchMovie(@Query("query") String key_movie);
}
