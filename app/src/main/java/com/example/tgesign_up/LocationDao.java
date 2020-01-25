package com.example.tgesign_up;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT latitude,longitude,lga,ward,town FROM location_info where state = :state ")
    List<LocationInfo.locationCordsModel> getStateCords(String state);

    @Query("SELECT latitude,longitude FROM location_info where state = :lga ")
    List<LocationInfo.locationCordsModel> getWardCords(String lga);

    @Query("SELECT latitude,longitude FROM location_info where state = :town ")
    List<LocationInfo.locationCordsModel> getTownCords(String town);


    @Query("SELECT * FROM location_info where sync_flag ='0'")
    List<LocationTable> getAllData();

    @Query("SELECT COUNT(unique_location_id) FROM location_info where staff_id = :staffId")
    String getLocationCount(String staffId);


      @Query("update location_info set sync_flag = '1' where unique_location_id = :location_id ")
      void updateSyncFlag(String location_id);

    /*
     * Insert the object in database
     * @param data, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LocationTable locationData);

    /*
     * update the object in database
     * @param data, object to be updated
     */
    @Update
    void update(StateTable lmrDistData);

    /*
     * delete the object from database
     * @param data, object to be deleted
     */
    @Delete
    void delete(StateTable lmrDistData);

    /*
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(StateTable... data);      // data... is varargs, here data is an array
}
