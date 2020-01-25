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
    Call<List<LocationTable>> syncDownLocations(
            @Field("staff_id") String staff_id,
            @Field("last_sync_time") String last_sync_time);

    @GET("download_location_constants.php")
    Call<List<ConstantsResponse>> getConstantValues();


    @FormUrlEncoded
    @POST("upload_location_data.php")
    Call<List<DefaultResponse>> syncUpLocations(
            @Field("location_list") String harvest_call_list);

}