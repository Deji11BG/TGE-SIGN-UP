package com.babbangona.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.Table.OldMembersTable;
import com.babbangona.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.babbangona.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.babbangona.tgesign_up.TGHomeMVP.TGModel;
import com.babbangona.tgesign_up.TGPage.TgMembersModel;

import java.util.List;

@Dao
public interface OldMembersDao {

    /*
    @Query("update test_questions set sync_flag = '1' where transaction_id = :txn_id and item_id = :item_id")
    void SyncFlag(String txn_id, String item_id);
    */

    /**
     * This method gets group leader details
     * @param unique_ik_number unique_ik_number
     * @return model class of all group leader details required
     */
    @Query("SELECT a.unique_ik_number, a.role, a.ik_number, a.first_name, a.middle_name, a.last_name, a.village_name, a.season_id, " +
            "a.level, a.unique_member_id, a.ward_id, a.phone_number, a.date_of_birth, a.sex, a.crop_type, b.member_program FROM " +
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" a LEFT OUTER JOIN "+
            TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +" b ON a.unique_ik_number = b.unique_ik_number WHERE a.unique_ik_number = :unique_ik_number AND a.role = 'Leader'")
    TGLeaderModel getTrustGroupLeader(String unique_ik_number);

    @Query("SELECT b.first_name, a.middle_name, b.last_name, b.village_name, b.unique_member_id, b.role FROM " + TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +
            " a INNER JOIN "+ TFMDBContractClass.TABLE_NEW_MEMBERS_DATA+" b ON a.unique_member_id = b.unique_member_id " +
            "WHERE b.unique_ik_number = :unique_ik_number AND b.role != 'Leader' and b.delete_flag = '0' " +
            " UNION " +
            "SELECT a.first_name, a.middle_name, a.last_name, a.village_name, a.unique_member_id, a.role FROM " + TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +
            " a LEFT JOIN "+ TFMDBContractClass.TABLE_NEW_MEMBERS_DATA+" b ON b.unique_member_id = a.unique_member_id " +
            "WHERE b.unique_member_id IS NULL AND a.unique_ik_number = :unique_ik_number AND a.role != 'Leader'" +
            " UNION "+
            "SELECT a.first_name, a.middle_name,a.last_name, a.village_name, a.unique_member_id, a.role FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA +
            " a LEFT JOIN "+ TFMDBContractClass.TABLE_OLD_MEMBERS_DATA+" b ON b.unique_member_id = a.unique_member_id " +
            "WHERE b.unique_member_id IS NULL AND a.unique_ik_number = :unique_ik_number AND a.role != 'Leader'  ")
    List<TGModel> getTrustGroupMembers(String unique_ik_number);

    @Query("SELECT  (first_name || ' ' || last_name) as memberName, village_name as memberVillage, unique_member_id as uniqueMemberId, member_id as memberId FROM  "+ TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" " +
            "WHERE unique_ik_number = :unique_ik_number AND role != 'Leader'  ")
    List<TgMembersModel> getMembers(String unique_ik_number);

    @Query("SELECT template FROM " +
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    String getLeaderTemplate(String unique_ik_number);

    @Query("SELECT season_id FROM " +
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" WHERE unique_ik_number = :unique_ik_number AND role = 'Leader'")
    String getSeasonID(String unique_ik_number);

    @Query("SELECT template FROM " +
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    String getMemberTemplateOld(String unique_member_id);

    @Query("SELECT * FROM " +
            TFMDBContractClass.TABLE_OLD_MEMBERS_DATA +" WHERE unique_member_id = :unique_member_id")
    FormMemberInformationModel getOldMemberDetails(String unique_member_id);

    /**
     * Insert the object in database
     * @param oldMembersTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OldMembersTable oldMembersTable);

    /**
     * update the object in database
     * @param oldMembersTable, object to be updated
     */
    @Update
    void update(OldMembersTable oldMembersTable);

    /**
     * delete the object from database
     * @param oldMembersTable, object to be deleted
     */
    @Delete
    void delete(OldMembersTable oldMembersTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(OldMembersTable... data);      // data... is varargs, here data is an array
}
