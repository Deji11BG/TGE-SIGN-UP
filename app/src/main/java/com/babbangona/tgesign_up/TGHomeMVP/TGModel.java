package com.babbangona.tgesign_up.TGHomeMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.babbangona.tgesign_up.Database.TFM.TFMDatabase;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TGModel {

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
    List<TGModel> getMemberList(Context context, String unique_ik_number) {
        List<TGModel> members_data = new ArrayList<>();
        try {
            members_data = new getTgMemberDetails(context){
                @Override
                protected void onPostExecute(List<TGModel> s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return members_data;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTgMemberDetails extends AsyncTask<String, Void, List<TGModel>> {
        Context mCtx;
        List<TGModel> tgModelList = new ArrayList<>();
        TFMDatabase tfmDatabase;

        getTgMemberDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<TGModel> doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tgModelList = tfmDatabase.getOldMembersTable().getTrustGroupMembers(strings[0]);
                return tgModelList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static CountModel getMemberExistence(Context context, String unique_member_id) {
        CountModel count = new CountModel();
        try {
            count = new checkMemberExistence(context){
                @Override
                protected void onPostExecute(CountModel s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    @SuppressLint("StaticFieldLeak")
    static CountModel getRegisteredMemberExistence(Context context, String unique_member_id) {
        CountModel count = new CountModel();
        try {
            count = new checkRegisteredMemberExistence(context){
                @Override
                protected void onPostExecute(CountModel s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class checkMemberExistence extends AsyncTask<String, Void, CountModel> {
        Context mCtx;
        CountModel count = new CountModel();
        TFMDatabase tfmDatabase;

        checkMemberExistence(Context context) {
            this.mCtx = context;
        }

        @Override
        protected CountModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                count = tfmDatabase.getMembersTable().getMemberExistence(strings[0]);
                return count;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class checkRegisteredMemberExistence extends AsyncTask<String, Void, CountModel> {
        Context mCtx;
        CountModel count = new CountModel();
        TFMDatabase tfmDatabase;

        checkRegisteredMemberExistence(Context context) {
            this.mCtx = context;
        }

        @Override
        protected CountModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                count = tfmDatabase.getMembersTable().getRegisteredMemberExistence(strings[0]);
                return count;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static int getReactivatedMemberResult(Context context, String unique_member_id) {
        int result ;
        try {
            result = new reactivateMember(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    static int getDeactivatedMemberResult(Context context, String unique_member_id) {
        int result ;
        try {
            result = new deactivateMember(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class reactivateMember extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        reactivateMember(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getMembersTable().reactivateMember(strings[0]);
                return 1;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class deactivateMember extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        deactivateMember(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getMembersTable().deactivateMember(strings[0]);
                return 1;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static CountModel getSecretaryCountResult(Context context, String unique_ik_number) {
        CountModel count = new CountModel();
        try {
            count = new getSecretaryCount(context){
                @Override
                protected void onPostExecute(CountModel s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return count;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getSecretaryCount extends AsyncTask<String, Void, CountModel> {
        Context mCtx;
        TFMDatabase tfmDatabase;
        CountModel count = new CountModel();

        getSecretaryCount(Context context) {
            this.mCtx = context;
        }

        @Override
        protected CountModel doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                count = tfmDatabase.getMembersTable().getSecretaryCount(strings[0]);
                return count;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static String getSecretaryIDResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getSecretaryID(context){
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
    public static abstract class getSecretaryID extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getSecretaryID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getSecretaryUniqueID(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static int getSwitchRoleResult(Context context, String unique_member_id, String change_to_role) {
        int result ;
        try {
            result = new switchRole(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_member_id,change_to_role).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class switchRole extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        switchRole(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getMembersTable().changeRole(strings[0],strings[1]);
                return 1;
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    static String getRoleResult(Context context, String unique_member_id) {
        String result ;
        try {
            result = new getRole(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getRole extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getRole(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberRole(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getTemplateResult(Context context, String unique_member_id) {
        String result ;
        try {
            result = new getTemplate(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTemplate extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTemplate(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberTemplate(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getSeasonIDResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getSeasonID(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "20";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getSeasonID extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getSeasonID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer_season_id", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getOldMembersTable().getSeasonID(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "20";
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

    @SuppressLint("StaticFieldLeak")
    String getOldMemberTemplateResult(Context context, String unique_member_id) {
        String result ;
        try {
            result = new getOldMemberTemplate(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getOldMemberTemplate extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getOldMemberTemplate(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getOldMembersTable().getMemberTemplateOld(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
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
