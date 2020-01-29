package com.babbangona.tgesign_up;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.babbangona.tgesign_up.Api.GPSController;
import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Api.UploadTFMData;
import com.babbangona.tgesign_up.Api.uploadSchedule;
import com.babbangona.tgesign_up.FormMemberLocationMVP.FormMemberLocationInterface;
import com.babbangona.tgesign_up.FormMemberLocationMVP.FormMemberLocationModel;
import com.babbangona.tgesign_up.FormMemberLocationMVP.FormMemberLocationPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormMemberLocation extends AppCompatActivity implements FormMemberLocationInterface {

    @BindView(R.id.actState)
    AutoCompleteTextView actState;

    @BindView(R.id.edlState)
    TextInputLayout edlState;

    @BindView(R.id.actLga)
    AutoCompleteTextView actLga;

    @BindView(R.id.edlLga)
    TextInputLayout edlLga;

    @BindView(R.id.actWard)
    AutoCompleteTextView actWard;

    @BindView(R.id.edlWard)
    TextInputLayout edlWard;

    @BindView(R.id.actVillage)
    AutoCompleteTextView actVillage;

    @BindView(R.id.edlVillage)
    TextInputLayout edlVillage;

    @BindView(R.id.toolbar_tg)
    Toolbar toolbar_tg;

    @BindView(R.id.btnAddMember)
    MaterialButton btnAddMember;

    @BindView(R.id.member_location_confirmation_dialog)
    LinearLayout member_location_confirmation_dialog;

    FormMemberLocationPresenter memberLocationPresenter;
    FormMemberLocationModel memberLocationModel;

    @BindView(R.id.bottom_sheet_state)
    TextView bottom_sheet_state;

    @BindView(R.id.bottom_sheet_lga)
    TextView bottom_sheet_lga;

    @BindView(R.id.bottom_sheet_ward)
    TextView bottom_sheet_ward;

    @BindView(R.id.bottom_sheet_village)
    TextView bottom_sheet_village;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private BottomSheetBehavior sheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_member_locaton);
        ButterKnife.bind(FormMemberLocation.this);
        setSupportActionBar(toolbar_tg);
        GPSController.checkPermission(this);
        GPSController.initialiseLocationListener(this);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        memberLocationPresenter = new FormMemberLocationPresenter(FormMemberLocation.this);
        memberLocationModel = new FormMemberLocationModel();


        memberLocationPresenter.fillStateSpinner(actState,FormMemberLocation.this,
                memberLocationPresenter.getRegistrationAction(this));

        toolbar_tg.setNavigationOnClickListener(view -> memberLocationPresenter.loadPreviousActivity());

        sheetBehavior = BottomSheetBehavior.from(member_location_confirmation_dialog);

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
        bottom_sheet_state.setText(Objects.requireNonNull(actState.getText()).toString());
        bottom_sheet_lga.setText(Objects.requireNonNull(actLga.getText()).toString());
        bottom_sheet_ward.setText(Objects.requireNonNull(actWard.getText()).toString());
        bottom_sheet_village.setText(Objects.requireNonNull(actVillage.getText()).toString());
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
        launchLuxand(this);
    }

    //mCtx.getResources().getString(R.string.verify_intro)

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
        int id = item.getItemId();
        if (id == R.id.action_help) {

            UploadTFMData uploadTFMData = new UploadTFMData(getApplicationContext());
            uploadTFMData.syncData();

            uploadSchedule cls2 = new uploadSchedule();
            cls2.getScheduleRecords(getApplicationContext());
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void spinnerViewController(AutoCompleteTextView autoCompleteTextView, ArrayAdapter arrayAdapter) {
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    @Override
    public void setToStateModel(AutoCompleteTextView autoCompleteTextView, final boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actLga);
            memberLocationPresenter.clearSpinnerText(actWard);
            memberLocationPresenter.clearSpinnerText(actVillage);
            String state_selection = (String) parent.getItemAtPosition(position);
            memberLocationModel.setState(state_selection);
            memberLocationPresenter.fillLgaSpinner(actLga, FormMemberLocation.this, memberLocationModel.getState(), bool);
        });
    }

    @Override
    public void setToLGAModel(AutoCompleteTextView autoCompleteTextView, final boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actWard);
            memberLocationPresenter.clearSpinnerText(actVillage);
            String lga_selection = (String) parent.getItemAtPosition(position);
            memberLocationModel.setLga(lga_selection);
            memberLocationPresenter.fillWardSpinner(actWard, FormMemberLocation.this, memberLocationModel.getLga(), bool);
        });
    }

    @Override
    public void setToWardModel(AutoCompleteTextView autoCompleteTextView, boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actVillage);
            String ward_selection = (String) parent.getItemAtPosition(position);
            memberLocationModel.setWard(ward_selection);
            memberLocationPresenter.fillVillageSpinner(actVillage, FormMemberLocation.this, memberLocationModel.getWard());
        });

    }

    @Override
    public void setToVillageModel(AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            String village_selection = (String) parent.getItemAtPosition(position);
            memberLocationModel.setVillage_name(village_selection);
        });
    }

    @Override
    public void clearSpinnerText(AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setText("");
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
    public void alertDialogDisplay(Context context, LinearLayout linearLayout) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setView(linearLayout);
        builder.setTitle(context.getResources().getString(R.string.tfm_member_info_label));
        builder.setMessage(context.getResources().getString(R.string.alert_warning))
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
                    //This is to start authentication activity
                    memberLocationPresenter.startActivityForResult();
                })
                .setNegativeButton(context.getResources().getString(R.string.cancel), (dialog, id) -> dialog.cancel())
                .show();
    }

    @Override
    public void displaySnackBar(View view, String message) {
        Snackbar.make(view,message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void moveToAnotherActivity() {
        Intent intent = new Intent (FormMemberLocation.this, TFMHome.class);
        startActivity(intent);
    }

    @Override
    public void loadPreviousActivity() {
        Intent intent = new Intent (FormMemberLocation.this, FormMemberInformation.class);
        startActivity(intent);
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
    public void enableEditTextViewsForMembers(boolean bool) {
        actState.setEnabled(bool);
        actLga.setEnabled(bool);
    }

    @Override
    public void enableEditTextViewsForEdit(boolean bool) {
        actState.setEnabled(bool);
        actLga.setEnabled(bool);
        actWard.setEnabled(bool);
    }

    @Override
    public void getLocationValuesFromID(Context context) {
        memberLocationModel = memberLocationPresenter.getLeaderLocation(context);
        memberLocationModel.setState(memberLocationPresenter.getStateName(context,memberLocationModel.getState_id()));
        memberLocationModel.setLga(memberLocationPresenter.getLgaName(context,memberLocationModel.getLga_id()));
        memberLocationModel.setWard(memberLocationPresenter.getWardName(context,memberLocationModel.getWard_id()));
    }

    @Override
    public void setTextsForMembers() {
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actState,memberLocationModel.getState());
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actLga, memberLocationModel.getLga());
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actWard, memberLocationModel.getWard());
    }

    @Override
    public void setTextForEdit() {
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actState,memberLocationModel.getState());
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actLga, memberLocationModel.getLga());
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actWard, memberLocationModel.getWard());
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actVillage, memberLocationModel.getVillage_name());
    }

    @Override
    public void getMyLocationValuesFromID(Context context) {
        memberLocationModel = memberLocationPresenter.getMyLocation(context);
        memberLocationModel.setState(memberLocationPresenter.getStateName(context,memberLocationModel.getState_id()));
        memberLocationModel.setLga(memberLocationPresenter.getLgaName(context,memberLocationModel.getLga_id()));
        memberLocationModel.setWard(memberLocationPresenter.getWardName(context,memberLocationModel.getWard_id()));
    }

    @Override
    public void startActivityForResult() {
        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra("role","Leader");
        intent.putExtra("verification_stage","stage_2");
        startActivityForResult(intent,419);
    }

    @Override
    public void secretaryPresenceDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getResources().getString(R.string.tfm_dialog_attention));
        builder.setMessage("Data Saved")
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
                    //This is to start authentication activity
//                    memberLocationPresenter.secretaryTGQuestion(context);

                    memberLocationPresenter.startTGMembersActivity();
                    dialog.cancel();
                })
                .show();
                /*.setNeutralButton(context.getResources().getString(R.string.no), (dialog, id) -> {
            //memberLocationPresenter.startActivityForResult();
            memberLocationPresenter.startHomeActivity();
            dialog.cancel();
        })*/
    }

    @Override
    public void secretaryTGQuestion(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getResources().getString(R.string.tfm_dialog_attention));
        builder.setMessage("Does your secretary want to create a Supa TG?")
                .setPositiveButton(context.getResources().getString(R.string.yes), (dialog, id) -> {
                    //This is to start authentication activity
                    memberLocationPresenter.startTGMembersActivity();
                    dialog.cancel();
                })
                .setNeutralButton(context.getResources().getString(R.string.no), (dialog, id) -> {
                    //memberLocationPresenter.startActivityForResult();
                    memberLocationPresenter.startHomeActivity();
                    dialog.cancel();
                })
                .show();
    }

    @Override
    public void startHomeActivity() {
        Intent intent = new Intent(getApplicationContext(), TFMHome.class);
        startActivity(intent);
    }

    @Override
    public void startTGMembersActivity() {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String,String> user = sharedPreference.getUserDetails();
        if (Objects.requireNonNull(user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR)).equalsIgnoreCase("Leader")){
            Intent intent = new Intent(getApplicationContext(), ScheduleInfo.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        }
    }

    @OnClick(R.id.btnAddMember)
    public  void next(View view){
        if (memberLocationPresenter.validateMemberInfo(actState,actLga,actWard,actVillage) == 0){
            memberLocationPresenter.checkIfAutocompleteEmpty(actState,edlState,this.getResources().getString(R.string.error_message_state));
            memberLocationPresenter.checkIfAutocompleteEmpty(actLga,edlLga,this.getResources().getString(R.string.error_message_lga));
            memberLocationPresenter.checkIfAutocompleteEmpty(actWard,edlWard,this.getResources().getString(R.string.error_message_ward));
            memberLocationPresenter.checkIfAutocompleteEmpty(actVillage,edlVillage,this.getResources().getString(R.string.error_message_village));
        }else{
            //memberLocationPresenter.displayDialog(FormMemberLocation.this,actState,actLga,actWard,actVillage,actOtherCrops,edtOtherCropsNotListed);
            setBottomSheerTexts();
            showBottomSheet();
        }
    }

    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode might be 419 or 519 either for capture or authentication respectively
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        View parentLayout = findViewById(android.R.id.content);
        if (requestCode == 419) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //my code
                    memberLocationPresenter.collateAllResult(this,
                            memberLocationPresenter.getTextFromSpinner(actState),
                            memberLocationPresenter.getTextFromSpinner(actLga),
                            memberLocationPresenter.getTextFromSpinner(actWard),
                            memberLocationPresenter.getTextFromSpinner(actVillage),
                            parentLayout,memberLocationPresenter.getRegistrationAction(this));
                    //memberLocationPresenter.moveToNextActivity();
                    memberLocationPresenter.secretaryPresenceDialog(this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void launchLuxand(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(context.getResources().getString(R.string.tfm_dialog_attention));
        builder.setMessage(context.getResources().getString(R.string.verify_intro))
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
                    memberLocationPresenter.startActivityForResult();
                    dialog.cancel();
                })
                .show();

    }
}
