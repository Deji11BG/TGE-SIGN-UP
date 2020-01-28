package com.example.tgesign_up.Api;

import com.example.tgesign_up.Database.TFM.Table.CheckListTable;
import com.example.tgesign_up.Database.TFM.Table.MembersTable;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.example.tgesign_up.Home.OldMembersDownloadModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/*This interface class is used to read old TFM data generated from 2018. it connects the project to the
  PHP script required. It also works hand in hand with the ApiClient*/

public interface ApiInterface {

    @GET("tfm_input_record_controller.php")
    Call<List<OldMembersDownloadModel>> getInputRecords(@Query("staff_id") String staff_id);
//    Call<List<OldMembersTable>> getOldMembers(@Query("last_synced_time") String last_synced_time, @Query("staff_id") String staff_id);
//
    @GET("downloadProspectiveTGE")
    Call<List<prospectiveTGETable>> getProspectiveTGERecords(@Query("last_synced_time") String last_synced_time, @Query("staff_id") String staff_id);

    @GET("downloadProspectiveTGL")
    Call<List<prospectiveTGLTable>> getProspectiveTGLRecords(@Query("last_synced_time") String last_synced_time, @Query("staff_id") String staff_id);

    @GET("downloadChecklist.php")
    Call<List<CheckListTable>> getCheckList();
//    Call<List<MembersTable>> getCheckList(@Query("last_synced_time") String last_synced_time);
//
    @GET("downloadTFMAppVariables.php")
    Call<List<TFMAppVariables>> getTFMAppVariables();
//    Call<List<MembersTable>> getCheckList(@Query("last_synced_time") String last_synced_time);

    @FormUrlEncoded
    @POST("oldMemberTDataSyncUp")
    Call<List<SyncingResponseTFM>> syncUpTFMTable(@Field("members_table_data") String members_table_data, @Query("staff_id") String staff_id);

    @FormUrlEncoded
    @POST("newTGESyncUP")
    Call<List<SyncingResponseTFM>> syncUpTGETable(@Field("new_tge_data") String members_table_data, @Query("staff_id") String staff_id);

    @Multipart
    @POST("syncUpPicture")
    Call <ServerResponse> uploadTFMPicture(@Part MultipartBody.Part file, @Part("file") RequestBody name);

    /*@FormUrlEncoded
    @POST("trust_group_data_sync_up.php")
    Call<List<SyncingResponse>> syncUpTGTable(@Field("tg_table_data") String tg_table_data, @Query("staff_id") String staff_id);

    @Multipart
    @POST("file_upload.php")
    Call <ServerResponse> uploadFile(@Part MultipartBody.Part file, @Part("file") RequestBody name);*/

    /*@POST("ReadTemplate.php")
    Call<List<AnotherMainResponse>> getTemplateMemberT(@Query("last_synced_time") String last_synced_time, @Query("staff_id") String staff_id);

    //@FormUrlEncoded
    @POST("statistics.php")
    Call<List<Statistics>> getStatistics(@Query("staff_id") String staff_id);*/
}