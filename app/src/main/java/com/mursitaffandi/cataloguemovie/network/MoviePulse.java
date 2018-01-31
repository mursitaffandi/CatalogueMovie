package com.mursitaffandi.cataloguemovie.network;

import com.mursitaffandi.cataloguemovie.model.Result;
import java.util.List;


public interface MoviePulse {
    void onReciveMovie(List<Result> mResult);
    void onFailMovie(String fail_message);
}