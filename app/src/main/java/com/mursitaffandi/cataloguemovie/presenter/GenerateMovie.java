package com.mursitaffandi.cataloguemovie.presenter;

import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.network.ClientCall;
import com.mursitaffandi.cataloguemovie.network.MoviePulse;
import com.mursitaffandi.cataloguemovie.model.MMovie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenerateMovie {
    MoviePulse moviePulse;
    private ClientCall apiClient = new ClientCall();
    private Call<MMovie> apiCall;
    private ArrayList<Result> list;

    public GenerateMovie(MoviePulse moviePulse) {
        this.moviePulse = moviePulse;
    }

    public void getMovie(String key_movie) {
        apiCall = apiClient.getService().getSearchMovie(key_movie);
        apiCall.enqueue(new Callback<MMovie>() {
            @Override
            public void onResponse(Call<MMovie> call, Response<MMovie> response) {
                list = new ArrayList<>(response.body().getResults());
                moviePulse.onReciveMovie(list);
            }

            @Override
            public void onFailure(Call<MMovie> call, Throwable t) {
                moviePulse.onFailMovie(t.getMessage());
            }
        });

    }
}
