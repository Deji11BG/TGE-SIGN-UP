package com.example.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.tgesign_up.Database.TFM.TFMDBContractClass;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.example.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.example.tgesign_up.TGHomeMVP.TGModel;

import java.util.List;

@Dao
public interface prospectiveTGEDao {


    @Query("SELECT first_name,last_name,ik_number,member_id,unique_member_id FROM " + TFMDBContractClass.TABLE_PROSPECTIVE_TGE +" WHERE hub = :hub")
    List<prospectiveTGETable.prospectiveTGETableRecycler> getLeaders(String hub);

    @Query("SELECT DISTINCT hub FROM " + TFMDBContractClass.TABLE_PROSPECTIVE_TGE +"")
    List<String> getWards();

    @Query("SELECT template FROM " + TFMDBContractClass.TABLE_PROSPECTIVE_TGE +" WHERE unique_member_id = :unique_member_id")
    String getLeaderTemplate(String unique_member_id);

    @Query("SELECT first_name,last_name FROM " + TFMDBContractClass.TABLE_PROSPECTIVE_TGE +" WHERE unique_member_id = :unique_member_id")
    FormMemberInformationModel.memberKeyDetails getProspectiveTGEDetails(String unique_member_id);

    /**
     * Insert the object in database
     * @param prospectiveTGE, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(prospectiveTGETable prospectiveTGE);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<prospectiveTGETable> prospectiveTGE);

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
