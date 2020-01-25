package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.TGE_ID},
        indices = {@Index(value = TFMDBContractClass.TGE_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_NEW_TGE)

public class TGE {

    @ColumnInfo(name = TFMDBContractClass.TGE_ID)
    @NonNull
    private String tge_id;

    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    private String staff_id;

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)
    private String last_name;


    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)
    private String sync_flag;

    public TGE(){

    }

    @NonNull
    public String getTge_id() {
        return tge_id;
    }

    public void setTge_id(@NonNull String tge_id) {
        this.tge_id = tge_id;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(String unique_member_id) {
        this.unique_member_id = unique_member_id;
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
}
