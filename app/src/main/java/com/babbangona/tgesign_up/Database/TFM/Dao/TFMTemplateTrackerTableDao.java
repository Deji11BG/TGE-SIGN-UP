package com.babbangona.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;

@Dao
public interface TFMTemplateTrackerTableDao {

    @Query(" SELECT template_tracker FROM "+
            TFMDBContractClass.TABLE_TFM_TEMPLATE_TRACKER +
            " WHERE " + TFMDBContractClass.COL_TEMPLATE_INDEX+" = :template_index ")
    String getTemplate(String template_index);

    /**
     * Insert the object in database
     * @param tfmTemplateTrackerTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TFMTemplateTrackerTable tfmTemplateTrackerTable);

    /**
     * update the object in database
     * @param tfmTemplateTrackerTable, object to be updated
     */
    @Update
    void update(TFMTemplateTrackerTable tfmTemplateTrackerTable);

    /**
     * delete the object from database
     * @param tfmTemplateTrackerTable, object to be deleted
     */
    @Delete
    void delete(TFMTemplateTrackerTable tfmTemplateTrackerTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(TFMTemplateTrackerTable... data);      // data... is varargs, here data is an array
}
