package com.babbangona.tgesign_up.Database.Location.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.babbangona.tgesign_up.Database.DBContractClass;

@Entity(primaryKeys = {DBContractClass.COL_LGA_ID}, tableName = DBContractClass.TABLE_LGA)
public class LgaTable {

    @ColumnInfo(name = DBContractClass.COL_LGA_ID)
    @NonNull
    private String lga_id;

    @ColumnInfo(name = DBContractClass.COL_LGA_NAME)
    private String lga_name;

    @ColumnInfo(name = DBContractClass.COL_STATE_ID)
    private String state_id;


    public LgaTable(String lga_id, String lga_name, String state_id){

        this.lga_id = lga_id;
        this.lga_name = lga_name;
        this.state_id = state_id;

    }

    public @Ignore LgaTable(){

    }

    @NonNull
    public String getLga_id() {
        return lga_id;
    }

    public void setLga_id(@NonNull String lga_id) {
        this.lga_id = lga_id;
    }

    public String getLga_name() {
        return lga_name;
    }

    public void setLga_name(String lga_name) {
        this.lga_name = lga_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }




}
