package com.babbangona.tgesign_up.FormMemberInformationMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Database.Location.LocationDatabase;
import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.TFMDatabase;
import com.babbangona.tgesign_up.Database.TFM.Table.MembersTable;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.babbangona.tgesign_up.R;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class FormMemberInformationModel {

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

    @SerializedName("crop_type")
    private String crop_type;

    @SerializedName("template")
    private String template;

    @SerializedName("regdate")
    private String regdate;

    @SerializedName("level")
    private String level;

    @SerializedName("age")
    private String age;

    @SerializedName("ward_id")
    private String ward_id;

    @SerializedName("member_program")
    private String member_program;

    public FormMemberInformationModel() {
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCrop_type() {
        return crop_type;
    }

    public void setCrop_type(String crop_type) {
        this.crop_type = crop_type;
    }

    List<String> getSexList(Context c) {
        List<String> sexList = new ArrayList<>();
        sexList.add(c.getResources().getString(R.string.tfm_member_info_sex_male));
        sexList.add(c.getResources().getString(R.string.tfm_member_info_sex_female));
        return sexList;
    }

    List<String> getRoleList(Context c, String unique_ik_number){
        SharedPreference sharedPreference = new SharedPreference(c);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String role_to_register_for = user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR);

        List<String> roleList = new ArrayList<>();
        if (role_to_register_for != null) {
            if (role_to_register_for.equalsIgnoreCase("Leader")){
                roleList.add("Leader");
            }else{
                CountModel secretary_count_result = getSecretaryCount(c,unique_ik_number);
                int secretary_count = secretary_count_result.getCount();
                if (secretary_count == 0){
                    roleList.add("Member");
                    roleList.add("Secretary");
                }else{
                    roleList.add("Member");
                }

            }
        }
        return roleList;
    }

    List<String> getCropList(Context c){
        List<String> cropList = new ArrayList<>();
        cropList.add(c.getResources().getString(R.string.tfm_member_info_preferred_crop1));
        cropList.add(c.getResources().getString(R.string.tfm_member_info_preferred_crop2));
        cropList.add(c.getResources().getString(R.string.tfm_member_info_preferred_crop3));
        return cropList;
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

    @SuppressLint("StaticFieldLeak")
    public FormMemberInformationModel.memberKeyDetails getProspectiveTGLDetailsResult(Context context, String unique_member_id) {
        FormMemberInformationModel.memberKeyDetails member_details = new FormMemberInformationModel.memberKeyDetails();
        try {
            member_details = new getProspectiveTGLDetails(context){
                @Override
                protected void onPostExecute(FormMemberInformationModel.memberKeyDetails s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return member_details;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getProspectiveTGLDetails extends AsyncTask<String, Void, FormMemberInformationModel.memberKeyDetails> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getProspectiveTGLDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected FormMemberInformationModel.memberKeyDetails doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                Log.d("first_name_1",tfmDatabase.getProspectiveTGLDao().getProspectiveTGLDetails(strings[0]).getFirst_name()+"");
                return tfmDatabase.getProspectiveTGLDao().getProspectiveTGLDetails(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public FormMemberInformationModel.memberKeyDetails getProspectiveTGEDetailsResult(Context context, String unique_member_id) {
        FormMemberInformationModel.memberKeyDetails member_details = new FormMemberInformationModel.memberKeyDetails();
        try {
            member_details = new getProspectiveTGEDetails(context){
                @Override
                protected void onPostExecute(FormMemberInformationModel.memberKeyDetails s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return member_details;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getProspectiveTGEDetails extends AsyncTask<String, Void, FormMemberInformationModel.memberKeyDetails> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getProspectiveTGEDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected FormMemberInformationModel.memberKeyDetails doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getProspectiveTGEDao().getProspectiveTGEDetails(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private CountModel getSecretaryCount(Context context, String unique_ik_number) {
        CountModel secretary_count = new CountModel();
        try {
            secretary_count = new checkSecretaryExistence(context){
                @Override
                protected void onPostExecute(CountModel s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getTrustGroupSecretaryCount
    @SuppressLint("StaticFieldLeak")
    public static abstract class checkSecretaryExistence extends AsyncTask<String, Void, CountModel> {
        Context mCtx;
        CountModel secretary_count_list = new CountModel();
        TFMDatabase tfmDatabase;

        checkSecretaryExistence(Context context) {
            this.mCtx = context;
        }

        @Override
        protected CountModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                secretary_count_list = tfmDatabase.getMembersTable().getTrustGroupSecretaryCount(strings[0]);
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public List<String> getAllRegisteredIKResult (Context context) {
        List<String> secretary_count = new ArrayList<>();
        try {
            secretary_count = new getAllRegisteredIK(context){
                @Override
                protected void onPostExecute(List<String> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getTrustGroupSecretaryCount
    @SuppressLint("StaticFieldLeak")
    public static abstract class getAllRegisteredIK extends AsyncTask<Void, Void, List<String>> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getAllRegisteredIK(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getAllRegisteredIK();

            }catch (Exception e){
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static String getLgaIDFromWardIDResult(Context context, String ward_id) {
        String result ;
        try {
            result = new getLgaIDFromWardID(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(ward_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "0";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLgaIDFromWardID extends AsyncTask<String, Void, String> {
        Context mCtx;
        LocationDatabase locationDatabase;

        getLgaIDFromWardID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("get_lga_id_from_ward_id", Arrays.toString(strings));
                locationDatabase = LocationDatabase.getInstance(mCtx);
                return locationDatabase.getWard().getLgaId(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static String getStateIDFromLgaIDResult(Context context, String lga_id) {
        String result ;
        try {
            result = new getStateIDFromLgaID(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(lga_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "0";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getStateIDFromLgaID extends AsyncTask<String, Void, String> {
        Context mCtx;
        LocationDatabase locationDatabase;

        getStateIDFromLgaID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("state_id_from_lga_id", Arrays.toString(strings));
                locationDatabase = LocationDatabase.getInstance(mCtx);
                return locationDatabase.getLga().getStateId(strings[0]);
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
            List<Integer> all_id = new FormMemberInformationModel.getAllMemberIds(context){
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
    static MyLeaderLocation getLeaderLocationResult(Context context, String unique_ik_number) {
        MyLeaderLocation myLeaderLocation = new MyLeaderLocation();
        try {
            myLeaderLocation = new getLeaderLocation(context){
                @Override
                protected void onPostExecute(MyLeaderLocation s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return myLeaderLocation;
    }

    //getTrustGroupSecretaryCount
    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderLocation extends AsyncTask<String, Void, MyLeaderLocation> {
        Context mCtx;
        MyLeaderLocation myLeaderLocation = new MyLeaderLocation();
        TFMDatabase tfmDatabase;

        getLeaderLocation(Context context) {
            this.mCtx = context;
        }

        @Override
        protected MyLeaderLocation doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                myLeaderLocation = tfmDatabase.getMembersTable().getMyLeaderLocation(strings[0]);
                return myLeaderLocation;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public FormMemberInformationModel getOldMemberDetailsResult(Context context, String unique_member_id) {
        FormMemberInformationModel member_details = new FormMemberInformationModel();
        try {
            member_details = new getOldMemberDetails(context){
                @Override
                protected void onPostExecute(FormMemberInformationModel s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return member_details;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getOldMemberDetails extends AsyncTask<String, Void, FormMemberInformationModel> {
        Context mCtx;
        FormMemberInformationModel member_details = new FormMemberInformationModel();
        TFMDatabase tfmDatabase;

        getOldMemberDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected FormMemberInformationModel doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                member_details = tfmDatabase.getOldMembersTable().getOldMemberDetails(strings[0]);
                return member_details;
            }catch (Exception e){
                e.printStackTrace();
                return null;
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

    public static class memberKeyDetails{

        private String first_name;

        private String last_name;

        public memberKeyDetails() {
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
    }

    public static class MyLeaderLocation{

        private String leader_ward_id;
        private String leader_village_name;
        private String member_program;

        public String getMember_program() {
            return member_program;
        }

        public void setMember_program(String member_program) {
            this.member_program = member_program;
        }

        public MyLeaderLocation(){
        }

        String getLeader_ward_id() {
            return leader_ward_id;
        }

        public void setLeader_ward_id(String leader_ward_id) {
            this.leader_ward_id = leader_ward_id;
        }

        String getLeader_village_name() {
            return leader_village_name;
        }

        public void setLeader_village_name(String leader_village_name) {
            this.leader_village_name = leader_village_name;
        }
    }
}
