package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_NEW_TGE)

public class TGE {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)
    @NonNull
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    private String staff_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)
    private String last_name;

    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)
    private String sync_flag;

    @ColumnInfo(name = TFMDBContractClass.COL_APP_VERSION)
    private String app_version;

    @ColumnInfo(name = TFMDBContractClass.COL_IMEI)
    private String imei;

    @ColumnInfo(name = TFMDBContractClass.COL_SLOT_ID)
    private String slot_id;

    public TGE(){

    }

    public TGE(@NonNull String unique_member_id, String staff_id, String first_name,
               String last_name, String sync_flag, String app_version,
               String imei, String slot_id) {
        this.unique_member_id = unique_member_id;
        this.staff_id = staff_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.sync_flag = sync_flag;
        this.app_version = app_version;
        this.imei = imei;
        this.slot_id = slot_id;
    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
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

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }
}
