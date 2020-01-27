package com.example.tgesign_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tgesign_up.Api.GPSController;
import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationInterface;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormMemberInformation extends AppCompatActivity implements FormMemberInformationInterface {

    @BindView(R.id.edtFirstName)
    TextInputEditText edtFirstName;

    @BindView(R.id.edlFirstName)
    TextInputLayout edlFirstName;

    @BindView(R.id.edtLastName)
    TextInputEditText edtLastName;

    @BindView(R.id.edlLastName)
    TextInputLayout edlLastName;

    @BindView(R.id.edtPhoneNumber)
    TextInputEditText edtPhoneNumber;

    @BindView(R.id.edlPhoneNumber)
    TextInputLayout edlPhoneNumber;

    @BindView(R.id.edtAge)
    TextInputEditText edtAge;

    @BindView(R.id.edlAge)
    TextInputLayout edlAge;

    @BindView(R.id.actSex)
    AutoCompleteTextView actSex;

    @BindView(R.id.edlSex)
    TextInputLayout edlSex;

    @BindView(R.id.btnNext)
    MaterialButton btnNext;

    @BindView(R.id.toolbar_tg)
    Toolbar toolbar_tg;

    @BindView(R.id.member_information_confirmation_dialog)
    LinearLayout member_information_confirmation_dialog;

    @BindView(R.id.bottom_sheet_first_name)
    TextView bottom_sheet_first_name;

    @BindView(R.id.bottom_sheet_last_name)
    TextView bottom_sheet_last_name;

    @BindView(R.id.bottom_sheet_phone_number)
    TextView bottom_sheet_phone_number;

    @BindView(R.id.bottom_sheet_age)
    TextView bottom_sheet_age;

    @BindView(R.id.bottom_sheet_sex)
    TextView bottom_sheet_sex;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    FormMemberInformationPresenter memberInformationPresenter;
    FormMemberInformationModel memberInformationModel;
    FormMemberInformationModel.memberKeyDetails memberKeyDetails;

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        ButterKnife.bind(FormMemberInformation.this);
        setSupportActionBar(toolbar_tg);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        GPSController.checkPermission(this);
        GPSController.initialiseLocationListener(this);
        memberInformationPresenter = new FormMemberInformationPresenter(FormMemberInformation.this);
        memberInformationModel = new FormMemberInformationModel();
        memberKeyDetails = new FormMemberInformationModel.memberKeyDetails();

        memberInformationPresenter.enableEditTextViews(false);

        SharedPreference sharedPreference = new SharedPreference(this);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        if (Objects.requireNonNull(user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR))
                .equalsIgnoreCase("Leader")){
            memberKeyDetails = memberInformationModel.getProspectiveTGEDetailsResult(this,user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID));
        }else{
            memberKeyDetails = memberInformationModel.getProspectiveTGLDetailsResult(this,user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID));
        }

        memberInformationModel.setFirst_name(memberKeyDetails.getFirst_name());
        memberInformationModel.setLast_name(memberKeyDetails.getLast_name());

        memberInformationPresenter.setTextOfEditTextViews(true);

        memberInformationPresenter.controlEditTextAppearance(memberInformationPresenter.getRegistrationAction(this));

        memberInformationPresenter.textWatcher(edtPhoneNumber,"phone",edlPhoneNumber,this.getResources().getString(R.string.tfm_error_wrong_phone_number));
        memberInformationPresenter.textWatcher(edtAge,"age",edlAge,this.getResources().getString(R.string.tfm_error_wrong_age));

        toolbar_tg.setNavigationOnClickListener(view -> memberInformationPresenter.loadPreviousActivity());

        sheetBehavior = BottomSheetBehavior.from(member_information_confirmation_dialog);

        addBehaviourToBottomSheet(sheetBehavior);
    }

    void addBehaviourToBottomSheet(BottomSheetBehavior sheetBehavior){
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_DRAGGING:
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    void setBottomSheerTexts(){
        bottom_sheet_first_name.setText(Objects.requireNonNull(edtFirstName.getText()).toString());
        bottom_sheet_last_name.setText(Objects.requireNonNull(edtLastName.getText()).toString());
        bottom_sheet_phone_number.setText(Objects.requireNonNull(edtPhoneNumber.getText()).toString());
        bottom_sheet_age.setText(Objects.requireNonNull(edtAge.getText()).toString());
        bottom_sheet_sex.setText(Objects.requireNonNull(actSex.getText()).toString());
    }

    void showBottomSheet(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    @OnClick(R.id.btnCancel)
    public  void setBtnCancel(){
        showBottomSheet();
    }

    @OnClick(R.id.btnConfirm)
    public  void setBtnConfirm(View view){
        memberInformationPresenter.moveToNextActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tfm_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_help) {
//            try {
//                startActivity(new Intent(FormMemberInformation.this, ViewActivityIssues.class)
//                        .putExtra("activity_id", "tfm_3")
//                        .putExtra("app_id", "tfm")
//                        .putExtra("staff_id", "T-10000000000000AA")//get this guy from shared prefs
//                        .putExtra("user_location", ""));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(FormMemberInformation.this, "Activity not found", Toast.LENGTH_LONG).show();
//            }
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void spinnerViewController(AutoCompleteTextView autoCompleteTextView, ArrayAdapter arrayAdapter) {
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    @Override
    public void setErrorOfTextView(TextInputLayout textInputLayout, String error_message) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error_message);
    }

    @Override
    public void removeErrorFromText(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

    @Override
    public void alertDialogDisplay(Context context, LinearLayout layout) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setView(layout);
        builder.setTitle("Member Information");
        builder.setMessage("(!) Ensure that the details are correct before you proceed")
                .setPositiveButton("Ok", (dialog, id) -> {
                    memberInformationPresenter.saveDetailsToSharedPreference(edtFirstName, edtLastName, edtPhoneNumber, edtAge, actSex,
                             FormMemberInformation.this);
                    memberInformationPresenter.moveToNextActivity();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel())
                .show();
    }

    @Override
    public void moveToAnotherActivity() {
        Intent intent = new Intent (FormMemberInformation.this, FormMemberLocation.class);
        startActivity(intent);
    }

    @Override
    public void loadPreviousActivity() {
        Intent intent = new Intent (FormMemberInformation.this, TGHome.class);
        startActivity(intent);
    }

    @Override
    public void enableEditTextViews(boolean bool) {
        edtFirstName.setEnabled(bool);
        edtLastName.setEnabled(bool);
    }

    @Override
    public void setTextOfEditTextViews(boolean bool) {
        if (bool){
            memberInformationPresenter.setTextOfEditTextViews(edtFirstName,memberInformationModel.getFirst_name());
            memberInformationPresenter.setTextOfEditTextViews(edtLastName,memberInformationModel.getLast_name());
        }
    }

    @Override
    public void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text) {
        textOfEditTextViews.setText(text);
    }

    @Override
    public void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text) {
        autoCompleteTextView.setText(text);
    }

    @Override
    public void controlEditTextAppearance(boolean bool) {
        if (bool){
            memberInformationPresenter.fillThisSpinner(actSex,FormMemberInformation.this,"Sex");
        }else{
            memberInformationPresenter.fillThisSpinner(actSex,FormMemberInformation.this,"Sex");
        }
    }

    @OnClick(R.id.btnNext)
    public  void next(View view){
        if (memberInformationPresenter.validateMemberInfo(edtFirstName,edtLastName,edtPhoneNumber,edtAge,actSex) == 0){
            editorChecker();
        }else if (memberInformationPresenter.phoneNumberChecker(edtPhoneNumber) == 0){
            memberInformationPresenter.setTextViewError(edlPhoneNumber, this.getResources().getString(R.string.tfm_error_wrong_phone_number));
        }else if (memberInformationPresenter.ageChecker(edtAge) == 0){
            memberInformationPresenter.setTextViewError(edlAge,this.getResources().getString(R.string.tfm_error_wrong_age));
        }else{
            //memberInformationPresenter.displayDialog(FormMemberInformation.this,edtFirstName,edtLastName,edtAge,edtPhoneNumber,actSex,actCropType,actMemberRole);
            editorChecker();
            setBottomSheerTexts();
            showBottomSheet();
        }
    }

    void editorChecker(){
        memberInformationPresenter.checkIfTextViewEmpty(edtFirstName,edlFirstName,this.getResources().getString(R.string.tfm_error_first_name));
        memberInformationPresenter.checkIfTextViewEmpty(edtLastName,edlLastName,this.getResources().getString(R.string.tfm_error_last_name));
        memberInformationPresenter.checkIfTextViewEmpty(edtPhoneNumber,edlPhoneNumber,this.getResources().getString(R.string.tfm_error_empty_phone_number));
        memberInformationPresenter.checkIfTextViewEmpty(edtAge,edlAge,this.getResources().getString(R.string.tfm_error_empty_age));
        memberInformationPresenter.checkIfAutocompleteEmpty(actSex,edlSex,this.getResources().getString(R.string.tfm_error_sex));
    }


    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode 419 for  authentication
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        if (requestCode == 419) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //my code
                    sharedPreference.setKeyPassAuthentication("1");
                    memberInformationPresenter.saveDetailsToModelClass(edtFirstName,edtLastName,edtPhoneNumber,edtAge,actSex,
                            FormMemberInformation.this);
                    memberInformationPresenter.loadPreviousActivity();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
