package com.babbangona.tgesign_up.FormMemberLocationMVP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.babbangona.tgesign_up.Api.GPSController;
import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.BuildConfig;
import com.babbangona.tgesign_up.Database.TFM.Table.MembersTable;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.babbangona.tgesign_up.Database.TFM.Table.TGE;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Objects;

public class FormMemberLocationPresenter implements FormMemberLocationPresenterInterface{

    private FormMemberLocationInterface viewObject;
    private FormMemberLocationModel formMemberLocationModel;
    private MembersTable membersTable;
    private TGE tge;

    public FormMemberLocationPresenter(FormMemberLocationInterface viewObject){
        this.viewObject = viewObject;
        formMemberLocationModel = new FormMemberLocationModel();
        membersTable = new MembersTable();
        tge = new TGE();
    }

    public FormMemberLocationPresenter(){
        formMemberLocationModel = new FormMemberLocationModel();
    }

    @Override
    public void fillStateSpinner(AutoCompleteTextView autoCompleteTextView, Context context, boolean bool) {
        ArrayAdapter state = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, FormMemberLocationModel.getState(context));
        if (bool){
            viewObject.spinnerViewController(autoCompleteTextView,state);
        }
        viewObject.setToStateModel(autoCompleteTextView, bool);
    }

    @Override
    public void fillLgaSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String state, boolean bool) {
        ArrayAdapter lga = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, FormMemberLocationModel.getLga(context, state));
        viewObject.spinnerViewController(autoCompleteTextView,lga);
        viewObject.setToLGAModel(autoCompleteTextView, bool);
    }

    @Override
    public void fillWardSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String lga, boolean bool) {
        ArrayAdapter ward = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, FormMemberLocationModel.getWard(context, lga));
        viewObject.spinnerViewController(autoCompleteTextView,ward);
        viewObject.setToWardModel(autoCompleteTextView, bool);
    }

    @Override
    public void fillVillageSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String ward) {
        ArrayAdapter village = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, FormMemberLocationModel.getVillage(context, ward));
        viewObject.spinnerViewController(autoCompleteTextView,village);
        viewObject.setToVillageModel(autoCompleteTextView);
    }

    @Override
    public void clearSpinnerText(AutoCompleteTextView autoCompleteTextView) {
        viewObject.clearSpinnerText(autoCompleteTextView);
    }


    @Override
    public String getTextFromSpinner(AutoCompleteTextView autoCompleteTextView){
        return autoCompleteTextView.getText().toString();
    }

    @Override
    public int validateMemberInfo(AutoCompleteTextView state, AutoCompleteTextView lga, AutoCompleteTextView ward, AutoCompleteTextView village){

        // Checks if the state field is empty
        if(Objects.requireNonNull(state.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the lga field is empty
        else if(Objects.requireNonNull(lga.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the ward field is empty
        else if(Objects.requireNonNull(ward.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the age village is empty
        else if(Objects.requireNonNull(village.getText()).toString().matches("")) {
            return 0;
        }

        //all checks are passed
        else{
            return 1;
        }
    }

    @Override
    public void checkIfAutocompleteEmpty(AutoCompleteTextView autoCompleteTextView, TextInputLayout textInputLayout, String error_message) {
        String textEntered = Objects.requireNonNull(autoCompleteTextView.getText()).toString();
        if (textEntered.equalsIgnoreCase("")){
            viewObject.setErrorOfTextView(textInputLayout, error_message);
        }else{
            viewObject.removeErrorFromText(textInputLayout);
        }
    }

    @Override
    public void displayDialog(Context context, AutoCompleteTextView state, AutoCompleteTextView lga, AutoCompleteTextView ward,
                              AutoCompleteTextView village, AutoCompleteTextView other_bg_crops, TextInputEditText other_crops_not_listed){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        int paddingDp = 20;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);

        String state_got                    = "State                  : " + getTextFromSpinner(state);
        String lga_got                      = "LGA                    : " + getTextFromSpinner(lga);
        String ward_got                     = "Ward                   : " + getTextFromSpinner(ward);
        String village_got                  = "Village                : " + getTextFromSpinner(village);
        String other_crops_got              = "Other Crops            : " + getTextFromSpinner(other_bg_crops);
        String other_crops_not_listed_got   = "Other Crops not Listed : " + getViewContentText(other_crops_not_listed);

        final TextView first_name = new TextView(context);
        first_name.setText(state_got);
        first_name.setPadding(paddingPixel, 0, 0, 0);
        first_name.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(first_name);

        final TextView last_name = new TextView(context);
        last_name.setText(lga_got);
        last_name.setPadding(paddingPixel, 0, 0, 0);
        last_name.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(last_name);

        final TextView age = new TextView(context);
        age.setText(ward_got);
        age.setPadding(paddingPixel, 0, 0, 0);
        age.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(age);

        final TextView phone_number = new TextView(context);
        phone_number.setText(village_got);
        phone_number.setPadding(paddingPixel, 0, 0, 0);
        phone_number.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(phone_number);

        final TextView sex = new TextView(context);
        sex.setText(other_crops_got);
        sex.setPadding(paddingPixel, 0, 0, 0);
        sex.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(sex);

        final TextView crop = new TextView(context);
        crop.setText(other_crops_not_listed_got);
        crop.setPadding(paddingPixel, 0, 0, 0);
        crop.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(crop);

        viewObject.alertDialogDisplay(context,layout);
    }

    @Override
    public void moveToNextActivity() {
        viewObject.moveToAnotherActivity();
    }

    @Override
    public void loadPreviousActivity() {
        viewObject.loadPreviousActivity();
    }

    @Override
    public boolean getRegistrationAction(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String registration_action = user.get(SharedPreference.KEY_REGISTRATION_ACTION);
        boolean result = false;
        if (registration_action != null) {
            result = registration_action.equalsIgnoreCase("new");
        }
        return result;
    }

    @Override
    public void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text) {
        viewObject.setTextOfEditTextViews(textOfEditTextViews, text);
    }

    @Override
    public void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text) {
        viewObject.setTextOfAutoCompleteTextViews(autoCompleteTextView, text);
    }

    @Override
    public boolean getRegistrationRole(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String registration_role = user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR);
        boolean result = false;
        if (registration_role != null) {
            result = registration_role.equalsIgnoreCase("Leader");
        }
        return result;
    }

    @Override
    public void enableEditTextViewsForMembers(boolean bool) {
        viewObject.enableEditTextViewsForMembers(bool);
    }

    @Override
    public void enableEditTextViewsForEdit(boolean bool) {
        viewObject.enableEditTextViewsForEdit(bool);
    }

    @Override
    public FormMemberLocationModel getLeaderLocation(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        return formMemberLocationModel.getLeaderLocationDetails(context,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
    }

    @Override
    public String getStateName(Context context, String state_id){
        return formMemberLocationModel.getStateName(context, state_id);
    }

    @Override
    public String getLgaName(Context context, String lga_id){
        return formMemberLocationModel.getLgaName(context, lga_id);
    }

    @Override
    public String getWardName(Context context, String ward_id){
        return formMemberLocationModel.getWardName(context, ward_id);
    }

    @Override
    public void getLocationValuesFromID(Context context) {
        viewObject.getLocationValuesFromID(context);
    }

    @Override
    public void setTextsForMembers() {
        viewObject.setTextsForMembers();
    }

    @Override
    public void setTextForEdit() {
        viewObject.setTextForEdit();
    }

    @Override
    public void getMyLocationValuesFromID(Context context) {
        viewObject.getMyLocationValuesFromID(context);
    }

    @Override
    public void startActivityForResult() {
        viewObject.startActivityForResult();
    }

    @Override
    public void secretaryPresenceDialog(Context context) {
        viewObject.secretaryPresenceDialog(context);
    }

    @Override
    public void secretaryTGQuestion(Context context) {
        viewObject.secretaryTGQuestion(context);
    }

    @Override
    public void startHomeActivity() {
        viewObject.startHomeActivity();
    }

    @Override
    public void startTGMembersActivity() {
        viewObject.startTGMembersActivity();
    }

    @Override
    public int validateMemberInfo(AutoCompleteTextView state, AutoCompleteTextView lga, AutoCompleteTextView ward) {
        // Checks if the state field is empty
        if(Objects.requireNonNull(state.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the lga field is empty
        else if(Objects.requireNonNull(lga.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the ward field is empty
        else if(Objects.requireNonNull(ward.getText()).toString().matches("")) {
            return 0;
        }

        //all checks are passed
        else{
            return 1;
        }
    }

    @Override
    public FormMemberLocationModel getMyLocation(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        return formMemberLocationModel.getMyLocationDetails(context,user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID));
    }

    @Override
    public String getViewContentText(TextInputEditText textInputEditText){
        return Objects.requireNonNull(textInputEditText.getText()).toString();
    }

    @Override
    public void collateAllResult(Context context, String state, String lga, String ward, String village, View view, boolean bool){
        GPSController.checkPermission(context);
        GPSController.initialiseLocationListener(context);
        SharedPreference sharedPreference = new SharedPreference(context);
        TFMTemplateTrackerTable tfmTemplateTrackerTable = new TFMTemplateTrackerTable();
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String bundled_template = user.get(SharedPreference.KEY_BUNDLED_TEMPLATE);
        String unique_member_id = user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
        String qr_ik_number = user.get(SharedPreference.KEY_QR_IK_NUMBER);
        String staff_id = user.get(SharedPreference.KEY_STAFF_ID);
        String first_name = user.get(SharedPreference.KEY_FIRST_NAME);
        String last_name = user.get(SharedPreference.KEY_LAST_NAME);
        String phone_number = user.get(SharedPreference.KEY_PHONE_NUMBER);
        String birth_day = user.get(SharedPreference.KEY_MEMBER_BIRTHDAY);
        String sex = user.get(SharedPreference.KEY_SEX);
        String member_role = user.get(SharedPreference.KEY_MEMBER_ROLE);
        String crop_type = user.get(SharedPreference.KEY_CROP_TYPE);
        String template = user.get(SharedPreference.KEY_TEMPLATE);
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        String ik_number = user.get(SharedPreference.KEY_IK_NUMBER);
        String member_picture_byte_array = user.get(SharedPreference.KEY_MEMBER_PICTURE);
        String member_picture_byte_array_large = user.get(SharedPreference.KEY_MEMBER_PICTURE_LARGE);
        String pass_authentication_flag = user.get(SharedPreference.KEY_PASS_AUTHENTICATION);
        String member_program = formMemberLocationModel.getMemberProgramResult(context,unique_ik_number);
        String saveResult, savePicture, bundleSaveResult, saveResult1 = "",savePictureLarge;
        membersTable.setStaff_id(staff_id);
        membersTable.setFirst_name(first_name);
        membersTable.setLast_name(last_name);
        membersTable.setPhone_number(phone_number);
        membersTable.setDate_of_birth(birth_day);
        membersTable.setSex(sex);
        membersTable.setRole(member_role);
        membersTable.setCrop_type(crop_type);
        membersTable.setState_id(formMemberLocationModel.getState_id(context,state));
        membersTable.setLga_id(formMemberLocationModel.getLga_id(context,lga));
        membersTable.setWard_id(formMemberLocationModel.getWard_id(context,ward));
        membersTable.setVillage_name(village);
        membersTable.setRegdate(formMemberLocationModel.reg_date_generator());
        membersTable.setPass_verification(pass_authentication_flag);
        membersTable.setMember_program(member_program);
        membersTable.setMember_id(1);
        membersTable.setDelete_flag("0");
        membersTable.setDeactivate_flag("0");
        membersTable.setSync_flag("0");
        membersTable.setLatitude(user.get(SharedPreference.KEY_LATITUDE));
        membersTable.setLongitude(user.get(SharedPreference.KEY_LONGITUDE));
        membersTable.setImei(user.get(SharedPreference.KEY_PHONE_IMEI));
        membersTable.setTemplate(template);

        tge.setFirst_name(first_name);
        tge.setLast_name(last_name);
        tge.setStaff_id(staff_id);
        tge.setApp_version(BuildConfig.VERSION_NAME);
        tge.setTge_id(formMemberLocationModel.new_ik_number_generator(ik_number));
        if (unique_member_id != null) {
            tge.setUnique_member_id(unique_member_id);
        }
        tge.setSync_flag("0");
        tge.setImei(user.get(SharedPreference.KEY_PHONE_IMEI));


        tfmTemplateTrackerTable.setTemplate_id("1");
        tfmTemplateTrackerTable.setTemplate_tracker(bundled_template);

        if (unique_member_id != null) {
                membersTable.setUnique_member_id(unique_member_id);
        }
        if (Objects.requireNonNull(user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR)).
                    equalsIgnoreCase("Leader")){
            membersTable.setUnique_ik_number(ik_number);
            membersTable.setIk_number(formMemberLocationModel.new_ik_number_generator(ik_number));
            membersTable.setTge_id(formMemberLocationModel.new_ik_number_generator(ik_number));
            saveResult = formMemberLocationModel.getSaveDataResult(context,membersTable);
            saveResult1 = formMemberLocationModel.getSaveNewTGDataResult(context,tge);
        }else{
            membersTable.setUnique_ik_number(qr_ik_number);
            membersTable.setIk_number(qr_ik_number);
            membersTable.setTge_id(formMemberLocationModel.new_ik_number_generator(ik_number));
            saveResult = formMemberLocationModel.getSaveDataResult(context,membersTable);
        }

        Log.d("result_save",saveResult);
        Log.d("result_save1",saveResult1);

        Bitmap image_bitmap = getBitmap(member_picture_byte_array);
        Bitmap image_bitmap_large = getBitmap(member_picture_byte_array_large);
        savePicture = formMemberLocationModel.saveToSdCard(image_bitmap, unique_member_id,"small",context);
        savePictureLarge = formMemberLocationModel.saveToSdCard(image_bitmap_large, unique_member_id,"large",context);


        bundleSaveResult = formMemberLocationModel.saveTrackerPileResult(context,tfmTemplateTrackerTable);
        Log.d("save_tracker_result", bundleSaveResult+""+savePictureLarge);

        if (savePicture == null){
            Log.d("image_save_result","Null result");
        }else if (savePicture.equalsIgnoreCase("fileExists")){
            Log.d("image_save_result","Image did not save");
        }else if (savePicture.equalsIgnoreCase("success")){
            Log.d("image_save_result","Image saved");
        }else{
            Log.d("image_save_result","This means hell");
        }


    }

    private Bitmap getBitmap(String member_picture_byte_array){
        byte[] imageAsBytes = new byte[0];
        if (member_picture_byte_array != null) {
            imageAsBytes = Base64.decode(member_picture_byte_array.getBytes(), Base64.DEFAULT);
        }
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }

}
