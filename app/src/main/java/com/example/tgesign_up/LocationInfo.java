package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocationInfo extends AppCompatActivity {
    //Bind views for layout elements
    @BindView(R.id.sp_location_state)
    TextInputLayout sp_state_layout;

    @BindView(R.id.sp_location_lga)
    TextInputLayout sp_lga_layout;

//    @BindView(R.id.et_location_lga)
//    TextInputLayout et_lga_layout;

    @BindView(R.id.sp_location_ward)
    TextInputLayout sp_ward_layout;

//    @BindView(R.id.et_location_ward)
//    TextInputLayout et_ward_layout;

//    @BindView(R.id.et_location_town)
//    TextInputLayout et_town_layout;

    @BindView(R.id.ac_location_state)
    AutoCompleteTextView ac_state;

    @BindView(R.id.ac_location_lga)
    AutoCompleteTextView ac_lga;

    @BindView(R.id.ac_location_ward)
    AutoCompleteTextView ac_ward;

    @BindView(R.id.next)
    MaterialButton btnNext;

    @BindView(R.id.bottom_sheet)
    LinearLayout bottomSheet;

    @BindView(R.id.sheet_state)
    TextView sheetState;

    @BindView(R.id.sheet_lga)
    TextView sheetLga;

    @BindView(R.id.sheet_ward)
    TextView sheetWard;

    @BindView(R.id.sheet_town)
    TextView sheetTown;

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
    String selectedState,selectedLga,selectedWard,selectedTown;
    SharedPreferenceController sharedPreferenceController;
    private BottomSheetBehavior sheetBehavior;
    Double userLat,userLng, X1, Y1, Z1, X2, Y2, Z2;
    ArrayAdapter state,lga,ward,town;
    String staffId,appVersion,staffRole,lgeEditFlag;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loc_activity_location_info_total);
        ButterKnife.bind(this);

        //Model and shared prefs initialisation
        sharedPreferenceController= new SharedPreferenceController(getApplicationContext());

        //retrieving user data from shared prefs
        HashMap<String, String> userDetails = sharedPreferenceController.getUserDetails();
        staffId= userDetails.get("staff_id");
        appVersion= userDetails.get("app_version");
        staffRole= userDetails.get("staff_role");
        X1=Double.valueOf(userDetails.get("distance_X1"));
        Y1=Double.valueOf(userDetails.get("distance_Y1"));
        Z1=Double.valueOf(userDetails.get("distance_Z1"));
        X2=Double.valueOf(userDetails.get("distance_X2"));
        Y2=Double.valueOf(userDetails.get("distance_Y2"));
        Z2=Double.valueOf(userDetails.get("distance_Z2"));
        lgeEditFlag=userDetails.get("lga_edit_flag");

        //Deactivating next button
        btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
        btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
        btnNext.setEnabled(false);

        //Calculation for state distances
        stateList= getStateDetails(getApplicationContext());
        state= adapterGenerator(stateList);
        ac_state.setAdapter(state);


        //on click listener for lga field to auto populate ward adapter
        ac_state.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lgaList.clear();
                wardList.clear();
                autolgaList.clear();
                autowardList.clear();
                ac_lga.setText("");
                ac_ward.setText("");
                btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                btnNext.setEnabled(false);
                selectedState = (String)parent.getItemAtPosition(position);
                lgaList= getLgaDetails(getApplicationContext(),selectedState);

                lga= adapterGenerator(lgaList);

                ac_lga.setAdapter(lga);

            }
        });


       //on click listener for lga field to auto populate ward adapter
       ac_lga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               wardList.clear();
               autowardList.clear();
               ac_ward.setText("");
               btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
               btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
               btnNext.setEnabled(false);
               selectedLga = (String)parent.getItemAtPosition(position);
               wardList= getWardDetails(getApplicationContext(),selectedLga);
              for(Iterator<String> iterator= autowardList.iterator();iterator.hasNext();){
                  String s= iterator.next();
                  if(!wardList.contains(s)){
                      iterator.remove();
                  }
              }
               Log.d("wardlists",autowardList.toString()+Arrays.asList(wardList).toString());

               autowardList.addAll(wardList);
               ward= adapterGenerator(autowardList);

               ac_ward.setAdapter(ward);

           }
       });


        //activation of next button after entering town
        ac_ward.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0 &&
                !ac_state.getText().toString().equalsIgnoreCase("")&&
                !ac_lga.getText().toString().equalsIgnoreCase("")&&
                !ac_ward.getText().toString().equalsIgnoreCase("")){
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





    }



    //on click listener on next button to bring up bottomsheet for confirmation
    public void submit(View v){
        Log.d("lgalist-check",lgaList.toString()+"-----" +ac_ward.getText().toString());

        if(wardList.contains(ac_ward.getText().toString())){
            if(!ac_lga.getText().toString().equalsIgnoreCase("") &&
                    !ac_ward.getText().toString().equalsIgnoreCase("") ){
                //ShowDialog();

                Intent intent =  new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("staff_id",staffId);
                intent.putExtra("app_version",appVersion);
                intent.putExtra("staff_role",staffRole);


                startActivity(intent);

            }


        }

        else {
            Toast.makeText(LocationInfo.this, "Selected ward does not belong to selected LGA!!", Toast.LENGTH_LONG).show();

        }


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
       List<String> returnList = new ArrayList<>();
       list.addAll(list);
       ArrayAdapter localAdapter= new ArrayAdapter<String>(this,
               android.R.layout.simple_dropdown_item_1line, returnList);

       return localAdapter;
   }







}
