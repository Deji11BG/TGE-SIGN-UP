package com.babbangona.tgesign_up.Database.TFM.Table;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;

@Entity(primaryKeys = {TFMDBContractClass.COL_ID},
        indices = {@Index(value = TFMDBContractClass.COL_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_CHECK_LIST)

public class CheckListTable {

    @ColumnInfo(name = TFMDBContractClass.COL_ID)
    @NonNull
    private String id;

    @ColumnInfo(name = TFMDBContractClass.COL_QUESTION_ID)
    private String question_id;

    @ColumnInfo(name = TFMDBContractClass.COL_QUESTION)
    private String question;

    @ColumnInfo(name = TFMDBContractClass.COL_LANGUAGE_ID)
    private String language_id;

    public CheckListTable(){

    }

    public CheckListTable(@NonNull String id, String question_id, String question, String language_id) {
        this.id = id;
        this.question_id = question_id;
        this.question = question;
        this.language_id = language_id;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getLanguage_id() {
        return language_id;
    }

    public void setLanguage_id(String language_id) {
        this.language_id = language_id;
    }
}
