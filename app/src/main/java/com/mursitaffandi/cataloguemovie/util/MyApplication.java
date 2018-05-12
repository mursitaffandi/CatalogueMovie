package com.mursitaffandi.cataloguemovie.util;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;

/**
 * Created by mursitaffandi on 14/01/18.
 */

public class MyApplication extends Application {
    private static Context mContext;

    private static Picasso picasso;

    private void setPicasso() {

        OkHttpClient okClient = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        picasso = new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttp3Downloader(okClient))
                .loggingEnabled(true)
                .build();
    }

    public static Picasso getPicasso() {
        return picasso;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setPicasso();

        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());

    }

    public static Context getContext(){
        return mContext;
    }
}
