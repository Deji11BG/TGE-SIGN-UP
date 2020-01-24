package com.example.tgesign_up;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tgesign_up.Api.GPSController;
import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.FormMemberLocationMVP.FormMemberLocationInterface;
import com.example.tgesign_up.FormMemberLocationMVP.FormMemberLocationModel;
import com.example.tgesign_up.FormMemberLocationMVP.FormMemberLocationPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

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

    @BindView(R.id.actOtherCrops)
    AutoCompleteTextView actOtherCrops;

    @BindView(R.id.edtOtherCropsNotListed)
    TextInputEditText edtOtherCropsNotListed;

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

    @BindView(R.id.bottom_sheet_other_crops)
    TextView bottom_sheet_other_crops;

    @BindView(R.id.bottom_sheet_other_crops_not_listed)
    TextView bottom_sheet_other_crops_not_listed;

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
        SharedPreference sharedPreference = new SharedPreference(this);
        HashMap<String, String> user = sharedPreference.getUserDetails();

        if (memberLocationPresenter.getRegistrationRole(this)){
            if (!memberLocationPresenter.getRegistrationAction(this)){
                memberLocationPresenter.enableEditTextViewsForEdit(false);
                memberLocationPresenter.getMyLocationValuesFromID(this);
                memberLocationPresenter.setTextForEdit();
            }else{
                memberLocationPresenter.fillStateSpinner(actState,FormMemberLocation.this,
                        memberLocationPresenter.getRegistrationAction(this));
            }
        }else{
            if (memberLocationPresenter.getRegistrationAction(this)){
                memberLocationPresenter.enableEditTextViewsForMembers(false);
                memberLocationPresenter.getLocationValuesFromID(this);
                memberLocationPresenter.setTextsForMembers();
            }else{
                if (Objects.requireNonNull(user.get(SharedPreference.KEY_REGISTRATION_ACTION)).
                        equalsIgnoreCase(this.getResources().getString(R.string.registration_action_old_1))){
                    memberLocationPresenter.enableEditTextViewsForMembers(false);
                    memberLocationPresenter.getLocationValuesFromID(this);
                    memberLocationPresenter.setTextsForMembers();
                    sharedPreference.setKeyRegistrationAction(this.getResources().getString(R.string.registration_action_old_2));
                }else{
                    memberLocationPresenter.enableEditTextViewsForEdit(true);
                    memberLocationPresenter.getMyLocationValuesFromID(this);
                    memberLocationPresenter.setTextForEdit();
                }

            }
            memberLocationPresenter.fillWardSpinner(actWard,FormMemberLocation.this,
                    memberLocationModel.getLga(), memberLocationPresenter.getRegistrationAction(this));
            memberLocationPresenter.fillVillageSpinner(actVillage,FormMemberLocation.this,memberLocationModel.getWard());
        }

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
        bottom_sheet_other_crops.setText(Objects.requireNonNull(actOtherCrops.getText()).toString());
        bottom_sheet_other_crops_not_listed.setText(Objects.requireNonNull(edtOtherCropsNotListed.getText()).toString());
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
        memberLocationPresenter.startActivityForResult();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tfm_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            try {
                startActivity(new Intent(FormMemberLocation.this, ViewActivityIssues.class)
                        .putExtra("activity_id", "tfm_4")
                        .putExtra("app_id", "tfm")
                        .putExtra("staff_id", "T-10000000000000AA")//get this guy from shared prefs
                        .putExtra("user_location", ""));

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(FormMemberLocation.this, "Activity not found", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void spinnerViewController(AutoCompleteTextView autoCompleteTextView, ArrayAdapter arrayAdapter) {
        autoCompleteTextView.setAdapter(arrayAdapter);
    }

    @Override
    public void setToStateModel(AutoCompleteTextView autoCompleteTextView, boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actLga);
            memberLocationPresenter.clearSpinnerText(actWard);
            memberLocationPresenter.clearSpinnerText(actVillage);
            String state_selection = (String)parent.getItemAtPosition(position);
            memberLocationModel.setState(state_selection);
            memberLocationPresenter.fillLgaSpinner(actLga,FormMemberLocation.this,memberLocationModel.getState(), bool);
        });
    }

    @Override
    public void setToLGAModel(AutoCompleteTextView autoCompleteTextView, boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actWard);
            memberLocationPresenter.clearSpinnerText(actVillage);
            String lga_selection = (String)parent.getItemAtPosition(position);
            memberLocationModel.setLga(lga_selection);
            memberLocationPresenter.fillWardSpinner(actWard,FormMemberLocation.this,memberLocationModel.getLga(), bool);
        });
    }

    @Override
    public void setToWardModel(AutoCompleteTextView autoCompleteTextView, boolean bool) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            memberLocationPresenter.clearSpinnerText(actVillage);
            String ward_selection = (String)parent.getItemAtPosition(position);
            memberLocationModel.setWard(ward_selection);
            memberLocationPresenter.fillVillageSpinner(actVillage,FormMemberLocation.this,memberLocationModel.getWard());
        });

    }

    @Override
    public void setToVillageModel(AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setOnItemClickListener((parent, view, position, rowId) -> {
            String village_selection = (String)parent.getItemAtPosition(position);
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
        Intent intent = new Intent (FormMemberLocation.this, TGHome.class);
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
        memberLocationPresenter.setTextOfAutoCompleteTextViews(actOtherCrops, memberLocationModel.getOther_crops());
        memberLocationPresenter.setTextOfEditTextViews(edtOtherCropsNotListed, memberLocationModel.getOther_not_listed_crops());
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
        intent.putExtra("role","Member");
        startActivityForResult(intent,419);
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
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        if (requestCode == 419) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //my code
                    sharedPreference.setKeyPassAuthentication("1");
                    memberLocationPresenter.collateAllResult(this,
                            memberLocationPresenter.getTextFromSpinner(actState),
                            memberLocationPresenter.getTextFromSpinner(actLga),
                            memberLocationPresenter.getTextFromSpinner(actWard),
                            memberLocationPresenter.getTextFromSpinner(actVillage),
                            memberLocationPresenter.getTextFromSpinner(actOtherCrops),
                            memberLocationPresenter.getViewContentText(edtOtherCropsNotListed), parentLayout,memberLocationPresenter.getRegistrationAction(this));
                    memberLocationPresenter.moveToNextActivity();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
