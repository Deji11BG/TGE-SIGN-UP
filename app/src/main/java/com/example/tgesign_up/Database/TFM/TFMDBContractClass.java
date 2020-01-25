package com.example.tgesign_up.Database.TFM;

/**
 * This table contains all the strings for the database
 * */


public class TFMDBContractClass {

    public static final String TFM_DATABASE_NAME = "tfm";
    public static final int TFM_DATABASE_VERSION = 1;



    public static final String TABLE_NEW_MEMBERS_DATA           = "members";
    public static final String TABLE_OLD_MEMBERS_DATA           = "old_members";
    public static final String TABLE_TRUST_GROUP_DATA           = "trust_group";
    public static final String TFM_APP_VARIABLES                = "tfm_app_variables";
    public static final String TABLE_CHECK_LIST                 = "check_list";
    public static final String TABLE_LAST_SYNC                  = "last_sync_table";
    public static final String TABLE_NEW_TGE                    = "new_tge";
    public static final String TABLE_TFM_TEMPLATE_TRACKER       = "tfm_template_tracker";
    public static final String TABLE_PROSPECTIVE_TGE   = "prospectiveTGE";
    public static final String TABLE_PROSPECTIVE_TGL   = "prospectiveTGL";
    public static final String TABLE_SCHEDULE   = "schedule";

    /**
     * members data column names
     */
    public static final String COL_UNIQUE_MEMBER_ID             = "unique_member_id";
    public static final String COL_UNIQUE_IK_NUMBER             = "unique_ik_number";
    public static final String COL_MEMBER_ID                    = "member_id";
    public static final String COL_FIRST_NAME                   = "first_name";
    public static final String COL_MIDDLE_NAME                  = "middle_name";
    public static final String COL_LAST_NAME                    = "last_name";
    public static final String COL_DATE_OF_BIRTH                = "date_of_birth";
    public static final String COL_SEX                          = "sex";
    public static final String COL_CROP_TYPE                    = "crop_type";
    public static final String COL_PHONE_NUMBER                 = "phone_number";
    public static final String COL_STATE_ID                     = "state_id";
    public static final String COL_LGA_ID                       = "lga_id";
    public static final String COL_VILLAGE_NAME                 = "village_name";
    public static final String COL_WARD_ID                      = "ward_id";
    public static final String COL_ROLE                         = "role";
    public static final String COL_TEMPLATE                     = "template";
    public static final String COL_REG_DATE                     = "regdate";
    public static final String COL_SYNC_FLAG                    = "sync_flag";
    public static final String COL_DEACTIVATE_MEMBER            = "deactivate_flag";
    public static final String COL_DELETE_MEMBER                = "delete_flag";
    public static final String COL_OTHER_CROPS                  = "other_crops";
    public static final String COL_OTHER_NOT_LISTED_CROPS       = "other_not_listed_crops";
    public static final String COL_MEMBER_PROGRAM               = "member_program";
    public static final String COL_SEASON_ID                    = "season_id";
    public static final String COL_PASS_VERIFICATION            = "pass_verification";
    public static final String COL_LONGITUDE                    = "longitude";
    public static final String COL_LATITUDE                     = "latitude";
    public static final String ACTIVE_FLAG                     = "active_flag";


    /**
     * template tracker table
     */
    public static final String COL_TEMPLATE_INDEX     = "template_id";
    public static final String COL_TEMPLATE_TRACKER        = "template_tracker";

    /**
     * app_version_constants table
     */
    public static final String VARIABLE_ID                      = "variable_id";
    public static final String COL_KKG_MIN_OLD                  = "kkg_min_old";
    public static final String COL_KKG_MAX_OLD                  = "kkg_max_old";
    public static final String COL_KKG_MIN_NEW                  = "kkg_min_new";
    public static final String COL_KKG_MAX_NEW                  = "kkg_max_new";
    public static final String COL_MA_MIN_OLD                   = "ma_min_old";
    public static final String COL_MA_MAX_OLD                   = "ma_max_old";
    public static final String COL_MA_MIN_NEW                   = "ma_min_new";
    public static final String COL_MA_MAX_NEW                   = "ma_max_new";

    /**
     * last_sync table
     */
    public static final String COL_LAST_SYNC_DOWN_INPUT_RECORD    = "last_sync_down_input_record";
    public static final String COL_LAST_SYNC_DOWN_OUTPUT_RECORD   = "last_sync_down_output_record";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_1  = "last_sync_down_extra_record_1";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_2  = "last_sync_down_extra_record_2";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_3  = "last_sync_down_extra_record_3";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_4  = "last_sync_down_extra_record_4";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_5  = "last_sync_down_extra_record_5";
    public static final String COL_LAST_SYNC_DOWN_EXTRA_RECORD_6  = "last_sync_down_extra_record_6";

    /**
     * check_list table
     */
    public static final String COL_ID                           = "id";
    public static final String COL_QUESTION_ID                  = "question_id";
    public static final String COL_QUESTION                     = "question";
    public static final String COL_LANGUAGE_ID                  = "language_id";


    /**
     * old members column names
     */
    public static final String COL_IK_NUMBER                    = "ik_number";
    public static final String TGE_ID                    = "tge_id";


    /**
     * trustGroup column names
     */
    public static final String COL_STAFF_ID                     = "staff_id";
    public static final String COL_LEVEL                        = "level";
    public static final String COL_FINISHED_CHECKLIST           = "finished_checklist";

    /*schedule column names

     */
    public static final String WARD                     = "ward";
    public static final String SLOTID                        = "slot_id";
    public static final String FIRST_DAY           = "first_day";
    public static final String FIRST_TIME           = "first_time";
    public static final String SECOND_DAY           = "second_day";
    public static final String SECOND_TIME           = "second_time";
    public static final String SCHEDULE_SYNC_FLAG           = "schedule_sync_flag";
    public static final String SCHEDULE_COUNT           = "schedule_count";


}
