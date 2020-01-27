package com.example.tgesign_up.Database.SharedPreferences;


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
    private static final String PREF_NAME      = "AndroidHivePref";

    public static final String KEY_APP_LANG = "app_lang";

    public static final String KEY_STAFF_NAME  = "staff_name";
    public static final String KEY_STAFF_ID    = "staff_id";
    public static final String KEY_STAFF_ROLE  = "staff_role";
    public static final String KEY_IMPORT_FLAG = "import_flag";

    /**
     * Keys below are for holding data from member information temporarily
     * */

    public static final String KEY_FIRST_NAME   = "first_name";
    public static final String KEY_LAST_NAME    = "last_name";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_AGE          = "age";
    public static final String KEY_SEX          = "sex";
    public static final String KEY_MEMBER_ROLE  = "member_role";
    public static final String KEY_CROP_TYPE    = "crop_type";


    /**
     * Keys below are for holding data from member location temporarily
     * */

    public static final String KEY_STATE        = "state";
    public static final String KEY_LGA          = "lga";
    public static final String KEY_WARD         = "ward";
    public static final String KEY_VILLAGE      = "village";
    public static final String KEY_REFERRAL      = "referral_code";


    public static final String KEY_TEST_ID        = "test_id";
    public static final String KEY_LONGITUDE      = "longitude";
    public static final String KEY_LATITUDE       = "latitude";

    public static final String KEY_QUESTION_INDEX = "question_index";
    public static final String KEY_HOLD_ANSWERS   = "answers_list";

    public static final String KEY_QUESTION_ID = "question_id";
    public static final String KEY_QUESTION_WEIGHT   = "question_weight";

    public static final String KEY_ANSWER_ID = "answer_id";
    public static final String KEY_ANSWER_POINT   = "answer_point";

    public static final String KEY_SELECTED_ANSWER_INDEX = "selected_answer_index";

    public static final String KEY_INTERVIEW_SCORE = "sum_interview_score";

    public static final String KEY_TEMPLATE = "template";

    public static final String KEY_LAST_SYNC_TIME = "last_sync_time";

    public static final String KEY_TG_IK_NUMBER = "tg_ik_number";

    public static final String KEY_TG_UNIQUE_MEMBER_ID = "unique_member_id";


   // public static final String KEY_VILLAGE = "sum_interview_score";

    /**
     * Shared Preferences for Onboarding and Landing page
     **/
    public static final String KEY_LANDING_IMPORT = "landing_import";
    public static final String KEY_APPS_LIST_LAST_SYNC_DOWN = "apps_list_last_sync_down";
    public static final String KEY_ONBOARDING_STATUS = "onboarding_status";

/*shared prefs for schedule sessions

 */
    public static final String SLOT_ID = "slot_id";
    public static final String FIRST_DAY = "first_day";
    public static final String SECOND_DAY = "second_day";
    public static final String FIRST_TIME = "first_time";
    public static final String SECOND_TIME = "second_time";
    //public static final String SCHEDUL = "second_time";
    public static final String SCHEDULE_COUNT = "shedule_count";


    // Constructor
    public SharedPreferenceController(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferenceController() {

    }

    public void HoldMemberInfo(String first_name, String last_name, String phone_number,
                               String age, String sex, String role, String crop_type){
        editor.putString(KEY_FIRST_NAME,   first_name);
        editor.putString(KEY_LAST_NAME,    last_name);
        editor.putString(KEY_PHONE_NUMBER, phone_number);
        editor.putString(KEY_AGE,          age);
        editor.putString(KEY_SEX,          sex);
        editor.putString(KEY_CROP_TYPE,    crop_type);

        editor.commit();

    }

    public void HoldMemberLoc(String state,String lga, String ward, String village, String referral_code){
        editor.putString(KEY_STATE,      state);
        editor.putString(KEY_LGA,        lga);
        editor.putString(KEY_WARD,       ward);
        editor.putString(KEY_VILLAGE,    village);
        editor.putString(KEY_REFERRAL,    referral_code);
        editor.commit();
    }

    public void CreateLoginSession(String staffname, String staffid, String staff_role){
        // Storing username in pref
        editor.putString(KEY_STAFF_NAME, staffname);
        // Storing role in pref
        editor.putString(KEY_STAFF_ID, staffid);
        editor.putString(KEY_STAFF_ROLE, staff_role);

        // commit changes
        editor.commit();
    }

    //use this to save ik number of the leader that you selected on the tfm home page
    public void saveIkNumber(String ikNumber){
        editor.putString(KEY_TG_IK_NUMBER,ikNumber);
        editor.commit();
    }

    //use this to save  unique member id of the member that you selected on the tg members page
    public void saveUniqueMemberId(String memberId){
        editor.putString(KEY_TG_UNIQUE_MEMBER_ID,memberId);
        editor.commit();
    }

    public void setImportFlag(String flag){
        editor.putString(KEY_IMPORT_FLAG,flag);
        editor.commit();
    }

    public void setTestId(String testId){
        editor.putString(KEY_TEST_ID,testId);
        editor.commit();
    }

    public void setKeyLastSyncTime(String lastSyncTime){
        editor.putString(KEY_LAST_SYNC_TIME,lastSyncTime);
        editor.commit();
    }

    public void HoldCordinates(String latitude, String longitude){
        editor.putString(KEY_LATITUDE,latitude);
        editor.putString(KEY_LONGITUDE,longitude);
        editor.commit();


    }

    public void HoldQuestionNo(String quesNos){
        editor.putString(KEY_QUESTION_INDEX,quesNos);
        editor.commit();
    }

    public void saveQuestionAndWeight(String quesNos, String weight){
        editor.putString(KEY_QUESTION_ID,quesNos);
        editor.putString(KEY_QUESTION_WEIGHT,weight);
        editor.commit();
    }

    public void saveAnswerAndPoint(String answer, String point){
        editor.putString(KEY_ANSWER_ID,answer);
        editor.putString(KEY_ANSWER_POINT,point);
        editor.commit();
    }

    public void saveWard(String ward){
        editor.putString(KEY_WARD,ward);
        editor.commit();
    }


    public void saveVillage(String village){
        editor.putString(KEY_VILLAGE,village);
        editor.commit();
    }


    public void saveRefCode(String referral_code){
        editor.putString(KEY_REFERRAL, referral_code);
        editor.commit();
    }

    public void saveSelectedAnserIndex(String answer){
        editor.putString(KEY_SELECTED_ANSWER_INDEX,answer);
        editor.commit();
    }

    public void updateInterviewScore(String interviewScore){
        editor.putString(KEY_INTERVIEW_SCORE,interviewScore);
        editor.commit();		        editor.commit();
    }

    public void setLandingImport(Boolean value){
        editor.putBoolean(KEY_LANDING_IMPORT, value);
        editor.commit();
    }

    public void setAppsListLastSync(String value){
        editor.putString(KEY_APPS_LIST_LAST_SYNC_DOWN, value);
        editor.commit();
    }

    public void setOnboardingStatus(Boolean value){
        editor.putBoolean(KEY_ONBOARDING_STATUS, value);
        editor.commit();
    }

    public void setAppLanguage(String value){
        editor.putString(KEY_APP_LANG, value);
        editor.commit();
    }

    public Boolean getLandingImport(){
        return pref.getBoolean(KEY_LANDING_IMPORT, false);
    }

    public String getInterviewScore(){
        String score = pref.getString(KEY_INTERVIEW_SCORE, "");
        return score;
    }

    public String getKeyLastSyncTime(){
        String last_sync_time = pref.getString(KEY_LAST_SYNC_TIME, "");
        return last_sync_time;
    }

    public String getSelectedAnswerIndex(){
        String name = pref.getString(KEY_SELECTED_ANSWER_INDEX, "");
        return name;
    }

    public String getQuestionID(){
        String name = pref.getString(KEY_QUESTION_ID, "");
        return name;
    }

    public String getQuestionWeight(){
        String name = pref.getString(KEY_QUESTION_WEIGHT, "");
        return name;
    }

    public String getAnswerID(){
        String name = pref.getString(KEY_ANSWER_ID, "");
        return name;
    }

    public String getAnswerPoint(){
        String name = pref.getString(KEY_ANSWER_POINT, "0");
        return name;
    }

    public String getTestID(){
        String name = pref.getString(KEY_TEST_ID, "");
        return name;
    }

    public String getWard(){
        String ward = pref.getString(KEY_WARD, "");
        return ward;
    }

    public String getVillage(){
        String village = pref.getString(KEY_VILLAGE, "");
        return village;
    }

    public String getTemplate(){
        String template = pref.getString(KEY_TEMPLATE, "");
        return template;
    }

    public String getAppsListLastSync(){
        return pref.getString(KEY_APPS_LIST_LAST_SYNC_DOWN, "2019-12-22 12:00:00");
    }

    public Boolean getOnboardingStatus(){
        return pref.getBoolean(KEY_ONBOARDING_STATUS, false);
    }

    public String getRefCode(){
        String referral = pref.getString(KEY_REFERRAL, "");
        return referral;
    }

    public String getAppLanguage(){
        return pref.getString(KEY_APP_LANG, "en");
    }

    public void HoldAnswers(String answers){
        editor.putString(KEY_HOLD_ANSWERS, answers);
        editor.commit();
    }

//save schedule info
    public void scheduleInfo(String slotID,String firstDay,String secondDay,String
            firstTIme,String secondTime){
        editor.putString(SLOT_ID,slotID);
        editor.putString(FIRST_DAY,firstDay);
        editor.putString(SECOND_DAY,secondDay);
        editor.putString(FIRST_TIME,firstTIme);
        editor.putString(SECOND_TIME,secondTime);


        editor.commit();
    }
    public void schedulecount(String answers){
        editor.putString(SCHEDULE_COUNT, answers);
        editor.commit();
    }


    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_STAFF_NAME, pref.getString(KEY_STAFF_NAME, null));
        user.put(KEY_STAFF_ID, pref.getString(KEY_STAFF_ID, null));
        user.put(KEY_IMPORT_FLAG, pref.getString(KEY_IMPORT_FLAG, "0"));
        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, "0"));
        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, "0"));
        user.put(KEY_PHONE_NUMBER, pref.getString(KEY_PHONE_NUMBER, "0"));
        user.put(KEY_AGE, pref.getString(KEY_AGE, "0"));
        user.put(KEY_SEX, pref.getString(KEY_SEX, "0"));
        user.put(KEY_MEMBER_ROLE, pref.getString(KEY_MEMBER_ROLE, "0"));
        user.put(KEY_CROP_TYPE, pref.getString(KEY_CROP_TYPE, "0"));
        user.put(KEY_TEST_ID, pref.getString(KEY_TEST_ID, "0"));
        user.put(KEY_LATITUDE, pref.getString(KEY_LATITUDE, "0"));
        user.put(KEY_LONGITUDE, pref.getString(KEY_LONGITUDE, "0"));
        user.put(KEY_QUESTION_INDEX, pref.getString(KEY_QUESTION_INDEX, "0"));
        user.put(KEY_HOLD_ANSWERS, pref.getString(KEY_HOLD_ANSWERS, "[]"));


        return user;
    }

    //use this to get ik number of the leader that you selected on the tg search page
    public String getIkNumber(){
        String ikNumber = pref.getString(KEY_TG_IK_NUMBER, "");
        return ikNumber;
    }


    //use this to save  unique member id of the member that you selected on the tg members page
    public String getUniqueMemberId(){
        String memberId = pref.getString(KEY_TG_UNIQUE_MEMBER_ID, "");
        return memberId;
    }

    public String getSlotId() {
        String name = pref.getString(SLOT_ID, "");
        return name;
    }
    public String getFirstDay() {
        String name = pref.getString(FIRST_DAY, "");
        return name;
    }
    public String getSecondDay() {
        String name = pref.getString(SECOND_DAY, "");
        return name;
    }
    public String getFirstTime() {
        String name = pref.getString(FIRST_TIME, "");
        return name;
    }
    public String getSecondTime() {
        String name = pref.getString(SECOND_TIME, "");
        return name;
    }
    public String getScheduleCount() {
        String name = pref.getString(SCHEDULE_COUNT, "");
        return name;
    }
}



