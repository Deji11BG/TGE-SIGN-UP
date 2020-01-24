package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_TEMPLATE_INDEX},
        tableName = TFMDBContractClass.TABLE_TFM_TEMPLATE_TRACKER)

public class TFMTemplateTrackerTable {

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE_INDEX)
    @NonNull
    private String template_id;

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE_TRACKER)
    private String template_tracker;

    public TFMTemplateTrackerTable(@NonNull String template_id, String template_tracker) {
        this.template_id = template_id;
        this.template_tracker = template_tracker;
    }

    public TFMTemplateTrackerTable() {
    }

    @NonNull
    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(@NonNull String template_id) {
        this.template_id = template_id;
    }

    public String getTemplate_tracker() {
        return template_tracker;
    }

    public void setTemplate_tracker(String template_tracker) { this.template_tracker = template_tracker; }
}
