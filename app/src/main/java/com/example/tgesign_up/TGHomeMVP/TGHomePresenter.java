package com.example.tgesign_up.TGHomeMVP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Api.GPSController;
import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.TFM.Table.MembersTable;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.example.tgesign_up.R;
import com.example.tgesign_up.TFMRecyclers.TGRecycler.BottomOffsetDecoration;
import com.example.tgesign_up.TFMRecyclers.TGRecycler.MemberCardRecyclerInterface;
import com.example.tgesign_up.TFMRecyclers.TGRecycler.MemberCardRecyclerViewAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class TGHomePresenter implements TGHomePresenterInterface {
    private TGHomeInterface viewObject;
    private MemberCardRecyclerInterface recyclerInterface;
    private TGModel tgModel;
    private TGLeaderModel tgLeaderModel;
    private MembersTable membersTable;

    public TGHomePresenter(MemberCardRecyclerInterface recyclerInterface){
        this.recyclerInterface = recyclerInterface;
        tgModel = new TGModel();
        tgLeaderModel = new TGLeaderModel();
        membersTable = new MembersTable();

    }

    public TGHomePresenter(TGHomeInterface viewObject){
        this.viewObject = viewObject;
        tgModel = new TGModel();
        tgLeaderModel = new TGLeaderModel();
        membersTable = new MembersTable();
    }

    public TGHomePresenter(){
        tgModel = new TGModel();
        tgLeaderModel = new TGLeaderModel();
        membersTable = new MembersTable();
    }

    @Override
    public void printLeaderFullName(String first_name, String middle_name, String last_name, String role, String member_program){

        String formatted_name = first_name +" "+last_name;
        /*String formatted_name = "";
        if (first_name != null && !first_name.equalsIgnoreCase("")){
            if (middle_name != null && !middle_name.equalsIgnoreCase("")){
                if (last_name != null && !last_name.equalsIgnoreCase("")){
                    formatted_name = first_name + " "+middle_name + " "+last_name;
                }
            }else if (last_name != null && !last_name.equalsIgnoreCase("")){
                formatted_name = first_name +" "+last_name;
            }else{
                formatted_name = first_name;
            }
        }*/
        viewObject.setLeaderSwitchTextView(formatted_name,role,member_program);
    }

    @Override
    public void setTextOfTextViews(TextView view, String starting_text, String texts){
        viewObject.textViewController(view, starting_text, texts);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public TGLeaderModel getLeaderDetails(Context context, String unique_ik_number) {
        TGLeaderModel leader_data = new TGLeaderModel();
        try {
            leader_data = new TGLeaderModel.getLeaderDetails(context){
                @Override
                protected void onPostExecute(TGLeaderModel s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_data;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public TGLeaderModel.CountModel getLeaderCount(Context context, String unique_ik_number) {
        TGLeaderModel.CountModel leader_data = new TGLeaderModel.CountModel();
        try {
            leader_data = new TGLeaderModel.checkLeaderExistence(context){
                @Override
                protected void onPostExecute(TGLeaderModel.CountModel s) {}
            }.execute(unique_ik_number).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_data;
    }

    @Override
    public String ikNumberManipulator(String required_ik_number, String temporary_ik_number) {
        String temp_ik_number;
        if (required_ik_number == null || required_ik_number.equalsIgnoreCase("")){
            temp_ik_number = temporary_ik_number;
        }else{
            temp_ik_number = required_ik_number;
        }
        return temp_ik_number;
    }

    @Override
    public void setLeader_image(Context context, String unique_id, String unique_ik_number){

        int leader_registered_count = tgLeaderModel.getLeaderCount(context,unique_ik_number);
        File imgFile;

        if (leader_registered_count > 0){
            File ImgDirectory = new File(Environment.getExternalStorageDirectory().getPath(), "TFMPictures");
            String image_name = File.separator + tgLeaderModel.getLeaderUniqueIDResult(context,unique_ik_number) + ".jpg";
            imgFile = new File(ImgDirectory.getAbsoluteFile(),image_name);
        }else{
            File ImgDirectory = new File(Environment.getExternalStorageDirectory().getPath(), "TestPictures");
            String image_name = File.separator + unique_id + ".jpg";
            imgFile = new File(ImgDirectory.getAbsoluteFile(),image_name);
        }


        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            viewObject.setLeader_image(myBitmap);
        }
    }

    @Override
    public void recyclerViewSetter(RecyclerView recyclerView, Context context, String unique_ik_number){
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        float offsetPx = context.getResources().getDimension(R.dimen.bottom_offset_dp);
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addItemDecoration(bottomOffsetDecoration);
        MemberCardRecyclerViewAdapter adapter = new MemberCardRecyclerViewAdapter(tgModel.getMemberList(context,unique_ik_number), context);
        viewObject.recyclerController(recyclerView,adapter);
    }

    @Override
    public void switchController(SwitchCompat switchCompat, int count){
        if (count == 0){
            viewObject.setCheckOfSwitch(switchCompat, false);
        }else{
            viewObject.setCheckOfSwitch(switchCompat, true);
        }
    }

    @Override
    public void loadNextActivity(){
        viewObject.loadNextActivity();
    }

    @Override
    public void loadNextActivityFromRecycler(Context context, String unique_member_id) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setUniqueMemberId(unique_member_id);
        recyclerInterface.loadNextActivityFromRecycler();
    }

    @Override
    public void loadNextActivityFromRecyclerNewMember(Context context, String unique_member_id) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setUniqueMemberId(unique_member_id);
        recyclerInterface.loadNextActivityFromRecyclerNewMember();
    }

    @Override
    public void loadNextActivityForLeaderEdit(Context context, String unique_member_id) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setUniqueMemberId(unique_member_id);
        viewObject.loadNextActivityForLeaderEdit();
    }

    @Override
    public void snackBarPresenter(Context context, String message, View view){
        viewObject.displaySnackBar(context,message,view);
    }

    @Override
    public void loadPreviousActivity(){
        viewObject.loadPreviousActivity();
    }

    @Override
    public void displayDialog(String message, Context context) {
        viewObject.displayDialog(message,context);
    }

    @Override
    public void roleToRegisterController(Context context, String role_to_register, String kind_of_registration) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setKeyRoleToRegisterFor(role_to_register);
        sharedPreference.setKeyRegistrationAction(kind_of_registration);
    }

    @Override
    public void setTextOfLocationRecycler(TextView view, String starting_text, String texts) {
        recyclerInterface.textViewController(view,starting_text,texts);
    }

    @Override
    public TGModel.CountModel getMemberCountRecycler(Context context, String unique_member_id) {
        return TGModel.getMemberExistence(context,unique_member_id);
    }

    @Override
    public TGModel.CountModel getRegisteredMemberCountRecycler(Context context, String unique_member_id) {
        return TGModel.getRegisteredMemberExistence(context,unique_member_id);
    }

    @Override
    public void recyclerSwitchController(SwitchCompat switchCompat, int count) {
        if (count == 0){
            recyclerInterface.setCheckOfSwitch(switchCompat, false);
        }else if (count > 0){
            recyclerInterface.setCheckOfSwitch(switchCompat, true);
        }
    }

    @Override
    public void displayDeactivateDialog(String message, Context context, String unique_member_id) {
        recyclerInterface.displayDeactivateDialog(message, context, unique_member_id);
    }

    @Override
    public void displayReactivateDialog(String message, Context context, String unique_member_id) {
        recyclerInterface.displayReactivateDialog(message, context, unique_member_id);
    }

    @Override
    public int deactivateMember(Context context, String unique_member_id) {
        return TGModel.getDeactivatedMemberResult(context,unique_member_id);
    }

    @Override
    public int reactivateMember(Context context, String unique_member_id) {
        return TGModel.getReactivatedMemberResult(context,unique_member_id);
    }

    @Override
    public void displayToast(Context context, String message) {
        recyclerInterface.toastGenerator(context, message);
    }

    @Override
    public void switchRoleToSecretary(Context context, String unique_member_id) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        int switch_member_to_secretary_result = 0;
        TGModel.CountModel secretary_count = TGModel.getSecretaryCountResult(context, unique_ik_number);
        Log.d("secretary_count", unique_ik_number+"|"+ secretary_count.getCount());
        if (secretary_count.getCount() == 0){
            switch_member_to_secretary_result = TGModel.getSwitchRoleResult(context,unique_member_id,context.getResources().getString(R.string.tfm_member_info_role_secretary));
        }else if (secretary_count.getCount() > 0){
            String secretary_unique_member_id = TGModel.getSecretaryIDResult(context, unique_ik_number);
            int switch_secretary_to_member_result = TGModel.getSwitchRoleResult(context,secretary_unique_member_id,context.getResources().getString(R.string.tfm_member_info_role_member));
            if (switch_secretary_to_member_result == 1){
                switch_member_to_secretary_result = TGModel.getSwitchRoleResult(context,unique_member_id,context.getResources().getString(R.string.tfm_member_info_role_secretary));
            }
        }
        Log.d("final_result", String.valueOf(switch_member_to_secretary_result));
    }

    @Override
    public void switchRoleToMember(Context context, String unique_member_id) {
        int switch_secretary_to_member_result = TGModel.getSwitchRoleResult(context,unique_member_id,context.getResources().getString(R.string.tfm_member_info_role_member));
        Log.d("final_result", String.valueOf(switch_secretary_to_member_result));
    }

    @Override
    public String getMemberRoleRecycler(Context context, String unique_member_id) {
        return TGModel.getRoleResult(context,unique_member_id);
    }

    @Override
    public void setTemplateToSharedPreference(Context context, String template, String member_picture,
                                              String bundledTemplate, String member_picture_large) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setTemplate(template);
        sharedPreference.setKeyMemberPicture(member_picture);
        sharedPreference.setKeyBundledTemplate(bundledTemplate);
        sharedPreference.setKeyMemberPictureLarge(member_picture_large);
    }

    @Override
    public String returnTemplateFromSharedPreference(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        return user.get(SharedPreference.KEY_TEMPLATE);
    }

    @Override
    public void displayToastForLeader(Context context, String message) {
        viewObject.displayToast(context, message);
    }

    @Override
    public String getLeaderUniqueID(Context context, String unique_ik_number) {
        return tgLeaderModel.getLeaderUniqueIDResult(context,unique_ik_number);
    }

    @Override
    public String getMemberTemplate(Context context, String unique_member_id) {
        return tgModel.getTemplateResult(context, unique_member_id);
    }

    @Override
    public String returnUniqueMemberIDFromSharedPreference(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        return user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
    }

    @Override
    public void loadNextActivityForLeader() {
        viewObject.loadNextActivityForLeader();
    }

    @Override
    public String getUniqueIKNumberLeaderTemplate(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        return tgLeaderModel.getLeaderTemplateResult(context, user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
    }

    @Override
    public void showDialogForLeaderVerification(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        viewObject.showDialogForLeaderVerification(builder,s,context);
    }

    @Override
    public String getSeasonID(Context context, String unique_ik_number) {
        return tgModel.getSeasonIDResult(context, unique_ik_number);
    }

    @Override
    public String getMemberProgram(Context context, String unique_ik_number) {
        return tgModel.getMemberProgramResult(context, unique_ik_number);
    }

    @Override
    public int getMemberCount(Context context, String unique_ik_number) {
        return tgModel.getMemberCountResult(context, unique_ik_number);
    }

    @Override
    public TFMAppVariables getTFMAppVariables(Context context, String variable_id) {
        return null;
    }

    @Override
    public int getLeaderCountForMember(Context context, String unique_ik_number) {
        return tgModel.getLeaderCountForMemberResult(context, unique_ik_number);
    }

    @Override
    public String getOldMemberTemplate(Context context, String unique_member_id) {
        return tgModel.getOldMemberTemplateResult(context, unique_member_id);
    }

    @Override
    public void displayDialogForOldMembers(String message, Context context, String unique_member_id) {
        recyclerInterface.displayDialogForOldMembers(message, context, unique_member_id);
    }

    @Override
    public void loadFieldMappingForRecycler(Context context, String unique_member_id) {
        recyclerInterface.launchFieldMappingActivity(context,unique_member_id);
    }

    @Override
    public void loadFieldMappingForLeader(Context context, String unique_member_id) {
        viewObject.launchFieldMappingActivity(context, unique_member_id);
    }

    @Override
    public String getTrackerPile(Context context) {
        return tgLeaderModel.getTemplateTrackerPileResult(context);
    }

    @Override
    public void showDialogForFailedCapture(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        viewObject.showDialogForFailedCapture(builder,s,context);
    }

    @Override
    public void showDialogForFailedVerification(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        viewObject.showDialogForFailedVerification(builder,s,context);
    }

    @Override
    public void showDialogForMemberCapture(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        viewObject.showDialogForMemberCapture(builder,s,context);
    }

    public void collateAllResult(Context context, TGLeaderModel tgLeaderModel){
        GPSController.checkPermission(context);
        GPSController.initialiseLocationListener(context);
        SharedPreference sharedPreference = new SharedPreference(context);
        TFMTemplateTrackerTable tfmTemplateTrackerTable = new TFMTemplateTrackerTable();
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String staff_id = user.get(SharedPreference.KEY_STAFF_ID);
        String template = user.get(SharedPreference.KEY_TEMPLATE);
        String bundled_template = user.get(SharedPreference.KEY_BUNDLED_TEMPLATE);
        String member_program = user.get(SharedPreference.KEY_MEMBER_PROGRAM);
        String member_picture_byte_array = user.get(SharedPreference.KEY_MEMBER_PICTURE);
        String member_picture_byte_array_large = user.get(SharedPreference.KEY_MEMBER_PICTURE_LARGE);
        String pass_authentication_flag = user.get(SharedPreference.KEY_PASS_AUTHENTICATION);
        String savePicture, saveResult, bundleSaveResult, savePictureLarge;
        membersTable.setStaff_id(staff_id);
        membersTable.setFirst_name(tgLeaderModel.getFirst_name());
        membersTable.setLast_name(tgLeaderModel.getLast_name());
        membersTable.setUnique_ik_number(tgLeaderModel.getUnique_ik_number());
        membersTable.setPhone_number(tgLeaderModel.getPhone_number());
        membersTable.setDate_of_birth(tgLeaderModel.getDate_of_birth());
        membersTable.setSex(tgLeaderModel.getSex());
        membersTable.setRole(tgLeaderModel.getRole());
        membersTable.setCrop_type(tgLeaderModel.getCrop_type());
        membersTable.setTemplate(template);
        membersTable.setState_id(TGLeaderModel.getStateIDFromLgaIDResult(context,TGLeaderModel.getLgaIDFromWardIDResult(context,tgLeaderModel.getWard_id())));
        membersTable.setLga_id(TGLeaderModel.getLgaIDFromWardIDResult(context,tgLeaderModel.getWard_id()));
        membersTable.setWard_id(tgLeaderModel.getWard_id());
        membersTable.setVillage_name(tgLeaderModel.getVillage_name());
        membersTable.setRegdate(tgLeaderModel.reg_date_generator());
        membersTable.setIk_number(tgLeaderModel.getIk_number());
        membersTable.setMember_program(member_program);
        membersTable.setPass_verification(pass_authentication_flag);
        membersTable.setDelete_flag("0");
        membersTable.setDeactivate_flag("0");
        membersTable.setSync_flag("0");
        membersTable.setOther_crops("");
        membersTable.setOther_not_listed_crops("");
        membersTable.setLatitude(user.get(SharedPreference.KEY_LATITUDE));
        membersTable.setLongitude(user.get(SharedPreference.KEY_LONGITUDE));

        tfmTemplateTrackerTable.setTemplate_id("1");
        tfmTemplateTrackerTable.setTemplate_tracker(bundled_template);

        final String unique_member_id_generated;
        if (Integer.parseInt(tgLeaderModel.getSeason_id()) < 20){
            unique_member_id_generated = tgLeaderModel.getUnique_member_id();
        }else{
            unique_member_id_generated = tgLeaderModel.unique_id_generator(staff_id);
        }
        membersTable.setUnique_member_id(unique_member_id_generated);

        membersTable.setMember_id(tgLeaderModel.getNewID(context,tgLeaderModel.getUnique_ik_number()));

        Bitmap image_bitmap = getBitmap(member_picture_byte_array);
        Bitmap image_bitmap_large = getBitmap(member_picture_byte_array_large);
        savePicture = tgLeaderModel.saveToSdCard(image_bitmap, unique_member_id_generated,"small",context);
        savePictureLarge = tgLeaderModel.saveToSdCard(image_bitmap_large, unique_member_id_generated,"large",context);
        saveResult = tgLeaderModel.getSaveDataResult(context,membersTable);
        bundleSaveResult = tgLeaderModel.saveTrackerPileResult(context,tfmTemplateTrackerTable);

        if (savePicture == null){
            Log.d("image_save_result","Null result");
        }else if (savePicture.equalsIgnoreCase("fileExists")){
            Log.d("image_save_result","Image did not save");
        }else if (savePicture.equalsIgnoreCase("success")){
            Log.d("image_save_result","Image saved");
        }else{
            Log.d("image_save_result","This means hell");
        }

        if (savePictureLarge == null){
            Log.d("image_save_result_L","Null result");
        }else if (savePictureLarge.equalsIgnoreCase("fileExists")){
            Log.d("image_save_result_L","Image did not save");
        }else if (savePictureLarge.equalsIgnoreCase("success")){
            Log.d("image_save_result_L","Image saved");
        }else{
            Log.d("image_save_result_L","This means hell");
        }

        Log.d("save_leader_result", saveResult);
        Log.d("save_tracker_result", bundleSaveResult);
    }

    private Bitmap getBitmap(String member_picture_byte_array){
        byte[] imageAsBytes = new byte[0];
        if (member_picture_byte_array != null) {
            imageAsBytes = Base64.decode(member_picture_byte_array.getBytes(), Base64.DEFAULT);
        }
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
