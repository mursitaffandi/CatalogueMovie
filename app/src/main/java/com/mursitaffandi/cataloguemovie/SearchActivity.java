package com.mursitaffandi.cataloguemovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.network.MoviePulse;
import com.mursitaffandi.cataloguemovie.adapter.AdapterResultMovies;
import com.mursitaffandi.cataloguemovie.presenter.GenerateMovie;
import com.mursitaffandi.cataloguemovie.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements MoviePulse {
    private static final String TAG_MOVIE_PARCEL = "1";
    @BindView(R.id.rv_main_list_movie)
    RecyclerView rv_resultMovies;

    private AdapterResultMovies mAdapterResultMovies;
    private ArrayList<Result> mMSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);

        mAdapterResultMovies = new AdapterResultMovies(this, null);
        rv_resultMovies.setHasFixedSize(true);
        rv_resultMovies.setAdapter(mAdapterResultMovies);
        rv_resultMovies.setLayoutManager(new LinearLayoutManager(this));

        String key_movie = getIntent().getStringExtra(Constant.KEY_SEARCH_MOVIE);
        new GenerateMovie(this).getMovie(key_movie);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMSearch != null)
            outState.putParcelableArrayList(TAG_MOVIE_PARCEL, mMSearch);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(TAG_MOVIE_PARCEL)) {
            mMSearch = savedInstanceState.getParcelable(TAG_MOVIE_PARCEL);
            mAdapterResultMovies.swap(mMSearch);
        }
    }

    @Override
    public void onReciveMovie(List<Result> mSearch) {
        mMSearch = new ArrayList<>(mSearch);
        mAdapterResultMovies.swap(mMSearch);
    }

    @Override
    public void onFailMovie(String fail_message) {
        Toast.makeText(this, fail_message, Toast.LENGTH_SHORT).show();
    }
}
