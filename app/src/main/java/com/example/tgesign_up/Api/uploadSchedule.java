package com.example.tgesign_up.Api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.example.tgesign_up.ScheduleInfo;
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








    private void getScheduleRecords(final String staff_id) {

        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(context);
        //String last_synced = sharedPreferenceController.getTfmOutputSyncTime();

        apiInterface = ApiClient.getApiClient().create(scheduleApiInterface.class);
        Call<List<scheduleTable>> call = apiInterface.syncDownSchedule();

        call.enqueue(new Callback<List<scheduleTable>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<List<scheduleTable>> call, @NonNull Response<List<scheduleTable>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    membersTableList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(membersTableList).toString());
                    String memberSize = String.valueOf(membersTableList != null ? membersTableList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (membersTableList == null){
                        //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download null",sharedPreferenceController.getTfmOutputSyncTime());
                    }else if(membersTableList.size() == 0){
                       // sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download empty",sharedPreferenceController.getTfmOutputSyncTime());
                    }else {
                        //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("1","Download Successful",membersTableList.get(0).getLast_sync_time());
                        saveScheduleData task = new saveScheduleData();
                        task.execute(membersTableList);
                        for (int i = 0; i < membersTableList.size(); i++) {
                            scheduleTable member = membersTableList.get(i);
                            //saveOutputRecordPictures(member);
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
            public void onFailure(@NotNull Call<List<scheduleTable>> call, @NotNull Throwable t) {
                Log.d("tobi", t.toString());
                //sharedPreferenceController.setTFMOutputFlagsAndDescriptions("0","Download failed",sharedPreferenceController.getTfmOutputSyncTime());

            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    public class saveScheduleData extends AsyncTask<List<scheduleTable>, Void, Void> {


        private final String TAG = saveScheduleData.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<scheduleTable>... params) {

            List<scheduleTable> membersTableList = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getscheduleTable().insert(membersTableList);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }










}
