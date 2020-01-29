package com.babbangona.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_NEW_MEMBERS_DATA)

public class MembersTable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)
    @NonNull
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_IK_NUMBER)
    private String unique_ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_IK_NUMBER)
    private String ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_ID)
    private int member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_MIDDLE_NAME)
    private String middle_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)
    private String last_name;

    @ColumnInfo(name = TFMDBContractClass.COL_PHONE_NUMBER)
    private String phone_number;

    @ColumnInfo(name = TFMDBContractClass.COL_DATE_OF_BIRTH)
    private String date_of_birth;

    @ColumnInfo(name = TFMDBContractClass.COL_SEX)
    private String sex;

    @ColumnInfo(name = TFMDBContractClass.COL_CROP_TYPE)
    private String crop_type;

    @ColumnInfo(name = TFMDBContractClass.COL_STATE_ID)
    private String state_id;

    @ColumnInfo(name = TFMDBContractClass.COL_LGA_ID)
    private String lga_id;

    @ColumnInfo(name = TFMDBContractClass.COL_WARD_ID)
    private String ward_id;

    @ColumnInfo(name = TFMDBContractClass.COL_VILLAGE_NAME)
    private String village_name;

    @ColumnInfo(name = TFMDBContractClass.COL_ROLE)
    private String role;

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE)
    private String template;

    @ColumnInfo(name = TFMDBContractClass.COL_REG_DATE)
    private String regdate;

    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)
    private String sync_flag;

    @ColumnInfo(name = TFMDBContractClass.COL_DEACTIVATE_MEMBER)
    private String deactivate_flag;

    @ColumnInfo(name = TFMDBContractClass.COL_DELETE_MEMBER)
    private String delete_flag;

    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    private String staff_id;

    @ColumnInfo(name = TFMDBContractClass.COL_OTHER_CROPS)
    private String other_crops;

    @ColumnInfo(name = TFMDBContractClass.COL_OTHER_NOT_LISTED_CROPS)
    private String other_not_listed_crops;

    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_PROGRAM)
    private String member_program;

    @ColumnInfo(name = TFMDBContractClass.COL_PASS_VERIFICATION)
    private String pass_verification;

    @ColumnInfo(name = TFMDBContractClass.COL_LATITUDE)
    private String latitude;

    @ColumnInfo(name = TFMDBContractClass.COL_LONGITUDE)
    private String longitude;

    @ColumnInfo(name = TFMDBContractClass.TGE_ID)
    private String tge_id;

    @ColumnInfo(name = TFMDBContractClass.COL_IMEI)
    private String imei;

    public MembersTable(@NonNull String unique_member_id, String unique_ik_number, String ik_number,
                        int member_id, String first_name, String middle_name, String last_name,
                        String phone_number, String date_of_birth, String sex, String crop_type,
                        String state_id, String lga_id, String ward_id, String village_name, String role,
                        String template, String regdate, String sync_flag, String deactivate_flag,
                        String delete_flag, String staff_id, String other_crops, String other_not_listed_crops,
                        String member_program, String pass_verification, String latitude, String longitude,
                        String tge_id, String imei) {
        this.unique_member_id = unique_member_id;
        this.unique_ik_number = unique_ik_number;
        this.ik_number = ik_number;
        this.member_id = member_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.crop_type = crop_type;
        this.state_id = state_id;
        this.lga_id = lga_id;
        this.ward_id = ward_id;
        this.village_name = village_name;
        this.role = role;
        this.template = template;
        this.regdate = regdate;
        this.sync_flag = sync_flag;
        this.deactivate_flag = deactivate_flag;
        this.delete_flag = delete_flag;
        this.staff_id = staff_id;
        this.other_crops = other_crops;
        this.other_not_listed_crops = other_not_listed_crops;
        this.member_program = member_program;
        this.pass_verification = pass_verification;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tge_id = tge_id;
        this.imei = imei;
    }

    public MembersTable(){

    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTge_id() {
        return tge_id;
    }

    public void setTge_id(String tge_id) {
        this.tge_id = tge_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPass_verification() {
        return pass_verification;
    }

    public void setPass_verification(String pass_verification) {
        this.pass_verification = pass_verification;
    }

    public String getMember_program() {
        return member_program;
    }

    public void setMember_program(String member_program) {
        this.member_program = member_program;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getOther_crops() {
        return other_crops;
    }

    public void setOther_crops(String other_crops) {
        this.other_crops = other_crops;
    }

    public String getOther_not_listed_crops() {
        return other_not_listed_crops;
    }

    public void setOther_not_listed_crops(String other_not_listed_crops) {
        this.other_not_listed_crops = other_not_listed_crops;
    }

    public String getCrop_type() {
        return crop_type;
    }

    public void setCrop_type(String crop_type) {
        this.crop_type = crop_type;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getLga_id() {
        return lga_id;
    }

    public void setLga_id(String lga_id) {
        this.lga_id = lga_id;
    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getUnique_ik_number() {
        return unique_ik_number;
    }

    public void setUnique_ik_number(String unique_ik_number) {
        this.unique_ik_number = unique_ik_number;
    }

    public String getIk_number() {
        return ik_number;
    }

    public void setIk_number(String ik_number) {
        this.ik_number = ik_number;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getSync_flag() {
        return sync_flag;
    }

    public void setSync_flag(String sync_flag) {
        this.sync_flag = sync_flag;
    }

    public String getDeactivate_flag() {
        return deactivate_flag;
    }

    public void setDeactivate_flag(String deactivate_flag) {
        this.deactivate_flag = deactivate_flag;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

}
