package com.example.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_OLD_MEMBERS_DATA)

public class OldMembersTable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)//
    @NonNull
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_IK_NUMBER)//
    private String unique_ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_IK_NUMBER)
    private String ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_ID)
    private String member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)//
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_MIDDLE_NAME)
    private String middle_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)//
    private String last_name;

    @ColumnInfo(name = TFMDBContractClass.COL_DATE_OF_BIRTH)//yyyy-mm-dd
    private String date_of_birth;

    @ColumnInfo(name = TFMDBContractClass.COL_SEX)//
    private String sex;

    @ColumnInfo(name = TFMDBContractClass.COL_PHONE_NUMBER)//
    private String phone_number;

    @ColumnInfo(name = TFMDBContractClass.COL_WARD_ID)//
    private String ward_id;

    @ColumnInfo(name = TFMDBContractClass.COL_VILLAGE_NAME)//
    private String village_name;

    @ColumnInfo(name = TFMDBContractClass.COL_ROLE)//
    private String role;

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE)//
    private String template;

    @ColumnInfo(name = TFMDBContractClass.COL_REG_DATE)//
    private String regdate;

    @ColumnInfo(name = TFMDBContractClass.COL_LEVEL)
    private String level;

    @ColumnInfo(name = TFMDBContractClass.COL_SEASON_ID)//20
    private String season_id;

    @ColumnInfo(name = TFMDBContractClass.COL_CROP_TYPE)//
    private String crop_type;

    public OldMembersTable(@NonNull String unique_member_id, String unique_ik_number, String ik_number,
                           String member_id, String first_name, String middle_name, String last_name,
                           String date_of_birth, String sex, String phone_number, String ward_id,
                           String village_name, String role, String template, String regdate,
                           String level, String season_id, String crop_type) {
        this.unique_member_id = unique_member_id;
        this.unique_ik_number = unique_ik_number;
        this.ik_number = ik_number;
        this.member_id = member_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.sex = sex;
        this.phone_number = phone_number;
        this.ward_id = ward_id;
        this.village_name = village_name;
        this.role = role;
        this.template = template;
        this.regdate = regdate;
        this.level = level;
        this.season_id = season_id;
        this.crop_type = crop_type;
    }

    public OldMembersTable(){

    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getCrop_type() {
        return crop_type;
    }

    public void setCrop_type(String crop_type) {
        this.crop_type = crop_type;
    }

    public String getSeason_id() {
        return season_id;
    }

    public void setSeason_id(String season_id) {
        this.season_id = season_id;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
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

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
