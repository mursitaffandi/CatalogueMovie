package com.mursitaffandi.cataloguemovie.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.BaseColumns._ID;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class DBMovieHelper {

    private Context mContext;
    private DBMovieManager databaseHelper;

    private SQLiteDatabase database;

    public DBMovieHelper(Context context) {
        this.mContext = context;
    }

    public DBMovieHelper open() throws SQLException {
        databaseHelper = new DBMovieManager(mContext);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        databaseHelper.close();
    }

    public Cursor queryDBMovie() {
        return database.query(
                DBMovieContract.TABLE_MOVIE_NAME,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC"
        );
    }

    public Cursor queryByIdDBMovie(String id) {
        return database.query(
                DBMovieContract.TABLE_MOVIE_NAME,
                null,
                DBMovieContract.MovieColumns.COLUMN_MOVIE_ID  + " = ?",
                new String[]{id},
                null,
                null,
                null
        );
    }

    public long insertDBMovie(ContentValues values) {
        return database.insert(
                DBMovieContract.TABLE_MOVIE_NAME,
                null,
                values
        );
    }

    public int deleteDBMovie(int id) {
        return database.delete(
                DBMovieContract.TABLE_MOVIE_NAME,
                DBMovieContract.MovieColumns.COLUMN_MOVIE_ID + " = ?",
                new String[]{String.valueOf(id)}
        );
    }

    public boolean checkAvalableMovie(int movie_id) {
        Cursor cursor = queryByIdDBMovie(String.valueOf(movie_id));
        boolean avalableResult = cursor.getCount() > 0;
        return avalableResult;
    }
}
