package com.example.tgesign_up;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class scheduleDefaultResponse {
    @SerializedName("schedule_flag")
    @Expose
    private String schedule_flag;

    @SerializedName("last_synced")
    @Expose
    private String last_synced;

    public scheduleDefaultResponse(String schedule_flag, String last_synced) {
        this.schedule_flag = schedule_flag;
        this.last_synced = last_synced;
    }

    public String getSchedule_flag() {
        return schedule_flag;
    }


    public String getLast_synced() {
        return last_synced;
    }
}



