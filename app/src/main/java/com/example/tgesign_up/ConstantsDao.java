package com.example.tgesign_up;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.ArrayList;

@Dao
public interface ConstantsDao {
/*
    @Query("SELECT lga_edit_flag, distance_X,distance_Y, distance_Z FROM constants_table")
    ConstantsTable getConstants();*/

  /*@Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
  void SyncFlag(String txn_id, String item_id);*/

    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ArrayList<StateTable> lmrDistData);

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
