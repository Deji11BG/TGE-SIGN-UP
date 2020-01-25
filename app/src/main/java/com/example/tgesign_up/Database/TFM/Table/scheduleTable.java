package com.example.tgesign_up.Database.TFM.Table;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;


@Entity(primaryKeys = {TFMDBContractClass.WARD},
        indices = {@Index(value = TFMDBContractClass.WARD, unique = true)},
        tableName = TFMDBContractClass.TABLE_SCHEDULE)

public class scheduleTable {

    @ColumnInfo(name = TFMDBContractClass.WARD)//
    @NonNull
    public String ward;

    @ColumnInfo(name = TFMDBContractClass.SLOTID)
    public String slot_id;

    @ColumnInfo(name = TFMDBContractClass.FIRST_DAY)
    public String first_day;

    @ColumnInfo(name = TFMDBContractClass.FIRST_TIME)//
    public String first_time;
    @ColumnInfo(name = TFMDBContractClass.SECOND_DAY)
    public String second_day;

    @ColumnInfo(name = TFMDBContractClass.SECOND_TIME)//
    public String second_time;

    @ColumnInfo(name = TFMDBContractClass.SCHEDULE_SYNC_FLAG)
    public String schedule_flag;

    @ColumnInfo(name = TFMDBContractClass.SCHEDULE_COUNT)//
    public int schedule_count;



    public scheduleTable(@NonNull String ward, String slot_id, String first_day,
                               String first_time, String second_day, String second_time,String schedule_flag,Integer schedule_count ) {
        this.ward = ward;
        this.slot_id = slot_id;
        this.first_day= first_day;
        this.first_time = first_time;
        this.second_day = second_day;
        this.second_time=second_time;
        this.schedule_count = schedule_count;
        this.schedule_flag = schedule_flag;

    }

    public scheduleTable(){

    }

    @NonNull
    public String getWard() {
        return ward;
    }

    public void setWard(@NonNull String ward) {
        this.ward = ward;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getFirst_day() {
        return first_day;
    }

    public void setFirst_day(String first_day) {
        this.first_day = first_day;
    }

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getSecond_day() {
        return second_day;
    }

    public void setSecond_day(String second_day) {
        this.second_day = second_day;
    }

    public String getSecond_time() {
        return second_time;
    }

    public void setSecond_time(String second_time) {
        this.second_time = second_time;
    }

    public String getSchedule_flag() {
        return schedule_flag;
    }

    public void setSchedule_flag(String schedule_flag) {
        this.schedule_flag = schedule_flag;
    }

    public int getSchedule_count() {
        return schedule_count;
    }

    public void setSchedule_count(Integer schedule_count) {
        this.schedule_count = schedule_count;
    }



}

