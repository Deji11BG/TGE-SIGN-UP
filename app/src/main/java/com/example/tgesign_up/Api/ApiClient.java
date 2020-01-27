package com.example.tgesign_up.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*This is one of the classes used by retrofit package, this ApiClient contains the base url and gson
  builder.*/

class ApiClient {

    //private static final String BASE_URL = "http://172.168.1.32/tgl_recruitment_tfm/";
  private static final String BASE_URL = "http://apps.babbangona.com/sign_up_app/public/api/v1/";
//private static final String BASE_URL = "http://172.168.1.32/tgl_recruitment_tfm/";

    private static Retrofit retrofit = null;


    static Retrofit getApiClient(){
        if (retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.level(HttpLoggingInterceptor.Level.NONE);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(100, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

}

/*
http://d89a7462.ngrok.io/bgdirect/
http://apps.babbangona.com/tgl_test/tgl_sync/tfm/
"http://apps.babbangona.com/tgl_test/tgl_sync/tfm/"*/
