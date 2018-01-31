package com.mursitaffandi.cataloguemovie.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class DBMovieManager extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "db_movie";
    //Query create table movie
    private final String CREATE_TABLE_MOVIE_FAVORITE = "CREATE TABLE " + DBMovieContract.TABLE_MOVIE_NAME + "("
            + DBMovieContract.MovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_ID + " INTEGER NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_TITLE + " VARCHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_RELEASEDATE + " CHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_POSTERPATH + " CHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_ORIGINALTITLE + " VARCHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_VOTEAVERAGE + " CHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_ORIGINALLANGUAGE + " CHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_ADULT + " INTEGER NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_BACKDROPPATH + " VARCHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_VOTECOUNT + " INTEGER NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_VIDEO + " INTEGER NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_POPULARITY + " CHAR NOT NULL ,"
            + DBMovieContract.MovieColumns.COLUMN_MOVIE_TIMESTAMP + "  DEFAULT CURRENT_TIMESTAMP "
            + ")";
    public DBMovieManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIE_FAVORITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DBMovieContract.TABLE_MOVIE_NAME );
        onCreate(sqLiteDatabase);
    }
}
