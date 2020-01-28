package com.example.tgesign_up;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class QRScanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scanner = new ZXingScannerView(this);
        setContentView(scanner);
        setTitle(getResources().getString(R.string.scan_referral_code));
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

    @Override
    public void handleResult(final Result result){
        String[] cleanedData = result.getText().split("\\*");   //gets the scanned data without the * after
        String data = cleanedData[0];
        Intent intentMessage = new Intent();
        intentMessage.putExtra("RESULT",data);
        setResult(Activity.RESULT_OK,intentMessage);
        finish();
    }

}
