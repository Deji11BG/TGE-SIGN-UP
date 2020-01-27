package com.example.tgesign_up.Database.TFM.Table;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_PROSPECTIVE_TGL)

public class prospectiveTGLTable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)//
    @NonNull
    private String unique_member_id;

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

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE)//
    private String template;

    @ColumnInfo(name = TFMDBContractClass.COL_ROLE)//
    private String role;
    @ColumnInfo(name = TFMDBContractClass.COL_REG_DATE)//
    private String regdate;

    @ColumnInfo(name = TFMDBContractClass.COL_SYNC_FLAG)//
    private String sync_flag;

    @ColumnInfo(name = TFMDBContractClass.ACTIVE_FLAG)//
    private String active_flag;

    public prospectiveTGLTable(@NonNull String unique_member_id, String unique_ik_number, String ik_number,
                               String member_id, String first_name, String middle_name, String last_name,
                               String role, String template, String regdate,String active_flag,String sync_flag) {
        this.unique_member_id = unique_member_id;
        this.member_id = member_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.ik_number=ik_number;
        this.role = role;
        this.template = template;
        this.regdate = regdate;
        this.active_flag=active_flag;
        this.sync_flag=sync_flag;
    }

    public prospectiveTGLTable(){

    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
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

    public void setSync_flag(String regdate) {
        this.sync_flag = sync_flag;
    }

    public String getActive_flag() {
        return active_flag;
    }

    public void setActive_flag(String active_flag) {
        this.active_flag = active_flag;
    }

    public static class prospectiveTGLTableRecycler{

        private String ik_number;

        private String member_id;

        private String first_name;

        private String last_name;

        private String unique_member_id;

        public prospectiveTGLTableRecycler() {
        }

        public String getUnique_member_id() {
            return unique_member_id;
        }

        public void setUnique_member_id(String unique_member_id) {
            this.unique_member_id = unique_member_id;
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

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }
    }

}

