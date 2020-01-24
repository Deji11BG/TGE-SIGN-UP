package com.example.tgesign_up.Database.Location.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tgesign_up.Database.DBContractClass;
import com.example.tgesign_up.Database.Location.Table.LgaTable;

import java.util.List;

@Dao
public interface LgaDao {

    @Query("select * from "+ DBContractClass.TABLE_LGA+" a " +
            "where a."+DBContractClass.COL_STATE_ID+" = :state_code")
    List<LgaTable> getLga(String state_code);


    @Query("select "+DBContractClass.COL_LGA_ID+" from "+DBContractClass.TABLE_LGA+"  where "+ DBContractClass.COL_LGA_NAME+" = :lga_name ")
    String getLgaId(String lga_name);

    @Query("select "+DBContractClass.COL_LGA_NAME+" from "+DBContractClass.TABLE_LGA+"  where "+ DBContractClass.COL_LGA_ID+" = :lga_id ")
    String getLgaName(String lga_id);

    @Query("SELECT "+DBContractClass.COL_STATE_ID+" FROM "+DBContractClass.TABLE_LGA+"  WHERE "+ DBContractClass.COL_LGA_ID+" = :lga_id ")
    String getStateId(String lga_id);


  /*@Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
  void SyncFlag(String txn_id, String item_id);*/

    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LgaTable lmrDistData);

    /*
     * update the object in database
     * @param data, object to be updated
     */
    @Update
    void update(LgaTable lmrDistData);

    /*
     * delete the object from database
     * @param data, object to be deleted
     */
    @Delete
    void delete(LgaTable lmrDistData);

    /*
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(LgaTable... data);      // data... is varargs, here data is an array
}
