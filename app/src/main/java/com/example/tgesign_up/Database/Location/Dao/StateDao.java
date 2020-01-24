package com.example.tgesign_up.Database.Location.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tgesign_up.Database.DBContractClass;
import com.example.tgesign_up.Database.Location.Table.StateTable;

import java.util.List;

@Dao
public interface StateDao {

    @Query("select count("+ DBContractClass.COL_STATE_ID +") from "+DBContractClass.TABLE_STATE+" ")
    int getTableSize();


    @Query("select * from "+DBContractClass.TABLE_STATE+" ")
    List<StateTable> getState();

    @Query("select "+DBContractClass.COL_STATE_ID+" from "+DBContractClass.TABLE_STATE+"  where "+ DBContractClass.COL_STATE_NAME+" = :state_name ")
    String getStateId(String state_name);

    @Query("select "+DBContractClass.COL_STATE_NAME+" from "+DBContractClass.TABLE_STATE+"  where "+ DBContractClass.COL_STATE_ID+" = :state_id ")
    String getStateName(String state_id);

    /*@Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
    void SyncFlag(String txn_id, String item_id);*/

    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(StateTable lmrDistData);

    /*
     * update the object in database
     * @param data, object to be updated
     */
    @Update
    void update(StateTable lmrDistData);

    /*
     * delete the object from database
     * @param data, object to be deleted
     */
    @Delete
    void delete(StateTable lmrDistData);

    /*
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(StateTable... data);      // data... is varargs, here data is an array
}
