package com.mursitaffandi.myfavoritemovie;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.mursitaffandi.myfavoritemovie.DBMovieContract.MovieColumns.CONTENT_URI;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int LOAD_NOTES_ID = 1;
    @BindView(R.id.rv_now_playing_movie)
    RecyclerView rv_now_playing;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new Adapter(this);
        rv_now_playing.setLayoutManager(new LinearLayoutManager(this));
        rv_now_playing.setAdapter(adapter);
        getSupportLoaderManager().initLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_NOTES_ID, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this, CONTENT_URI, null, null, null, null);
    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapFavorite(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapFavorite(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_NOTES_ID);
    }

}
