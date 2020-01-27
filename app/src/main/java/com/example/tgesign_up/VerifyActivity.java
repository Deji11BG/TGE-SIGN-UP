package com.example.tgesign_up;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.babbangona.bgfr.BGFRMode;
import com.babbangona.bgfr.CustomLuxandActivity;
import com.example.tgesign_up.Home.TFMHomePresenter;
import com.example.tgesign_up.TGHomeMVP.TGHomePresenter;

import java.util.Objects;

public class VerifyActivity extends CustomLuxandActivity{

    //This is how to call this activity
    //startActivity(new Intent(this, VerifyActivity.class));


    @Override
    public Boolean setDetectFakeFaces() {
        //SET TO TRUE WHEN WE ARE READY TO DETECT FAKE FACES
        return true;
    }

    @Override
    public Long setTimer() {
        //SET TO WHATEVER COUNT DOWN TIME YOU WANT
        return 15000L;
    }

    @Override
    public Boolean setKeepFaces() {
        //SET TO TRUE FOR THE INCREASED LUXAND TRACKER SIZE
        return false;
    }


    @Override
    public void TimerFinished() {
        /*
        IF NO FACE FOUND
        WHAT YOU WANT TO DO WHEN NO FACE FOUND AND TIMER TIMES OUT
        */
        if (!getBGFR_FACEFOUND()) {
            showErrorAndClose(this.getResources().getString(R.string.no_face_found));
        } else {
            showErrorAndClose(this.getResources().getString(R.string.no_face_matched));
        }
    }

    @Override
    public void MyFlow() {
        TGHomePresenter tgHomePresenter = new TGHomePresenter();
        TFMHomePresenter tfmHomePresenter = new TFMHomePresenter();
        String role_to_authenticate_for = getIntent().getStringExtra("role");
        String template;

        if (Objects.requireNonNull(getIntent().getStringExtra("verification_stage")).equalsIgnoreCase("stage_1")){
            if (role_to_authenticate_for != null && role_to_authenticate_for.equalsIgnoreCase("Leader")) {
                template = tfmHomePresenter.getProspectiveTGETemplate(this);
            }else{
                template = tfmHomePresenter.getProspectiveTGLTemplate(this);
            }
        }else{
            template = tgHomePresenter.returnTemplateFromSharedPreference(this);
        }
        LoadTracker(template, BGFRMode.AUTHENTICATE);
    }

    @NonNull
    @Override
    public String buttonText() {
        return this.getResources().getString(R.string.tfm_verify_template);
    }

    @NonNull
    @Override
    public String headerText() {
        return this.getResources().getString(R.string.tfm_verify_template_header);
    }

    @Override
    public void Authenticated() {
        StopTimer();
        //Handles what happens after authentication
        Intent intentMessage = new Intent();
        intentMessage.putExtra("RESULT", 1);
        this.setResult(Activity.RESULT_OK,intentMessage);
        this.finish();
    }

    @Override
    public void TrackerSaved() {

    }
}