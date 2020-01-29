package com.babbangona.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.VARIABLE_ID},
        indices = {@Index(value = TFMDBContractClass.VARIABLE_ID, unique = true)},
        tableName = TFMDBContractClass.TFM_APP_VARIABLES)

public class TFMAppVariables {

    @ColumnInfo(name = TFMDBContractClass.VARIABLE_ID)
    @NonNull
    private String variable_id;

    @ColumnInfo(name = TFMDBContractClass.COL_KKG_MIN_OLD)
    private String kkg_min_old;

    @ColumnInfo(name = TFMDBContractClass.COL_KKG_MIN_NEW)
    private String kkg_min_new;

    @ColumnInfo(name = TFMDBContractClass.COL_KKG_MAX_OLD)
    private String kkg_max_old;

    @ColumnInfo(name = TFMDBContractClass.COL_KKG_MAX_NEW)
    private String kkg_max_new;

    @ColumnInfo(name = TFMDBContractClass.COL_MA_MIN_OLD)
    private String ma_min_old;

    @ColumnInfo(name = TFMDBContractClass.COL_MA_MIN_NEW)
    private String ma_min_new;

    @ColumnInfo(name = TFMDBContractClass.COL_MA_MAX_OLD)
    private String ma_max_old;

    @ColumnInfo(name = TFMDBContractClass.COL_MA_MAX_NEW)
    private String ma_max_new;

    public TFMAppVariables(){

    }

    public TFMAppVariables(@NonNull String variable_id, String kkg_min_old,
                           String kkg_min_new, String kkg_max_old,
                           String kkg_max_new, String ma_min_old,
                           String ma_min_new, String ma_max_old,
                           String ma_max_new) {
        this.variable_id = variable_id;
        this.kkg_min_old = kkg_min_old;
        this.kkg_min_new = kkg_min_new;
        this.kkg_max_old = kkg_max_old;
        this.kkg_max_new = kkg_max_new;
        this.ma_min_old = ma_min_old;
        this.ma_min_new = ma_min_new;
        this.ma_max_old = ma_max_old;
        this.ma_max_new = ma_max_new;
    }

    @NonNull
    public String getVariable_id() {
        return variable_id;
    }

    public void setVariable_id(@NonNull String app_version) {
        this.variable_id = app_version;
    }

    public String getKkg_min_old() {
        return kkg_min_old;
    }

    public void setKkg_min_old(String kkg_min_old) {
        this.kkg_min_old = kkg_min_old;
    }

    public String getKkg_min_new() {
        return kkg_min_new;
    }

    public void setKkg_min_new(String kkg_min_new) {
        this.kkg_min_new = kkg_min_new;
    }

    public String getKkg_max_old() {
        return kkg_max_old;
    }

    public void setKkg_max_old(String kkg_max_old) {
        this.kkg_max_old = kkg_max_old;
    }

    public String getKkg_max_new() {
        return kkg_max_new;
    }

    public void setKkg_max_new(String kkg_max_new) {
        this.kkg_max_new = kkg_max_new;
    }

    public String getMa_min_old() {
        return ma_min_old;
    }

    public void setMa_min_old(String ma_min_old) {
        this.ma_min_old = ma_min_old;
    }

    public String getMa_min_new() {
        return ma_min_new;
    }

    public void setMa_min_new(String ma_min_new) {
        this.ma_min_new = ma_min_new;
    }

    public String getMa_max_old() {
        return ma_max_old;
    }

    public void setMa_max_old(String ma_max_old) {
        this.ma_max_old = ma_max_old;
    }

    public String getMa_max_new() {
        return ma_max_new;
    }

    public void setMa_max_new(String ma_max_new) {
        this.ma_max_new = ma_max_new;
    }
}
