package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_IK_NUMBER},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_IK_NUMBER, unique = true)},
        tableName = TFMDBContractClass.TABLE_TRUST_GROUP_DATA)

public class TrustGroupTable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_IK_NUMBER)
    @NonNull
    private String unique_ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_IK_NUMBER)
    private String ik_number;


    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    private String staff_id;

    @ColumnInfo(name = TFMDBContractClass.COL_LEVEL)
    private String level;

    @ColumnInfo(name = TFMDBContractClass.COL_FINISHED_CHECKLIST)
    private String finished_checklist;

    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)
    private String sync_flag;

    public TrustGroupTable(@NonNull String unique_ik_number, String ik_number,
                           String staff_id, String level,
                           String finished_checklist, String sync_flag) {
        this.unique_ik_number = unique_ik_number;
        this.ik_number = ik_number;
        this.staff_id = staff_id;
        this.level = level;
        this.finished_checklist = finished_checklist;
        this.sync_flag = sync_flag;
    }

    public TrustGroupTable(){

    }

    @NonNull
    public String getUnique_ik_number() {
        return unique_ik_number;
    }

    public void setUnique_ik_number(@NonNull String unique_ik_number) {
        this.unique_ik_number = unique_ik_number;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getFinished_checklist() {
        return finished_checklist;
    }

    public void setFinished_checklist(String finished_checklist) {
        this.finished_checklist = finished_checklist;
    }

    public String getIk_number() {
        return ik_number;
    }

    public void setIk_number(String ik_number) {
        this.ik_number = ik_number;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public static class SyncingResponse{

        private String unique_ik_number;
        private String sync_flag;

        public SyncingResponse() {
        }

        public String getUnique_ik_number() {
            return unique_ik_number;
        }

        public void setUnique_ik_number(String unique_ik_number) {
            this.unique_ik_number = unique_ik_number;
        }

        public String getSync_flag() {
            return sync_flag;
        }

        public void setSync_flag(String sync_flag) {
            this.sync_flag = sync_flag;
        }
    }
}
