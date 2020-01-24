package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;

import java.util.List;

@Dao
public interface TFMAppVariablesDao {

    @Query("SELECT * FROM " +
            TFMDBContractClass.TFM_APP_VARIABLES +" WHERE variable_id = :variable_id")
    TFMAppVariables getTFMAppVariables(String variable_id);

    /**
     * Insert the object in database
     * @param appVersionConstants, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TFMAppVariables appVersionConstants);

    /**
     * Insert the object in database
     * @param appVersionConstants, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<TFMAppVariables> appVersionConstants);

    /**
     * update the object in database
     * @param appVersionConstants, object to be updated
     */
    @Update
    void update(TFMAppVariables appVersionConstants);

    /**
     * delete the object from database
     * @param appVersionConstants, object to be deleted
     */
    @Delete
    void delete(TFMAppVariables appVersionConstants);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(TFMAppVariables... data);      // data... is varargs, here data is an array
}
