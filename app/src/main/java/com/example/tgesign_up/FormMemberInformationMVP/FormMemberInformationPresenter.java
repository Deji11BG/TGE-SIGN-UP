package com.example.tgesign_up.FormMemberInformationMVP;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tgesign_up.Api.GPSController;
import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.TFM.Table.MembersTable;
import com.example.tgesign_up.Database.TFM.Table.TFMTemplateTrackerTable;
import com.example.tgesign_up.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FormMemberInformationPresenter implements FormMemberInformationPresenterInterface {
    private FormMemberInformationInterface viewObject;
    private FormMemberInformationModel formMemberInformationModel;

    private MembersTable membersTable;

    public FormMemberInformationPresenter(FormMemberInformationInterface viewObject){
        this.viewObject = viewObject;
        formMemberInformationModel = new FormMemberInformationModel();
        membersTable = new MembersTable();
    }


    @Override
    public void fillThisSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String command) {
        List<String> spinnerList;
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        if (command.equalsIgnoreCase(context.getResources().getString(R.string.tfm_member_info_sex))){
            spinnerList = formMemberInformationModel.getSexList(context);
        }else if (command.equalsIgnoreCase(context.getResources().getString(R.string.tfm_member_info_crop_type))){
            spinnerList = formMemberInformationModel.getCropList(context);
        }else if (command.equalsIgnoreCase(context.getResources().getString(R.string.tfm_member_info_member_role))){
            spinnerList = formMemberInformationModel.getRoleList(context,unique_ik_number);
        }else{
            spinnerList = formMemberInformationModel.getRoleList(context,unique_ik_number);
        }
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, spinnerList);
        viewObject.spinnerViewController(autoCompleteTextView,spinnerAdapter);
    }

    @Override
    public void checkIfTextViewEmpty(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String error_message) {
        String textEntered = Objects.requireNonNull(textInputEditText.getText()).toString();
        if (textEntered.equalsIgnoreCase("")){
            viewObject.setErrorOfTextView(textInputLayout, error_message);
        }else{
            viewObject.removeErrorFromText(textInputLayout);
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
    public int validateMemberInfo(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                                  TextInputEditText age, AutoCompleteTextView sex, AutoCompleteTextView member_role,
                                  AutoCompleteTextView crop_type){

        // Checks if the first_name field is empty
        if(Objects.requireNonNull(first_name.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the last_name field is empty
        else if(Objects.requireNonNull(last_name.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the phone number field is empty
        else if(Objects.requireNonNull(phone_number.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the age field is empty
        else if(Objects.requireNonNull(age.getText()).toString().matches("")) {
            return 0;
        }

        // Checks if the sex field is empty
        else if(sex.getText().toString().matches("")) {
            return 0;
        }

        // Checks if the member role field is empty
        else if(member_role.getText().toString().matches("")) {
            return 0;
        }

        // Checks if the crop type field is empty
        else if(crop_type.getText().toString().matches("")) {
            return 0;

        }

        //Save all data to shared preferences if all the checks are passed
        else{
            return 1;
        }
    }

    @Override
    public int phoneNumberChecker(TextInputEditText phone_number) {
        if(Objects.requireNonNull(phone_number.getText()).toString().length() != 11) {
            return 0;
        }else if (!phone_number.getText().toString().startsWith("0")){
            return 0;
        } else{
            return 1;
        }
    }

    @Override
    public int ageChecker(TextInputEditText age) {
        if(Integer.valueOf(Objects.requireNonNull(age.getText()).toString()) < 18) {
            return 0;
        }else if (Integer.valueOf(age.getText().toString()) > 112){
            return 0;
        } else{
            return 1;
        }
    }

    @Override
    public void setTextViewError(TextInputLayout textInputLayout, String error_message) {
        viewObject.setErrorOfTextView(textInputLayout, error_message);
    }

    @Override
    public void displayDialog(Context context, TextInputEditText et_first_name, TextInputEditText et_last_name, TextInputEditText et_age,
                              TextInputEditText et_phone_number, AutoCompleteTextView sp_sex, AutoCompleteTextView sp_crop, AutoCompleteTextView sp_role){
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        int paddingDp = 20;
        float density = context.getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);

        String first_name_got           = "First Name   : " + getViewContentText(et_first_name);
        String last_name_got            = "Last Name    : " + getViewContentText(et_last_name);
        String age_got                  = "Age          : " + getViewContentText(et_age);
        String phone_number_got         = "Phone Number : " + getViewContentText(et_phone_number);
        String sex_got                  = "Sex          : " + getSpinnerContent(sp_sex);
        String crop_got                 = "Crop Type    : " + getSpinnerContent(sp_crop);
        String role_got                 = "Member Role  : " + getSpinnerContent(sp_role);

        final TextView first_name = new TextView(context);
        first_name.setText(first_name_got);
        first_name.setPadding(paddingPixel, 0, 0, 0);
        first_name.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(first_name);

        final TextView last_name = new TextView(context);
        last_name.setText(last_name_got);
        last_name.setPadding(paddingPixel, 0, 0, 0);
        last_name.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(last_name);

        final TextView age = new TextView(context);
        age.setText(age_got);
        age.setPadding(paddingPixel, 0, 0, 0);
        age.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(age);

        final TextView phone_number = new TextView(context);
        phone_number.setText(phone_number_got);
        phone_number.setPadding(paddingPixel, 0, 0, 0);
        phone_number.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(phone_number);

        final TextView sex = new TextView(context);
        sex.setText(sex_got);
        sex.setPadding(paddingPixel, 0, 0, 0);
        sex.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(sex);

        final TextView crop = new TextView(context);
        crop.setText(crop_got);
        crop.setPadding(paddingPixel, 0, 0, 0);
        crop.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(crop);

        final TextView role = new TextView(context);
        role.setText(role_got);
        role.setPadding(paddingPixel, 0, 0, 0);
        role.setTypeface(null, Typeface.BOLD_ITALIC);
        layout.addView(role);

        viewObject.alertDialogDisplay(context,layout);
    }

    @Override
    public String getViewContentText(TextInputEditText textInputEditText){
        return Objects.requireNonNull(textInputEditText.getText()).toString();
    }

    @Override
    public String getSpinnerContent(AutoCompleteTextView autoCompleteTextView){
        return Objects.requireNonNull(autoCompleteTextView.getText()).toString();
    }

    @Override
    public void moveToNextActivity() {
        viewObject.moveToAnotherActivity();
    }

    @Override
    public void saveDetailsToSharedPreference(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                                              TextInputEditText age, AutoCompleteTextView sex, AutoCompleteTextView member_role,
                                              AutoCompleteTextView crop_type,
                                              Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setKeyFirstName(getViewContentText(first_name));
        sharedPreference.setKeyLastName(getViewContentText(last_name));
        sharedPreference.setKeyPhoneNumber(getViewContentText(phone_number));
        sharedPreference.setKeyAge(getViewContentText(age));
        sharedPreference.setKeySex(getSpinnerContent(sex));
        sharedPreference.setKeyMemberRole(getSpinnerContent(member_role));
        sharedPreference.setKeyCropType(getSpinnerContent(crop_type));
        sharedPreference.setKeyMemberBirthday(calculateBirthDate(Integer.valueOf(getViewContentText(age))));
    }

    @Override
    public void loadPreviousActivity() {
        viewObject.loadPreviousActivity();
    }

    @Override
    public void enableEditTextViews(boolean bool) {
        viewObject.enableEditTextViews(bool);
    }

    @Override
    public boolean getRegistrationAction(Context context){
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
    public String autoFillEditTexts(boolean bool, Context context) {
        String unique_member_id = "";
        if (!bool){
            SharedPreference sharedPreference = new SharedPreference(context);
            HashMap<String, String> user = sharedPreference.getUserDetails();
            unique_member_id = user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
        }
        return unique_member_id;
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
    public void setTextOfEditTextViews(boolean bool) {
        viewObject.setTextOfEditTextViews(bool);
    }

    @Override
    public String calculateAge(String date_of_birth){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date dateOfBirth = null;
        try {
            dateOfBirth = dateFormat.parse(date_of_birth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar now = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        if (dateOfBirth != null) {
            dob.setTime(dateOfBirth);
        }
        if (dob.after(now)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = now.get(Calendar.MONTH);
        int month2 = dob.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = now.get(Calendar.DAY_OF_MONTH);
            int day2 = dob.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }
        return String.valueOf(age);
    }

    @Override
    public void controlEditTextAppearance(boolean bool) {
        viewObject.controlEditTextAppearance(bool);
    }

    @Override
    public void saveDetailsToModelClass(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                                        TextInputEditText age, AutoCompleteTextView sex, AutoCompleteTextView member_role,
                                        AutoCompleteTextView crop_type, Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        String ik_number = user.get(SharedPreference.KEY_IK_NUMBER);
        FormMemberInformationModel formMemberInformationModel = new FormMemberInformationModel();
        formMemberInformationModel.setFirst_name(getViewContentText(first_name));
        formMemberInformationModel.setLast_name(getViewContentText(last_name));
        formMemberInformationModel.setPhone_number(getViewContentText(phone_number));
        formMemberInformationModel.setDate_of_birth(calculateBirthDate(Integer.valueOf(getViewContentText(age))));
        formMemberInformationModel.setSex(getSpinnerContent(sex));
        formMemberInformationModel.setRole(getSpinnerContent(member_role));
        formMemberInformationModel.setCrop_type(getSpinnerContent(crop_type));
        formMemberInformationModel.setUnique_ik_number(unique_ik_number);
        formMemberInformationModel.setIk_number(ik_number);

        FormMemberInformationModel.MyLeaderLocation myLeaderLocation = FormMemberInformationModel.getLeaderLocationResult(context, unique_ik_number);
        formMemberInformationModel.setWard_id(myLeaderLocation.getLeader_ward_id());
        formMemberInformationModel.setVillage_name(myLeaderLocation.getLeader_village_name());
        formMemberInformationModel.setMember_program(myLeaderLocation.getMember_program());

        collateAllResult(context,formMemberInformationModel);
    }

    @Override
    public void textWatcher(final TextInputEditText textInputEditText, final String module, final TextInputLayout textInputLayout, final String error_message) {
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Objects.requireNonNull(textInputEditText.getText()).toString().length() > 0){
                    if (!validateFields(module,textInputEditText)){
                        viewObject.setErrorOfTextView(textInputLayout,error_message);
                    }else {
                        viewObject.removeErrorFromText(textInputLayout);
                    }
                }else{
                    viewObject.removeErrorFromText(textInputLayout);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String calculateBirthDate(int age){
        String day, month, year;
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(new Date());
        String[] time = timeStamp.split("/");

        day = time[2];
        month = time[1];
        year = time[0];

        int birthYear = Integer.valueOf(year) - age;
        return   birthYear + "-" + month + "-" + day;
    }

    private void collateAllResult(Context context, FormMemberInformationModel formMemberInformationModel){
        GPSController.checkPermission(context);
        GPSController.initialiseLocationListener(context);
        SharedPreference sharedPreference = new SharedPreference(context);
        TFMTemplateTrackerTable tfmTemplateTrackerTable = new TFMTemplateTrackerTable();
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String staff_id = user.get(SharedPreference.KEY_STAFF_ID);
        String template = user.get(SharedPreference.KEY_TEMPLATE);
        String pass_authentication = user.get(SharedPreference.KEY_PASS_AUTHENTICATION);
        String member_picture_byte_array = user.get(SharedPreference.KEY_MEMBER_PICTURE);
        String bundled_template = user.get(SharedPreference.KEY_BUNDLED_TEMPLATE);
        String savePicture, saveResult, bundleSaveResult;
        membersTable.setStaff_id(staff_id);
        membersTable.setFirst_name(formMemberInformationModel.getFirst_name());
        membersTable.setLast_name(formMemberInformationModel.getLast_name());
        membersTable.setUnique_ik_number(formMemberInformationModel.getUnique_ik_number());
        membersTable.setPhone_number(formMemberInformationModel.getPhone_number());
        membersTable.setDate_of_birth(formMemberInformationModel.getDate_of_birth());
        membersTable.setSex(formMemberInformationModel.getSex());
        membersTable.setRole(formMemberInformationModel.getRole());
        membersTable.setCrop_type(formMemberInformationModel.getCrop_type());
        membersTable.setTemplate(template);
        membersTable.setPass_verification(pass_authentication);
        membersTable.setState_id(FormMemberInformationModel.getStateIDFromLgaIDResult(context,FormMemberInformationModel.getLgaIDFromWardIDResult(context,formMemberInformationModel.getWard_id())));
        membersTable.setLga_id(FormMemberInformationModel.getLgaIDFromWardIDResult(context,formMemberInformationModel.getWard_id()));
        membersTable.setWard_id(formMemberInformationModel.getWard_id());
        membersTable.setVillage_name(formMemberInformationModel.getVillage_name());
        membersTable.setRegdate(formMemberInformationModel.reg_date_generator());
        membersTable.setIk_number(formMemberInformationModel.getIk_number());
        membersTable.setMember_program(formMemberInformationModel.getMember_program());
        membersTable.setDelete_flag("0");
        membersTable.setDeactivate_flag("0");
        membersTable.setSync_flag("0");
        membersTable.setOther_crops("");
        membersTable.setOther_not_listed_crops("");
        membersTable.setLatitude(user.get(SharedPreference.KEY_LATITUDE));
        membersTable.setLongitude(user.get(SharedPreference.KEY_LONGITUDE));

        tfmTemplateTrackerTable.setTemplate_id("1");
        tfmTemplateTrackerTable.setTemplate_tracker(bundled_template);

        final String unique_member_id_generated = formMemberInformationModel.unique_id_generator(staff_id);
        membersTable.setUnique_member_id(unique_member_id_generated);
        membersTable.setMember_id(formMemberInformationModel.getNewID(context,formMemberInformationModel.getUnique_ik_number()));
        byte[] imageAsBytes = new byte[0];
        if (member_picture_byte_array != null) {
            imageAsBytes = Base64.decode(member_picture_byte_array.getBytes(), Base64.DEFAULT);
        }
        Bitmap image_bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        savePicture = formMemberInformationModel.saveToSdCard(image_bitmap, unique_member_id_generated);
        saveResult = formMemberInformationModel.getSaveDataResult(context,membersTable);
        bundleSaveResult = formMemberInformationModel.saveTrackerPileResult(context,tfmTemplateTrackerTable);
        if (savePicture == null){
            Log.d("image_save_result","Null result");
        }else if (savePicture.equalsIgnoreCase("fileExists")){
            Log.d("image_save_result","Image did not save");
        }else if (savePicture.equalsIgnoreCase("success")){
            Log.d("image_save_result","Image saved");
        }else{
            Log.d("image_save_result","This means hell");
        }

        Log.d("save_leader_result", saveResult);
        Log.d("save_tracker_result", bundleSaveResult);
    }

    private boolean validateFields(String module, TextInputEditText textInputEditText){
        String watchWord = Objects.requireNonNull(textInputEditText.getText()).toString();
        if (module.equalsIgnoreCase("phone")){
            if( watchWord.length() < 10 ||
                    watchWord.length() > 14 ||
                    watchWord.length()==12 ) {
                return false;
            }else{
                return watchWord.matches("^0(70|90|80|81)\\d{8}");
            }
        }else if (module.equalsIgnoreCase("age")){
            return Integer.valueOf(watchWord) >= 18 && Integer.valueOf(watchWord) <= 112;
        }else{
            return false;
        }
    }

}
