package com.mursitaffandi.cataloguemovie.fragment;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mursitaffandi.cataloguemovie.R;
import com.mursitaffandi.cataloguemovie.adapter.AdapterFavoriteMovie;
import com.mursitaffandi.cataloguemovie.db.DBMovieContract;
import com.mursitaffandi.cataloguemovie.db.DBMovieHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private Context context;
    private Unbinder unbinder;
    @BindView(R.id.rv_now_playing_movie)
    RecyclerView rv_now_playing;
    private AdapterFavoriteMovie adapter;
    private LoaderManager loaderManager = null;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        context = view.getContext();
        unbinder = ButterKnife.bind(this, view);
        loaderManager = getLoaderManager();
        getLoaderManager().initLoader(1, null, this);
        adapter = new AdapterFavoriteMovie(context);
        rv_now_playing.setLayoutManager(new LinearLayoutManager(context));
        rv_now_playing.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loaderManager.restartLoader(1, null, this);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if (menuVisible && loaderManager!=null){
            loaderManager.restartLoader(1, null, this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(context, DBMovieContract.MovieColumns.CONTENT_URI , null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapFavorite(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapFavorite(null);
    }
}
