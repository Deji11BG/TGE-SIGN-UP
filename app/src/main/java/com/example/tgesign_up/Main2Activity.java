package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Bind views for layout elements

    @BindView(R.id.tv_user_details1)
    TextView tvStaffId;

    @BindView(R.id.tv_user_details2)
    TextView tvAppVersion;

    @BindView(R.id.tv_user_details3)
    TextView tvLastSynced;

    @BindView(R.id.tv_user_details4)
    TextView tvLocationCount;

    @BindView(R.id.startverification)
    Button addLocation;

    private List<LocationTable> locationList;

    String staffId, harvestSyncUp, harvestSyncDown, locationCount, appVersion,lastSynced;
    String dateUpdated="";
    private List<DefaultResponse> syncingResponse = new ArrayList<>();

    SharedPreferenceController sharedPreferenceController;
    ProgressDialog downloadProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_activity_homepage);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Location Verification");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);

        sharedPreferenceController= new SharedPreferenceController(getApplicationContext());


        String tempStaffName = getIntent().getStringExtra("staff_name");
        String tempStaffId = getIntent().getStringExtra("staff_id");
        String tempStaffRole = getIntent().getStringExtra("staff_role");
        String tempAppVersion= BuildConfig.VERSION_NAME;

        sharedPreferenceController.holdSessionInfo(tempStaffId,tempStaffRole,tempAppVersion,tempStaffName);




        //retrieving user data from shared prefs
        HashMap<String, String> userDetails = sharedPreferenceController.getUserDetails();
        staffId=userDetails.get("staff_id");
        appVersion= userDetails.get("app_version");
        lastSynced= userDetails.get("last_synced");

        tvStaffId.setText("Staff ID: " + staffId);
        tvAppVersion.setText("App Version: " + appVersion);
        tvLastSynced.setText("Last Synced: " + lastSynced);
        tvLocationCount.setText("Locations Saved: " + getLocationCount(getApplicationContext(),staffId));


        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(),DeviceSetup.class);
                //intent.putExtra("module","TgeVerification");
                startActivity(intent);
            }
        });

        //getSupportActionBar()).setSubtitle(mik);





    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        String harvestSyncUpResult, harvestSyncDownResult, harvestLogsResult;



        if (id == R.id.sync) {

            locationUpload();


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void locationUpload (){


        downloadProgress = new ProgressDialog(Main2Activity.this);
        downloadProgress.setMessage("Uploading....");
        downloadProgress.setTitle("Locations Sync-up");



        locationList = new ArrayList<>(0);
        locationList = getLocationData(Main2Activity.this);
        Log.i("tag",new Gson().toJson(locationList)+ staffId);
        if(locationList.isEmpty()){
            Toast.makeText(Main2Activity.this, "All Records have been Succesffuly Synced", Toast.LENGTH_LONG).show();
            constantDownload();

        }



        else {

            retrofit2.Call<List<DefaultResponse>> call = ApiClient
                .getInstance()
                .getApi()
                .syncUpLocations(new Gson().toJson(locationList));
          // show it
/*
          downloadProgress.show();
*/

            call.enqueue(new Callback<List<DefaultResponse>>() {
                @Override
                public void onResponse(
                    retrofit2.Call<List<DefaultResponse>> call,
                    Response<List<DefaultResponse>> response) {

                    if (response.isSuccessful()) {
                        syncingResponse = response.body();

                        Log.d("syncingResponse-1", Objects.requireNonNull(syncingResponse).toString()+new Gson().toJson(syncingResponse));
                        for (int i = 0; i < syncingResponse.size(); i++) {
                            DefaultResponse returnData = syncingResponse.get(i);
                            Main2Activity.updateSyncFlag updateTask = new Main2Activity.updateSyncFlag(getApplicationContext());
                            updateTask.execute(returnData.getLocation_id());
                            sharedPreferenceController.saveLastSynced(returnData.getLast_synced());
                            Log.d("iValue:", String.valueOf(i)+ returnData.toString());


                        }
                        //downloadProgress.dismiss();
                        Toast.makeText(Main2Activity.this, "Location Data Successfully Uploaded", Toast.LENGTH_LONG).show();
                        constantDownload();
                    }
                }



                @Override
                public void onFailure(Call<List<DefaultResponse>> call, Throwable t) {
                    //downloadProgress.dismiss();
                    Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i("errorr", "onFailure: " + t.getMessage());
                    constantDownload();

                }


            });
        }

        //return "done";
}


    private void locationDownload (){


        Call<List<LocationTable>> call = ApiClient
            .getInstance()
            .getApi()
            .syncDownLocations(staffId,lastSynced);

        call.enqueue(new Callback<List<LocationTable>>() {
            @Override
            public void onResponse(Call<List<LocationTable>> call, Response<List<LocationTable>> response) {
                List<LocationTable> result = response.body();
                Log.i("errorr", lastSynced +String.valueOf(result));

                if(result.isEmpty()){
                    Toast.makeText(Main2Activity.this, "Location Data up to Date!!", Toast.LENGTH_LONG).show();

                }

                else {
                    for (int i = 0; i < result.size(); i++) {
                        LocationTable returnFlags = result.get(i);
                        Main2Activity.insertLocation insertTask = new Main2Activity.insertLocation(getApplicationContext());
                        insertTask.execute(returnFlags);
                    }

                    //downloadProgress.dismiss();
                    Toast.makeText(Main2Activity.this, "Location Data Download Successful!!", Toast.LENGTH_LONG).show();
                    startActivity(getIntent());
                }


            }



            @Override
            public void onFailure(Call<List<LocationTable>> call, Throwable t) {
                //downloadProgress.dismiss();
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("errorr", "onFailure: "+t.getMessage() );
                startActivity(getIntent());
            }
        });

        //return "done";
    }


    private void constantDownload (){


        Call<List<ConstantsResponse>> call = ApiClient
                .getInstance()
                .getApi()
                .getConstantValues();

        call.enqueue(new Callback<List<ConstantsResponse>>() {
            @Override
            public void onResponse(Call<List<ConstantsResponse>> call, Response<List<ConstantsResponse>> response) {
                List<ConstantsResponse> result = response.body();
                Log.i("errorr", String.valueOf(result));
                for (int i = 0; i < result.size(); i++) {
                    ConstantsResponse returnData = result.get(i);
                    insertConstant(returnData);
                }
                //downloadProgress.dismiss();
                Toast.makeText(Main2Activity.this, "Constants Data Download Successful!!", Toast.LENGTH_LONG).show();
                locationDownload();

            }



            @Override
            public void onFailure(Call<List<ConstantsResponse>> call, Throwable t) {
                //downloadProgress.dismiss();
                Toast.makeText(Main2Activity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("errorr", "onFailure: "+t.getMessage() );

                locationDownload();

            }
        });

        //return "done";
    }



    // function to retrieve state data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<LocationTable> getLocationData(Context context) {
        List<LocationTable> secretary_count = new ArrayList<LocationTable>();
        try {
            secretary_count = new fetchLocationData(context){
                @Override
                protected void onPostExecute(List<LocationTable> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class fetchLocationData extends AsyncTask<Void, Void, List<LocationTable> > {
        Context mCtx;
        List<LocationTable> secretary_count_list = new ArrayList<LocationTable>();
        LocationDatabase locationDatabase;

        fetchLocationData(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<LocationTable> doInBackground(Void... voids) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getLocationInfo().getAllData();
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }




    //function to insert data into the location_info table
    @SuppressLint("StaticFieldLeak")
    public class insertLocation extends AsyncTask<LocationTable, Void, Void> {
        LocationDatabase locationDatabase;
        Context mCtx;


        insertLocation(Context context) {
            this.mCtx = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(LocationTable... params) {

//            MembersTable membersTable = params[0];

            try {
                params[0].setSync_flag("1");
                locationDatabase = LocationDatabase.getInstance(mCtx);
                locationDatabase.getLocationInfo().insert(params[0]);
            } catch (Exception e) {
            }

            return null;
        }
    }

    //function to insert data into the location_info table
    @SuppressLint("StaticFieldLeak")
    public class updateSyncFlag extends AsyncTask<String, Void, Void> {
        LocationDatabase locationDatabase;
        Context mCtx;


        updateSyncFlag(Context context) {
            this.mCtx = context;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

//            MembersTable membersTable = params[0];

            try {
                locationDatabase = LocationDatabase.getInstance(mCtx);
                locationDatabase.getLocationInfo().updateSyncFlag(params[0]);
            } catch (Exception e) {
            }

            return null;
        }
    }


    public void insertConstant(ConstantsResponse constant){
        Log.i("errorr", new Gson().toJson(constant.toString()));
        Log.i("cons-1", constant.getValue().toString());
        Log.i("cons-1", constant.getValue().toString()+ constant.getConstants_id().toString());

        //String list =  new Gson().toJson(harvestCalls).replace("\"","");
        //Log.i("Registration",  list);
        if(constant.getConstants_id()==0){
            sharedPreferenceController.saveLgaFlag(constant.getValue());
            Log.i("cons-1", constant.getValue().toString());

        }

        else if(constant.getConstants_id()==1){
            sharedPreferenceController.saveDistanceX1(constant.getValue());
        }

        else if(constant.getConstants_id()==2){
            sharedPreferenceController.saveDistanceY1(constant.getValue());
        }

        else if(constant.getConstants_id()==3){
            sharedPreferenceController.saveDistanceZ1(constant.getValue());
        }

        else if(constant.getConstants_id()==4){
            sharedPreferenceController.saveDistanceX2(constant.getValue());
        }

        else if(constant.getConstants_id()==5){
            sharedPreferenceController.saveDistanceY2(constant.getValue());
        }

        else if(constant.getConstants_id()==6){
            sharedPreferenceController.saveDistanceZ2(constant.getValue());
        }

    }


    // function to retrieve state data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private String getLocationCount(Context context, String staffId) {
        String secretary_count = "";
        try {
            secretary_count = new fetchLocationCount(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(staffId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class fetchLocationCount extends AsyncTask<String, Void, String > {
        Context mCtx;
        String secretary_count_list ="";
        LocationDatabase locationDatabase;

        fetchLocationCount(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... params) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getLocationInfo().getLocationCount(params[0]);
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }


}


