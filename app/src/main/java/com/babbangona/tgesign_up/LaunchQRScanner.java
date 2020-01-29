package com.babbangona.tgesign_up;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LaunchQRScanner extends AppCompatActivity {

    @BindView(R.id.bt_next)
    Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_qrscanner);
        ButterKnife.bind(LaunchQRScanner.this);
        bt_next.setEnabled(false);

        ProgressDialog pd1 = new ProgressDialog(LaunchQRScanner.this);
        pd1.setMessage("Please wait...");
        pd1.show();
        pd1.setCancelable(false);
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                pd1.dismiss();
                bt_next.setEnabled(true);
            }
        }.start();

    }

    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode might be 419 or 519 either for edit or leader validation and registration respectively
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 619){
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //This is supposed to be used for new or old members...
                    loadFormMemberInformation();
                }else if (data.getIntExtra("RESULT", 0) == 2){
                    showDialogForFailedCapture(this.getResources().getString(R.string.tfm_wrong_qr_scanned),LaunchQRScanner.this);
                }else{
                    showDialogForFailedCapture(this.getResources().getString(R.string.tfm_ik_number_already_registered),LaunchQRScanner.this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.bt_next)
    public void useScanner(){
        startActivityForResult(new Intent(this,QRScanner.class),619);
    }

    void loadFormMemberInformation(){
        Intent intent = new Intent(this, FormMemberInformation.class);
        startActivity(intent);
    }

    public void showDialogForFailedCapture(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        showDialogForFailedCapture(builder,s,context);
    }

    public void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, which) -> {
                    //this is to dismiss the dialog
                    dialog.dismiss();
                    LaunchQRScanner.this.finish();
                }).setCancelable(false)
                .show();
    }
}
