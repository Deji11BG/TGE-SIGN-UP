package com.example.tgesign_up;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.TGE;
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
                //Log.d("warddd",ward);

                return tfmDatabase.getscheduleTable().getschedule(ward);
            }catch (Exception e){
                e.printStackTrace();
                Log.e("eeeee",e.toString());
            }
            return null;
        }

    }
    public static class updateScheduleCount extends AsyncTask<String, Void, Void>{
        Context context;
        SharedPreferenceController sharedPreference;


        TFMDatabase tfmDatabase;
        String ward,slot_id,count;
        Integer countInt;

        public updateScheduleCount(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Void doInBackground(String... params) {


            try{
                sharedPreference = new SharedPreferenceController(context);

                tfmDatabase = TFMDatabase.getInstance(context);
                 count=sharedPreference.getScheduleCount();

                 countInt= Integer.valueOf(count);
                 ward=sharedPreference.getWard();
                Log.d("warddd",ward);
                Log.d("countasunc",countInt.toString());
                 slot_id=sharedPreference.getSlotId();

                //fd.fieldsdao().updateFieldStatusRoom(field_id,status);

                tfmDatabase.getscheduleTable().updateScheduleCount(countInt,ward,slot_id);

            }catch (Exception e){
                e.printStackTrace();
                Log.e("warddd",e.toString());
            }
            return null;
        }
    }
    @SuppressLint("StaticFieldLeak")
    public static class getMemberInfo extends AsyncTask<String, Void, Void>{

        Context context;
        TFMDatabase tfmDatabase;

        SharedPreference sharedPreference;
        SharedPreferenceController prefs;
        SharedPreferences.Editor memEdit;


        public getMemberInfo(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Void doInBackground(String... params) {

            sharedPreference = new SharedPreference(context);
            prefs= new SharedPreferenceController(context);
            HashMap<String,String> user = sharedPreference.getUserDetails();
            String unique_memberID = user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
            String slotID=prefs.getSlotId();

            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                // Room.databaseBuilder(context,
                //TFMDatabase.class, "DB").build();
                tfmDatabase = TFMDatabase.getInstance(context);

                 tfmDatabase.getTGEDao().updateSlotID(unique_memberID,slotID);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
}


