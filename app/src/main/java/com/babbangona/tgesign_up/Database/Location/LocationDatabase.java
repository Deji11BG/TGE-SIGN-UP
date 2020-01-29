package com.babbangona.tgesign_up.Database.Location;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.babbangona.tgesign_up.Database.DBContractClass;
import com.babbangona.tgesign_up.Database.Location.Dao.LgaDao;
import com.babbangona.tgesign_up.Database.Location.Dao.StateDao;
import com.babbangona.tgesign_up.Database.Location.Dao.VillageDao;
import com.babbangona.tgesign_up.Database.Location.Dao.WardDao;
import com.babbangona.tgesign_up.Database.Location.Table.LgaTable;
import com.babbangona.tgesign_up.Database.Location.Table.StateTable;
import com.babbangona.tgesign_up.Database.Location.Table.VillageTable;
import com.babbangona.tgesign_up.Database.Location.Table.WardTable;


@Database(entities = {LgaTable.class, StateTable.class , VillageTable.class, WardTable.class},

        version = DBContractClass.LOCATION_DATABASE_VERSION, exportSchema = false)

public abstract  class LocationDatabase extends RoomDatabase {

    public abstract LgaDao getLga();
    public abstract StateDao getState();
    public abstract VillageDao getVillage();
    public abstract WardDao getWard();


    private static LocationDatabase locationDB;

    /**
     * Return instance of database creation
     * */
    public static LocationDatabase getInstance(Context context) {
        if (null == locationDB) {
            locationDB = buildDatabaseInstance(context);
        }
        return locationDB;
    }


    private static LocationDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                LocationDatabase.class,
                DBContractClass.LOCATION_DATABASE_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public void cleanUp(){
        locationDB = null;
    }
}
