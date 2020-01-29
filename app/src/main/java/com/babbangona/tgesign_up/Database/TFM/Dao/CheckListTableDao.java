package com.babbangona.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.Table.CheckListTable;

import java.util.List;

@Dao
public interface CheckListTableDao {

    @Query("SELECT id, question_id, question, language_id FROM " + TFMDBContractClass.TABLE_CHECK_LIST + " WHERE language_id = :language_id" )
    List<CheckListTable> getAllCheckList(String language_id);

    @Query("SELECT question_id FROM " + TFMDBContractClass.TABLE_CHECK_LIST +" WHERE question = :question AND language_id = :language_id")
    String getCheckListQuestionId(String question, String language_id);

    @Query("SELECT question_id FROM " +
            TFMDBContractClass.TABLE_CHECK_LIST +
            " WHERE question != :upfront_deposit AND question != :upfront_deposit_receipt AND language_id = :language_id")
    List<String> getAllCheckListQuestionId(String upfront_deposit, String upfront_deposit_receipt, String language_id);

    /**
     * Insert the object in database
     * @param checkListTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CheckListTable checkListTable);

    /**
     * Insert the object in database
     * @param checkListTableList, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<CheckListTable> checkListTableList);

    /**
     * update the object in database
     * @param checkListTable, object to be updated
     */
    @Update
    void update(CheckListTable checkListTable);

    /**
     * delete the object from database
     * @param checkListTable, object to be deleted
     */
    @Delete
    void delete(CheckListTable checkListTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(CheckListTable... data);      // data... is varargs, here data is an array
}
