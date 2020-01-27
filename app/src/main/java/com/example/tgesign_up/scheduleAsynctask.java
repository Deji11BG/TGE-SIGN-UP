package com.example.tgesign_up;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.room.Room;

import static android.content.Context.MODE_PRIVATE;

public class scheduleAsynctask {

    @SuppressLint("StaticFieldLeak")
    public static class getshedule extends AsyncTask<String, Void, List<scheduleTable>>{

        Context context;
        TFMDatabase tfmDatabase;
        SharedPreferenceController sharedPreference;



        public getshedule(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<scheduleTable> doInBackground(String... params) {

            sharedPreference = new SharedPreferenceController(context);

           // HashMap<String,String> user = sharedPreference.getUserDetails();
            //String unique_id_for_field_mapping = user.get(SharedPreference.KEY_UNIQUE_ID_FIELD_MAPPING);

            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                // Room.databaseBuilder(context,
                //TFMDatabase.class, "DB").build();
                tfmDatabase = TFMDatabase.getInstance(context);
                String ward =sharedPreference.getWard();

                return tfmDatabase.getscheduleTable().getschedule(ward);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class updateScheduleCount extends AsyncTask<String, Void, Void>{
        Context context;

        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;
        TFMDatabase tfmDatabase;
        SharedPreferenceController sharedPreference;

        public updateScheduleCount(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Void doInBackground(String... params) {


            try{

                tfmDatabase = TFMDatabase.getInstance(context);
                String count=sharedPreference.getScheduleCount();
                Integer countInt= Integer.valueOf(count);
                String ward=sharedPreference.getWard();
                String slot_id=sharedPreference.getSlotId();

                //fd.fieldsdao().updateFieldStatusRoom(field_id,status);

                tfmDatabase.getscheduleTable().updateScheduleCount(countInt,ward,slot_id);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


}


