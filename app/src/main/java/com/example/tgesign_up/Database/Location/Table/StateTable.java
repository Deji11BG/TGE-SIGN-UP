package com.example.tgesign_up.Database.Location.Table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.example.tgesign_up.Database.DBContractClass;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {DBContractClass.COL_STATE_ID}, tableName = DBContractClass.TABLE_STATE)
public class StateTable {

    @ColumnInfo(name = DBContractClass.COL_STATE_NAME)
    private String state_name;

    @ColumnInfo(name = DBContractClass.COL_STATE_ID)
    @NonNull
    private String state_id;


    public StateTable(@NotNull String state_id, String state_name){

        this.state_id = state_id;
        this.state_name = state_name;
    }

    public StateTable(){

    }



    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    @NonNull
    public String getState_id() {
        return state_id;
    }

    public void setState_id(@NonNull String state_id) {
        this.state_id = state_id;
    }





}
