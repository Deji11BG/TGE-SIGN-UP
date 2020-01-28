package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;


@Entity(primaryKeys = {TFMDBContractClass.COL_PICTURE_NAME},
        indices = {@Index(value = TFMDBContractClass.COL_PICTURE_NAME, unique = true)},
        tableName = TFMDBContractClass.TABLE_PICTURE_SYNC)

public class PictureSync {

    @ColumnInfo(name = TFMDBContractClass.COL_PICTURE_NAME)
    @NonNull
    private String picture_name;

    public PictureSync(){

    }

    public PictureSync(@NonNull String picture_name) {
        this.picture_name = picture_name;
    }

    @NonNull
    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(@NonNull String picture_name) {
        this.picture_name = picture_name;
    }
}
