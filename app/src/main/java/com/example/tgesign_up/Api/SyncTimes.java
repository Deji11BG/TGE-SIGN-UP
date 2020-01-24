package com.example.tgesign_up.Api;


public class SyncTimes {

    private String staff_id;

    private LastSyncDownTimes lastSyncDownTimes;

    public SyncTimes() {
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public LastSyncDownTimes getLastSyncDownTimes() {
        return lastSyncDownTimes;
    }

    public void setLastSyncDownTimes(LastSyncDownTimes lastSyncDownTimes) {
        this.lastSyncDownTimes = lastSyncDownTimes;
    }

    public static class LastSyncDownTimes {

        private String last_sync_down_time_input_records;
        private String last_sync_down_time_output_records;

        public String getLast_sync_down_time_output_records() {
            return last_sync_down_time_output_records;
        }

        public void setLast_sync_down_time_output_records(String last_sync_down_time_output_records) {
            this.last_sync_down_time_output_records = last_sync_down_time_output_records;
        }

        public String getLast_sync_down_time_input_records() {
            return last_sync_down_time_input_records;
        }

        void setLast_sync_down_time_input_records(String last_sync_down_time_input_records) {
            this.last_sync_down_time_input_records = last_sync_down_time_input_records;
        }
    }



}
