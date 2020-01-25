package com.example.tgesign_up.Api;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.CheckListTable;
import com.example.tgesign_up.Database.TFM.Table.MembersTable;
import com.example.tgesign_up.Database.TFM.Table.OldMembersTable;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Home.OldMembersDownloadModel;
import com.example.tgesign_up.TGHomeMVP.TGLeaderModel;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("ALL")
public class DownloadPictureService extends IntentService {


    TFMDatabase tfmDatabase;
    ApiInterface apiInterface;
    TGLeaderModel tgLeaderModel;
    private List<MembersTable> membersTableList = new ArrayList<>();
    private List<CheckListTable> checkListTableList = new ArrayList<>();
    private List<TFMAppVariables> tfmAppVariables = new ArrayList<>();
    private List<OldMembersDownloadModel> oldMembersDownloadModelList = new ArrayList<>();



    public DownloadPictureService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        tgLeaderModel = new TGLeaderModel();

        //dbAssetHelper = new DBAssetHelper(getApplicationContext());
        //pd = new ProgressDialog(DownloadService.this);

        // if you override onCreate(), make sure to call super().
        // If a Context object is needed, call getApplicationContext() here.
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String staff_id = user.get(SharedPreference.KEY_STAFF_ID);
        //sendNotification("Downloading Data");

        getInputRecords(staff_id);
        getCheckListRecord();
        getTFMAppVariables();
        /*syncUpTFMData();
        syncUpTGData();*/
        //getOutputRecords("T-10000000000000AA");
    }


    public void getInputRecords(final String staff_id) {

        String last_synced = "0";

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<OldMembersDownloadModel>> call = apiInterface.getInputRecords(staff_id);

        call.enqueue(new Callback<List<OldMembersDownloadModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<OldMembersDownloadModel>> call,
                                   @NonNull Response<List<OldMembersDownloadModel>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    oldMembersDownloadModelList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(oldMembersDownloadModelList).toString());
                    String memberSize = String.valueOf(oldMembersDownloadModelList != null ? oldMembersDownloadModelList.size() : 0);
                    Log.d("listSize", memberSize);

                    if (oldMembersDownloadModelList.size() > 0){
                        String last_sync_time_input_records = oldMembersDownloadModelList.get(oldMembersDownloadModelList.size()-1).getLast_sync_down_time_input_records();

                        /*SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
                        HashMap<String, String> user = sharedPreference.getUserDetails();
                        String sync_time_json = user.get(SharedPreference.KEY_STAFF_SYNC_TIME);
                        Gson gson = new Gson();
                        List<SyncTimes> syncTimesList = new ArrayList<>();

                        if (!sync_time_json.equalsIgnoreCase("")){
                            syncTimesList = gson.fromJson(sync_time_json,syncTimesList.getClass());

                            if(staffSyncPresent(syncTimesList,staff_id)){
                                SyncTimes syncPresent1 = staffSyncPresent1(syncTimesList,staff_id);
                                SyncTimes syncTimes = new SyncTimes();
                                SyncTimes.LastSyncDownTimes lastSyncDownTimes,lastSyncDownTimes1 = new SyncTimes.LastSyncDownTimes();
                                syncTimes.setStaff_id(syncPresent1.getStaff_id());
                                lastSyncDownTimes = syncPresent1.getLastSyncDownTimes();
                                lastSyncDownTimes1.setLast_sync_down_time_input_records(last_sync_time_input_records);
                                lastSyncDownTimes1.setLast_sync_down_time_output_records(lastSyncDownTimes.getLast_sync_down_time_output_records());
                                syncTimes.setLastSyncDownTimes(lastSyncDownTimes1);
                                syncTimesList.add(syncTimes);

                            }else{
                                addNewSync(staff_id,last_sync_time_input_records,syncTimesList);
                            }
                        }else {
                            addNewSync(staff_id,last_sync_time_input_records,syncTimesList);
                        }*/

                        Log.d("last_sync",last_sync_time_input_records+"");

                        for (int i = 0; i < oldMembersDownloadModelList.size(); i++) {
                            OldMembersDownloadModel member = oldMembersDownloadModelList.get(i);
                            saveToOldMembersTable(member);
                        }
                    }


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
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<OldMembersDownloadModel>> call, @NotNull Throwable t) {
                Log.d("tobi_input_records", t.toString());

            }
        });

    }

    public void getCheckListRecord() {

        String last_synced = "0";

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<CheckListTable>> call = apiInterface.getCheckList();

        call.enqueue(new Callback<List<CheckListTable>>() {
            @Override
            public void onResponse(@NonNull Call<List<CheckListTable>> call,
                                   @NonNull Response<List<CheckListTable>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    checkListTableList = response.body();

                    Log.d("response_check_list", Objects.requireNonNull(checkListTableList).toString());
                    String checkListSize = String.valueOf(checkListTableList != null ? checkListTableList.size() : 0);
                    Log.d("listSize", checkListSize);

                    saveToCheckListTable(checkListTableList);

                    /*for (int i = 0; i < checkListTableList.size(); i++) {
                        CheckListTable checkListTable = checkListTableList.get(i);
                    }*/

                }else {
                    int sc = response.code();
                    Log.d("scCode:- ",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            Toast.makeText(DownloadPictureService.this, "Error 400: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            Toast.makeText(DownloadPictureService.this, "Error 404: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            Toast.makeText(DownloadPictureService.this, "Error: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<CheckListTable>> call, @NotNull Throwable t) {
                Log.d("tobi_check_list", t.toString());

            }
        });

    }

    public void getTFMAppVariables() {

        String last_synced = "0";

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TFMAppVariables>> call = apiInterface.getTFMAppVariables();

        call.enqueue(new Callback<List<TFMAppVariables>>() {
            @Override
            public void onResponse(@NonNull Call<List<TFMAppVariables>> call,
                                   @NonNull Response<List<TFMAppVariables>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    tfmAppVariables = response.body();

                    Log.d("response_check_list", Objects.requireNonNull(tfmAppVariables).toString());
                    String checkListSize = String.valueOf(tfmAppVariables != null ? tfmAppVariables.size() : 0);
                    Log.d("listSize", checkListSize);

                    saveToTFMAppVariables(tfmAppVariables);

                    /*for (int i = 0; i < checkListTableList.size(); i++) {
                        CheckListTable checkListTable = checkListTableList.get(i);
                    }*/

                }else {
                    int sc = response.code();
                    Log.d("scCode:- ",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            Toast.makeText(DownloadPictureService.this, "Error 400: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            Toast.makeText(DownloadPictureService.this, "Error 404: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            Toast.makeText(DownloadPictureService.this, "Error: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<TFMAppVariables>> call, @NotNull Throwable t) {
                Log.d("tobi_tfm_app_variables", t.toString());

            }
        });

    }

    public void getOutputRecords(final String staff_id) {

        String last_synced = "0";

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<MembersTable>> call = apiInterface.getOutputRecords(staff_id);

        call.enqueue(new Callback<List<MembersTable>>() {
            @Override
            public void onResponse(@NonNull Call<List<MembersTable>> call, @NonNull Response<List<MembersTable>> response) {
                //Log.d("tobiRes", ""+ Objects.requireNonNull(response.body()).toString());
                if (response.isSuccessful()) {
                    membersTableList = response.body();

                    Log.d("Retrofit_response1", Objects.requireNonNull(membersTableList).toString());
                    String memberSize = String.valueOf(membersTableList != null ? membersTableList.size() : 0);
                    Log.d("listSize", memberSize);


                    for (int i = 0; i < membersTableList.size(); i++) {
                        MembersTable member = membersTableList.get(i);
                        SaveTFMOutputData task = new SaveTFMOutputData();
                        task.execute(member);
                    }

                }else {
                    int sc = response.code();
                    Log.d("scCode",""+sc);
                    switch (sc) {
                        case 400:
                            Log.e("Error 400", "Bad Request");
                            Toast.makeText(DownloadPictureService.this, "Error 400: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        case 404:
                            Log.e("Error 404", "Not Found");
                            Toast.makeText(DownloadPictureService.this, "Error 404: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            Log.e("Error", "Generic Error");
                            Toast.makeText(DownloadPictureService.this, "Error: Network Error Please Reconnect", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(@NotNull Call<List<MembersTable>> call, @NotNull Throwable t) {
                Log.d("tobi", t.toString());

            }
        });

    }

    void saveToCheckListTable(List<CheckListTable> checkListTableList){
        saveCheckListTable saveTrustGroupInputData = new saveCheckListTable();
        saveTrustGroupInputData.execute(checkListTableList);
    }

    void saveToTFMAppVariables(List<TFMAppVariables> checkListTableList){
        saveTFMAppVariables saveTFMAppVariables = new saveTFMAppVariables();
        saveTFMAppVariables.execute(checkListTableList);
    }

    public void saveToOldMembersTable(OldMembersDownloadModel member){
        OldMembersTable oldMembersListData = new OldMembersTable();
        TrustGroupTable trustGroupListData = new TrustGroupTable();

        oldMembersListData.setUnique_member_id(member.getUnique_member_id());
        oldMembersListData.setUnique_ik_number(member.getUnique_ik_number());
        oldMembersListData.setIk_number(member.getIk_number());
        oldMembersListData.setMember_id(member.getMember_id());
        oldMembersListData.setFirst_name(member.getFirst_name());
        oldMembersListData.setMiddle_name(member.getMiddle_name());
        oldMembersListData.setLast_name(member.getLast_name());
        oldMembersListData.setDate_of_birth(member.getDate_of_birth());
        oldMembersListData.setSex(member.getSex());
        oldMembersListData.setPhone_number(member.getPhone_number());
        oldMembersListData.setVillage_name(member.getVillage_name());
        oldMembersListData.setRole(member.getRole());
        oldMembersListData.setTemplate(member.getTemplate());
        oldMembersListData.setRegdate(member.getRegdate());
        oldMembersListData.setLevel(member.getLevel());
        oldMembersListData.setWard_id(member.getWard_id());
        oldMembersListData.setSeason_id(member.getSeason_id());
        oldMembersListData.setCrop_type(member.getCrop_type());

        trustGroupListData.setUnique_ik_number(member.getUnique_ik_number());
        trustGroupListData.setIk_number(member.getIk_number());
        trustGroupListData.setLevel(member.getLevel());
        trustGroupListData.setStaff_id(member.getStaff_id());
        trustGroupListData.setFinished_checklist(member.getFinished_checklist());

        SaveTFMInputData task = new SaveTFMInputData();
        task.execute(oldMembersListData);


        if(!ImageStorage.checkIfImageExists(member.getPicture_name())) {
            GetImages getImages = new GetImages(member.getPicture_url(),member.getPicture_name());
            getImages.execute();
        }

        SaveTrustGroupInputData saveTrustGroupInputData = new SaveTrustGroupInputData();
        saveTrustGroupInputData.execute(trustGroupListData);
    }

    /**
     * This class handles the download of pictures of Trust group Leaders.
     */
    @SuppressLint("StaticFieldLeak")
    private class GetImages extends AsyncTask<Object, Object, Object> {
        private String requestUrl, imagename_;
        //private ImageView view;
        private Bitmap bitmap ;
        private FileOutputStream fos;

        private GetImages(String requestUrl, String image_name) {
            this.requestUrl = requestUrl;
            //this.view = view;
            this.imagename_ = image_name ;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {
                /*URL url = new URL(requestUrl);
                URLConnection conn = url.openConnection();
                bitmap = BitmapFactory.decodeStream(conn.getInputStream());*/


                HttpURLConnection connection = null;
                InputStream is = null;
                ByteArrayOutputStream out = null;
                try {
                    connection = (HttpURLConnection) new URL(requestUrl).openConnection();
                    is = connection.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                } catch (Throwable e) {
                    if (!this.isCancelled()) {
                        //error = new ImageError(e).setErrorCode(ImageError.ERROR_GENERAL_EXCEPTION);
                        this.cancel(true);
                    }
                } finally {
                    try {
                        if (connection != null)
                            connection.disconnect();
                        if (out != null) {
                            out.flush();
                            out.close();
                        }
                        if (is != null)
                            is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } catch (Exception ex) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(!ImageStorage.checkIfImageExists(imagename_)) {
                //view.setImageBitmap(bitmap);

                String result = ImageStorage.saveToSdCard(bitmap, imagename_);
                Log.d("storeResult",result+"");
                if (result == null){
                    Toast.makeText(DownloadPictureService.this, "Null result", Toast.LENGTH_SHORT).show();
                }else if (result.equalsIgnoreCase("fileExists")){
                    Toast.makeText(DownloadPictureService.this, "Image did not save", Toast.LENGTH_SHORT).show();
                }else if (result.equalsIgnoreCase("success")){
                    Toast.makeText(DownloadPictureService.this, "Image saved", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class ImageStorage {

        public static String saveToSdCard(Bitmap bitmap, String filename) {

            String stored = null;

            File ChkImgDirectory;
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                ChkImgDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "TestPictures");

                File file, file3;
                File file1 = new File(ChkImgDirectory.getAbsoluteFile(), filename + ".jpg");
                File file2 = new File(ChkImgDirectory.getAbsoluteFile(), ".nomedia");
                if (!ChkImgDirectory.exists() && !ChkImgDirectory.mkdirs()) {
                    ChkImgDirectory.mkdir();
                    file = file1;
                    file3 = file2;
                    if (!file3.exists()){
                        try {
                            FileOutputStream outNoMedia = new FileOutputStream(file3);
                            outNoMedia.flush();
                            outNoMedia.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (file.exists()){
                        stored = "fileExists";
                    }else{
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                            out.flush();
                            out.close();
                            stored = "success";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    file = file1;
                    file3 = file2;
                    if (!file3.exists()){
                        try {
                            FileOutputStream outNoMedia = new FileOutputStream(file3);
                            outNoMedia.flush();
                            outNoMedia.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (file.exists()){
                        stored = "fileExists";
                    }else{
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                            out.flush();
                            out.close();
                            stored = "success";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            return stored;
        }

        public static File getImage(String imagename) {

            File mediaImage = null;
            try {
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root);
                if (!myDir.exists())
                    return null;

                mediaImage = new File(myDir.getPath() + "/TGL_TFMPictures"+imagename);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mediaImage;
        }

        public static boolean checkIfImageExists(String imagename) {
            Bitmap b;
            File file = ImageStorage.getImage("/"+imagename+".jpg");
            String path = Objects.requireNonNull(file).getAbsolutePath();
            b = BitmapFactory.decodeFile(path);

            return b != null;
        }
    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class SaveTFMInputData extends AsyncTask<OldMembersTable, Void, Void> {


        private final String TAG = SaveTFMInputData.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(OldMembersTable... params) {

            OldMembersTable oldMembersTable = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(DownloadPictureService.this);
                tfmDatabase.getOldMembersTable().insert(oldMembersTable);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class saveCheckListTable extends AsyncTask<List<CheckListTable>, Void, Void> {


        private final String TAG = TrustGroupTable.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<CheckListTable>... params) {

            List<CheckListTable> checkListTables = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(DownloadPictureService.this);
                tfmDatabase.getCheckListTableDao().insert(checkListTables);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class saveTFMAppVariables extends AsyncTask<List<TFMAppVariables>, Void, Void> {


        private final String TAG = TrustGroupTable.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SafeVarargs
        @Override
        protected final Void doInBackground(List<TFMAppVariables>... params) {

            List<TFMAppVariables> tfmAppVariables = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(DownloadPictureService.this);
                tfmDatabase.getTFMAppVariablesTable().insert(tfmAppVariables);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class SaveTrustGroupInputData extends AsyncTask<TrustGroupTable, Void, Void> {


        private final String TAG = TrustGroupTable.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(TrustGroupTable... params) {

            TrustGroupTable trustGroupTable = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(DownloadPictureService.this);
                tfmDatabase.getTrustGroupTable().insert(trustGroupTable);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    /**
     * This AsyncTask carries out saving to database the downloaded data by calling a database helper method
     * This background thread is required as the volume of data is pretty much high
     */
    @SuppressLint("StaticFieldLeak")
    public class SaveTFMOutputData extends AsyncTask<MembersTable, Void, Void> {


        private final String TAG = SaveTFMInputData.class.getSimpleName();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(MembersTable... params) {

            MembersTable membersTable = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(DownloadPictureService.this);
                tfmDatabase.getMembersTable().insert(membersTable);
            } catch (Exception e) {
                Log.d(TAG, Objects.requireNonNull(e.getMessage()));
            }

            return null;
        }
    }

    boolean staffSyncPresent(List<SyncTimes> syncTimesList, String staff_id){
        int present = 0;
        for(SyncTimes syncTimes : syncTimesList){
            if(syncTimes.getStaff_id().equalsIgnoreCase(staff_id) ){
                present += 1;
            }
        }
        if (present > 0){
            return true;
        }else {
            return false;
        }
    }

    SyncTimes staffSyncPresent1(List<SyncTimes> syncTimesList, String staff_id){
        SyncTimes resultSyncTimes = new SyncTimes();
        for(SyncTimes syncTimes : syncTimesList){
            if(syncTimes.getStaff_id().equalsIgnoreCase(staff_id) ){
                resultSyncTimes = syncTimes;
            }
        }
        return resultSyncTimes;
    }

    void addNewSync(String staff_id, String last_sync_time_input_records,
                    List<SyncTimes> syncTimesList){
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        Gson gson = new Gson();
        SyncTimes syncTimes = new SyncTimes();
        SyncTimes.LastSyncDownTimes lastSyncDownTimes = new SyncTimes.LastSyncDownTimes();
        lastSyncDownTimes.setLast_sync_down_time_input_records(last_sync_time_input_records);
        syncTimes.setStaff_id(staff_id);
        syncTimes.setLastSyncDownTimes(lastSyncDownTimes);
        syncTimesList.add(syncTimes);

        String sync_to_save = gson.toJson(syncTimesList,syncTimesList.getClass());
        sharedPreference.setKeyStaffSyncTime(sync_to_save);
    }

    /**
     * This method calls SyncUp so as to push the composed List from it online
     */
    private void syncUpTFMData() {
        SharedPreference sharedPreference = new SharedPreference(DownloadPictureService.this);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String composed_json = tgLeaderModel.syncUpTFMDataResult(DownloadPictureService.this);
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
                    for (int i = 0; i < syncingResponse.size(); i++) {
                        SyncingResponseTFM member = syncingResponse.get(i);
                        tgLeaderModel.saveMembersTableReturnResult(DownloadPictureService.this,member);

                        Log.d("iValue:", String.valueOf(i));

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
    private void syncUpTGData() {

        SharedPreference sharedPreference = new SharedPreference(DownloadPictureService.this);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        String composed_tg_json = tgLeaderModel.syncUpTGDataResult(DownloadPictureService.this);
        Log.d("composed_tg_json",composed_tg_json);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<TrustGroupTable.SyncingResponse>> call = apiInterface.syncUpTGTable(composed_tg_json,user.get(SharedPreference.KEY_STAFF_ID));
        call.enqueue(new Callback<List<TrustGroupTable.SyncingResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<TrustGroupTable.SyncingResponse>> call, @NonNull Response<List<TrustGroupTable.SyncingResponse>> response) {
                Log.d("RETROFIT_TG", "onResponse: " + response.body());
                if (response.isSuccessful()) {

                    List<TrustGroupTable.SyncingResponse> syncingResponse = response.body();

                    Log.d("syncingResponseTG", Objects.requireNonNull(syncingResponse).toString());
                    for (int i = 0; i < syncingResponse.size(); i++) {
                        TrustGroupTable.SyncingResponse member = syncingResponse.get(i);
                        tgLeaderModel.saveTGTableReturnResult(DownloadPictureService.this,member);

                        Log.d("iValue:", String.valueOf(i));

                    }
                } else {
                    Log.i("tobi_tg", "onResponse ERROR: " + response.body());
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
            public void onFailure(@NonNull Call<List<TrustGroupTable.SyncingResponse>> call, @NonNull Throwable t) {
                Log.d("TobiNewTG", t.toString());
            }
        });
    }

}