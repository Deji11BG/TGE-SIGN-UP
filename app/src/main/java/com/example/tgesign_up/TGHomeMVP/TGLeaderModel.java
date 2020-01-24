package com.example.tgesign_up.TGHomeMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;

import com.example.tgesign_up.Api.SyncingResponseTFM;
import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.MembersTable;
import com.example.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.example.tgesign_up.Database.TFM.Table.TrustGroupTable;
import com.example.tgesign_up.LocationDatabase;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TGLeaderModel {

    @SerializedName("unique_member_id")
    private String unique_member_id;

    @SerializedName("unique_ik_number")
    private String unique_ik_number;

    @SerializedName("ik_number")
    private String ik_number;

    @SerializedName("member_id")
    private String member_id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("middle_name")
    private String middle_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("date_of_birth")
    private String date_of_birth;

    @SerializedName("sex")
    private String sex;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("village_name")
    private String village_name;

    @SerializedName("role")
    private String role;

    @SerializedName("template")
    private String template;

    @SerializedName("regdate")
    private String regdate;

    @SerializedName("level")
    private String level;

    @SerializedName("ward_id")
    private String ward_id;

    @SerializedName("member_program")
    private String member_program;

    @SerializedName("crop_type")
    private String crop_type;

    @SerializedName("season_id")
    private String season_id;

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    public String getCrop_type() {
        return crop_type;
    }

    public void setCrop_type(String crop_type) {
        this.crop_type = crop_type;
    }

    public String getMember_program() {
        return member_program;
    }

    public void setMember_program(String member_program) {
        this.member_program = member_program;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getUnique_ik_number() {
        return unique_ik_number;
    }

    public void setUnique_ik_number(String unique_ik_number) {
        this.unique_ik_number = unique_ik_number;
    }

    public String getIk_number() {
        return ik_number;
    }

    public void setIk_number(String ik_number) {
        this.ik_number = ik_number;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    String unique_id_generator(String staff_id){
        if (staff_id != null){
            if (staff_id.length() > 7){
                staff_id = staff_id.substring(staff_id.length() - 6);
            }else{
                return "T-" + staff_id+"_"+getDate("concat")+"_m";
            }
        }

        return "T-" + staff_id+"_"+getDate("concat")+"_m";
    }

    String reg_date_generator(){
        return getDate("spread");
    }

    private String getDate(String module){

        SimpleDateFormat dateFormat1;
        if (module.matches("concat")) {
            dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        }else if (module.matches("spread")) {
            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }else{
            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        java.util.Date date1 = new java.util.Date();
        return dateFormat1.format(date1);
    }

    @SuppressLint("StaticFieldLeak")
    int getNewID(Context context, String unique_ik_number) {
        int new_id = 0;
        try {
            List<Integer> all_id = new TGLeaderModel.getAllMemberIds(context){
            }.execute(unique_ik_number).get();
            new_id = createNewIdFromArray(all_id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new_id;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getAllMemberIds extends AsyncTask<String, Void, List<Integer>> {
        Context mCtx;
        List<Integer> memberIDList = new ArrayList<>();
        TFMDatabase tfmDatabase;

        getAllMemberIds(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<Integer> doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                memberIDList = tfmDatabase.getMembersTable().getMemberIdArray(strings[0]);
                return memberIDList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    private int createNewIdFromArray(@NotNull List<Integer> all_lmr_array){
        int[] ret = new int[all_lmr_array.size()];
        Iterator<Integer> iterator = all_lmr_array.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return number(ret);
    }

    private int number(int[] numbers) {
        int new_number_offset;
        Arrays.sort(numbers);
        HashSet<Integer> set = new HashSet<>();

        if (numbers.length > 0){
            int max = numbers[0];

            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }
            for (int i = numbers[0]; i < numbers[numbers.length - 1]; i++) {
                set.add(i);
            }
            for (int number : numbers) {
                set.remove(number);
            }
            if (set.size() > 0){
                new_number_offset = Collections.min(set);
            }else{
                new_number_offset = max+1;
            }
        }else{
            new_number_offset = 1;
        }
        return new_number_offset;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderDetails extends AsyncTask<String, Void, TGLeaderModel> {
        Context mCtx;
        TGLeaderModel leaderModelList = new TGLeaderModel();
        TFMDatabase tfmDatabase;

        getLeaderDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected TGLeaderModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leaderModelList = tfmDatabase.getOldMembersTable().getTrustGroupLeader(strings[0]);
                return leaderModelList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    int getLeaderCount(Context context, String unique_ik_number) {
        int leader_count = new TGLeaderModel.CountModel().getCount();
        try {
            leader_count = new checkLeaderExistence(context){
                @Override
                protected void onPostExecute(TGLeaderModel.CountModel s) {}
            }.execute(unique_ik_number).get().getCount();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_count;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class checkLeaderExistence extends AsyncTask<String, Void, CountModel> {
        Context mCtx;
        CountModel leaderModelList = new CountModel();
        TFMDatabase tfmDatabase;

        checkLeaderExistence(Context context) {
            this.mCtx = context;
        }

        @Override
        protected CountModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leaderModelList = tfmDatabase.getMembersTable().getTrustGroupLeaderCount(strings[0]);
                return leaderModelList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getLeaderUniqueIDResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getLeaderUniqueID(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderUniqueID extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getLeaderUniqueID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getLeaderUniqueMemberID(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getLeaderTemplateResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getLeaderTemplate(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderTemplate extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getLeaderTemplate(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getOldMembersTable().getLeaderTemplate(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }




    String saveToSdCard(Bitmap bitmap, String filename) {

        String stored = null;

            /*File sdcard = Environment.getExternalStorageDirectory() ;
            File folder = new File(sdcard.getAbsoluteFile(), "TGL_TFMPictures/.nomedia");//the dot makes this directory hidden to the user
            folder.mkdir();*/

        File ChkImgDirectory;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            ChkImgDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "TFMPictures");

            File file, file3;
            File file1 = new File(ChkImgDirectory.getAbsoluteFile(), filename + ".jpg");
            File file2 = new File(ChkImgDirectory.getAbsoluteFile(), ".nomedia");
            if (!ChkImgDirectory.exists() && !ChkImgDirectory.mkdirs()) {
                boolean x = ChkImgDirectory.mkdir();
                Log.d("is_file_created", String.valueOf(x));
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

    @SuppressLint("StaticFieldLeak")
    String getSaveDataResult(Context c, MembersTable membersTable) {
        String s = "0";
        try {
            s = new SaveData(c){

            }.execute(membersTable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public static  class SaveData extends AsyncTask<MembersTable, Void, String> {

        //private WeakReference<Main2Model> activityReference;
        Context c;

        SaveData(Context mCtx) {
            //activityReference = new WeakReference<>(context);
            this.c = mCtx;
        }

        @Override
        protected String doInBackground(MembersTable... strings) {

            try {
                Room.databaseBuilder(c,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();
                TFMDatabase tfmDatabase = TFMDatabase.getInstance(c);
                tfmDatabase.getMembersTable().insert(strings[0]);

                return "1";
            } catch (Exception e) {
                Log.d("MUFASA__1", e + "");
                return "0";
            }

        }

    }

    @SuppressLint("StaticFieldLeak")
    public String syncUpTFMDataResult(Context context) {
        String s = "0";

        try {
            List<MembersTable> wordList = new syncUpTFMData(context){
            }.execute().get();
            Gson gson = new Gson();
            //Use Gson to serialize Array List to JSON
            s = gson.toJson(wordList,wordList.getClass());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public class syncUpTFMData extends AsyncTask<String, Void, List<MembersTable>> {

        Context mCtx;
        TFMDatabase tfmDatabase;

        syncUpTFMData(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<MembersTable> doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getAllDataForSync();
            }catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public String syncUpTGDataResult(Context context) {
        String s = "0";
        try {
            List<TrustGroupTable> wordList = new syncUpTGData(context){
            }.execute().get();
            Gson gson = new Gson();
            //Use Gson to serialize Array List to JSON
            s = gson.toJson(wordList,wordList.getClass());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public class syncUpTGData extends AsyncTask<String, Void, List<TrustGroupTable>> {

        Context mCtx;
        TFMDatabase tfmDatabase;

        syncUpTGData(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<TrustGroupTable> doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getTrustGroupTable().getAllTGDataForSync();
            }catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void saveMembersTableReturnResult(Context context, SyncingResponseTFM syncingResponse) {

        try {
            new saveMembersTableReturn(context){
            }.execute(syncingResponse).get();

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves into database the result of syncing up LMR data Mostly sync_flag and time
     */
    @SuppressLint("StaticFieldLeak")

    public class saveMembersTableReturn extends AsyncTask<SyncingResponseTFM, Void, Void> {


        private final String TAG = saveMembersTableReturn.class.getSimpleName();

        Context mCtx;
        TFMDatabase tfmDatabase;

        saveMembersTableReturn(Context context) {
            this.mCtx = context;
        }

        //updateSyncStatus

        @Override
        protected Void doInBackground(SyncingResponseTFM... params) {

            SyncingResponseTFM member = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getMembersTable().updateSyncStatus(member.getUnique_member_id(),member.getSync_flag());
            } catch (Exception e) {
                Log.d(TAG, e+"");
            }

            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void saveTGTableReturnResult(Context context, TrustGroupTable.SyncingResponse syncingResponse) {

        try {
            new saveTGTableReturn(context){
            }.execute(syncingResponse).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method saves into database the result of syncing up LMR data Mostly sync_flag and time
     */
    @SuppressLint("StaticFieldLeak")

    public class saveTGTableReturn extends AsyncTask<TrustGroupTable.SyncingResponse, Void, Void> {


        private final String TAG = saveMembersTableReturn.class.getSimpleName();

        Context mCtx;
        TFMDatabase tfmDatabase;

        saveTGTableReturn(Context context) {
            this.mCtx = context;
        }

        //updateSyncStatus

        @Override
        protected Void doInBackground(TrustGroupTable.SyncingResponse... params) {

            TrustGroupTable.SyncingResponse member = params[0];

            try {
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getTrustGroupTable().updateTGSyncStatus(member.getUnique_ik_number(),member.getSync_flag());
            } catch (Exception e) {
                Log.d(TAG, e+"");
            }

            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public int getTFMDataCountForSyncResult(Context context) {
        int result ;
        try {
            result = new getTFMDataCountForSync(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTFMDataCountForSync extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTFMDataCountForSync(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getAllTFMDataCountForSync();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public int getTGDataCountForSyncResult(Context context) {
        int result ;
        try {
            result = new getTGDataCountForSync(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTGDataCountForSync extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTGDataCountForSync(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getTrustGroupTable().getAllTGDataCountForSync();
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getTemplateTrackerPileResult(Context context) {
        String result ;
        try {
            result = new getTemplateTrackerPile(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute("1").get();
        } catch (ExecutionException | InterruptedException e) {
            result = "0";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTemplateTrackerPile extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTemplateTrackerPile(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("tracker_id", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getTFMTemplateTrackerTableDao().getTemplate(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String saveTrackerPileResult(Context context, TFMTemplateTrackerTable tfmTemplateTrackerTable) {
        String s = "0";
        try {
            s = new saveTrackerPile(context){

            }.execute(tfmTemplateTrackerTable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public static  class saveTrackerPile extends AsyncTask<TFMTemplateTrackerTable, Void, String> {

        Context context;
        TFMDatabase tfmDatabase;

        saveTrackerPile(Context context) {
            //activityReference = new WeakReference<>(context);
            this.context = context;
        }

        @Override
        protected String doInBackground(TFMTemplateTrackerTable... strings) {

            try{
                Log.d("tracker_id", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getTFMTemplateTrackerTableDao().insert(strings[0]);
                return "1";
            }catch (Exception e){
                e.printStackTrace();
                return "0";
            }

        }

    }

    public static class CountModel{

        private int count;

        public CountModel() {
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
