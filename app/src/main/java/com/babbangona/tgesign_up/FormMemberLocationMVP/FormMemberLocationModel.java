package com.babbangona.tgesign_up.FormMemberLocationMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import androidx.room.Room;

import com.babbangona.tgesign_up.Database.DBContractClass;
import com.babbangona.tgesign_up.Database.Location.LocationDatabase;
import com.babbangona.tgesign_up.Database.Location.Table.LgaTable;
import com.babbangona.tgesign_up.Database.Location.Table.StateTable;
import com.babbangona.tgesign_up.Database.Location.Table.VillageTable;
import com.babbangona.tgesign_up.Database.Location.Table.WardTable;
import com.babbangona.tgesign_up.Database.TFM.TFMDBContractClass;
import com.babbangona.tgesign_up.Database.TFM.TFMDatabase;
import com.babbangona.tgesign_up.Database.TFM.Table.MembersTable;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.babbangona.tgesign_up.Database.TFM.Table.TGE;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FormMemberLocationModel {

    @SerializedName("state_id")
    private String state_id;

    @SerializedName("lga_id")
    private String lga_id;

    @SerializedName("ward_id")
    private String ward_id;

    @SerializedName("state")
    private String state;

    @SerializedName("lga")
    private String lga;

    @SerializedName("ward")
    private String ward;

    @SerializedName("village_name")
    private String village_name;

    @SerializedName("other_crops")
    private String other_crops;

    @SerializedName("other_not_listed_crops")
    private String other_not_listed_crops;

    private int createNewIdFromArray(@NotNull List<Integer> all_lmr_array){
        int[] ret = new int[all_lmr_array.size()];
        Iterator<Integer> iterator = all_lmr_array.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return number(ret);
    }

    private int number(int[] numbers) {
        int new_number_offset;
        Arrays.sort(numbers);
        HashSet<Integer> set = new HashSet<>();

        if (numbers.length > 0){
            int max = numbers[0];

            for (int i = 1; i < numbers.length; i++) {
                if (numbers[i] > max) {
                    max = numbers[i];
                }
            }
            for (int i = numbers[0]; i < numbers[numbers.length - 1]; i++) {
                set.add(i);
            }
            for (int number : numbers) {
                set.remove(number);
            }
            if (set.size() > 0){
                new_number_offset = Collections.min(set);
            }else{
                new_number_offset = max+1;
            }
        }else{
            new_number_offset = 1;
        }
        return new_number_offset;
    }

    public String getState_id() {
        return state_id;
    }

    String unique_id_generator(String staff_id){
        if (staff_id != null){
            if (staff_id.length() > 7){
                staff_id = staff_id.substring(staff_id.length() - 6);
            }else{
                return "T-" + staff_id+"_"+getDate("concat")+"_m";
            }
        }
        return "T-" + staff_id+"_"+getDate("concat")+"_m";
    }

    String new_ik_number_generator(String ik_number){
        if (ik_number != null){
            if (ik_number.length() > 6){
                ik_number = ik_number.substring(ik_number.length() - 6);
            }else{
                return "IK00" + ik_number;
            }
        }
        return "IK00" + ik_number;
    }

    String reg_date_generator(){
        return getDate("spread");
    }

    private String getDate(String module){

        SimpleDateFormat dateFormat1;
        if (module.matches("concat")) {
            dateFormat1 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        }else if (module.matches("spread")) {
            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        }else{
            dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }

        java.util.Date date1 = new java.util.Date();
        return dateFormat1.format(date1);
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getLga_id() {
        return lga_id;
    }

    public void setLga_id(String lga_id) {
        this.lga_id = lga_id;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getOther_crops() {
        return other_crops;
    }

    public void setOther_crops(String other_crops) {
        this.other_crops = other_crops;
    }

    public String getOther_not_listed_crops() {
        return other_not_listed_crops;
    }

    public void setOther_not_listed_crops(String other_not_listed_crops) {
        this.other_not_listed_crops = other_not_listed_crops;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @SuppressLint("StaticFieldLeak")
    int getNewID(Context context, String unique_ik_number) {
        int new_id = 0;
        try {
            List<Integer> all_id = new FormMemberLocationModel.getAllMemberIds(context){
            }.execute(unique_ik_number).get();
            new_id = createNewIdFromArray(all_id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return new_id;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getAllMemberIds extends AsyncTask<String, Void, List<Integer>> {
        Context mCtx;
        List<Integer> memberIDList = new ArrayList<>();
        TFMDatabase tfmDatabase;

        getAllMemberIds(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<Integer> doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                memberIDList = tfmDatabase.getMembersTable().getMemberIdArray(strings[0]);
                return memberIDList;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getState_id(Context c, String state) {
        String state_id = "0";
        try {
            state_id = new FormMemberLocationModel.getLocationIds(c){
            }.execute("state", state).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return state_id;
    }

    @SuppressLint("StaticFieldLeak")
    String getLga_id(Context c, String lga) {
        String lga_id = "0";
        try {
            lga_id = new FormMemberLocationModel.getLocationIds(c){
            }.execute("lga", lga).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return lga_id;
    }

    @SuppressLint("StaticFieldLeak")
    public String getWard_id(Context c, String ward) {
        String ward_id = "0";
        try {
            ward_id = new FormMemberLocationModel.getLocationIds(c){
            }.execute("ward",ward).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return ward_id;
    }

    @SuppressLint("StaticFieldLeak")
    String getSaveDataResult(Context c, MembersTable membersTable) {
        String s = "0";
        try {
            s = new SaveData(c){

            }.execute(membersTable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    String getSaveNewTGDataResult(Context c, TGE tge) {
        String s = "0";
        try {
            s = new getSaveNewTGData(c){

            }.execute(tge).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public static  class getSaveNewTGData extends AsyncTask<TGE, Void, String> {

        //private WeakReference<Main2Model> activityReference;
        Context c;

        getSaveNewTGData(Context mCtx) {
            //activityReference = new WeakReference<>(context);
            this.c = mCtx;
        }

        @Override
        protected String doInBackground(TGE... strings) {

            try {
                Room.databaseBuilder(c,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();
                TFMDatabase tfmDatabase = TFMDatabase.getInstance(c);
                tfmDatabase.getTGEDao().insert(strings[0]);

                return "1";
            } catch (Exception e) {
                Log.d("MUFASA__1", e + "");
                return "0";
            }

        }

    }

    /**
     * This method returns a list of state names
     * */
    @SuppressLint("StaticFieldLeak")
    public static List<String> getState(Context c){
        List<String> state_names = new ArrayList<>();
        try {
            state_names = new FormMemberLocationModel.getDBParams(c){

            }.execute("state").get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        return state_names;
    }

    /**
     * This method returns a list of lga names
     * */
    @SuppressLint("StaticFieldLeak")
    public static List<String> getLga(Context c, String state_name){
        List<String> lst = new ArrayList<>();
        try {
            lst = new FormMemberLocationModel.getDBParams(c){

            }.execute("lga",state_name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * This method returns a list of ward names
     * */
    @SuppressLint("StaticFieldLeak")
    public static List<String> getWard(Context c, String lga_name){
        List<String> lst = new ArrayList<>();
        try {
            lst = new FormMemberLocationModel.getDBParams(c){

            }.execute("ward",lga_name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }


        return lst;
    }

    /**
     * This method returns a list of village names
     * */
    @SuppressLint("StaticFieldLeak")
    static List<String> getVillage(Context c, String ward_name){
        List<String> lst = new ArrayList<>();
        try {
            lst = new FormMemberLocationModel.getDBParams(c){

            }.execute("village",ward_name).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return lst;
    }

    @SuppressLint("StaticFieldLeak")
    public static  class getDBParams extends AsyncTask<String, Void, List<String>> {

        //private WeakReference<Main2Model> activityReference;
        Context c;

        getDBParams(Context mCtx) {
            //activityReference = new WeakReference<>(context);
            this.c = mCtx;
        }


        @Override
        protected List<String> doInBackground(String... strings) {
            List<StateTable> data_state;
            List<LgaTable> data_lga;
            List<WardTable> data_ward;
            List<VillageTable> data_village;

            List<String> data1 = new ArrayList<>();
            Room.databaseBuilder(c,
                    LocationDatabase.class, DBContractClass.LOCATION_DATABASE_NAME).build();
            try{
                LocationDatabase lmr = LocationDatabase.getInstance(c);

                if(strings[0].matches("state")) {
                    data_state = lmr.getState().getState();

                    for(StateTable stateTable : data_state){
                        data1.add(stateTable.getState_name());
                    }

                }
                else if(strings[0].matches("lga")) {

                    String state_id = lmr.getState().getStateId(strings[1]);
                    Log.d("damiLGA", String.valueOf(state_id));
                    data_lga = lmr.getLga().getLga(state_id);

                    for(LgaTable lgaTable : data_lga){
                        data1.add(lgaTable.getLga_name());
                    }
                }

                else if(strings[0].matches("ward")) {

                    String lga_id = lmr.getLga().getLgaId(strings[1]);
                    data_ward = lmr.getWard().getWard(lga_id);


                    for(WardTable wardTable : data_ward){
                        data1.add(wardTable.getWard_name());
                    }
                }

                else if(strings[0].matches("village")) {

                    String ward_id = lmr.getWard().getWardId(strings[1]);
                    data_village = lmr.getVillage().getVillage(ward_id);

                    for(VillageTable villageTable : data_village){
                        data1.add(villageTable.getVillage_name());
                    }
                }


                return data1;
            }catch(Exception e){
                return data1;
            }

        }



    }

    @SuppressLint("StaticFieldLeak")
    public static  class getLocationIds extends AsyncTask<String, Void, String> {

        //private WeakReference<Main2Model> activityReference;
        Context c;

        getLocationIds(Context mCtx) {
            //activityReference = new WeakReference<>(context);
            this.c = mCtx;
        }


        @Override
        protected String doInBackground(String... strings) {

            String id_collected = "0";

            Room.databaseBuilder(c,
                    LocationDatabase.class, DBContractClass.LOCATION_DATABASE_NAME).build();
            try{
                LocationDatabase lmr = LocationDatabase.getInstance(c);

                if(strings[0].matches("state")) {

                    id_collected = lmr.getState().getStateId(strings[1]);

                }
                else if(strings[0].matches("lga")) {

                    id_collected = lmr.getLga().getLgaId(strings[1]);

                }

                else if(strings[0].matches("ward")) {

                    id_collected = lmr.getWard().getWardId(strings[1]);

                }


                return id_collected;
            }catch(Exception e){
                return id_collected;
            }

        }



    }

    @SuppressLint("StaticFieldLeak")
    public static  class SaveData extends AsyncTask<MembersTable, Void, String> {

        //private WeakReference<Main2Model> activityReference;
        Context c;

        SaveData(Context mCtx) {
            //activityReference = new WeakReference<>(context);
            this.c = mCtx;
        }

        @Override
        protected String doInBackground(MembersTable... strings) {

            try {
                Room.databaseBuilder(c,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();
                TFMDatabase tfmDatabase = TFMDatabase.getInstance(c);
                tfmDatabase.getMembersTable().insert(strings[0]);

                return "1";
            } catch (Exception e) {
                Log.d("MUFASA__1", e + "");
                return "0";
            }

        }

    }

    @SuppressLint("StaticFieldLeak")
    FormMemberLocationModel getLeaderLocationDetails(Context context, String unique_ik_number) {
        FormMemberLocationModel leader_location = new FormMemberLocationModel();
        try {
            leader_location = new getLeaderLocation(context){
                @Override
                protected void onPostExecute(FormMemberLocationModel s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_location;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLeaderLocation extends AsyncTask<String, Void, FormMemberLocationModel> {
        Context mCtx;
        FormMemberLocationModel leader_location = new FormMemberLocationModel();
        TFMDatabase tfmDatabase;

        getLeaderLocation(Context context) {
            this.mCtx = context;
        }

        @Override
        protected FormMemberLocationModel doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leader_location = tfmDatabase.getMembersTable().getLeaderLocation(strings[0]);
                return leader_location;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    FormMemberLocationModel getMyLocationDetails(Context context, String unique_member_id) {
        FormMemberLocationModel leader_location = new FormMemberLocationModel();
        try {
            leader_location = new getMyLocationDetails(context){
                @Override
                protected void onPostExecute(FormMemberLocationModel s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_location;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getMyLocationDetails extends AsyncTask<String, Void, FormMemberLocationModel> {
        Context mCtx;
        FormMemberLocationModel leader_location = new FormMemberLocationModel();
        TFMDatabase tfmDatabase;

        getMyLocationDetails(Context context) {
            this.mCtx = context;
        }

        @Override
        protected FormMemberLocationModel doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                leader_location = tfmDatabase.getMembersTable().getMyLocation(strings[0]);
                return leader_location;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getStateName(Context context, String state_id) {
        String state_name = "";
        try {
            state_name = new getStateName(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(state_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return state_name;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getStateName extends AsyncTask<String, Void, String> {
        Context mCtx;
        String state_name;
        LocationDatabase locationDatabase;

        getStateName(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                state_name = locationDatabase.getState().getStateName(strings[0]);
                return state_name;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public String getLgaName(Context context, String lga_id) {
        String lga_name = "";
        try {
            lga_name = new getLgaName(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(lga_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return lga_name;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getLgaName extends AsyncTask<String, Void, String> {
        Context mCtx;
        String lga_name;
        LocationDatabase locationDatabase;

        getLgaName(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                lga_name = locationDatabase.getLga().getLgaName(strings[0]);
                return lga_name;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public String getWardName(Context context, String ward_id) {
        String ward_name = "";
        try {
            ward_name = new getWardName(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(ward_id).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return ward_name;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getWardName extends AsyncTask<String, Void, String> {
        Context mCtx;
        String ward_name;
        LocationDatabase locationDatabase;

        getWardName(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                locationDatabase = LocationDatabase.getInstance(mCtx);
                ward_name = locationDatabase.getWard().getWardName(strings[0]);
                return ward_name;
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getUpdateDataResult(Context c, MembersTable membersTable) {
        String s = "0";
        try {
            s = new updateRecords(c){

            }.execute(membersTable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class updateRecords extends AsyncTask<MembersTable, Void, String> {
        Context mCtx;

        updateRecords(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(MembersTable... strings) {
            try{
                Room.databaseBuilder(mCtx,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();
                TFMDatabase tfmDatabase = TFMDatabase.getInstance(mCtx);
                tfmDatabase.getMembersTable().update(strings[0]);
                return "1";
            }catch (Exception e){
                e.printStackTrace();
                return "0";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getTemplateResult(Context context, String unique_member_id) {
        String result ;
        try {
            result = new getTemplate(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getTemplate extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getTemplate(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberTemplate(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String getMemberProgramResult(Context context, String unique_ik_number) {
        String result ;
        try {
            result = new getMemberProgram(context){
                @Override
                protected void onPostExecute(String s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            result = "MA";
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getMemberProgram extends AsyncTask<String, Void, String> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getMemberProgram(Context context) {
            this.mCtx = context;
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                Log.d("answer_member_program", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberProgram(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return "MA";
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    int getMemberIDResult(Context context, String unique_member_id) {
        int result ;
        try {
            result = new getMemberID(context){
                @Override
                protected void onPostExecute(Integer s) {}
            }.execute(unique_member_id).get();
        } catch (ExecutionException | InterruptedException e) {
            result = 0;
            e.printStackTrace();
        }
        return result;
    }

    @SuppressLint("StaticFieldLeak")
    public static abstract class getMemberID extends AsyncTask<String, Void, Integer> {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getMemberID(Context context) {
            this.mCtx = context;
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                Log.d("answer", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                return tfmDatabase.getMembersTable().getMemberId(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return 0;
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    String saveTrackerPileResult(Context context, TFMTemplateTrackerTable tfmTemplateTrackerTable) {
        String s = "0";
        try {
            s = new saveTrackerPile(context){

            }.execute(tfmTemplateTrackerTable).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return s;
    }

    @SuppressLint("StaticFieldLeak")
    public static  class saveTrackerPile extends AsyncTask<TFMTemplateTrackerTable, Void, String> {

        Context context;
        TFMDatabase tfmDatabase;

        saveTrackerPile(Context context) {
            //activityReference = new WeakReference<>(context);
            this.context = context;
        }

        @Override
        protected String doInBackground(TFMTemplateTrackerTable... strings) {

            try{
                Log.d("tracker_id", Arrays.toString(strings));
                tfmDatabase = TFMDatabase.getInstance(context);
                tfmDatabase.getTFMTemplateTrackerTableDao().insert(strings[0]);
                return "1";
            }catch (Exception e){
                e.printStackTrace();
                return "0";
            }

        }

    }

    String saveToSdCard(Bitmap bitmap, String filename, String child_name, Context context) {

        String stored = null;
        String picture_holder;

        if (child_name.equalsIgnoreCase("small")){
            picture_holder = "TFMPictures";
        }else{
            picture_holder = "TFMPicturesLarge";
        }

        File ChkImgDirectory;
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            ChkImgDirectory = new File(Objects.requireNonNull(context.getExternalFilesDir(null)).getAbsoluteFile(), picture_holder);

            File file, file3;
            File file1 = new File(ChkImgDirectory.getAbsoluteFile(), filename + ".jpg");
            File file2 = new File(ChkImgDirectory.getAbsoluteFile(), ".nomedia");
            if (!ChkImgDirectory.exists() && !ChkImgDirectory.mkdirs()) {
                boolean x = ChkImgDirectory.mkdir();
                Log.d("is_file_created", String.valueOf(x));
                file = file1;
                file3 = file2;
                if (!file3.exists()){
                    try {
                        FileOutputStream outNoMedia = new FileOutputStream(file3);
                        outNoMedia.flush();
                        outNoMedia.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists()){
                    stored = "fileExists";
                }else{
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                        out.flush();
                        out.close();
                        stored = "success";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                file = file1;
                file3 = file2;
                if (!file3.exists()){
                    try {
                        FileOutputStream outNoMedia = new FileOutputStream(file3);
                        outNoMedia.flush();
                        outNoMedia.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (file.exists()){
                    stored = "fileExists";
                }else{
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, out);
                        out.flush();
                        out.close();
                        stored = "success";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return stored;
    }
}
