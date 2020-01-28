package com.example.tgesign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Home.LeaderModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectHub extends AppCompatActivity {

    @BindView(R.id.actFilterHub)
    AutoCompleteTextView actFilterHub;

    @BindView(R.id.edlFilterHub)
    TextInputLayout edlFilterHub;

    @BindView(R.id.btnNext)
    MaterialButton btnNext;

    LeaderModel leaderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hub);
        ButterKnife.bind(SelectHub.this);
        leaderModel = new LeaderModel();

        fillThisSpinner(actFilterHub);
    }

    void fillThisSpinner(AutoCompleteTextView autoCompleteTextView){
        List<String> spinnerList;
        spinnerList = leaderModel.getAllHubsResult(SelectHub.this);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(SelectHub.this, android.R.layout.simple_dropdown_item_1line, spinnerList);
        autoCompleteTextView.setAdapter(spinnerAdapter);
    }

    @OnClick(R.id.btnNext)
    public  void next(){
        if (validateMemberInfo(actFilterHub) == 0){
            editorChecker();
        }else{
            editorChecker();
            moveToNextActivity();
        }
    }

    public int validateMemberInfo(AutoCompleteTextView sex){

        if(sex.getText().toString().matches("")) {
            return 0;
        }

        //Save all data to shared preferences if all the checks are passed
        else{
            return 1;
        }
    }

    void editorChecker(){
        checkIfAutocompleteEmpty(actFilterHub,edlFilterHub,this.getResources().getString(R.string.tfm_error_hub));
    }

    public void checkIfAutocompleteEmpty(AutoCompleteTextView autoCompleteTextView, TextInputLayout textInputLayout, String error_message) {
        String textEntered = Objects.requireNonNull(autoCompleteTextView.getText()).toString();
        if (textEntered.equalsIgnoreCase("")){
            setErrorOfTextView(textInputLayout, error_message);
        }else{
            removeErrorFromText(textInputLayout);
        }
    }

    public void setErrorOfTextView(TextInputLayout textInputLayout, String error_message) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(error_message);
    }

    public void removeErrorFromText(TextInputLayout textInputLayout) {
        textInputLayout.setError(null);
        textInputLayout.setErrorEnabled(false);
    }

    void moveToNextActivity(){
        Intent intent = new Intent(getApplicationContext(),TFMHome.class);
        SharedPreference sharedPreference = new SharedPreference(SelectHub.this);
        sharedPreference.setKeyFilterHub(actFilterHub.getText().toString());
        startActivity(intent);
    }
}
