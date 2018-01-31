package com.mursitaffandi.myfavoritemovie;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import static com.mursitaffandi.myfavoritemovie.util.Constant.BASE_CONTENT_URI;
import static com.mursitaffandi.myfavoritemovie.util.Constant.CONTENT_AUTHORITY;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class DBMovieContract {

    public static final String TABLE_MOVIE_NAME = "tb_favmovie";
    public static class MovieColumns implements BaseColumns {

        public static String COLUMN_MOVIE_ID = "id";
        public static String COLUMN_MOVIE_TITLE = "title";
        public static String COLUMN_MOVIE_RELEASEDATE = "releaseDate";
        public static String COLUMN_MOVIE_OVERVIEW = "overview";
        public static String COLUMN_MOVIE_POSTERPATH = "posterPath";
        public static String COLUMN_MOVIE_ORIGINALTITLE = "originalTitle";
        public static String COLUMN_MOVIE_VOTEAVERAGE = "voteAverage";
        public static String COLUMN_MOVIE_ORIGINALLANGUAGE = "originalLanguage";
        public static String COLUMN_MOVIE_ADULT= "is_adult";
        public static String COLUMN_MOVIE_BACKDROPPATH = "backdropPath";
        public static String COLUMN_MOVIE_VOTECOUNT = "voteCount";
        public static String COLUMN_MOVIE_VIDEO = "video";
        public static String COLUMN_MOVIE_POPULARITY = "popularity";
        public static String COLUMN_MOVIE_TIMESTAMP = "input_timestamp";


        // create content uri
            public static final Uri CONTENT_URI =
                    BASE_CONTENT_URI.buildUpon()
                            .appendPath(TABLE_MOVIE_NAME).build();

            // create cursor of base type directory for multiple entries
            public static final String CONTENT_DIR_TYPE =
                    ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIE_NAME;
            // create cursor of base type item for single entry
            public static final String CONTENT_ITEM_TYPE =
                    ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + CONTENT_AUTHORITY + "/" + TABLE_MOVIE_NAME;
            // for building URIs on insertion
            public static Uri buildMovieUri(long id){
                return ContentUris.withAppendedId(CONTENT_URI, id);
            }
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static boolean getColumnBoolean(Cursor cursor, String columnName) {
        return Boolean.parseBoolean(
                String.valueOf(
                cursor.getInt(
                        cursor.getColumnIndex(columnName))
                ));
    }
}
