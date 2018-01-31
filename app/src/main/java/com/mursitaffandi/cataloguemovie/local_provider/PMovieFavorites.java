package com.mursitaffandi.cataloguemovie.local_provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.mursitaffandi.cataloguemovie.db.DBMovieContract;
import com.mursitaffandi.cataloguemovie.db.DBMovieHelper;
import com.mursitaffandi.cataloguemovie.db.DBMovieManager;
import com.mursitaffandi.cataloguemovie.util.Constant;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class PMovieFavorites extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 101;
    static {

        // content://com.dicoding.mynotesapp/note
        sUriMatcher.addURI(Constant.CONTENT_AUTHORITY, DBMovieContract.TABLE_MOVIE_NAME, MOVIE);

        // content://com.dicoding.mynotesapp/note/id
        sUriMatcher.addURI(Constant.CONTENT_AUTHORITY,
                DBMovieContract.TABLE_MOVIE_NAME+ "/#",
                MOVIE_WITH_ID);
    }
    private DBMovieHelper mOpenHelper;

    // Codes for the UriMatcher //////


    @Override
    public boolean onCreate(){
        mOpenHelper = new DBMovieHelper(getContext());
        mOpenHelper.open();
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case MOVIE:{
                return DBMovieContract.MovieColumns.CONTENT_DIR_TYPE;
            }
            case MOVIE_WITH_ID:{
                return DBMovieContract.MovieColumns.CONTENT_ITEM_TYPE;
            }
            default:{
                throw new UnsupportedOperationException("Unknown uri: " + uri);
            }
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case MOVIE:
                cursor = mOpenHelper.queryDBMovie();
                break;
            case MOVIE_WITH_ID:
                cursor = mOpenHelper.queryByIdDBMovie(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

/*

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = mOpenHelper.insertDBMovie(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(DBMovieContract.MovieColumns.CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_WITH_ID:
                updated =  mOpenHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_WITH_ID:
                deleted =  mOpenHelper.deleteDBMovie(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }
*/

}
