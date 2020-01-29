package com.babbangona.tgesign_up.Api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.babbangona.tgesign_up.Database.TFM.TFMDatabase;
import com.babbangona.tgesign_up.Database.TFM.Table.PictureSync;
import com.babbangona.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.babbangona.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.babbangona.tgesign_up.TGHomeMVP.TGLeaderModel;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadTFMData {

    private ApiInterface apiInterface;
    private TGLeaderModel tgLeaderModel;
    private Context context;
    private TFMDatabase tfmDatabase;
    private List<prospectiveTGETable> tgeTableList = new ArrayList<>();
    private List<prospectiveTGLTable> tglTableList = new ArrayList<>();

    public UploadTFMData(Context context) {
        tgLeaderModel = new TGLeaderModel();
        this.context = context;
    }

    public void syncData() {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String staff_id = user.get(SharedPreference.KEY_STAFF_ID);
        Toast.makeText(context, "Sync Started", Toast.LENGTH_LONG).show();
        if (countTFMData() > 0){
            syncUpTFMData();
        }
        if (countTGEData() > 0){
            syncUpTGEData();
        }

        File ImgDirectory = new File(Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsoluteFile(), "TFMPictures");
        pictureLoop(ImgDirectory);

        getProspectiveTGERecords(staff_id);
        getProspectiveTGLRecords(staff_id);
    }

    private int countTFMData(){
        return tgLeaderModel.getTFMDataCountForSyncResult(context);
    }

    private int countTGEData(){
        return tgLeaderModel.getTGEDataCountForSyncResult(context);
    }

    /**
     * This method calls SyncUp so as to push the composed List from it online
     */
    private void syncUpTFMData() {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String composed_json = tgLeaderModel.syncUpTFMDataResult(context);
        Log.d("composed_json",composed_json);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SyncingResponseTFM>> call = apiInterface.syncUpTFMTable(composed_json,user.get(SharedPreference.KEY_STAFF_ID));
        call.enqueue(new Callback<List<SyncingResponseTFM>>() {
            @Override
            public void onResponse(@NonNull Call<List<SyncingResponseTFM>> call, @NonNull Response<List<SyncingResponseTFM>> response) {
                Log.d("RETROFIT_TFM_DATA", "onResponse: " + response.body());
                if (response.isSuccessful()) {

                    List<SyncingResponseTFM> syncingResponse = response.body();

                    Log.d("syncingResponseTFM", Objects.requireNonNull(syncingResponse).toString());

                    if (syncingResponse.size() > 0){
                        String last_sync_time = syncingResponse.get(0).getLast_sync_time();
                        sharedPreference.setKeyLastSyncUpTimeTfm(last_sync_time);
                        for (int i = 0; i < syncingResponse.size(); i++) {
                            SyncingResponseTFM member = syncingResponse.get(i);
                            tgLeaderModel.saveMembersTableReturnResult(context,member);

                            Log.d("iValue:", String.valueOf(i));

                        }
                        Toast.makeText(context, "Sync Up Done", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.i("tobi_tfm", "onResponse ERROR: " + response.body());
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 401:
                            Log.e("Error 401", "Bad Request");
                            break;
                        case 403:
                            Log.e("Error 403", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        case 500:
                            Log.e("Error 500", "Bad Request");
                            break;
                        case 501:
                            Log.e("Error 501", "Bad Request");
                            break;
                        default:
                            Log.e("Error", "Generic Error " + response.code());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<SyncingResponseTFM>> call, @NonNull Throwable t) {
                Log.d("TobiNewTFM", t.toString());
            }
        });
    }

    /**
     * This method calls SyncUp so as to push the composed List from it online
     */
    private void syncUpTGEData() {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String composed_json = tgLeaderModel.syncUpTGEDataResult(context);
        Log.d("composed_json_1",composed_json);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<SyncingResponseTFM>> call = apiInterface.syncUpTGETable(composed_json,user.get(SharedPreference.KEY_STAFF_ID));
        call.enqueue(new Callback<List<SyncingResponseTFM>>() {
            @Override
            public void onResponse(@NonNull Call<List<SyncingResponseTFM>> call, @NonNull Response<List<SyncingResponseTFM>> response) {
                Log.d("RETROFIT_TFM_DATA", "onResponse: " + response.body());
                if (response.isSuccessful()) {

                    List<SyncingResponseTFM> syncingResponse = response.body();

                    Log.d("syncingResponseTFM", Objects.requireNonNull(syncingResponse).toString());
                    if (syncingResponse.size() > 0){
                        String last_sync_time = syncingResponse.get(0).getLast_sync_time();
                        sharedPreference.setKeyLastSyncUpTimeTge(last_sync_time);

                        for (int i = 0; i < syncingResponse.size(); i++) {
                            SyncingResponseTFM member = syncingResponse.get(i);
                            tgLeaderModel.saveTGETableReturnResult(context,member);

                            Log.d("iValue:", String.valueOf(i));

                        }
                        Toast.makeText(context, "Sync Up Done", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Log.i("tobi_tge", "onResponse ERROR: " + response.body());
                    int sc = response.code();
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 401:
                            Log.e("Error 401", "Bad Request");
                            break;
                        case 403:
                            Log.e("Error 403", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        case 500:
                            Log.e("Error 500", "Bad Request");
                            break;
                        case 501:
                            Log.e("Error 501", "Bad Request");
                            break;
                        default:
                            Log.e("Error", "Generic Error " + response.code());
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<SyncingResponseTFM>> call, @NonNull Throwable t) {
                Log.d("TobiNewTGE", t.toString());
            }
        });
    }

    private void getProspectiveTGERecords(final String staff_id) {


        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String last_synced = user.get(SharedPreference.KEY_LAST_SYNC_TIME_TGE);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<prospectiveTGETable>> call = apiInterface.getProspectiveTGERecords(last_synced,staff_id);

        call.enqueue(new Callback<List<prospectiveTGETable>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<List<prospectiveTGETable>> call, @NonNull Response<List<prospectiveTGETable>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    tgeTableList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(tgeTableList).toString());
                    String memberSize = String.valueOf(tgeTableList != null ? tgeTableList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (tgeTableList.size() > 0){
                        sharedPreference.setKeyLastSyncTimeTge(tgeTableList.get(0).getLast_sync_time());
                        SaveTGERecord task = new SaveTGERecord();
                        task.execute(tgeTableList);
                        Toast.makeText(context, "Sync Down Done", Toast.LENGTH_SHORT).show();

                    }


                    /*for (int i = 0; i < membersTableList.size(); i++) {
                        MembersTable member = membersTableList.get(i);
                        SaveTFMOutputData task = new SaveTFMOutputData();
                        task.execute(member);
                    }*/

                }else {
                    int sc = response.code();
                    Log.d("scCode",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<prospectiveTGETable>> call, @NotNull Throwable t) {
                Log.d("tobi_1", t.toString());

            }
        });

    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class SaveTGERecord extends AsyncTask<List<prospectiveTGETable>, Void, Void> {


        private final String TAG = SaveTGERecord.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<prospectiveTGETable>... params) {

            List<prospectiveTGETable> membersTableList = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getProspectiveTGEDao().insert(membersTableList);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    private void getProspectiveTGLRecords(final String staff_id) {


        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String last_synced = user.get(SharedPreference.KEY_LAST_SYNC_TIME_TGL);

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<prospectiveTGLTable>> call = apiInterface.getProspectiveTGLRecords(last_synced,staff_id);

        call.enqueue(new Callback<List<prospectiveTGLTable>>() {
            @SuppressWarnings("unchecked")
            @Override
            public void onResponse(@NonNull Call<List<prospectiveTGLTable>> call, @NonNull Response<List<prospectiveTGLTable>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    tglTableList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(tglTableList).toString());
                    String memberSize = String.valueOf(tglTableList != null ? tglTableList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (tglTableList.size() > 0){
                        sharedPreference.setKeyLastSyncTimeTgl(tglTableList.get(0).getLast_sync_time());
                        SaveTGLRecord task = new SaveTGLRecord();
                        task.execute(tglTableList);
                        Toast.makeText(context, "Sync Down Done", Toast.LENGTH_SHORT).show();

                    }


                    /*for (int i = 0; i < membersTableList.size(); i++) {
                        MembersTable member = membersTableList.get(i);
                        SaveTFMOutputData task = new SaveTFMOutputData();
                        task.execute(member);
                    }*/

                }else {
                    int sc = response.code();
                    Log.d("scCode",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            break;
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<prospectiveTGLTable>> call, @NotNull Throwable t) {
                Log.d("tobi", t.toString());

            }
        });

    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class SaveTGLRecord extends AsyncTask<List<prospectiveTGLTable>, Void, Void> {


        private final String TAG = SaveTGERecord.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<prospectiveTGLTable>... params) {

            List<prospectiveTGLTable> membersTableList = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getProspectiveTGLDao().insert(membersTableList);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }



    //function to loop through files in folder
    private void pictureLoop(File dir) {
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        pictureLoop(file);
                    } else {

                        //Log.d("DIRect:",file.toString());


                        File file2 = new File(file.toString());

                        //Log.d("damiFile",file2.getName());
                        int check_if_picture_synced = tgLeaderModel.getPictureNameCountResult(context, file.getName());
                        if (check_if_picture_synced > 0) {
                            Log.d("pictureSynced", file.getName());
                        } else {
                            uploadFile(file2, file.getName());
                        }
                    }
                }
            }
        }
    }

    //upload file function
    private void uploadFile(File file, String name) {

        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());

        // ApiInterface getResponse = ApiClient.getClient(ApiClient.BASE_URL).create(ApiClient.class);

        ApiInterface getResponse = ApiClient.getApiClient().create(ApiInterface.class);
        Call call = getResponse.uploadTFMPicture(fileToUpload, filename);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ServerResponse serverResponse = (ServerResponse) response.body();
                if (serverResponse != null) {
                    if (serverResponse.getSuccess()) {
                        Log.d("ServerResponse","Successful "+ name);
                        //boolean deleted = file.delete();
                        //Log.d("result",String.valueOf(deleted));
                        SaveIntoDatabasePictures task = new SaveIntoDatabasePictures();
                        task.execute(name);
                    } else {
                        Log.d("ServerResponse","Not_Successful "+ name);
                    }
                } else {
                    Log.d("ServerResponse","Null Response");
                }
            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {
                Log.d("ServerResponse","Error Uploading Files" + t.toString());
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    public class SaveIntoDatabasePictures extends AsyncTask<String, Void, Void> {


        private final String TAG = SaveIntoDatabasePictures.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            PictureSync pictureSync = new PictureSync(params[0]);

            try {
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getPictureSyncDao().insert(pictureSync);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

}