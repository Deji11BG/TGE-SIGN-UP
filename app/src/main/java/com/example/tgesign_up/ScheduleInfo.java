package com.example.tgesign_up;

//active fields activity. recycler view is directly below it.

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tgesign_up.Database.TFM.Table.scheduleTable;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.schedulerecycler;
import com.example.tgesign_up.TGHomeMVP.schedulemodel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;

public class ScheduleInfo extends AppCompatActivity {
    private static final String TAG="viewmapped_fields";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<schedulemodel> memberList1=new ArrayList<>();
    //private ArrayList<modelmappedfield> memberList1=new ArrayList<>();
    private ArrayList<schedulemodel> number_list=new ArrayList<>();
    private List<scheduleTable> memberList2;
    private List<scheduleTable> memberList2TFM;
    private ArrayList<Map<String,String>> memberList3;
    private ArrayList firstname;
    private ArrayList lastname;
    private ArrayList number;
    private ArrayList phonenumber;
    String lga;
    private RecyclerView.Adapter adapter;
    private RecyclerView mRecyclerView;
    String staff_name;
    String staff_role;
    String staff_id;
    TextView ward;
    Spinner spinner_lga;
    TextView memberName;
    Button next;
    ImageView mImageView;

    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;


    //General Session Management
    SharedPreferenceController sharedPreferenceController;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;
    String first_name;
    String last_name;
    // TextView mem_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_activity_schedule_info);

        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
//        memEdit = member.edit();
        //OnlineDBHelper db = new OnlineDBHelper(this);
        //spinner_lga = findViewById(R.id.spinner_lga);
        ward=findViewById(R.id.wardschedule);
        Log.d(TAG, "onCreate: started.");

        //Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.schedule);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initiknumbers();
        //executeService();

        //String member_id=member.getString("member_id","R-20null1");
        //memEdit.putString("member_id3",member_id2);
        //memEdit.commit();


        loadRecyclerView();

    }


    public void loadRecyclerView(){
        memberList2 = new ArrayList<>();

        @SuppressLint("StaticFieldLeak")
        scheduleAsynctask.getshedule x = new scheduleAsynctask.getshedule(this) {

        };

        try {

            memberList2 = x.execute().get();

            memberList3 = new ArrayList<>();
            Map<String,String > map = null;

            for (scheduleTable X: memberList2){
                map = new HashMap<>();
                map.put("ward",X.ward);
                map.put("slot_id",X.slot_id);
                map.put("first_day",X.first_day);
                map.put("first_time",X.first_time);
                map.put("second_day",X.second_day);
                map.put("second_time",X.second_time);
                map.put("schedule_flag",X.schedule_flag);
                ward.setText(X.ward);

                map.put("schedule_count", String.valueOf(X.schedule_count));
                sharedPreferenceController.schedulecount(String.valueOf(X.schedule_count));
                memberList3.add(map);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.d("--HELLO--1",memberList3+"");
        recyclerController(memberList3);

    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
//        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new schedulemodel(
                        jsonObject.getString("ward"),

                        jsonObject.getString("slot_id"),
                        jsonObject.getString("first_day"),
                        jsonObject.getString("first_time"),
                        jsonObject.getString("second_day"),
                        jsonObject.getString("second_time"),

                        jsonObject.getString("schedule_flag"),
                        jsonObject.getInt("schedule_count")
                        //jsonObject.getString("phone_number")



                ));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        schedulerecycler tfmAdapter = new schedulerecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
    }


    public void onDestroy() {
        super.onDestroy();
        //executeService();
    }

//    public void executeService(){
//        componentName = new ComponentName(this, BackgroundSync.class);
//        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
//        builder.setPeriodic(1000);
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setPersisted(true);
//        jobInfo = builder.build();
//        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(jobInfo);
//
//
//    }



    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(activefield.this, TGHome.class));
        finish();

    }



}
