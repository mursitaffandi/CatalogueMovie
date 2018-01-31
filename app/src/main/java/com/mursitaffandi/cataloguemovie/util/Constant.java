package com.mursitaffandi.cataloguemovie.util;

import android.net.Uri;

/**
 * Created by mursitaffandi on 04/01/18.
 */

public class Constant {
    public static final String SIZE_IMAGE_POSTER = "w154/";
    public static final String SIZE_IMAGE_BACKDROP= "w342/";
    public static final String TAG_DETAIL = "id_movie_detail";
    public static final String KEY_SEARCH_MOVIE = "key_movie";
    public static final String MOVIE_LIST_STATE = "1";
    //    Content Provider Constant
    public static final String CONTENT_AUTHORITY = "com.mursitaffandi.cataloguemovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

}
