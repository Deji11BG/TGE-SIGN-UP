package com.example.tgesign_up;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"unique_location_id"}, tableName = "location_info")
public class LocationTable {

    @ColumnInfo(name ="unique_location_id")
    @NonNull
    private String unique_location_id;

    @ColumnInfo(name ="latitude")
    private String latitude;

    @ColumnInfo(name ="longitude")
    private String longitude;

    @ColumnInfo(name ="state")
    private String state;

    @ColumnInfo(name = "lga")
    private String lga;

    @ColumnInfo(name ="ward")
    private String ward;

    @ColumnInfo(name ="town")
    private String town;

    @ColumnInfo(name ="app_version")
    private String app_version;

    @ColumnInfo(name ="staff_id")
    private String staff_id;

    @ColumnInfo(name ="date_created")
    private String date_created;

    @ColumnInfo(name ="sync_flag")
    private String sync_flag;

    public LocationTable(@NonNull String unique_location_id, String latitude, String longitude, String state, String lga, String ward,
                         String town, String staff_id, String app_version, String date_created,  String sync_flag) {
        this.unique_location_id = unique_location_id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.state = state;
        this.lga = lga;
        this.ward = ward;
        this.town = town;
        this.staff_id = staff_id;
        this.app_version = app_version;
        this.date_created = date_created;
        this.sync_flag = sync_flag;
    }

    public LocationTable() {
    }

    @NonNull
    public String getUnique_location_id() {
        return unique_location_id;
    }

    public void setUnique_location_id(@NonNull String unique_location_id) {
        this.unique_location_id = unique_location_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLga() {
        return lga;
    }

    public void setLga(String lga) {
        this.lga = lga;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getApp_version() {
        return app_version;
    }

    public void setApp_version(String app_version) {
        this.app_version = app_version;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
