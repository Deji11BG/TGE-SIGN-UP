package com.example.tgesign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class CaptureTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout_1);
        startActivityForResult(new Intent(this,CaptureActivity.class),519);

      }


    @Override
    public void onBackPressed() {
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
        if (requestCode == 519){
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //This is supposed to be used for new or old members...
                    loadFormMemberInformation();
                }else if (data.getIntExtra("RESULT", 0) == 2){
                    showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_not_found),CaptureTemplate.this);
                }else{
                    showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_already_registered),CaptureTemplate.this);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                    CaptureTemplate.this.finish();
                }).setCancelable(false)
                .show();
    }
}

