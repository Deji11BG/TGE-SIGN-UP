package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScheduleInfo extends AppCompatActivity {
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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0 &&
                !sp_day.getText().toString().equalsIgnoreCase("")&&
                !sp_group.getText().toString().equalsIgnoreCase("")&&
                !sp_time.getText().toString().equalsIgnoreCase("")){
                    btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                    btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                    btnNext.setEnabled(false);
                }
                else {
                    btnNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    btnNext.setTextColor(getResources().getColor(R.color.text_color_clear_white));
                    btnNext.setEnabled(true);
                }
            }
        });

        //bottomsheet confirm button for submission
        sheetConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                String dateStamp = dateFormat.format(date);
                secretaryCheckDialog();
            }
        });

        //bottomsheet cancel button to lower sheet
        sheetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

            }
        });





    }

    private String getDay(Integer index){
        if(index==0||index==1){
            return "Monday";
        }
        else if(index==2||index==3){
            return "Tuesday";
        }
        else if(index==4||index==5){
            return "Wednesday";
        }
        else if(index==6||index==7){
            return "Thursday";
        }
        else if(index==8||index==9){
            return "Friday";
        }
        else if(index==10||index==11){
            return "Saturday";
        }
        else{
            return "Sunday";
        }
    }

    private List getIndexes(String day){
        if(day=="Monday"){
            List<Integer> list= new ArrayList<>();
            list.add(0);
            list.add(1);
            return list;
        }
        else  if(day=="Tuesday"){
            List<Integer> list= new ArrayList<>();
            list.add(2);
            list.add(3);
            return list;

        }
        else  if(day=="Wednesday"){
            List<Integer> list= new ArrayList<>();
            list.add(4);
            list.add(5);
            return list;

        }
        else  if(day=="Thursday"){
            List<Integer> list= new ArrayList<>();
            list.add(6);
            list.add(7);
            return list;
        }
        else  if(day=="Friday"){
            List<Integer> list= new ArrayList<>();
            list.add(8);
            list.add(9);
            return list;
        }
        else  if(day=="Saturday"){
            List<Integer> list= new ArrayList<>();
            list.add(10);
            list.add(11);
            return list;
        }
        else{
            List<Integer> list= new ArrayList<>();
            list.add(12);
            list.add(13);
            return list;
        }
    }

    public void secretaryCheckDialog(){
        final AlertDialog.Builder alertadd = new AlertDialog.Builder(ScheduleInfo.this);
        LayoutInflater factory = LayoutInflater.from(ScheduleInfo.this);
        alertadd.setCancelable(true);
        final View views = factory.inflate(R.layout.secretary_check_dialog, null);

        final Button no =    views.findViewById(R.id.location_false);
        final Button yes =    views.findViewById(R.id.location_true);
        alertadd.setView(views);

        final AlertDialog ad = alertadd.create();
        ad.show();



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
              secretaryEnquiryDialog();

            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                startActivity(new Intent(getApplicationContext(), TFMHome.class));
            }
        });
    }

    public void secretaryEnquiryDialog(){
        final AlertDialog.Builder alertadd = new AlertDialog.Builder(ScheduleInfo.this);
        LayoutInflater factory = LayoutInflater.from(ScheduleInfo.this);
        alertadd.setCancelable(true);
        final View views = factory.inflate(R.layout.secretary_enquiry_dialog, null);

        final Button no =    views.findViewById(R.id.location_false);
        final Button yes =    views.findViewById(R.id.location_true);
        alertadd.setView(views);

        final AlertDialog ad = alertadd.create();
        ad.show();



        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();


            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad.dismiss();
                startActivity(new Intent(getApplicationContext(), TFMHome.class));
            }
        });
    }


    //on click listener on next button to bring up bottomsheet for confirmation
    public void submit(View v){
        Log.d("lgalist-check",lgaList.toString()+"-----" +ac_ward.getText().toString());

        if(wardList.contains(ac_ward.getText().toString())){
            if(!ac_lga.getText().toString().equalsIgnoreCase("") &&
                    !ac_ward.getText().toString().equalsIgnoreCase("") ){

                showInfo();
                //ShowDialog();

                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    // btn_bottom_sheet.setText("Close sheet");
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    // btn_bottom_sheet.setText("Expand sheet");
                }

            }

            }


        else {
            Toast.makeText(ScheduleInfo.this, "Selected ward does not belong to selected LGA!!", Toast.LENGTH_LONG).show();

        }


    }

    //populating all textviews in the bottomsheet
    public void showInfo(){

        String ward = this.getResources().getString(R.string.bottom_sheet_ward);
        String day = this.getResources().getString(R.string.bottom_sheet_day);
        String group = this.getResources().getString(R.string.bottom_sheet_group);
        String time = this.getResources().getString(R.string.bottom_sheet_time);

        sheet_ward.setText(ward +" "+selectedWard);
        sheet_day.setText(day +" "+sp_day.getText().toString());
        sheet_group.setText(group +" "+sp_group.getText().toString());
        sheet_time.setText(time +" "+sp_time.getText().toString());

        Log.d("sheet_deets", sheet_ward.getText().toString()+sheet_day.getText().toString()+sheet_group.getText().toString());

    }


    // function to retrieve state data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<String> getStateDetails(Context context) {
        List<String> secretary_count = new ArrayList<String>();
        try {
            secretary_count = new getStateInfo(context){
                @Override
                protected void onPostExecute(List<String> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getStateInfo extends AsyncTask<Void, Void, List<String> >{
        Context mCtx;
        List<String> secretary_count_list = new ArrayList<String>();
        LocationDatabase locationDatabase;

        getStateInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getStateInfo().getState();
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    // function to retrieve lga data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<String> getLgaDetails(Context context, String state) {
        List<String> lga = new ArrayList<>();
        try {
            lga = new getLgaInfo(context){
                @Override
                protected void onPostExecute(List<String> s) {}
            }.execute(state).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("lga",lga.toString());

        return lga;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getLgaInfo extends AsyncTask<String, Void, List<String> >{
        Context mCtx;
        List<String> lga = new ArrayList<>();
        List<String> tempLga = new ArrayList<>();
        LocationDatabase locationDatabase;

        getLgaInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                Log.d("state00",strings[0]);

                tempLga = locationDatabase.getStateInfo().getLga(strings[0]);
                lga.addAll(tempLga);
                return lga;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    // function to retrieve ward data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<String> getWardDetails(Context context, String lga) {
        List<String> ward = new ArrayList<>();
        try {
            ward = new getWardInfo(context){
                @Override
                protected void onPostExecute(List<String> s) {}
            }.execute(lga).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("lga11",ward.toString());
        return ward;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getWardInfo extends AsyncTask<String, Void, List<String> >{
        Context mCtx;
        List<String> ward = new ArrayList<>();
        List<String> tempWard = new ArrayList<String>();

        LocationDatabase locationDatabase;

        getWardInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<String> doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                tempWard = locationDatabase.getStateInfo().getWard(strings[0]);
                ward.addAll(tempWard);
                return ward;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }







    //function used ot create all autocomplete adapters
   public ArrayAdapter adapterGenerator(List<String> list){
       ArrayAdapter localAdapter= new ArrayAdapter<String>(this,
               android.R.layout.simple_dropdown_item_1line, list);

       return localAdapter;
   }



    //function used ot create all autocomplete adapters
    public ArrayAdapter setAdapterGenerator(Set<String> set){
        List<String> returnList = new ArrayList<>();
        returnList.addAll(set);
        ArrayAdapter localAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, returnList);

        return localAdapter;
    }






}
