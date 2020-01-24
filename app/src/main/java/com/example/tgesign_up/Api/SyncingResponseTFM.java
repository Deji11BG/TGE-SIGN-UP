package com.example.tgesign_up.Api;

public class SyncingResponseTFM{

    private String unique_member_id;
    private String sync_flag;

    public SyncingResponseTFM() {
    }

    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }
}
