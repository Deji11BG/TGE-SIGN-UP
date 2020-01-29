package com.babbangona.tgesign_up.Api;

import com.babbangona.tgesign_up.TGHomeMVP.schedulemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface scheduleApiInterface {

//    @POST("uploadSignUpWardSchedule")
//    Call<List<scheduleDefaultResponse>> syncUpSchedule(@Field("wordlist") String sync_up_data);

    @GET("downloadSignUpWardSchedule")
    Call<List<schedulemodel>> syncDownSchedule(@Query( "ward") String ward);
}
