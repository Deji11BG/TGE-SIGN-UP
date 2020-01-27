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
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.example.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.example.tgesign_up.TGHomeMVP.TGModel;

import java.util.List;

@Dao
public interface prospectiveTGLDao {

    @Query("SELECT * FROM " +
            TFMDBContractClass.TABLE_PROSPECTIVE_TGL +" WHERE sync_flag ='0'")
    List<prospectiveTGLTable> getUnsynced();

    @Query("SELECT first_name,last_name,ik_number,member_id,unique_member_id FROM " +
            TFMDBContractClass.TABLE_PROSPECTIVE_TGE +" WHERE ik_number = :ik_number")
    List<prospectiveTGLTable.prospectiveTGLTableRecycler> getMembers(String ik_number);

    @Query("SELECT template FROM " +
            TFMDBContractClass.TABLE_PROSPECTIVE_TGL +" WHERE unique_member_id = :unique_member_id")
    String getProspectiveTGLTemplateResult(String unique_member_id);

    @Query("SELECT first_name,last_name FROM " + TFMDBContractClass.TABLE_PROSPECTIVE_TGL +" WHERE unique_member_id = :unique_member_id")
    FormMemberInformationModel.memberKeyDetails getProspectiveTGLDetails(String unique_member_id);

    /**
     * Insert the object in database
     * @param prospectiveTGL, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(prospectiveTGLTable prospectiveTGL);

    /**
     * update the object in database
     * @param prospectiveTGL, object to be updated
     */
    @Update
    void update(prospectiveTGLTable prospectiveTGL);

    /**
     * delete the object from database
     * @param prospectiveTGL, object to be deleted
     */
    @Delete
    void delete(prospectiveTGLTable prospectiveTGL);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(prospectiveTGLTable... data);      // data... is varargs, here data is an array
}
