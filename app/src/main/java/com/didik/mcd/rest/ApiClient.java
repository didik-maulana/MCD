package com.didik.mcd.rest;

import com.didik.mcd.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public ApiCall getClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BuildConfig.BASE_URL)
                .build();
        return retrofit.create(ApiCall.class);
    }
}
