package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.LastSyncTable;


@Dao
public interface LastSyncTableDao {

    /**
     * Insert the object in database
     * @param lastSyncTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LastSyncTable lastSyncTable);

    /**
     * update the object in database
     * @param lastSyncTable, object to be updated
     */
    @Update
    void update(LastSyncTable lastSyncTable);

    /**
     * delete the object from database
     * @param lastSyncTable, object to be deleted
     */
    @Delete
    void delete(LastSyncTable lastSyncTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(LastSyncTable... data);      // data... is varargs, here data is an array
}
