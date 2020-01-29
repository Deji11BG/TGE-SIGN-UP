package com.babbangona.tgesign_up.Database.TFM.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.Table.PictureSync;


@Dao
public interface PictureSyncDao {


    @Query("SELECT COUNT(picture_name) FROM " +
            TFMDBContractClass.TABLE_PICTURE_SYNC +
            " WHERE picture_name = :picture_name ")
    int getPictureNameCount(String picture_name);


    /**
     * Insert the object in database
     * @param pictureSync, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(PictureSync pictureSync);

    /**
     * update the object in database
     * @param pictureSync, object to be updated
     */
    @Update
    void update(PictureSync pictureSync);

    /**
     * delete the object from database
     * @param pictureSync, object to be deleted
     */
    @Delete
    void delete(PictureSync pictureSync);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(PictureSync... data);      // data... is varargs, here data is an array
}
