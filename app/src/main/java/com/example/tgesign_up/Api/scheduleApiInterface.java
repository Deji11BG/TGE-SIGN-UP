package com.example.tgesign_up.Api;

import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.example.tgesign_up.Home.OldMembersDownloadModel;
import com.example.tgesign_up.TGHomeMVP.schedulemodel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface scheduleApiInterface {
    @FormUrlEncoded
    @POST("uploadSignUpWardSchedule")
    Call<List<scheduleTable>> syncUpSchedule(@Field("wordlist") String sync_up_data);

    @GET("downloadSignUpWardSchedule")
    Call<List<schedulemodel>> syncDownSchedule(String ward);
}
