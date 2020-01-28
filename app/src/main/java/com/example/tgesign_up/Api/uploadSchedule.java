package com.example.tgesign_up.Api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.example.tgesign_up.ScheduleInfo;
import com.example.tgesign_up.TGHomeMVP.schedulemodel;
import com.example.tgesign_up.scheduleDefaultResponse;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class uploadSchedule {
    private scheduleApiInterface apiInterface;
    //private TGLeaderModel tgLeaderModel;
    private Context context;
    private TFMDatabase tfmDatabase;
    private List<SyncingResponseTFM> syncingResponseTFM = new ArrayList<>();
    private List<scheduleTable> membersTableList = new ArrayList<>();
    private List<schedulemodel> oldMembersDownloadModelList = new ArrayList<>();
    List<scheduleTable> unsyncedFields;
    SharedPreference sharedPreference;





    private void saveToScheduleTable(schedulemodel member){
        scheduleTable oldMembersListData = new scheduleTable();

        oldMembersListData.setWard(member.getWard());
        oldMembersListData.setFirst_day(member.getFirst_day());
        oldMembersListData.setFirst_time(member.getFirst_time());
        oldMembersListData.setSecond_day(member.getSecond_day());
        oldMembersListData.setSecond_time(member.getSecond_time());
        oldMembersListData.setSlot_id(member.getSlot_id());
        oldMembersListData.setSchedule_count(0);
        oldMembersListData.setSchedule_flag(member.getSchedule_flag());


        saveScheduleData task = new saveScheduleData();
        task.execute(oldMembersListData);

    }


    public void getScheduleRecords(Context context) {

        //SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
        sharedPreference = new SharedPreference(context);

        //sharedPreferenceController = new SharedPreferenceController(context);

        //String last_synced = sharedPreferenceController.getTfmOutputSyncTime();
        HashMap<String,String> user = sharedPreference.getUserDetails();
        String ward = user.get(SharedPreference.KEY_TRAINING_WARD);
        //String ward =sharedPreferenceController.getWard();
        Log.d("warddddd",ward);

        apiInterface = ApiClient.getApiClient().create(scheduleApiInterface.class);
        Call<List<schedulemodel>> call = apiInterface.syncDownSchedule(ward);

        call.enqueue(new Callback<List<schedulemodel>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<List<schedulemodel>> call, @NonNull Response<List<schedulemodel>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    oldMembersDownloadModelList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(oldMembersDownloadModelList).toString());
                    String memberSize = String.valueOf(oldMembersDownloadModelList != null ? oldMembersDownloadModelList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (oldMembersDownloadModelList == null){
                        //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download null",sharedPreferenceController.getTfmOutputSyncTime());
                    }else if(oldMembersDownloadModelList.size() == 0){
                       // sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download empty",sharedPreferenceController.getTfmOutputSyncTime());
                    }else {
                        //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download Successful",membersTableList.get(0).getLast_sync_time());
//                        saveToOldMembersTable(); task = new saveScheduleData();
//                        task.execute(membersTableList);
                        for (int i = 0; i < oldMembersDownloadModelList.size(); i++) {
                            schedulemodel member = oldMembersDownloadModelList.get(i);
                            saveToScheduleTable(member);
                        }
                    }



                }else {
                    int sc = response.code();
                    Log.d("scCode_output_record",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("0","Error 400: Download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("0","Error 404: Download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("0","Generic Error: Download failed",sharedPreferenceController.getTfmOutputSyncTime());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<schedulemodel>> call, @NotNull Throwable t) {
                Log.d("tobi", t.toString());
                //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("0","Download failed",sharedPreferenceController.getTfmOutputSyncTime());

            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    public class saveScheduleData extends AsyncTask<scheduleTable, Void, Void> {


        private final String TAG = saveScheduleData.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(scheduleTable... params) {

            scheduleTable membersTableList = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getscheduleTable().insert(membersTableList);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }


//    private void syncupSchedule() {
//
//        final TFMDatabase tfmDatabase;
//        tfmDatabase = TFMDatabase.getInstance(context);
//        final SharedPreferenceController sharedPreference;
//
//        unsyncedFields = tfmDatabase.getscheduleTable().getUnsynced();
//
//        //access the count through the integer unsyncedFM
//
//        //sync start
//        if (unsyncedFields.isEmpty()) {
//            //Toast.makeText(StartSync.this, "Field Mapping Table up to date", Toast.LENGTH_LONG).show();
//        } else {
//            apiInterface = ApiClient.getApiClient().create(scheduleApiInterface.class);
//
//            //ApiInterface service = ApiClient.getInstance().create(scheduleApiInterface.class);
//
//            Call<List<scheduleDefaultResponse>> call = apiInterface.syncUpSchedule(new Gson().toJson(unsyncedFields));
//            //Call<List<schedulemodel>> call = apiInterface.syncDownSchedule(ward);
//
//            Log.d("CHECK", new Gson().toJson(unsyncedFields));
//
//            call.enqueue(new Callback<List<scheduleDefaultResponse>>() {
//                @Override
//                public void onResponse(@NonNull Call<List<scheduleDefaultResponse>> call, @NonNull Response<List<scheduleDefaultResponse>> response) {
//                    List<scheduleDefaultResponse> syncingResponse = response.body();
//
//                    if (syncingResponse != null) {
//                        for(scheduleDefaultResponse h: syncingResponse){
//                            Log.d("CHECK", "Field ID: " + h.getSchedule_flag() + " Sync Status: "  + " Last synced: " + h.getLast_synced());
//                            SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
//
//                            String ward=sharedPreferenceController.getWard();
//                            String slot_id=sharedPreferenceController.getSlotId();
//                            tfmDatabase.getscheduleTable().updateScheduleFlag(h.getSchedule_flag(),ward,slot_id);
//                            //sharedPreferenceController.setFMflagsandDescriptions("1","sync success");
//                            //sharedPreferenceController.setLastSyncTimefm(h.getLast_synced());
//
//                        }
//                    }
//                    else{
//                        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
//                        //sharedPreferenceController.setFMflagsandDescriptions("0","sync failure");
//                    }
//                    //Toast.makeText(StartSync.this, "Field's Table Successfully Uploaded", Toast.LENGTH_LONG).show();
//
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<List<scheduleDefaultResponse>> call, @NonNull Throwable t) {
//                    Toast.makeText(context, "Failed : " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                    SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
//                    //sharedPreferenceController.setFMflagsandDescriptions("0","sync failure");
//
//                }
//            });
//
//        }
//    }

}
