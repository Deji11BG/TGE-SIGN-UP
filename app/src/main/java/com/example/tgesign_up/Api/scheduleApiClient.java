package com.example.tgesign_up.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class scheduleApiClient {
    public static final String BASE_URL="http://apps.babbangona.com/sign_up_app/public/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
