package com.babbangona.tgesign_up;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.babbangona.tgesign_up.Api.SharedPreference;

import java.util.HashMap;
import java.util.Objects;

import butterknife.ButterKnife;

public class VerifyTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        Log.i("BG_DEBUG", "onCreate: " + "VERIFY TEMPLATE");
        ButterKnife.bind(this);

        SharedPreference sharedPreference = new SharedPreference(this);
        HashMap<String,String> user = sharedPreference.getUserDetails();

        Intent intent = new Intent(this, VerifyActivity.class);
        if (Objects.requireNonNull(user.get(SharedPreference.KEY_ROLE_TO_REGISTER_FOR)).equalsIgnoreCase("Leader")){
            intent.putExtra("role","Leader");
        }else{
            intent.putExtra("role","Member");
        }
        intent.putExtra("verification_stage","stage_1");
        startActivityForResult(intent,419);
    }

    @Override
    public void onBackPressed () {
        finish();
    }

    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode might be 419 or 519 either for edit or leader validation and registration respectively
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreference sharedPreference = new SharedPreference(this);
        if (requestCode == 419) {
            if (data != null) {
                Log.d("hello_hello_1","getting_here_1");
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //old member passes authentication
                    sharedPreference.setKeyPassAuthentication("1");
                    loadCaptureActivityWithProgressDialog();
                }else{
                    //old member fails authentication
                    sharedPreference.setKeyPassAuthentication("0");
                    loadCaptureActivityWithProgressDialog();
                }
            }else{
                Log.d("hello_hello","getting_here");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void loadCaptureActivityWithProgressDialog(){
        final Intent intent = new Intent(VerifyTemplate.this, CaptureTemplate.class);
        final ProgressDialog pd1 = new ProgressDialog(VerifyTemplate.this);
        pd1.setMessage(this.getResources().getString(R.string.tfm_wait_for_recapture));
        pd1.show();
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                pd1.dismiss();
                startActivity(intent);
                finishThisActivity();
            }
        }.start();
    }

    void finishThisActivity(){
        this.finish();
    }

}

