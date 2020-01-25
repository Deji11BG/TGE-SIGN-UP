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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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

public class LocationInfo extends AppCompatActivity  {
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

    @BindView(R.id.sp_location_town)
    TextInputLayout ac_town_layout;

//    @BindView(R.id.et_location_town)
//    TextInputLayout et_town_layout;

    @BindView(R.id.ac_location_state)
    AutoCompleteTextView ac_state;

    @BindView(R.id.ac_location_lga)
    AutoCompleteTextView ac_lga;

    @BindView(R.id.ac_location_ward)
    AutoCompleteTextView ac_ward;

    @BindView(R.id.ac_location_town)
    AutoCompleteTextView ac_town;

//    @BindView(R.id.et_location_lga_val)
//    TextInputEditText et_lga;
//
//    @BindView(R.id.et_location_ward_val)
//    TextInputEditText et_ward;
//
//    @BindView(R.id.et_location_town_val)
//    TextInputEditText et_town;

    @BindView(R.id.next)
    MaterialButton btnNext;

   /* @BindView(R.id.rb_textview1)
    TextView state1;

    @BindView(R.id.rb_textview2)
    TextView state2;

    @BindView(R.id.rb_textview3)
    TextView state3;

    @BindView(R.id.rb_textview4)
    TextView state4;

    @BindView(R.id.radioButtonLayout1)
    LinearLayout stateGroup1;

    @BindView(R.id.radioButtonLayout2)
    LinearLayout stateGroup2;

    @BindView(R.id.radioButtonLayout3)
    LinearLayout stateGroup3;

    @BindView(R.id.radioButtonLayout4)
    LinearLayout stateGroup4;

    @BindView(R.id.radioButton1)
    RadioButton radioButton1;

    @BindView(R.id.radioButton2)
    RadioButton radioButton2;

    @BindView(R.id.radioButton3)
    RadioButton radioButton3;

    @BindView(R.id.radioButton4)
    RadioButton radioButton4;*/

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
    LinkedHashSet<String> stateList=new LinkedHashSet<>(),lgaList=new LinkedHashSet<>(), wardList=new LinkedHashSet<>(),townList=new LinkedHashSet<>()
            ,autolgaList=new LinkedHashSet<>(), autowardList=new LinkedHashSet<>(),autotownList=new LinkedHashSet<>();
    private static boolean ASC = true;
    private static boolean DESC = false;
    Map<String,Double> stateDistance,lgaDistance,wardDistance,townDistance;
    String selectedState,selectedLga,selectedWard,selectedTown;
    LocSharedPreferenceController locSharedPreferenceController;
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
        LocationTable insertModel= new LocationTable();
        locSharedPreferenceController = new LocSharedPreferenceController(getApplicationContext());
        lgaDistance= new HashMap<>();
        wardDistance= new HashMap<>();
        townDistance= new HashMap<>();

        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        userLat = Double.valueOf(locSharedPreferenceController.getUserLat());
        userLng = Double.valueOf(locSharedPreferenceController.getUserLong());

        //retrieving user data from shared prefs
        HashMap<String, String> userDetails = locSharedPreferenceController.getUserDetails();
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
        calculateStateDistance();
        stateDistance= sortByComparator(stateDistance,ASC);
       /* state1.setText(String.valueOf(stateDistance.keySet().toArray()[0]));
        state2.setText(String.valueOf(stateDistance.keySet().toArray()[1]));
        state3.setText(String.valueOf(stateDistance.keySet().toArray()[2]));
        state4.setText(String.valueOf(stateDistance.keySet().toArray()[3]));*/
        selectedState=String.valueOf(stateDistance.keySet().toArray()[0]);
        ac_state.setText(selectedState);
        for(int i=0;i<stateDistance.size();i++){
            stateList.add(String.valueOf(stateDistance.keySet().toArray()[i]));
        }

        initialCalcData();
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
                ac_town.setText("");
                btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                btnNext.setEnabled(false);
                selectedState = (String)parent.getItemAtPosition(position);
                getAutoLists(selectedState,"dropdown");
                lgaList= getLgaDetails(getApplicationContext(),selectedState);
                for(Iterator<String> iterator= autolgaList.iterator();iterator.hasNext();){
                    String s= iterator.next();
                    if(!lgaList.contains(s)){
                        iterator.remove();
                    }
                }
                Log.d("wardlists",autolgaList.toString()+Arrays.asList(lgaList).toString());

                autolgaList.addAll(lgaList);
                lga= adapterGenerator(autolgaList);

                ac_lga.setAdapter(lga);

            }
        });


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

//        selectedLga= ac_lga.getText().toString();
//
//        wardDistance=sortByComparator(getWardDistance(selectedLga),ASC);
//
//        for (int i=0; i<wardDistance.size();i++){
//            wardList.add(String.valueOf(wardDistance.keySet().toArray()[i]));
//
//        }
//        ac_ward.setAdapter(ward);
//
//        selectedWard= ac_ward.getText().toString();
//
//        townDistance= sortByComparator(getTownDistance(selectedWard),ASC);
//
//        for (int i=0; i<townDistance.size();i++){
//            townList.add(String.valueOf(townDistance.keySet().toArray()[i]));
//
//        }
//        ac_town.setAdapter(town);
//
//        selectedTown=ac_town.getText().toString();


       /* for (int i=0; i<4;i++){
            lgaDistance.add(getLgaDistance( String.valueOf(stateDistance.keySet().toArray()[i]))); ;

        }

        for (int i=0; i<lgaDistance.get(i).size();i++){

            for (int j=0; j<lgaDistance.get(i).size();j++) {

                if (lgaDistance.get(i).get(j) < 0.00) {
                    lgaList.add(String.valueOf(lgaDistance.get(i).keySet().toArray()[j]));
                }
            }

        }

*/

        //initial selection click on closest state
//       radioButton1.callOnClick();

        //on click listener for lga field to auto populate ward adapter
        ac_lga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                wardList.clear();
                autowardList.clear();
                ac_ward.setText("");
                ac_town.setText("");
                btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                btnNext.setEnabled(false);
                getAutoLists(selectedState,"dropdown");
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

        //on click listener for ward field to auto populate town adapter
        ac_ward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                townList.clear();
                autotownList.clear();
                ac_town.setText("");
                btnNext.setBackgroundColor(getResources().getColor(R.color.text_color_grey));
                btnNext.setTextColor(getResources().getColor(R.color.absolute_black));
                btnNext.setEnabled(false);
                getAutoLists(selectedState,"dropdown");
                selectedWard = (String)parent.getItemAtPosition(position);
                townList= getWardDetails(getApplicationContext(),selectedWard);

                /*for(Iterator<String> iterator= autotownList.iterator();iterator.hasNext();){
                    String s= iterator.next();
                    if(!townList.contains(s)){
                        iterator.remove();
                    }
                }
*/
                autotownList.addAll(townList);
                town= adapterGenerator(autotownList);

                ac_town.setAdapter(town);


            }
        });


        //activation of next button after entering town
        ac_town.addTextChangedListener(new TextWatcher() {
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

    //on click first radio button to select closest state and populate lga, ward and town fields
    public void initialCalcData()
    {
        //resetting all fields, clearing all lists and setting checked on the state selected
        ac_lga.setText("");
        ac_ward.setText("");
        ac_town.setText("");
        autolgaList.clear();
        autowardList.clear();
        autotownList.clear();
        lgaList.clear();
        wardList.clear();
        townList.clear();


        lgaDistance.clear();
        wardDistance.clear();
        townDistance.clear();

        Log.d("stateview",lgaList.toString()+wardList.toString()+townList.toString());

        if(lgeEditFlag.equalsIgnoreCase("1")){

        }
        else {
            ac_lga.setFocusable(false);
        }


        getAutoLists(selectedState,"prefill");

        lgaDistance= sortByComparator(lgaDistance,ASC);
        wardDistance= sortByComparator(wardDistance,ASC);
        townDistance= sortByComparator(townDistance,ASC);
        if(lgaDistance.isEmpty()){

        }
        else {
            ac_lga.setText(String.valueOf(lgaDistance.keySet().toArray()[0]));

        }
        if(wardDistance.isEmpty()){

        }

        else {
            ac_ward.setText(String.valueOf(wardDistance.keySet().toArray()[0]));

        }

        if (townDistance.isEmpty()){


        }

        else {
            ac_town.setText(String.valueOf(townDistance.keySet().toArray()[0]));

        }

        lgaList= getLgaDetails(getApplicationContext(),selectedState);

        Log.d("lgalist1",lgaList.toString()+"-----" +autolgaList.toString());



        Log.d("lgalist2",lgaList.toString()+"-----" +autolgaList.toString());

        wardList= getWardDetails(getApplicationContext(),ac_lga.getText().toString());


        autolgaList.addAll(lgaList);
        autowardList.addAll(wardList);


        Log.d("state",autolgaList.toString());
        lga= adapterGenerator(autolgaList);
        ward= adapterGenerator(autowardList);

        ac_lga.setAdapter(lga);
        ac_ward.setAdapter(ward);

        if(!ac_state.getText().toString().equalsIgnoreCase("") &&
                !ac_lga.getText().toString().equalsIgnoreCase("") &&
                !ac_ward.getText().toString().equalsIgnoreCase("") &&
                !ac_town.getText().toString().equalsIgnoreCase("") ){
            btnNext.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnNext.setTextColor(getResources().getColor(R.color.text_color_clear_white));
            btnNext.setEnabled(true);
        }
    }



    //on click listener on next button to bring up bottomsheet for confirmation
    public void submit(View v){
        Log.d("lgalist-check",lgaList.toString()+"-----" +ac_ward.getText().toString());

        if(wardList.contains(ac_ward.getText().toString())){
            if(!ac_lga.getText().toString().equalsIgnoreCase("") &&
                    !ac_ward.getText().toString().equalsIgnoreCase("") &&
                    !ac_town.getText().toString().equalsIgnoreCase("") ){

                locSharedPreferenceController.saveTgeWard(ac_ward.getText().toString());
                Intent i = new  Intent(this,TFMHome.class);
                this.startActivity(i);

            }
        }

        else {
            Toast.makeText(LocationInfo.this, "Selected ward does not belong to selected LGA!!", Toast.LENGTH_LONG).show();

        }


    }



    //distance conversion formula
    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }


    //distance calculation between 2 geographical points
    public double calculatedDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6.371; // Radius of the earth in m
        double dLat = deg2rad(Math.abs(lat2 - lat1));  // deg2rad below
        double dLon = deg2rad(Math.abs(lon2 - lon1));
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double d = R * c; // Distance in m
        return d;
        // return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)) * 110000;
    }

    //function to calculate distance btwn all states and the present position of user
    private void calculateStateDistance(){
        Double minlat,maxlat,minlng,maxlng,midlat,midlng;
        Double distance;
        stateDistance= new HashMap<>();

        List<stateModel> stateModels= getStateDetails(getApplicationContext());
        for (int i=0; i<stateModels.size(); i++){
            Log.d("state",String.valueOf(stateModels.get(10).getMax_lat()+String.valueOf(stateModels.size())));
            minlat= Double.valueOf(stateModels.get(i).getMin_lat());
            maxlat= Double.valueOf(stateModels.get(i).getMax_lat());
            minlng= Double.valueOf(stateModels.get(i).getMin_long());
            maxlng= Double.valueOf(stateModels.get(i).getMax_long());
            midlat= (minlat+maxlat)/2;
            midlng= (minlng+maxlng)/2;


            distance= calculatedDistance(midlat,midlng,userLat,userLng);
            stateDistance.put(stateModels.get(i).getState(),distance);
        }
    }



    //function to insert data into the location_info table
    @SuppressLint("StaticFieldLeak")
    public class insertData extends AsyncTask<LocationTable, Void, Void> {
        LocationDatabase locationDatabase;
        Context mCtx;


        insertData(Context context) {
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
                locationDatabase = LocationDatabase.getInstance(mCtx);
                locationDatabase.getLocationInfo().insert(params[0]);
            } catch (Exception e) {
            }

            return null;
        }
    }

    // function to retrieve state data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<stateModel> getStateDetails(Context context) {
        List<stateModel> secretary_count = new ArrayList<stateModel>();
        try {
            secretary_count = new getStateInfo(context){
                @Override
                protected void onPostExecute(List<stateModel> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getStateInfo extends AsyncTask<Void, Void, List<LocationInfo.stateModel> >{
        Context mCtx;
        List<stateModel> secretary_count_list = new ArrayList<stateModel>();
        LocationDatabase locationDatabase;

        getStateInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<stateModel> doInBackground(Void... voids) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getStateInfo().getStateDetails();
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    // function to retrieve lga data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private LinkedHashSet<String> getLgaDetails(Context context, String state) {
        LinkedHashSet<String> lga = new LinkedHashSet<String>();
        try {
            lga = new getLgaInfo(context){
                @Override
                protected void onPostExecute(LinkedHashSet<String> s) {}
            }.execute(state).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("lga",lga.toString());

        return lga;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getLgaInfo extends AsyncTask<String, Void, LinkedHashSet<String> >{
        Context mCtx;
        LinkedHashSet<String> lga = new LinkedHashSet<String>();
        List<String> tempLga = new ArrayList<>();
        LocationDatabase locationDatabase;

        getLgaInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected LinkedHashSet<String> doInBackground(String... strings) {
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
    private LinkedHashSet<String> getWardDetails(Context context, String lga) {
        LinkedHashSet<String> ward = new LinkedHashSet<String>();
        try {
            ward = new getWardInfo(context){
                @Override
                protected void onPostExecute(LinkedHashSet<String> s) {}
            }.execute(lga).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("lga11",ward.toString());
        return ward;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getWardInfo extends AsyncTask<String, Void, LinkedHashSet<String> >{
        Context mCtx;
        LinkedHashSet<String> ward = new LinkedHashSet<String>();
        List<String> tempWard = new ArrayList<String>();

        LocationDatabase locationDatabase;

        getWardInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected LinkedHashSet<String> doInBackground(String... strings) {
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


    // function to retrieve town data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private LinkedHashSet<locationCordsModel> getTownDetails(Context context, String location) {
        LinkedHashSet<locationCordsModel> locationCordsModels = new LinkedHashSet<locationCordsModel>();
        try {
            locationCordsModels = new getTownInfo(context){
                @Override
                protected void onPostExecute(LinkedHashSet<locationCordsModel> s) {}
            }.execute(location).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return locationCordsModels;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getTownInfo extends AsyncTask<String, Void, LinkedHashSet<LocationInfo.locationCordsModel> >{
        Context mCtx;
        LinkedHashSet<locationCordsModel> town = new LinkedHashSet<locationCordsModel>();
        List<locationCordsModel> tempTown = new ArrayList<>();
        LocationDatabase locationDatabase;

        getTownInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected LinkedHashSet<locationCordsModel> doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                tempTown = locationDatabase.getLocationInfo().getTownCords(strings[0]);
                town.addAll(tempTown);
                return town;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    // function to retrieve lga,ward and town data from data on location_info table
    @SuppressLint("StaticFieldLeak")
    private List<locationCordsModel> getAutoLgaDetails(Context context, String state) {
        List<locationCordsModel> secretary_count = new ArrayList<locationCordsModel>();
        try {
            secretary_count = new getAutoLgaInfo(context){
                @Override
                protected void onPostExecute(List<locationCordsModel> s) {}
            }.execute(state).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getAutoLgaInfo extends AsyncTask<String, Void, List<LocationInfo.locationCordsModel> >{
        Context mCtx;
        List<locationCordsModel> secretary_count_list = new ArrayList<locationCordsModel>();
        LocationDatabase locationDatabase;

        getAutoLgaInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<locationCordsModel> doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getLocationInfo().getStateCords(strings[0]);
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }
/*
    @SuppressLint("StaticFieldLeak")
    private List<stateModel> getAutoWardDetails(Context context) {
        List<stateModel> secretary_count = new ArrayList<stateModel>();
        try {
            secretary_count = new getStateInfo(context){
                @Override
                protected void onPostExecute(List<stateModel> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getAutoWardInfo extends AsyncTask<Void, Void, List<LocationInfo.stateModel> >{
        Context mCtx;
        List<stateModel> secretary_count_list = new ArrayList<stateModel>();
        LocationDatabase locationDatabase;

        getStateInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<stateModel> doInBackground(Void... voids) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getStateInfo().getStateDetails();
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private List<stateModel> getAutoTownDetails(Context context) {
        List<stateModel> secretary_count = new ArrayList<stateModel>();
        try {
            secretary_count = new getStateInfo(context){
                @Override
                protected void onPostExecute(List<stateModel> s) {}
            }.execute().get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return secretary_count;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getAutoTownInfo extends AsyncTask<Void, Void, List<LocationInfo.stateModel> >{
        Context mCtx;
        List<stateModel> secretary_count_list = new ArrayList<stateModel>();
        LocationDatabase locationDatabase;

        getStateInfo(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<stateModel> doInBackground(Void... voids) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                secretary_count_list = locationDatabase.getStateInfo().getStateDetails();
                return secretary_count_list;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    */

    //function to auto populate lga, ward and town lists within a certain distance to present location of user
    private void getAutoLists(String state, String useCase){
        Double lat,lng;
        Double distance_x=0.0,distance_y=0.0,distance_z=0.0;
        Double distance;

        if(useCase.equalsIgnoreCase("prefill")){
            distance_x=X1;
            distance_y=Y1;
            distance_z=Z1;
        }

        else if(useCase.equalsIgnoreCase("dropdown")){
            distance_x=X2;
            distance_y=Y2;
            distance_z=Z2;
        }

        List<locationCordsModel> autoLgaCords= getAutoLgaDetails(getApplicationContext(),state);
        for (int i=0; i<autoLgaCords.size(); i++) {
            if (!autoLgaCords.get(i).getLatitude().equalsIgnoreCase("null") && !autoLgaCords.get(i).getLongitude().equalsIgnoreCase("null")) {
                lat = Double.valueOf(autoLgaCords.get(i).getLatitude());
                lng = Double.valueOf(autoLgaCords.get(i).getLongitude());


                distance = calculatedDistance(lat, lng, userLat, userLng);
                if (distance < distance_x) {
                    autotownList.add(autoLgaCords.get(i).getTown());
                    townDistance.put(autoLgaCords.get(i).getTown(),distance);
                }

                if (distance < distance_y) {
                    autowardList.add(autoLgaCords.get(i).getWard());
                    wardDistance.put(autoLgaCords.get(i).getWard(),distance);

                }
                if (distance < distance_z) {
                    autolgaList.add(autoLgaCords.get(i).getLga());
                    lgaDistance.put(autoLgaCords.get(i).getLga(),distance);

                }
            }
            else {

            }

        }

    }

    /*private void getAutoWardDistance(String state){
        Double lat,lng;

        Double distance;
        Map<String,Double> localWardDistance= new HashMap<>();

        List<locationCordsModel> lgaCords= getLgaDetails(getApplicationContext(),state);
        for (int i=0; i<lgaCords.size(); i++){
            lat= Double.valueOf(lgaCords.get(i).getLatitude());
            lng= Double.valueOf(lgaCords.get(i).getLongitude());


            distance= calculatedDistance(lat,lng,userLat,userLng);
            localWardDistance.put(lgaCords.get(i).getLocation(),distance);
        }



    }
    private void getAutoTownDistance(String state){
        Double lat,lng;

        Double distance;
        Map<String,Double> localTownDistance= new HashMap<>();

        List<locationCordsModel> lgaCords= getLgaDetails(getApplicationContext(),state);
        for (int i=0; i<lgaCords.size(); i++){
            lat= Double.valueOf(lgaCords.get(i).getLatitude());
            lng= Double.valueOf(lgaCords.get(i).getLongitude());


            distance= calculatedDistance(lat,lng,userLat,userLng);


        }



    }
*/
    //function used ot create all autocomplete adapters
    public ArrayAdapter adapterGenerator(LinkedHashSet<String> listSet){
        List<String> list = new ArrayList<>();
        list.addAll(listSet);
        ArrayAdapter localAdapter= new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list);

        return localAdapter;
    }

    //function to calculate distance btwn all states and the present position of user
    private void minLgaDistance(){

    }

    //function to calculate distance btwn all states and the present position of user
    private void minWardDistance(){

    }

    //function to calculate distance btwn all states and the present position of user
    private void minTownDistance(){

    }





    //state model creation
    public static class stateModel{

        private String state;
        private String min_lat;
        private String max_lat;
        private String min_long;
        private String max_long;

        public stateModel() {
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getMin_lat() {
            return min_lat;
        }

        public void setMin_lat(String min_lat) {
            this.min_lat = min_lat;
        }

        public String getMax_lat() {
            return max_lat;
        }

        public void setMax_lat(String max_lat) {
            this.max_lat = max_lat;
        }

        public String getMin_long() {
            return min_long;
        }

        public void setMin_long(String min_long) {
            this.min_long = min_long;
        }

        public String getMax_long() {
            return max_long;
        }

        public void setMax_long(String max_long) {
            this.max_long = max_long;
        }
    }

    //location model creation
    public static class locationCordsModel{
        //        private String location;
        private String latitude;
        private String longitude;
        private String lga;
        private String ward;
        private String town;
        private String state;

        public locationCordsModel() {
        }


        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLga() {
            return lga;
        }

        public void setLga(String lga) {
            this.lga = lga;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }


//    public static void main(String[] args)
//    {
//
//        // Creating dummy unsorted map
//        Map<String, Double> unsortMap = new HashMap<String, Double>();
//        unsortMap.put("B", 55.00);
//        unsortMap.put("A", 80.00);
//        unsortMap.put("D", 20.00);
//        unsortMap.put("C", 70);
//
//
//        System.out.println("After sorting ascending order......");
//        Map<String, Double> sortedMapAsc = sortByComparator(unsortMap, ASC);
//        printMap(sortedMapAsc);
//
//
//        System.out.println("After sorting descindeng order......");
//        Map<String, Double> sortedMapDesc = sortByComparator(unsortMap, DESC);
//        printMap(sortedMapDesc);
//
//    }

    //function to sort values in a list based on its values
    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order)
    {

        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>()
        {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }



}
