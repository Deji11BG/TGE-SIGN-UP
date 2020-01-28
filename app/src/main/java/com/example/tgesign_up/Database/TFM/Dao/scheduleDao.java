package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.example.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.example.tgesign_up.TGHomeMVP.TGModel;

import java.util.List;

@Dao
public interface scheduleDao {

    @Query("SELECT * FROM " +
            TFMDBContractClass.TABLE_SCHEDULE +" WHERE schedule_sync_flag ='0'")
    List<scheduleTable> getUnsynced();

    @Query("SELECT ward,first_day,first_time,second_day,second_time,slot_id,schedule_count FROM " +
            TFMDBContractClass.TABLE_SCHEDULE +" WHERE ward = :ward")
    List<scheduleTable> getschedule(String ward);

    @Query("UPDATE " + TFMDBContractClass.TABLE_SCHEDULE +" SET schedule_sync_flag =:schedule_flag WHERE ward = :ward AND slot_id=:slot_id")
    void updateScheduleFlag(String schedule_flag, String ward,String slot_id);

    @Query("UPDATE " + TFMDBContractClass.TABLE_SCHEDULE +" SET schedule_count =:schedule_count WHERE ward = :ward AND slot_id=:slot_id")
    void updateScheduleCount(Integer schedule_count, String ward,String slot_id);
    /**
     * Insert the object in database
     * @param schedule, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(scheduleTable schedule);

    /**
     * update the object in database
     * @param schedule, object to be updated
     */
    @Update
    void update(scheduleTable schedule);

    /**
     * delete the object from database
     * @param schedule, object to be deleted
     */
    @Delete
    void delete(scheduleTable schedule);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(scheduleTable... data);      // data... is varargs, here data is an array
}
