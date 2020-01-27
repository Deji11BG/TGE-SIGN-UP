package com.example.tgesign_up.Database.Location.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tgesign_up.Database.DBContractClass;
import com.example.tgesign_up.Database.Location.Table.WardTable;

import java.util.List;

@Dao
public interface WardDao {



    /*@Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
    void SyncFlag(String txn_id, String item_id);*/

    @Query("select * from "+ DBContractClass.TABLE_WARD+" a " +
            "where a."+ DBContractClass.COL_LGA_ID+" = :lga_id ")
    List<WardTable> getWard(String lga_id);

    @Query("select "+DBContractClass.COL_WARD_ID+" from "+DBContractClass.TABLE_WARD+" where "+DBContractClass.COL_WARD_NAME+" = :ward_name ")
    String getWardId(String ward_name);

    @Query("select "+DBContractClass.COL_WARD_NAME+" from "+DBContractClass.TABLE_WARD+" where "+DBContractClass.COL_WARD_ID+" = :ward_id ")
    String getWardName(String ward_id);

    @Query("SELECT "+DBContractClass.COL_LGA_ID+" FROM "+DBContractClass.TABLE_WARD+" WHERE "+DBContractClass.COL_WARD_ID+" = :ward_id ")
    String getLgaId(String ward_id);

    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(WardTable lmrDistData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<WardTable> lmrDistData);

    /*
     * update the object in database
     * @param data, object to be updated
     */
    @Update
    void update(WardTable lmrDistData);

    /*
     * delete the object from database
     * @param data, object to be deleted
     */
    @Delete
    void delete(WardTable lmrDistData);

    /*
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(WardTable... data);      // data... is varargs, here data is an array
}
