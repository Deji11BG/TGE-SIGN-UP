package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.TrustGroupTable;
import com.example.tgesign_up.Home.LeaderModel;

import java.util.List;

@Dao
public interface TrustGroupDao {

    @Query("SELECT a.unique_ik_number, a.ik_number, b.first_name, b.middle_name, b.last_name, " +
            "b.village_name, b.level, b.unique_member_id, b.season_id FROM " +
            TFMDBContractClass.TABLE_TRUST_GROUP_DATA +
            " a INNER JOIN "+
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +
            " b ON a.unique_ik_number = b.unique_ik_number WHERE a.staff_id = :staff_id AND b.role = 'Leader'")
    List<LeaderModel> getTrustGroups(String staff_id);

    @Query("SELECT finished_checklist FROM " +
            TFMDBContractClass.TABLE_TRUST_GROUP_DATA +
            "  WHERE unique_ik_number = :unique_ik_number")
    String getFinishedCheckList(String unique_ik_number);

    @Query("UPDATE " + TFMDBContractClass.TABLE_TRUST_GROUP_DATA +
            " SET sync_flag = '0',finished_checklist = :flag  WHERE unique_ik_number = :unique_ik_number")
    void saveCheckListFlag(String flag, String unique_ik_number);

    @Query("SELECT * FROM " + TFMDBContractClass.TABLE_TRUST_GROUP_DATA + " WHERE sync_flag != '1'")
    List<TrustGroupTable> getAllTGDataForSync();

    @Query("SELECT COUNT(unique_ik_number) FROM " + TFMDBContractClass.TABLE_TRUST_GROUP_DATA + " WHERE sync_flag != '1'")
    int getAllTGDataCountForSync();

    @Query("UPDATE " + TFMDBContractClass.TABLE_TRUST_GROUP_DATA + " SET sync_flag = :sync_flag WHERE unique_ik_number = :unique_ik_number")
    void updateTGSyncStatus(String unique_ik_number, String sync_flag);

    /**
     * Insert the object in database
     * @param trustGroupTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TrustGroupTable trustGroupTable);

    /**
     * update the object in database
     * @param trustGroupTable, object to be updated
     */
    @Update
    void update(TrustGroupTable trustGroupTable);

    /**
     * delete the object from database
     * @param trustGroupTable, object to be deleted
     */
    @Delete
    void delete(TrustGroupTable trustGroupTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(TrustGroupTable... data);      // data... is varargs, here data is an array
}
