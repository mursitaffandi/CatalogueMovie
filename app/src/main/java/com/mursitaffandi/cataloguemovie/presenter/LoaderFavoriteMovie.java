package com.mursitaffandi.cataloguemovie.presenter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.mursitaffandi.cataloguemovie.db.DBMovieHelper;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class LoaderFavoriteMovie extends AsyncTaskLoader<Cursor> {
    DBMovieHelper dbMovieHelper;
    public LoaderFavoriteMovie(Context context) {
        super(context);
        dbMovieHelper = new DBMovieHelper(getContext());
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        dbMovieHelper.open();
    }

    @Override
    public Cursor loadInBackground() {
        return dbMovieHelper.queryDBMovie();
    }

    @Override
    public void deliverResult(Cursor data) {
        super.deliverResult(data);
        dbMovieHelper.close();
    }
}
