package com.mursitaffandi.cataloguemovie.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.adapter.AdapterResultMovies;
import com.mursitaffandi.cataloguemovie.model.MMovie;
import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.network.ClientCall;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mursitaffandi.cataloguemovie.util.Constant.MOVIE_LIST_STATE;

public class UpcomingFragment extends Fragment {

    private Context context;
    private Unbinder unbinder;

    @BindView(R.id.rv_now_playing_movie)
    RecyclerView rv_now_playing;

    private AdapterResultMovies adapter;

    private Call<MMovie> apiCall;
    private ClientCall apiClient = new ClientCall();

    private ArrayList<Result> list;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();

        unbinder = ButterKnife.bind(this, view);

        adapter = new AdapterResultMovies(context, null);
        rv_now_playing.setLayoutManager(new LinearLayoutManager(context));
        rv_now_playing.setAdapter(adapter);
        if (savedInstanceState == null)
            loadData();
        else  {
            list = savedInstanceState.getParcelableArrayList(MOVIE_LIST_STATE);
            adapter.swap(list);
        }

        return view;
    }

    private void loadData() {
        apiCall = apiClient.getService().getUpcomingMovie();
        apiCall.enqueue(new Callback<MMovie>() {
            @Override
            public void onResponse(Call<MMovie> call, Response<MMovie> response) {
                list = new ArrayList<>(response.body().getResults());
                adapter.swap(list);
            }

            @Override
            public void onFailure(Call<MMovie> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (apiCall != null) apiCall.cancel();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (list != null)
            outState.putParcelableArrayList(MOVIE_LIST_STATE, list);
    }

}
