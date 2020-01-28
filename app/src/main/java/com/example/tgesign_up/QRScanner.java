package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.FormMemberInformationMVP.FormMemberInformationModel;
import com.google.zxing.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scanner;


    SharedPreference sharedPreference;

    FormMemberInformationModel formMemberInformationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = new ZXingScannerView(this);
        setContentView(scanner);
        setTitle(getResources().getString(R.string.scan_referral_code));
        sharedPreference = new SharedPreference(getApplicationContext());
        formMemberInformationModel = new FormMemberInformationModel();
    }

    @Override
    public void onResume(){
        super.onResume();
        scanner.setResultHandler(this);
        setTitle(getResources().getString(R.string.scan_referral_code));
        scanner.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        scanner.stopCamera();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void handleResult(final Result result) {
        String[] cleanedData = result.getText().split("\\*");
        String[] data = cleanedData[0].split(",");


        int year = Calendar.getInstance().get(Calendar.YEAR);

        if(String.valueOf(year).equalsIgnoreCase(data[1])){
            List<String> checkList;
            sharedPreference.setKeyQrIkNumber(data[0]);
            checkList = formMemberInformationModel.getAllRegisteredIKResult(QRScanner.this);

            if (checkList != null) {
                if (checkList.contains(data[0])){
                    finishThisActivity(0);
                }else{
                    finishThisActivity(1);
                }
            }else{
                finishThisActivity(2);
            }

        }else{
            finishThisActivity(2);
        }


    }

    void finishThisActivity(int data){
        Intent intentMessage = new Intent();
        intentMessage.putExtra("RESULT",data);
        setResult(Activity.RESULT_OK, intentMessage);
        finish();
    }

}
