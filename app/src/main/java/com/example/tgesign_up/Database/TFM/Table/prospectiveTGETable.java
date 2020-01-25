package com.example.tgesign_up.Database.TFM.Table;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Home.LeaderModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_PROSPECTIVE_TGE)

public class prospectiveTGETable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)//
    @NonNull
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_ID)
    private String member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)//
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_MIDDLE_NAME)
    private String middle_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)//
    private String last_name;

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE)//
    private String template;

    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)
    private String sync_flag;

    @ColumnInfo(name = TFMDBContractClass.ACTIVE_FLAG)
    private String active_flag;

    public prospectiveTGETable(@NonNull String unique_member_id, String member_id,
                               String first_name, String middle_name, String last_name,
                               String template, String active_flag,String sync_flag) {
        this.unique_member_id = unique_member_id;
        this.member_id = member_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.template = template;
        this.active_flag=active_flag;
        this.sync_flag=sync_flag;
    }

    public prospectiveTGETable(){

    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String regdate) {
        this.sync_flag = sync_flag;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }


    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderDetails extends AsyncTask<Void, Void, List<prospectiveTGETable>> {
        Context mCtx;
        List<prospectiveTGETable> leaderModelList = new ArrayList<>();
        TFMDatabase tfmDatabase;

        getLeaderDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<prospectiveTGETable> doInBackground(Void... voids) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leaderModelList = tfmDatabase.getProspectiveTGEDao().getLeaders();
                return leaderModelList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }



}

