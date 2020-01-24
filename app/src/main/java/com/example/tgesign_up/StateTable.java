package com.example.tgesign_up;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"ward_id"}, tableName = "states_info")
public class StateTable {
    @ColumnInfo(name ="ward_id")
    @NonNull
    private String ward_id;

    @ColumnInfo(name ="state")
    private String state;

    @ColumnInfo(name ="lga")
    private String lga;

    @ColumnInfo(name ="ward")
    private String ward;

    @ColumnInfo(name ="min_lat")
    private String min_lat;

    @ColumnInfo(name ="max_lat")
    private String max_lat;

    @ColumnInfo(name ="min_long")
    private String min_long;

    @ColumnInfo(name = "max_long")
    private String max_long;

//    @ColumnInfo(name ="center_distance")
//    private String center_distance;



    public StateTable(@NonNull String ward_id, String state, String lga, String ward, String min_lat, String max_lat, String min_long, String max_long ) {
        this.state = state;
        this.min_lat = min_lat;
        this.min_long = min_long;
        this.max_lat = max_lat;
        this.max_long = max_long;
        this.ward_id = ward_id;
        this.lga = lga;
        this.ward = ward;
    }

    public StateTable() {
    }

    @NonNull
    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(@NonNull String ward_id) {
        this.ward_id = ward_id;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMin_lat() {
        return min_lat;
    }

    public void setMin_lat(String min_lat) {
        this.min_lat = min_lat;
    }

    public String getMin_long() {
        return min_long;
    }

    public void setMin_long(String min_long) {
        this.min_long = min_long;
    }

    public String getMax_lat() {
        return max_lat;
    }

    public void setMax_lat(String max_lat) {
        this.max_lat = max_lat;
    }

    public String getMax_long() {
        return max_long;
    }

    public void setMax_long(String max_long) {
        this.max_long = max_long;
    }

//    public String getCenter_distance() {
//        return center_distance;
//    }
//
//    public void setCenter_distance(String center_distance) {
//        this.center_distance = center_distance;
//    }

    @NonNull
    public String getLga() {
        return lga;
    }

    public void setLga(@NonNull String lga) {
        this.lga = lga;
    }

    @NonNull
    public String getWard() {
        return ward;
    }

    public void setWard(@NonNull String ward) {
        this.ward = ward;
    }
}
