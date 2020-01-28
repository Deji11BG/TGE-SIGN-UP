package com.example.tgesign_up.Api;


import android.content.Context;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*This class captures the needed details from the app that is needed in separate activities*/

public class SharedPreference {
    // Shared Preferences
    android.content.SharedPreferences pref;

    // Editor for Shared preferences
    android.content.SharedPreferences.Editor editor;

    // Context
    Context context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "TGE_Pref";

    // Email address (make variable public to access from outside)
    public static final String KEY_STAFF_NAME  = "staffname";

    // User name (make variable public to access from outside)
    public static final String KEY_STAFF_ID = "staffid";

    public static final String KEY_LAST_SYNCED_TIME = "last_sync_time";
    public static final String KEY_LAST_SYNCED_TIME_OLD_RECORD = "last_sync_time_old_record";
    public static final String KEY_SEASON_ID = "season_id";
    public static final String KEY_IK_NUMBER = "ik_number";
    public static final String KEY_MEMBER_ID = "member_id";
    public static final String KEY_UNIQUE_MEMBER_ID = "unique_member_id";
    public static final String KEY_TEMPLATE = "template";
    public static final String KEY_SYNC_UP_TIME = "sync_up_time";
    public static final String KEY_REFERRAL_SYNC_UP_TIME = "referral_sync_up_time";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_GPS_TIME = "GPSTime";

    public static final String KEY_UNIQUE_IK_NUMBER = "unique_ik_number";
    public static final String KEY_FIRST_NAME = "first_name";
    public static final String KEY_LAST_NAME = "last_name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_AGE = "age";
    public static final String KEY_SEX = "sex";
    public static final String KEY_CROP_TYPE = "crop_type";
    public static final String KEY_MEMBER_ROLE = "member_role";
    public static final String KEY_MEMBER_BIRTHDAY = "member_birthday";
    public static final String KEY_ROLE_TO_REGISTER_FOR = "role_to_register_for";
    public static final String KEY_REGISTRATION_ACTION = "registration_action";
    public static final String KEY_MEMBER_PICTURE = "member_bitmap";
    public static final String KEY_MEMBER_PICTURE_LARGE = "member_bitmap_large";
    public static final String KEY_MEMBER_PROGRAM = "member_program";
    public static final String KEY_PASS_AUTHENTICATION = "pass_authentication";
    public static final String KEY_CHECKLIST_ARRAY = "check_list_array";
    public static final String KEY_STAFF_SYNC_TIME = "staff_sync_time";
    public static final String KEY_UNIQUE_ID_FIELD_MAPPING = "unique_id_for_field_mapping";
    public static final String KEY_BUNDLED_TEMPLATE = "bundled_template";
    public static final String KEY_PHONE_IMEI = "imei";
    public static final String KEY_QR_IK_NUMBER = "qr_ik_number";
    public static final String KEY_TRAINING_WARD = "training_ward";
    public static final String KEY_FILTER_HUB = "filter_hub";
    public static final String KEY_LAST_SYNC_UP_TIME_TFM = "last_sync_up_time_tfm";
    public static final String KEY_LAST_SYNC_UP_TIME_TGE = "last_sync_up_time_tge";
    public static final String KEY_LAST_SYNC_TIME_TGE = "last_sync_down_time_tge";
    public static final String KEY_LAST_SYNC_TIME_TGL = "last_sync_down_time_tgl";

    public static final String KEY_ROLE = "role";

    public void coordinates(String latitude, String longitude, String GPSTime){
        // Storing username in pref
        editor.putString(KEY_LATITUDE, latitude);

        editor.putString(KEY_LONGITUDE, longitude);


        editor.putString(KEY_GPS_TIME, GPSTime);


        // commit changes
        editor.commit();
    }



    // Constructor
    public SharedPreference(Context context){
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreference() {
    }

    public void setKeyLastSyncTimeTge(String last_sync_down_time_tge){
        // Storing name in pref
        editor.putString(KEY_LAST_SYNC_TIME_TGE, last_sync_down_time_tge);

        // commit changes
        editor.commit();
    }

    public void setKeyLastSyncTimeTgl(String last_sync_up_time_tge){
        // Storing name in pref
        editor.putString(KEY_LAST_SYNC_TIME_TGL, last_sync_up_time_tge);

        // commit changes
        editor.commit();
    }

    public void setKeyLastSyncUpTimeTge(String last_sync_up_time_tge){
        // Storing name in pref
        editor.putString(KEY_LAST_SYNC_UP_TIME_TGE, last_sync_up_time_tge);

        // commit changes
        editor.commit();
    }

    public void setKeyLastSyncUpTimeTfm(String last_sync_up_time_tfm){
        // Storing name in pref
        editor.putString(KEY_LAST_SYNC_UP_TIME_TFM, last_sync_up_time_tfm);

        // commit changes
        editor.commit();
    }

    public void setKeyFilterHub(String filter_hub){
        // Storing name in pref
        editor.putString(KEY_FILTER_HUB, filter_hub);

        // commit changes
        editor.commit();
    }

    public void setKeyTrainingWard(String training_ward){
        // Storing name in pref
        editor.putString(KEY_TRAINING_WARD, training_ward);

        // commit changes
        editor.commit();
    }

    public void setKeyPhoneImei(String verify_activity_result){
        // Storing name in pref
        editor.putString(KEY_PHONE_IMEI, verify_activity_result);

        // commit changes
        editor.commit();
    }

    public void setKeyQrIkNumber(String qr_ik_number){
        // Storing name in pref
        editor.putString(KEY_QR_IK_NUMBER, qr_ik_number);

        // commit changes
        editor.commit();
    }

    public void setKeyMemberPictureLarge(String member_bitmap_large){
        // Storing name in pref
        editor.putString(KEY_MEMBER_PICTURE_LARGE, member_bitmap_large);

        // commit changes
        editor.commit();
    }

    public void setKeyBundledTemplate(String bundled_template){
        // Storing name in pref
        editor.putString(KEY_BUNDLED_TEMPLATE, bundled_template);

        // commit changes
        editor.commit();
    }

    public void setKeyUniqueIdFieldMapping(String unique_id_for_field_mapping){
        // Storing name in pref
        editor.putString(KEY_UNIQUE_ID_FIELD_MAPPING, unique_id_for_field_mapping);

        // commit changes
        editor.commit();
    }

    public void setKeyStaffSyncTime(String staff_sync_time){
        // Storing name in pref
        editor.putString(KEY_STAFF_SYNC_TIME, staff_sync_time);

        // commit changes
        editor.commit();
    }

    public void setKeyChecklistArray(Set<String> check_list_array){
        // Storing name in pref
        editor.putStringSet(KEY_CHECKLIST_ARRAY, check_list_array);

        // commit changes
        editor.commit();
    }

    public void setKeyPassAuthentication(String pass_authentication){
        // Storing name in pref
        editor.putString(KEY_PASS_AUTHENTICATION, pass_authentication);

        // commit changes
        editor.commit();
    }

    public void setKeyMemberProgram(String member_program){
        // Storing name in pref
        editor.putString(KEY_MEMBER_PROGRAM, member_program);

        // commit changes
        editor.commit();
    }

    public void setKeyMemberPicture(String member_bitmap){
        // Storing name in pref
        editor.putString(KEY_MEMBER_PICTURE, member_bitmap);

        // commit changes
        editor.commit();
    }

    public void setKeyRegistrationAction(String registration_action){
        // Storing name in pref
        editor.putString(KEY_REGISTRATION_ACTION, registration_action);

        // commit changes
        editor.commit();
    }

    public void setKeyRoleToRegisterFor(String role_to_register_for){
        // Storing name in pref
        editor.putString(KEY_ROLE_TO_REGISTER_FOR, role_to_register_for);

        // commit changes
        editor.commit();
    }

    public void setKeyMemberBirthday(String member_birthday){
        // Storing name in pref
        editor.putString(KEY_MEMBER_BIRTHDAY, member_birthday);

        // commit changes
        editor.commit();
    }

    public void setKeyMemberRole(String member_role){
        // Storing name in pref
        editor.putString(KEY_MEMBER_ROLE, member_role);

        // commit changes
        editor.commit();
    }

    public void setKeyCropType(String crop_type){
        // Storing name in pref
        editor.putString(KEY_CROP_TYPE, crop_type);

        // commit changes
        editor.commit();
    }

    public void setKeySex(String sex){
        // Storing name in pref
        editor.putString(KEY_SEX, sex);

        // commit changes
        editor.commit();
    }

    public void setKeyAge(String age){
        // Storing name in pref
        editor.putString(KEY_AGE, age);

        // commit changes
        editor.commit();
    }

    public void setKeyPhoneNumber(String phone_number){
        // Storing name in pref
        editor.putString(KEY_PHONE_NUMBER, phone_number);

        // commit changes
        editor.commit();
    }

    public void setKeyLastName(String last_name){
        // Storing name in pref
        editor.putString(KEY_LAST_NAME, last_name);

        // commit changes
        editor.commit();
    }

    public void setKeyFirstName(String first_name){
        // Storing name in pref
        editor.putString(KEY_FIRST_NAME, first_name);

        // commit changes
        editor.commit();
    }

    public void setUniqueIkNumber(String unique_ik_number){
        // Storing name in pref
        editor.putString(KEY_UNIQUE_IK_NUMBER, unique_ik_number);

        // commit changes
        editor.commit();
    }

    public void setTemplate(String template){
        // Storing name in pref
        editor.putString(KEY_TEMPLATE, template);

        // commit changes
        editor.commit();
    }

    public void setIKNumber(String ik_number){
        // Storing name in pref
        editor.putString(KEY_IK_NUMBER, ik_number);

        // commit changes
        editor.commit();
    }

    public void setUniqueMemberId(String unique_member_id){
        // Storing name in pref
        editor.putString(KEY_UNIQUE_MEMBER_ID, unique_member_id);

        // commit changes
        editor.commit();
    }

    public String getKeyActivityResult(){
        return pref.getString(KEY_PHONE_IMEI,"Nothing_yet");
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        // user name
        user.put(KEY_STAFF_NAME, pref.getString(KEY_STAFF_NAME, null));

        // user email id
        user.put(KEY_STAFF_ID, pref.getString(KEY_STAFF_ID, "T-10000000000000AA"));

        user.put(KEY_ROLE,pref.getString(KEY_ROLE,"ES"));

        user.put(KEY_LAST_SYNCED_TIME,pref.getString(KEY_LAST_SYNCED_TIME,"2019-07-21 00:00:00"));
        user.put(KEY_SYNC_UP_TIME,pref.getString(KEY_SYNC_UP_TIME,"0000-00-00 00:00:00"));
        user.put(KEY_REFERRAL_SYNC_UP_TIME,pref.getString(KEY_REFERRAL_SYNC_UP_TIME,"0000-00-00 00:00:00"));
        user.put(KEY_LAST_SYNCED_TIME_OLD_RECORD,pref.getString(KEY_SYNC_UP_TIME,"2019-07-21 00:00:00"));

        user.put(KEY_SEASON_ID,pref.getString(KEY_SEASON_ID,"R19"));

        user.put(KEY_IK_NUMBER,pref.getString(KEY_IK_NUMBER,"IKXXXXXX"));

        user.put(KEY_UNIQUE_IK_NUMBER,pref.getString(KEY_UNIQUE_IK_NUMBER,"IKXXXXXX"));
        user.put(KEY_FIRST_NAME,pref.getString(KEY_FIRST_NAME,"Babban"));
        user.put(KEY_LAST_NAME,pref.getString(KEY_LAST_NAME,"Gona"));
        user.put(KEY_PHONE_NUMBER,pref.getString(KEY_PHONE_NUMBER,"0X123456789"));
        user.put(KEY_AGE,pref.getString(KEY_AGE,"156"));
        user.put(KEY_SEX,pref.getString(KEY_SEX,"Trans"));
        user.put(KEY_CROP_TYPE,pref.getString(KEY_CROP_TYPE,"Maize"));
        user.put(KEY_MEMBER_ROLE,pref.getString(KEY_MEMBER_ROLE,"Leader"));
        user.put(KEY_MEMBER_BIRTHDAY,pref.getString(KEY_MEMBER_BIRTHDAY,"21/6/1995"));
        user.put(KEY_REGISTRATION_ACTION,pref.getString(KEY_REGISTRATION_ACTION,"edit"));

        user.put(KEY_MEMBER_ID,pref.getString(KEY_MEMBER_ID,"1"));
        user.put(KEY_UNIQUE_MEMBER_ID,pref.getString(KEY_UNIQUE_MEMBER_ID,"10001"));
        user.put(KEY_TEMPLATE,pref.getString(KEY_TEMPLATE,"FOUR"));
        user.put(KEY_LATITUDE,pref.getString(KEY_LATITUDE,"0.00"));
        user.put(KEY_LONGITUDE,pref.getString(KEY_LONGITUDE,"0.00"));
        user.put(KEY_GPS_TIME,pref.getString(KEY_GPS_TIME,"0000-00-00 00:00:00"));
        user.put(KEY_ROLE_TO_REGISTER_FOR,pref.getString(KEY_ROLE_TO_REGISTER_FOR,"Member"));
        user.put(KEY_MEMBER_PICTURE,pref.getString(KEY_MEMBER_PICTURE,"Member"));
        user.put(KEY_MEMBER_PICTURE_LARGE,pref.getString(KEY_MEMBER_PICTURE_LARGE,"Member_large"));
        user.put(KEY_MEMBER_PROGRAM,pref.getString(KEY_MEMBER_PROGRAM,"Dami"));
        user.put(KEY_PASS_AUTHENTICATION,pref.getString(KEY_PASS_AUTHENTICATION,"0"));
        user.put(KEY_STAFF_SYNC_TIME,pref.getString(KEY_STAFF_SYNC_TIME,""));
        user.put(KEY_UNIQUE_ID_FIELD_MAPPING,pref.getString(KEY_UNIQUE_ID_FIELD_MAPPING,"T-10000000000000AA_20191127113805_m"));
        user.put(KEY_BUNDLED_TEMPLATE,pref.getString(KEY_BUNDLED_TEMPLATE,"FOUR"));
        user.put(KEY_QR_IK_NUMBER,pref.getString(KEY_QR_IK_NUMBER,"IKXXXXXXXX"));
        user.put(KEY_TRAINING_WARD,pref.getString(KEY_TRAINING_WARD,"Nothing Selected"));
        user.put(KEY_FILTER_HUB,pref.getString(KEY_FILTER_HUB,"Lekki"));
        user.put(KEY_LAST_SYNC_UP_TIME_TFM,pref.getString(KEY_LAST_SYNC_UP_TIME_TFM,"2019-01-01"));
        user.put(KEY_LAST_SYNC_UP_TIME_TGE,pref.getString(KEY_LAST_SYNC_UP_TIME_TGE,"2019-01-01"));
        user.put(KEY_LAST_SYNC_TIME_TGE,pref.getString(KEY_LAST_SYNC_TIME_TGE,"2019-01-01"));
        user.put(KEY_LAST_SYNC_TIME_TGL,pref.getString(KEY_LAST_SYNC_TIME_TGL,"2019-01-01"));

        // return user
        return user;
    }


}