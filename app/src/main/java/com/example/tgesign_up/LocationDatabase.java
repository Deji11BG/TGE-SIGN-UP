package com.example.tgesign_up;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {StateTable.class },version = 1, exportSchema = false)

public abstract  class LocationDatabase extends RoomDatabase {

    public abstract StateDao getStateInfo();
    public abstract LocationDao getLocationInfo();

    public abstract ConstantsDao getConstantsInfo();

    private static LocationDatabase locationDatabase;

    /**
     * Return instance of database creation
     * */
    public static LocationDatabase getInstance(Context context) {
        if (null == locationDatabase) {
            locationDatabase = buildDatabaseInstance(context);
        }
        return locationDatabase;
    }


    private static LocationDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                LocationDatabase.class,
                "locaion_database")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        locationDatabase = null;
    }
}
