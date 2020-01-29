package com.babbangona.tgesign_up.Database.Location.Table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.babbangona.tgesign_up.Database.DBContractClass;

@Entity(primaryKeys = {DBContractClass.COL_VILLAGE_ID}, tableName = DBContractClass.TABLE_VILLAGE)
public class VillageTable {





    @ColumnInfo(name = DBContractClass.COL_VILLAGE_ID)
    @NonNull
    private String village_id;

    @ColumnInfo(name = DBContractClass.COL_VILLAGE_NAME)
    private String village_name;

    @ColumnInfo(name = DBContractClass.COL_WARD_ID)
    private String ward_id;



    public VillageTable(String village_id, String village_name, String ward_id){

        this.village_id     = village_id;
        this.village_name   = village_name;
        this.ward_id        = ward_id;

    }

    public VillageTable(){

    }

    @NonNull
    public String getVillage_id() {
        return village_id;
    }

    public void setVillage_id(@NonNull String village_id) {
        this.village_id = village_id;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }



}
