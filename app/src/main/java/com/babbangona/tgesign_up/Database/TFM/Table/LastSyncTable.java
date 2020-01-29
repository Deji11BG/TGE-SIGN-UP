package com.babbangona.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_STAFF_ID},
        indices = {@Index(value = TFMDBContractClass.COL_STAFF_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_LAST_SYNC)

public class LastSyncTable {

    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    @NonNull
    private String staff_id;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_INPUT_RECORD)
    private String last_sync_down_input_record;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_OUTPUT_RECORD)
    private String last_sync_down_output_record;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_1)
    private String last_sync_down_extra_record_1;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_2)
    private String last_sync_down_extra_record_2;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_3)
    private String last_sync_down_extra_record_3;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_4)
    private String last_sync_down_extra_record_4;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_5)
    private String last_sync_down_extra_record_5;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_SYNC_DOWN_EXTRA_RECORD_6)
    private String last_sync_down_extra_record_6;

    public LastSyncTable(){

    }

    @NonNull
    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(@NonNull String staff_id) {
        this.staff_id = staff_id;
    }

    public String getLast_sync_down_input_record() {
        return last_sync_down_input_record;
    }

    public void setLast_sync_down_input_record(String last_sync_down_input_record) {
        this.last_sync_down_input_record = last_sync_down_input_record;
    }

    public String getLast_sync_down_output_record() {
        return last_sync_down_output_record;
    }

    public void setLast_sync_down_output_record(String last_sync_down_output_record) {
        this.last_sync_down_output_record = last_sync_down_output_record;
    }

    public String getLast_sync_down_extra_record_1() {
        return last_sync_down_extra_record_1;
    }

    public void setLast_sync_down_extra_record_1(String last_sync_down_extra_record_1) {
        this.last_sync_down_extra_record_1 = last_sync_down_extra_record_1;
    }

    public String getLast_sync_down_extra_record_2() {
        return last_sync_down_extra_record_2;
    }

    public void setLast_sync_down_extra_record_2(String last_sync_down_extra_record_2) {
        this.last_sync_down_extra_record_2 = last_sync_down_extra_record_2;
    }

    public String getLast_sync_down_extra_record_3() {
        return last_sync_down_extra_record_3;
    }

    public void setLast_sync_down_extra_record_3(String last_sync_down_extra_record_3) {
        this.last_sync_down_extra_record_3 = last_sync_down_extra_record_3;
    }

    public String getLast_sync_down_extra_record_4() {
        return last_sync_down_extra_record_4;
    }

    public void setLast_sync_down_extra_record_4(String last_sync_down_extra_record_4) {
        this.last_sync_down_extra_record_4 = last_sync_down_extra_record_4;
    }

    public String getLast_sync_down_extra_record_5() {
        return last_sync_down_extra_record_5;
    }

    public void setLast_sync_down_extra_record_5(String last_sync_down_extra_record_5) {
        this.last_sync_down_extra_record_5 = last_sync_down_extra_record_5;
    }

    public String getLast_sync_down_extra_record_6() {
        return last_sync_down_extra_record_6;
    }

    public void setLast_sync_down_extra_record_6(String last_sync_down_extra_record_6) {
        this.last_sync_down_extra_record_6 = last_sync_down_extra_record_6;
    }
}
