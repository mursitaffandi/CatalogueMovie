package com.mursitaffandi.cataloguemovie.network;

import com.mursitaffandi.cataloguemovie.model.Result;
import com.mursitaffandi.cataloguemovie.model.movie_detail.MDetail;

import java.util.List;

/**
 * Created by mursitaffandi on 16/01/18.
 */

public interface PulseDetail {
    void onReciveMovie(MDetail mResult);
    void onFailMovie(String fail_message);
}
