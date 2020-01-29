package com.babbangona.tgesign_up.TGHomeMVP;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.tgesign_up.TFMRecyclers.TGRecycler.MemberCardRecyclerViewAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public interface TGHomeInterface {
    /*void hideView(View view);
    void showView(View view);*/
    void setLeaderSwitchTextView(String info, String role, String member_program);
    void textViewController(TextView textView, String starting_text, String info);
    void setLeader_image(Bitmap bitmap);
    void recyclerController(RecyclerView member_recycler_view, MemberCardRecyclerViewAdapter adapter);
    void setCheckOfSwitch(SwitchCompat switchCompat, boolean bool);
    void loadNextActivity();
    void loadPreviousActivity();
    void displaySnackBar(Context context, String message, View view);
    void displayDialog(String message, Context context);
    void displayToast(Context context, String message);
    void loadNextActivityForLeaderEdit();
    void loadNextActivityForLeader();
    void showDialogForLeaderVerification(MaterialAlertDialogBuilder builder, String s, Context context);
    void launchFieldMappingActivity(Context context, String unique_member_id);
    void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context);
    void showDialogForFailedVerification(MaterialAlertDialogBuilder builder, String s, Context context);
    void showDialogForMemberCapture(MaterialAlertDialogBuilder builder, String s, Context context);
}
