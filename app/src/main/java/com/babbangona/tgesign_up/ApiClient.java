package com.babbangona.tgesign_up;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*This is one of the classes used by retrofit package, this ApiClient contains the base url and gson
  builder.*/

public class ApiClient {
    //public static final String BASE_URL = "http://7d50e96a.ngrok.io/cc_harvest/";
    public static final String BASE_URL = "http://apps.babbangona.com/location_verification/";
    //public static final String BASE_URL = "http://1126ea61.ngrok.io/lmr_tfm/";
    public Retrofit retrofit = null;
    private static ApiClient mInstance;


    private ApiClient(){

        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                addConverterFactory(GsonConverterFactory.create(gson)).build();


    }

    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

}
