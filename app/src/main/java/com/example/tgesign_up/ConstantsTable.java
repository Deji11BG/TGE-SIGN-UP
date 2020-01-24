package com.example.tgesign_up;


import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"ward_id"}, tableName = "constants_table")
public class ConstantsTable {
    @ColumnInfo(name ="distance_X1")
    private String distance_X1;

    @ColumnInfo(name ="distance_X2")
    private String distance_X2;

    @ColumnInfo(name ="distance_Y1")
    private String distance_Y1;

    @ColumnInfo(name ="distance_Y2")
    private String distance_Y2;

    @ColumnInfo(name ="distance_Z1")
    private String distance_Z1;

    @ColumnInfo(name ="distance_Z2")
    private String distance_Z2;

    @ColumnInfo(name ="lga_edit_flag")
    private String lga_edit_flag;



//    @ColumnInfo(name ="center_distance")
//    private String center_distance;


    public ConstantsTable(String distance_X1, String distance_X2, String distance_Y1, String distance_Y2, String distance_Z1, String distance_Z2, String lga_edit_flag) {
        this.distance_X1 = distance_X1;
        this.distance_X2 = distance_X2;
        this.distance_Y1 = distance_Y1;
        this.distance_Y2 = distance_Y2;
        this.distance_Z1 = distance_Z1;
        this.distance_Z2 = distance_Z2;
        this.lga_edit_flag = lga_edit_flag;
    }

    public ConstantsTable() {
    }

    public String getDistance_X1() {
        return distance_X1;
    }

    public void setDistance_X1(String distance_X1) {
        this.distance_X1 = distance_X1;
    }

    public String getDistance_X2() {
        return distance_X2;
    }

    public void setDistance_X2(String distance_X2) {
        this.distance_X2 = distance_X2;
    }

    public String getDistance_Y1() {
        return distance_Y1;
    }

    public void setDistance_Y1(String distance_Y1) {
        this.distance_Y1 = distance_Y1;
    }

    public String getDistance_Y2() {
        return distance_Y2;
    }

    public void setDistance_Y2(String distance_Y2) {
        this.distance_Y2 = distance_Y2;
    }

    public String getDistance_Z1() {
        return distance_Z1;
    }

    public void setDistance_Z1(String distance_Z1) {
        this.distance_Z1 = distance_Z1;
    }

    public String getDistance_Z2() {
        return distance_Z2;
    }

    public void setDistance_Z2(String distance_Z2) {
        this.distance_Z2 = distance_Z2;
    }

    public String getLga_edit_flag() {
        return lga_edit_flag;
    }

    public void setLga_edit_flag(String lga_edit_flag) {
        this.lga_edit_flag = lga_edit_flag;
    }
}
