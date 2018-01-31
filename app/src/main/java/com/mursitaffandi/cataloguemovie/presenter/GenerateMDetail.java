package com.mursitaffandi.cataloguemovie.presenter;

import com.mursitaffandi.cataloguemovie.model.MMovie;
import com.mursitaffandi.cataloguemovie.model.movie_detail.MDetail;
import com.mursitaffandi.cataloguemovie.network.ClientCall;
import com.mursitaffandi.cataloguemovie.network.PulseDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mursitaffandi on 16/01/18.
 */

public class GenerateMDetail {
    PulseDetail moviePulse;
    private ClientCall apiClient = new ClientCall();
    private Call<MDetail> apiCall;
    private MDetail result;

    public GenerateMDetail(PulseDetail moviePulse) {
        this.moviePulse = moviePulse;
    }

    public void getMovie(int key_movie) {
        apiCall = apiClient.getService().getDetailMovie(String.valueOf(key_movie));
        apiCall.enqueue(new Callback<MDetail>() {
            @Override
            public void onResponse(Call<MDetail> call, Response<MDetail> response) {
                result = response.body();
                moviePulse.onReciveMovie(result);
            }

            @Override
            public void onFailure(Call<MDetail> call, Throwable t) {
                moviePulse.onFailMovie(t.getMessage());
            }
        });

    }
}
