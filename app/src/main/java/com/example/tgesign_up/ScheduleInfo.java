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

<<<<<<< HEAD
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
=======
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
>>>>>>> 4de17ee500bfc71634818965d2db581192713e9a

import java.util.ArrayList;
<<<<<<< HEAD
import java.util.HashMap;
=======
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
>>>>>>> 4de17ee500bfc71634818965d2db581192713e9a
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
<<<<<<< HEAD
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
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    //General Session Management
    SharedPreferenceController sharedPreferenceController;
=======
    //Bind views for layout elements
    @BindView(R.id.sp_day)
    AutoCompleteTextView sp_day;

    @BindView(R.id.sp_group)
    AutoCompleteTextView sp_group;

    @BindView(R.id.sp_time)
    AutoCompleteTextView sp_time;

    @BindView(R.id.tv_ward)
    AutoCompleteTextView tv_ward;

    @BindView(R.id.ac_location_lga)
    AutoCompleteTextView ac_lga;

    @BindView(R.id.ac_location_ward)
    AutoCompleteTextView ac_ward;

    @BindView(R.id.next)
    MaterialButton btnNext;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @BindView(R.id.sheet_ward)
    TextView sheet_ward;

    @BindView(R.id.sheet_day)
    TextView sheet_day;

    @BindView(R.id.sheet_group)
    TextView sheet_group;

    @BindView(R.id.sheet_time)
    TextView sheet_time;

    @BindView(R.id.btnConfirm)
    Button sheetConfirm;

    @BindView(R.id.btnCancel)
    Button sheetCancel;

    //Variable declarations
    List<String> stateList=new ArrayList<>(),lgaList=new ArrayList<>(), wardList=new ArrayList<>(),townList=new ArrayList<>()
            ,autolgaList=new ArrayList<>(), autowardList=new ArrayList<>(),autotownList=new ArrayList<>();
    private static boolean ASC = true;
    private static boolean DESC = false;
    Map<String,Double> stateDistance,lgaDistance,wardDistance,townDistance;
    String selectedWard,selectedGroup,selectedTime,selectedDay;
    LocSharedPreferenceController sharedPreferenceController;
    private BottomSheetBehavior sheetBehavior;
    Double userLat,userLng, X1, Y1, Z1, X2, Y2, Z2;
    ArrayAdapter dayAdapter,groupAdapter,timeAdapter;
    String staffId,appVersion,staffRole,lgeEditFlag;
    Integer dayMorning=0, dayEvening=0, slotsTaken=0, slotsTotal=0, countLimit=0;
    List groupList=new ArrayList<>();
    List timeList=new ArrayList<>();
    List dayList=new ArrayList<>();
    Set<String> dayAdapterList= new HashSet<>();


>>>>>>> 4de17ee500bfc71634818965d2db581192713e9a

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
<<<<<<< HEAD
        setContentView(R.layout.loc_activity_schedule_info);
=======
        setContentView(R.layout.loc_activity_schedule_info_total);
        ButterKnife.bind(this);

        //Model and shared prefs initialisation
        sharedPreferenceController= new LocSharedPreferenceController(getApplicationContext());
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
//bottomsheet behavior initialisation
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //  btn_bottom_sheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        //btn_bottom_sheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });


        //retrieving user data from shared prefs
        HashMap<String, String> userDetails = sharedPreferenceController.getUserDetails();
        staffId= userDetails.get("staff_id");
        appVersion= userDetails.get("app_version");
        staffRole= userDetails.get("staff_role");
        lgeEditFlag=userDetails.get("lga_edit_flag");

        final HashMap<String, String> scheduleDetails = sharedPreferenceController.getScheduleDetails();



        //Deactivating next button
        btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
        btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
        btnNext.setEnabled(false);

//get limit for each group
        countLimit= Integer.valueOf(scheduleDetails.get("count_limit"));


        //populate days with their respective group counts
        for(int i=0; i<14;i++){
            dayList.add(i,scheduleDetails.get("day_code_"+i));
        }

        //getting adapter for days
        for(int i=0; i<14;i++){
            String day= getDay(i);
            if(((int)dayList.get(i))<=countLimit){
                dayAdapterList.add(day);
            }
        }
        dayAdapter=setAdapterGenerator(dayAdapterList);
        sp_day.setAdapter(dayAdapter);




       //on click listener for lga field to auto populate ward adapter
       sp_day.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               List selectedDayIndexes= new ArrayList();
               sp_group.setText("");
               sp_time.setText("");
               btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
               btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
               btnNext.setEnabled(false);
               selectedDay = (String)parent.getItemAtPosition(position);
               selectedDayIndexes=getIndexes(selectedDay);
               for (int i=0; i<selectedDayIndexes.size();i++){
                   if((int)selectedDayIndexes.get(i)<=20){
                       groupList.add(scheduleDetails.get("group"+(i+1)));
                   }
               }
              groupAdapter= adapterGenerator(groupList);

               sp_group.setAdapter(groupAdapter);

           }
       });

        //on click listener for lga field to auto populate ward adapter
        sp_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List selectedDayIndexes= new ArrayList();
                sp_group.setText("");
                sp_time.setText("");
                btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                btnNext.setEnabled(false);
                selectedGroup = (String)parent.getItemAtPosition(position);
                if(selectedGroup.equalsIgnoreCase("Morning Group")){
                    timeList.add(scheduleDetails.get("time1"));
                }
                else if(selectedGroup.equalsIgnoreCase("Evening Group")){
                    timeList.add(scheduleDetails.get("time2"));
                }
                timeAdapter= adapterGenerator(timeList);

                sp_time.setAdapter(timeAdapter);

            }
        });


        //activation of next button after entering town
        sp_time.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
>>>>>>> 4de17ee500bfc71634818965d2db581192713e9a

        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        memEdit = member.edit();
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
