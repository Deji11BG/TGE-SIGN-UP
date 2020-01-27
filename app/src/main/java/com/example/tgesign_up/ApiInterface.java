package com.example.tgesign_up;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("download_location_data.php")

    @GET("download_location_constants.php")
    Call<List<ConstantsResponse>> getConstantValues();


}