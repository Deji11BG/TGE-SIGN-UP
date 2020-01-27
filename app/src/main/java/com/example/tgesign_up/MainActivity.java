package com.example.tgesign_up;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.example.tgesign_up.Database.DBContractClass;
import com.example.tgesign_up.Database.Location.LocationDatabase;
import com.example.tgesign_up.Database.Location.Table.LgaTable;
import com.example.tgesign_up.Database.Location.Table.StateTable;
import com.example.tgesign_up.Database.Location.Table.VillageTable;
import com.example.tgesign_up.Database.Location.Table.WardTable;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.bt_open)
    Button open;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_activity_main);
        ButterKnife.bind(MainActivity.this);

        checkDBStatus();
    }

    /**
     * This method handles the checks on the location DB to see if it is empty and in the event that
     * it is, it calls a separate method that populates the DB
     **/

    @SuppressLint("StaticFieldLeak")
    public void checkDBStatus(){

        // this method checks the value of the flag in shared preference whether 1 or 0
        int controller = getRecordFlag();

        // In the event that the above method returns 0, this method checks the size of the state table on the location DB and
        // in the event that that is empty it goes ahead to call the methods that populates the
        //database
        if(controller == 0){
            int tableStatus = getRoomDBCount();

            if(tableStatus == 0){

                final ProgressDialog pd1 = new ProgressDialog(MainActivity.this);
                pd1.setMessage(this.getResources().getString(R.string.tfm_wait_for_recapture));
                pd1.show();
                pd1.setCancelable(false);

                //function call to the method that reads state data from asset
                stateTableInsertResult();

                //function call to the method that reads lga data from asset
                lgaTableInsertResult();

                //function call to the method that reads ward data from asset
                wardTableInsertResult();

                //function call to the method that reads ward data from asset
                villageTableInsertResult();

                //function call to the method that reads prospective TGE data from asset
                prospectiveTGEInsertResult();

                new CountDownTimer(10000,1000){
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }
                    @Override
                    public void onFinish() {
                        pd1.dismiss();
                    }
                }.start();

            }
        }

    }

    public int getRecordFlag(){
        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        HashMap<String,String> user = sharedPreferenceController.getUserDetails();
        return Integer.parseInt(Objects.requireNonNull(user.get(SharedPreferenceController.KEY_IMPORT_FLAG)));
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

        return Integer.valueOf(dbResponse);
    }

    @SuppressLint("StaticFieldLeak")
    public static class dbCheck extends AsyncTask<String, String, String> {

        Context c1;

        dbCheck(Context c){
            this.c1 = c;
        }

        @Override
        protected String doInBackground(String... strings) {
            Room.databaseBuilder(c1,
                    LocationDatabase.class, DBContractClass.LOCATION_DATABASE_NAME).build();
            LocationDatabase lmr = LocationDatabase.getInstance(c1);
            return String.valueOf(lmr.getState().getTableSize());
        }
    }

    @SuppressWarnings("unchecked")
    void stateTableInsertResult(){
        try {

            //function call to the async task method that preload into the database
            @SuppressLint("StaticFieldLeak")
            String x = new stateTableInsert(getApplicationContext()){}
                    .execute(importStateData()).get();
            Log.d("abc",x);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class stateTableInsert extends AsyncTask<ArrayList<StateTable>, String, String> {

        Context c1;

        stateTableInsert(Context c){
            this.c1 = c;

        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<StateTable>... arrayLists) {

            Room.databaseBuilder(c1,
                    LocationDatabase.class, "tfm").build();
            LocationDatabase locationDatabase = LocationDatabase.getInstance(c1);

            locationDatabase.getState().insert(arrayLists[0]);

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    void lgaTableInsertResult(){
        try {

            //function call to the async task method that preload into the database
            @SuppressLint("StaticFieldLeak")
            String x = new lgaTableInsert(getApplicationContext()){}
                    .execute(importLgaData()).get();
            Log.d("abc",x);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class lgaTableInsert extends AsyncTask<ArrayList<LgaTable>, String, String> {

        Context c1;

        lgaTableInsert(Context c){
            this.c1 = c;

        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<LgaTable>... arrayLists) {

            Room.databaseBuilder(c1,
                    LocationDatabase.class, "tfm").build();
            LocationDatabase locationDatabase = LocationDatabase.getInstance(c1);

            locationDatabase.getLga().insert(arrayLists[0]);

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    void wardTableInsertResult(){
        try {

            //function call to the async task method that preload into the database
            @SuppressLint("StaticFieldLeak")
            String x = new wardTableInsert(getApplicationContext()){}
                    .execute(importWardTable()).get();
            Log.d("abc",x);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class wardTableInsert extends AsyncTask<ArrayList<WardTable>, String, String> {

        Context c1;

        wardTableInsert(Context c){
            this.c1 = c;

        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<WardTable>... arrayLists) {
            Room.databaseBuilder(c1,
                    LocationDatabase.class, "tfm").build();
            LocationDatabase locationDatabase = LocationDatabase.getInstance(c1);

            locationDatabase.getWard().insert(arrayLists[0]);

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    void villageTableInsertResult(){
        try {

            //function call to the async task method that preload into the database
            @SuppressLint("StaticFieldLeak")
            String x = new villageTableInsert(getApplicationContext()){}
                    .execute(importVillageTable()).get();
            Log.d("abc",x);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class villageTableInsert extends AsyncTask<ArrayList<VillageTable>, String, String> {

        Context c1;

        villageTableInsert(Context c){
            this.c1 = c;
        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<VillageTable>... arrayLists) {
            Room.databaseBuilder(c1,
                    LocationDatabase.class, "tfm").build();
            LocationDatabase locationDatabase = LocationDatabase.getInstance(c1);

            locationDatabase.getVillage().insert(arrayLists[0]);

            return "";
        }
    }

    @SuppressWarnings("unchecked")
    void prospectiveTGEInsertResult(){
        try {

            //function call to the async task method that preload into the database
            @SuppressLint("StaticFieldLeak")
            String x = new prospectiveTGEInsert(getApplicationContext()){}
                    .execute(importProspectiveTGETable()).get();
            Log.d("abc",x);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    public static class prospectiveTGEInsert extends AsyncTask<ArrayList<prospectiveTGETable>, String, String> {

        Context c1;

        prospectiveTGEInsert(Context c){
            this.c1 = c;
        }

        @SafeVarargs
        @Override
        protected final String doInBackground(ArrayList<prospectiveTGETable>... arrayLists) {
            Room.databaseBuilder(c1,
                    TFMDatabase.class, "tfm").build();
            TFMDatabase tfmDatabase = TFMDatabase.getInstance(c1);

            tfmDatabase.getProspectiveTGEDao().insert(arrayLists[0]);

            return "";
        }
    }

    public ArrayList<StateTable> importStateData(){

        ArrayList<StateTable> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("state.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("HSFID")){

                }else{
                    //maps CSV row to model class and adds to list
                    final StateTable inv = new StateTable(
                            content[0],
                            content[1]);
                    inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return inventoryTS; //return List

    }

    public ArrayList<LgaTable>   importLgaData(){

        ArrayList<LgaTable> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("lga.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("HSFID")){

                }else{
                    //maps CSV row to model class and adds to list
                    final LgaTable inv = new LgaTable(
                            content[0],
                            content[1],
                            content[2]);
                    inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return inventoryTS; //return List

    }

    public ArrayList<VillageTable> importVillageTable(){


        ArrayList<VillageTable> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("village.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("HSFID")){

                }else{
                    //maps CSV row to model class and adds to list
                    final VillageTable inv = new VillageTable(
                            content[0],
                            content[1],
                            content[2]);
                    Log.d("MUFASA_VILLAGE",""+""+content[0]+"   "+content[1]+"   "+content[2]);
                    inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return inventoryTS; //return List

    }

    public ArrayList<WardTable> importWardTable(){


        ArrayList<WardTable> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("ward.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equals("HSFID")){

                }else{
                    //maps CSV row to model class and adds to list
                    final WardTable inv = new WardTable(
                            content[0],
                            content[1],
                            content[2]);
                    inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        /**
         * This is the function call to update the record flag to 1
         * */
        setRecordFlag();
        return inventoryTS; //return List

    }

    public ArrayList<prospectiveTGETable> importProspectiveTGETable(){


        ArrayList<prospectiveTGETable> inventoryTS = new ArrayList<>(); //List to hold model class objects
        String[] content = null; //placeholder String array
        try{
            //opens CSV fle in asset folder
            InputStream inputStream = getAssets().open("prospective_tge.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            //read from CSV file by line and read into object. Also adds into the list.
            while ((line = br.readLine()) != null){
                content = line.split(",");
                //To escape out the header row
                if (content[0].equalsIgnoreCase("unique_member_id") || content.length < 2){

                }else{
                    //maps CSV row to model class and adds to list
                    final prospectiveTGETable inv = new prospectiveTGETable(
                            content[0],
                            content[1],
                            content[2],
                            content[3],
                            content[4],
                            content[5],
                            content[6]);
                    inventoryTS.add(inv);
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        /**
         * This is the function call to update the record flag to 1
         * */
        setRecordFlag();
        return inventoryTS; //return List

    }

    public void setRecordFlag(){
        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        sharedPreferenceController.setImportFlag("1");

    }

    /*@SuppressLint("StaticFieldLeak")
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
    }*/

    @OnClick(R.id.bt_open)
    public void buttonClick(View view){
        moveToTFMHomeActivity();
    }


    /*public void onClick(View v) {

        //do something
        moveToTFMHomeActivity();

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
        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }*/

    void moveToTFMHomeActivity(){
        Intent intent = new Intent(getApplicationContext(),TFMHome.class);
        startActivity(intent);
    }

    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

}
