package com.babbangona.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.Table.MembersTable;
import com.babbangona.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.babbangona.tgesign_up.FormMemberLocationMVP.FormMemberLocationModel;
import com.babbangona.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.babbangona.tgesign_up.TGHomeMVP.TGModel;

import java.util.List;

@Dao
public interface MembersDao {

    @Query("SELECT count(unique_member_id) as count FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    TGLeaderModel.CountModel getTrustGroupLeaderCount(String unique_ik_number);

    @Query("SELECT DISTINCT ik_number FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" ")
    List<String> getAllRegisteredIK();

    @Query("SELECT count(unique_member_id) as count FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Secretary'")
    FormMemberInformationModel.CountModel getTrustGroupSecretaryCount(String unique_ik_number);

    @Query("SELECT ward_id as leader_ward_id, village_name as leader_village_name, member_program FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    FormMemberInformationModel.MyLeaderLocation getMyLeaderLocation(String unique_ik_number);

    @Query("SELECT member_id FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number")
    List<Integer> getMemberIdArray(String unique_ik_number);

    @Query("SELECT count(unique_member_id) as count FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id  and deactivate_flag = '0'")
    TGModel.CountModel getMemberExistence(String unique_member_id);

    @Query("SELECT count(unique_member_id) as count FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    TGModel.CountModel getRegisteredMemberExistence(String unique_member_id);

    @Query("UPDATE " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" SET deactivate_flag = '1' WHERE unique_member_id = :unique_member_id")
    void deactivateMember(String unique_member_id);

    @Query("UPDATE " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" SET deactivate_flag = '0' WHERE unique_member_id = :unique_member_id")
    void reactivateMember(String unique_member_id);

    @Query("SELECT * FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    FormMemberInformationModel getMemberDetails(String unique_member_id);

    @Query("SELECT state_id, lga_id, ward_id FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number and role = 'Leader'")
    FormMemberLocationModel getLeaderLocation(String unique_ik_number);

    @Query("SELECT state_id, lga_id, ward_id, village_name, other_crops, other_not_listed_crops FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    FormMemberLocationModel getMyLocation(String unique_member_id);

    @Query("SELECT count(unique_member_id) as count FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Secretary'")
    TGModel.CountModel getSecretaryCount(String unique_ik_number);

    @Query("SELECT unique_member_id FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Secretary'")
    String getSecretaryUniqueID(String unique_ik_number);

    @Query("UPDATE " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" SET role = :new_role WHERE unique_member_id = :unique_member_id")
    void changeRole(String unique_member_id, String new_role);

    @Query("SELECT role FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    String getMemberRole(String unique_member_id);

    @Query("SELECT unique_member_id FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    String getLeaderUniqueMemberID(String unique_ik_number);

    @Query("SELECT template FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    String getMemberTemplate(String unique_member_id);

    @Query("SELECT member_id FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    int getMemberId(String unique_member_id);

    @Query("SELECT member_program FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    String getMemberProgram(String unique_ik_number);

    @Query("SELECT COUNT(unique_member_id) FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +
            " WHERE unique_ik_number = :unique_ik_number AND role != 'Leader' AND deactivate_flag = '0' AND delete_flag = '0'")
    int getMemberCount(String unique_ik_number);

    @Query("SELECT COUNT(unique_member_id) FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +
            " WHERE unique_ik_number = :unique_ik_number AND role = 'Leader' AND deactivate_flag = '0' AND delete_flag = '0'")
    int getLeaderCountForMember(String unique_ik_number);

    @Query("SELECT * FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " WHERE sync_flag != '1'")
    List<MembersTable> getAllDataForSync();

    @Query("SELECT COUNT(unique_member_id) FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " WHERE sync_flag != '1'")
    int getAllTFMDataCountForSync();

    @Query("UPDATE " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " SET sync_flag = :sync_flag WHERE unique_member_id = :unique_member_id")
    void updateSyncStatus(String unique_member_id, String sync_flag);

    @Query("SELECT COUNT(unique_member_id) FROM " +
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " WHERE unique_member_id = :unique_member_id ")
    int getRegisteredMemberCount(String unique_member_id);

    //query to get from Dami's table
    @Query("SELECT member_id,unique_ik_number,first_name,last_name,unique_member_id,phone_number,crop_type,state_id,lga_id,village_name,role,staff_id FROM "+ TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " WHERE unique_member_id like:unique_member_id")
    List<MembersTable> loadMemberInfo(String unique_member_id);

    /**
     * Insert the object in database
     * @param membersTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MembersTable membersTable);

    /**
     * Insert the object in database
     * @param membersTableList, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<MembersTable> membersTableList);

    /**
     * update the object in database
     * @param membersTable, object to be updated
     */
    @Update
    void update(MembersTable membersTable);

    /**
     * delete the object from database
     * @param membersTable, object to be deleted
     */
    @Delete
    void delete(MembersTable membersTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(MembersTable... data);      // data... is varargs, here data is an array
}
