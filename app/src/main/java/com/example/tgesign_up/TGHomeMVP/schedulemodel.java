package com.example.tgesign_up.TGHomeMVP;


public class schedulemodel {

//model class for tgselect

    private String ward;
    private String slot_id;
    private String first_day;
    private String first_time;
    private String second_day;
    private String second_time;

    private Integer schedulecount;
    private String schedule_flag;

    public schedulemodel(String ward, String slot_id, String first_time, String first_day,
                       String second_time, String second_day,String schedule_flag, Integer schedule_count  ) {

        this.ward = ward;
        this.slot_id = slot_id;
        this.first_day = first_day;
        this.first_time = first_time;
        this.second_day = second_day;
        this.second_time = second_time;
        this.schedulecount = schedule_count;
        this.schedule_flag=schedule_flag;

    }

    public String getWard() {
        return ward;
    }

    public String getSchedule_flag() {
        return schedule_flag;
    }

    public void setSchedule_flag(String schedule_flag) {
        this.schedule_flag = schedule_flag;
    }

    public void setWard(String ward) {
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

    public Integer getSchedulecount() {
        return schedulecount;
    }

    public void setSchedulecount(Integer schedulecount) {
        this.schedulecount = schedulecount;
    }
}