package com.babbangona.tgesign_up;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.babbangona.bgfr.BGFRMode;
import com.babbangona.bgfr.CustomLuxandActivity;
import com.babbangona.bgfr.Database.BGFRInfo;
import com.babbangona.tgesign_up.TGHomeMVP.TGHomePresenter;

import java.io.ByteArrayOutputStream;

public class CaptureActivity extends CustomLuxandActivity {

    //startActivity(new Intent(this, CaptureActivity.class));
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
        if (!getBGFR_FACEFOUND()){
            showErrorAndClose(this.getResources().getString(R.string.no_face_found));
        }

        else {
            setBGFR_MODE(BGFRMode.CAPTURE_NEW);
            StartTimer();
        }
    }

    @Override
    public void MyFlow() {
//        loadBlankTracker("BABBANGONA-MEMBER-" + Math.random());
        TGHomePresenter tgHomePresenter = new TGHomePresenter();
        String template                             = "0";

        try{
            template                             = tgHomePresenter.getTrackerPile(this);
        }
        catch(NullPointerException n){
            Log.i(TAG, "MyFlow: NULL POINTER EXCEPTION CAUGHT, EMPTY DATABASE ==>" + n.getLocalizedMessage());
        }


        if(template == null || template.matches("^\\s*0\\s*$")  || template.isEmpty() ){
            loadBlankTracker("BABBANGONA-MEMBER-" + Math.random());
        } else {
            LoadTracker(template, BGFRMode.AUTHENTICATE );
        }
    }

    @NonNull
    @Override
    public String buttonText() {
        return this.getResources().getString(R.string.tfm_capture_template);
    }

    @NonNull
    @Override
    public String headerText() {
        return this.getResources().getString(R.string.tfm_capture_template_header);
    }

    @Override
    public void Authenticated() {
        StopTimer();

        //dialog that shows template exist
        intentResultValue(0);

    }

    @Override
    public void TrackerSaved() {
        StopTimer();
        BGFRInfo info                                       = new BGFRInfo(this);
        String individualTemplate                           = info.getSingleTemplate();
        String bundledTemplate                              = info.getBundledTemplate();
        Bitmap imagePersonLarge                             = info.getImage();
        String image_person                                 = info.getImageString();



        save(individualTemplate, image_person, bundledTemplate,getEncodedBitmap(imagePersonLarge));
    }

    public void save(String template, String imageBitmap, String bundledTemplate, String image_person_large){
        TGHomePresenter tgHomePresenter = new TGHomePresenter();
        tgHomePresenter.setTemplateToSharedPreference(this,template,imageBitmap,bundledTemplate,image_person_large);
        intentResultValue(1);
    }

    String getEncodedBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    void intentResultValue(int result){
        Intent intentMessage = new Intent();
        intentMessage.putExtra("RESULT",result);
        this.setResult(Activity.RESULT_OK,intentMessage);
        this.finish();
    }

}
