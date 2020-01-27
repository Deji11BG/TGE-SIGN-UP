package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.TGE;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;

import java.util.List;

@Dao
public interface TGEDao {


    @Query("SELECT * FROM " +
            TFMDBContractClass.TABLE_NEW_TGE +" WHERE sync_flag ='0'")
    List<TGE> getUnsynced();

    /**
     * Insert the object in database
     * @param prospectiveTGE, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(prospectiveTGETable prospectiveTGE);

    /**
     * update the object in database
     * @param prospectiveTGE, object to be updated
     */
    @Update
    void update(prospectiveTGETable prospectiveTGE);

    /**
     * delete the object from database
     * @param prospectiveTGE, object to be deleted
     */
    @Delete
    void delete(prospectiveTGETable prospectiveTGE);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(prospectiveTGETable... data);      // data... is varargs, here data is an array
}
