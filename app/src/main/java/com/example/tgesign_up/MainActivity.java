package com.example.tgesign_up;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.bt_open)
    Button open;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_activity_main);
        ButterKnife.bind(this);

        open.setOnClickListener(this);



        checkDBStatus();
    }

    @SuppressLint("StaticFieldLeak")
    public void checkDBStatus(){


        // this method checks the value of the flag in shared preference whether 1 or 0
        int controller = getRecordFlag();

        // In the event that the above method returns 0, this method checks the size of the state table on the location DB and
        // in the event that that is empty it goes ahead to call the methods that populates the
        //database
        if(controller == 0){
            int tablesataus = getRoomDBCount();

            if(tablesataus == 0){

                //function call to the method that reads lga data from asset
                //importStateData();

                try {

                    //function call to the async task method that preloads into the database
                    String x = new dbInsert(getApplicationContext()){}
                    .execute(importStateData()).get();
                    Log.d("abc",x);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else return;

    }

    public void  setRecordFlag(){
        LocSharedPreferenceController locSharedPreferenceController = new LocSharedPreferenceController(getApplicationContext());
        locSharedPreferenceController.setImportFlag("1");

    }

    public int getRecordFlag(){
        LocSharedPreferenceController locSharedPreferenceController = new LocSharedPreferenceController(getApplicationContext());
        int flag = Integer.valueOf(locSharedPreferenceController.getImportFlag()) ;
        return flag;
    }

    @SuppressLint("StaticFieldLeak")
    public int getRoomDBCount(){
        String dbResponse = "";
        try {
            dbResponse  =  new dbCheck(getApplicationContext()){

            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        int tableSize = Integer.valueOf(dbResponse);
        return tableSize;
    }



    public ArrayList<StateTable> importStateData(){

        ArrayList<StateTable> importData = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("location.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("ward_id" +
                        "")){

                }else{
                    //maps CSV row to model class and adds to list
                    final StateTable data = new StateTable(
                            content[0],
                            content[1],
                            content[2],
                            content[3],
                            content[4],
                            content[5],
                            content[6],
                            content[7]);
                    importData.add(data);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        setRecordFlag(); //Set import Flag
        return importData; //return List

    }

    @SuppressLint("StaticFieldLeak")
    public static class dbCheck extends AsyncTask<String, String, String> {

        Context c1;

        public dbCheck(Context c){
            this.c1 = c;
        }

        @Override
        protected String doInBackground(String... strings) {
            Room.databaseBuilder(c1,
                    LocationDatabase.class, "locaion_database").build();
            LocationDatabase dbSize = LocationDatabase.getInstance(c1);
            String  tableSize = String.valueOf(dbSize.getStateInfo().getTableSize());
            return tableSize;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class dbInsert extends AsyncTask<ArrayList<StateTable>, String, String> {

        Context c1;

        dbInsert(Context c){
            this.c1 = c;

        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<StateTable>... arrayLists) {
            Room.databaseBuilder(c1,
                    LocationDatabase.class, "location_database").build();
            LocationDatabase locationDatabase = LocationDatabase.getInstance(c1);

            locationDatabase.getStateInfo().insert(arrayLists[0]);

            return "";
        }
    }


    public void onClick(View v) {

        //do something

        if (permissionGranted()) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Enable location");
                dialog.setPositiveButton("Turn on location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
                dialog.show();
            }

            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //Assign Not Applicable to fields that doesn't require columns


                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setComponent(new ComponentName("com.babbangona.accesscontrol", "com.babbangona.accesscontrol.MainActivity"));
                new Intent(getApplicationContext(), LocationInfo.class);
                startActivity(intent);
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

}
