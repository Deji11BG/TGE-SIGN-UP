package com.babbangona.tgesign_up.Database.Location.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.tgesign_up.Database.DBContractClass;
import com.babbangona.tgesign_up.Database.Location.Table.VillageTable;

import java.util.List;

@Dao
public interface VillageDao {

    /*@Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
    void SyncFlag(String txn_id, String item_id);*/

    @Query("select * from "+ DBContractClass.TABLE_VILLAGE+" a " +
            "where a."+DBContractClass.COL_WARD_ID+" = :ward_id ")
    List<VillageTable> getVillage(String ward_id);



    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VillageTable lmrDistData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<VillageTable> lmrDistData);

    /*
     * update the object in database
     * @param data, object to be updated
     */
    @Update
    void update(VillageTable lmrDistData);

    /*
     * delete the object from database
     * @param data, object to be deleted
     */
    @Delete
    void delete(VillageTable lmrDistData);

    /*
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(VillageTable... data);      // data... is varargs, here data is an array
}
