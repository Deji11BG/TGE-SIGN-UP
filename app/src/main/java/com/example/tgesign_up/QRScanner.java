package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    String Response;
    String module;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);


    }

    @Override
    public void onResume(){
        super.onResume();

        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void handleResult(final com.google.zxing.Result result) {
        String cleanedData[] = result.getText().split("\\*");
        String data[] = cleanedData[0].split(",");
        SharedPreferenceController sharedPreferenceController= new SharedPreferenceController(getApplicationContext());



        int year = Calendar.getInstance().get(Calendar.YEAR);
        if(String.valueOf(year).equalsIgnoreCase(data[1])){
            sharedPreferenceController.saveQrIkNumber(data[0]);
        }

        Intent intent =  new Intent(getApplicationContext(),FormMemberInformation.class);


        startActivity(intent);

    }

}
