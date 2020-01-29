package com.babbangona.tgesign_up.Database.Location.Table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.babbangona.tgesign_up.Database.DBContractClass;


@Entity(primaryKeys = {DBContractClass.COL_WARD_ID}, tableName = DBContractClass.TABLE_WARD)
public class WardTable {



    @ColumnInfo(name = DBContractClass.COL_WARD_ID)
    @NonNull
    private String ward_id;

    @ColumnInfo(name = DBContractClass.COL_WARD_NAME)
    private String ward_name;

    @ColumnInfo(name = DBContractClass.COL_LGA_ID)
    private String lga_id;




    public WardTable(String ward_id, String ward_name, String lga_id){

        this.lga_id = lga_id;
        this.ward_id = ward_id;
        this.ward_name = ward_name;

    }

    public WardTable(){

    }


    @NonNull
    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(@NonNull String ward_id) {
        this.ward_id = ward_id;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getLga_id() {
        return lga_id;
    }

    public void setLga_id(String lga_id) {
        this.lga_id = lga_id;
    }

}
