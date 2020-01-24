package com.example.tgesign_up.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LeaderModel {

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

    @SerializedName("season_id")
    private String season_id;

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
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
    String getFinishedCheckListFlagResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getFinishedCheckListFlag(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "0";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getFinishedCheckListFlag extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getFinishedCheckListFlag(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer_checkListFlag", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getTrustGroupTable().getFinishedCheckList(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "0";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderDetails extends AsyncTask<String, Void, List<LeaderModel>> {
        Context mCtx;
        List<LeaderModel> leaderModelList = new ArrayList<>();
        TFMDatabase tfmDatabase;

        getLeaderDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<LeaderModel> doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leaderModelList = tfmDatabase.getTrustGroupTable().getTrustGroups(strings[0]);
                return leaderModelList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    int getMemberCountResult(Context context, String unique_ik_number) {
        int result ;
        try {
            result = new getMemberCount(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getMemberCount extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getMemberCount(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer_member_count", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberCount(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    TFMAppVariables getTFMAppVariablesResult(Context context, String variable_id) {
        TFMAppVariables result ;
        try {
            result = new getTFMAppVariables(context){
                @Override
                protected void onPostExecute(TFMAppVariables s) {}
            }.execute(variable_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = new TFMAppVariables();
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTFMAppVariables extends AsyncTask<String, Void, TFMAppVariables> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTFMAppVariables(Context context) {
            this.mCtx = context;
        }

        @Override
        protected TFMAppVariables doInBackground(String... strings) {
            try{
                Log.d("answer_variable_id", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getTFMAppVariablesTable().getTFMAppVariables(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getMemberProgramResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getMemberProgram(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "MA";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getMemberProgram extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getMemberProgram(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer_member_program", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberProgram(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "MA";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    int getLeaderCountForMemberResult(Context context, String unique_ik_number) {
        int result ;
        try {
            result = new getLeaderCountForMember(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderCountForMember extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getLeaderCountForMember(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer_member_count", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getLeaderCountForMember(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }
}
