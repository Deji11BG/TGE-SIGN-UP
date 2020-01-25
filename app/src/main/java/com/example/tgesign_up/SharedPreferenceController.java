package com.example.tgesign_up;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;


public class SharedPreferenceController {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME      = "Location";

    public static final String KEY_APP_VERSION  = "app_version";
    public static final String KEY_STAFF_ID    = "staff_id";
    public static final String KEY_STAFF_ROLE  = "staff_role";
    public static final String KEY_STAFF_NAME  = "staff_name";
    public static final String KEY_IMPORT_FLAG = "import_flag";
    public static final String KEY_LAST_SYNCED = "last_synced";
    public static final String KEY_DISTANCE_X1 = "distance_X1";
    public static final String KEY_DISTANCE_Y1   = "distance_Y1";
    public static final String KEY_DISTANCE_Z1 = "distance_Z1";
    public static final String KEY_DISTANCE_X2 = "distance_X2";
    public static final String KEY_DISTANCE_Y2   = "distance_Y2";
    public static final String KEY_DISTANCE_Z2 = "distance_Z2";
    public static final String KEY_LGA_EDIT_FLAG   = "lga_edit_flag";
    public static final String KEY_MEMBER_QR_IK   = "member_qr_ik";

    /**
     * Keys below are for holding initial mapping information temporarily
     * */

    public static final String KEY_USER_LAT = "user_lat";
    public static final String KEY_USER_LONG = "user_long";
    public static final String KEY_STATE        = "state";
    public static final String KEY_LGA          = "lga";
    public static final String KEY_WARD         = "ward";
    public static final String KEY_TOWN      = "town";


    /**
     * Keys below are for holding day counts
     * */

    public static final String KEY_MON_MORNING = "day_code_0";
    public static final String KEY_MON_EVENING = "day_code_1";
    public static final String KEY_TUE_MORNING = "day_code_2";
    public static final String KEY_TUE_EVENING = "day_code_3";
    public static final String KEY_WED_MORNING = "day_code_4";
    public static final String KEY_WED_EVENING = "day_code_5";
    public static final String KEY_THU_MORNING = "day_code_6";
    public static final String KEY_THU_EVENING = "day_code_7";
    public static final String KEY_FRI_MORNING = "day_code_8";
    public static final String KEY_FRI_EVENING = "day_code_9";
    public static final String KEY_SAT_MORNING = "day_code_10";
    public static final String KEY_SAT_EVENING = "day_code_11";
    public static final String KEY_SUN_MORNING = "day_code_12";
    public static final String KEY_SUN_EVENING = "day_code_13";
    public static final String KEY_COUNT_LIMIT = "count_limit";


    /**
     * Keys below are for holding day counts
     * */

    public static final String KEY_GROUP1 = "group1";
    public static final String KEY_GROUP2 = "group2";
    public static final String KEY_GROUP3 = "group3";
    public static final String KEY_GROUP4 = "group4";
    public static final String KEY_GROUP5 = "group5";


    /**
     * Keys below are for holding day counts
     * */

    public static final String KEY_TIME1 = "time1";
    public static final String KEY_TIME2 = "time2";
    public static final String KEY_TIME3 = "time3";
    public static final String KEY_TIME4 = "time4";
    public static final String KEY_TIME5 = "time5";



    /**
     * Keys below are for holding constants data
     * */








    // Constructor
    public SharedPreferenceController(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferenceController() {

    }


    public void holdSessionInfo(String staff_id, String staff_role, String app_version, String staff_name){
        editor.putString(KEY_STAFF_ID,   staff_id);
        editor.putString(KEY_STAFF_NAME,   staff_name);
        editor.putString(KEY_STAFF_ROLE,    staff_role);
        editor.putString(KEY_APP_VERSION, app_version);

        editor.commit();

    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_STAFF_ID, pref.getString(KEY_STAFF_ID, "mumu"));
        user.put(KEY_STAFF_ROLE, pref.getString(KEY_STAFF_ROLE, null));
        user.put(KEY_APP_VERSION, pref.getString(KEY_APP_VERSION, null));
        user.put(KEY_LAST_SYNCED, pref.getString(KEY_LAST_SYNCED, "2019-00-00 00:00:00"));
        user.put(KEY_DISTANCE_X1, pref.getString(KEY_DISTANCE_X1, "0.0"));
        user.put(KEY_DISTANCE_Y1, pref.getString(KEY_DISTANCE_Y1, "0.0"));
        user.put(KEY_DISTANCE_Z1, pref.getString(KEY_DISTANCE_Z1, "0.0"));
        user.put(KEY_DISTANCE_X2, pref.getString(KEY_DISTANCE_X2, "0.0"));
        user.put(KEY_DISTANCE_Y2, pref.getString(KEY_DISTANCE_Y2, "0.0"));
        user.put(KEY_DISTANCE_Z2, pref.getString(KEY_DISTANCE_Z2, "0.0"));
        user.put(KEY_LGA_EDIT_FLAG, pref.getString(KEY_LGA_EDIT_FLAG, "1"));

        return user;
    }


    


    public void saveUserCords(String user_lat,String user_long){
        editor.putString(KEY_USER_LAT,  user_lat);
        editor.putString(KEY_USER_LONG, user_long);

        editor.commit();
    }

    public void saveLastSynced(String last_synced){
        editor.putString(KEY_LAST_SYNCED   ,      last_synced);

        editor.commit();
    }
    public HashMap<String, String> getScheduleDetails(){
        HashMap<String, String> schedule = new HashMap<String, String>();
        // user name
        schedule.put(KEY_MON_MORNING, pref.getString(KEY_MON_MORNING, ""));
        schedule.put(KEY_MON_EVENING, pref.getString(KEY_MON_EVENING, ""));
        schedule.put(KEY_TUE_MORNING, pref.getString(KEY_TUE_MORNING, ""));
        schedule.put(KEY_TUE_EVENING, pref.getString(KEY_TUE_EVENING, ""));
        schedule.put(KEY_WED_MORNING, pref.getString(KEY_WED_MORNING, ""));
        schedule.put(KEY_WED_EVENING, pref.getString(KEY_WED_EVENING, ""));
        schedule.put(KEY_THU_MORNING, pref.getString(KEY_THU_MORNING, ""));
        schedule.put(KEY_THU_EVENING, pref.getString(KEY_THU_EVENING, ""));
        schedule.put(KEY_FRI_MORNING, pref.getString(KEY_FRI_MORNING, ""));
        schedule.put(KEY_FRI_EVENING, pref.getString(KEY_FRI_EVENING, ""));
        schedule.put(KEY_SAT_MORNING, pref.getString(KEY_SAT_MORNING, ""));
        schedule.put(KEY_SAT_EVENING, pref.getString(KEY_SAT_EVENING, ""));
        schedule.put(KEY_SUN_MORNING, pref.getString(KEY_SUN_MORNING, ""));
        schedule.put(KEY_SUN_EVENING, pref.getString(KEY_SUN_EVENING, ""));
        schedule.put(KEY_TIME1, pref.getString(KEY_TIME1, ""));
        schedule.put(KEY_TIME2, pref.getString(KEY_TIME2, ""));
        schedule.put(KEY_TIME3, pref.getString(KEY_TIME3, ""));
        schedule.put(KEY_TIME4, pref.getString(KEY_TIME4, ""));
        schedule.put(KEY_TIME5, pref.getString(KEY_TIME5, ""));
        schedule.put(KEY_GROUP1, pref.getString(KEY_GROUP1, ""));
        schedule.put(KEY_GROUP2, pref.getString(KEY_GROUP2, ""));
        schedule.put(KEY_GROUP3, pref.getString(KEY_GROUP3, ""));
        schedule.put(KEY_GROUP4, pref.getString(KEY_GROUP4, ""));
        schedule.put(KEY_GROUP5, pref.getString(KEY_GROUP5, ""));

        return schedule;
    }

    public void saveDayCount(String monMorning, String monEvening,String tueMorning, String tueEvening,String wedMorning, String wedEvening,
                             String thuMorning, String thuEvening,String friMorning, String friEvening,String satMorning, String satEvening,
                             String sunMorning, String sunEvening){
        editor.putString(KEY_MON_MORNING   ,      monMorning);
        editor.putString(KEY_MON_EVENING   ,      monEvening);
        editor.putString(KEY_TUE_MORNING   ,      tueMorning);
        editor.putString(KEY_TUE_EVENING   ,      tueEvening);
        editor.putString(KEY_WED_MORNING   ,      wedMorning);
        editor.putString(KEY_WED_EVENING   ,      wedEvening);
        editor.putString(KEY_THU_MORNING   ,      thuMorning);
        editor.putString(KEY_THU_EVENING   ,      thuEvening);
        editor.putString(KEY_FRI_MORNING   ,      friMorning);
        editor.putString(KEY_FRI_EVENING   ,      friEvening);
        editor.putString(KEY_SAT_MORNING   ,      satMorning);
        editor.putString(KEY_SAT_EVENING   ,      satEvening);
        editor.putString(KEY_SUN_MORNING   ,      sunMorning);
        editor.putString(KEY_SUN_EVENING   ,      sunEvening);


        editor.commit();
    }

    public void saveQrIkNumber(String ikNumber){
        editor.putString(KEY_MEMBER_QR_IK,  ikNumber);

        editor.commit();
    }

    public void saveGroup(String groupIndex,String groupString){
        editor.putString("KEY_GROUP"+ groupIndex,  groupString);

        editor.commit();
    }

    public void saveTime(String groupIndex,String groupTime){
        editor.putString("KEY_TIME"+ groupIndex,  groupTime);

        editor.commit();
    }

    public void saveCountLimit(String count){
        editor.putString(KEY_COUNT_LIMIT,  count);

        editor.commit();
    }



    public void saveDistanceX1(String distance_X1){
        editor.putString(KEY_DISTANCE_X1,   distance_X1);

        editor.commit();

    }

    public void saveDistanceX2(String distance_X2){
        editor.putString(KEY_DISTANCE_X2,   distance_X2);

        editor.commit();

    }

    public void saveDistanceY1(String distance_Y1){
        editor.putString(KEY_DISTANCE_Y1,   distance_Y1);

        editor.commit();

    }

    public void saveDistanceY2(String distance_Y2){
        editor.putString(KEY_DISTANCE_Y2,   distance_Y2);

        editor.commit();

    }

    public void saveDistanceZ1(String distance_Z1){

        editor.putString(KEY_DISTANCE_Z1,   distance_Z1);

        editor.commit();

    }

    public void saveDistanceZ2(String distance_Z2){

        editor.putString(KEY_DISTANCE_Z2,   distance_Z2);

        editor.commit();

    }

    public void saveLgaFlag(String lga_edit_flag){

        editor.putString(KEY_LGA_EDIT_FLAG,   lga_edit_flag);

        editor.commit();

    }

    public String getUserLat(){
        String user_lat = pref.getString(KEY_USER_LAT, "");
        return user_lat;
    }

    public String getUserLong(){
        String user_long = pref.getString(KEY_USER_LONG, "");
        return user_long;
    }

    public String getImportFlag(){
        String user_long = pref.getString(KEY_IMPORT_FLAG,"0");
        return user_long;
    }

    public void setImportFlag(String flag){
        editor.putString(KEY_IMPORT_FLAG,flag);
        editor.commit();
    }








}



