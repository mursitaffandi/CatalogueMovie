package com.mursitaffandi.cataloguemovie.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.mursitaffandi.cataloguemovie.BuildConfig;
import com.mursitaffandi.cataloguemovie.util.Constant;
import com.mursitaffandi.cataloguemovie.util.Localize;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientCall {
    private UrlEndpoint apiCall;

    public ClientCall() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new StethoInterceptor()).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                HttpUrl httpUrl = request.url().newBuilder()
                        .addQueryParameter("api_key", BuildConfig.API_KEY)
                        .addQueryParameter("language", Localize.getCountry())
                        .build();

                request = request.newBuilder().url(httpUrl).build();
                return chain.proceed(request);
            }
        }).build();

        Retrofit retrofit =  new Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        apiCall = retrofit.create(UrlEndpoint.class);
    }

    public UrlEndpoint getService() {
        return apiCall;
    }
}
