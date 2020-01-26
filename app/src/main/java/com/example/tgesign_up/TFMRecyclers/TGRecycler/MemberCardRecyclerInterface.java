package com.example.tgesign_up.TFMRecyclers.TGRecycler;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

public interface MemberCardRecyclerInterface {
    void textViewController(TextView textView, String starting_text, String info);
    void setCheckOfSwitch(SwitchCompat switchCompat, boolean bool);
    void displayDeactivateDialog(String message, Context context, String unique_member_id);
    void displayReactivateDialog(String message, Context context, String unique_member_id);
    void toastGenerator(Context context, String message);
    void loadNextActivityFromRecycler();
    void loadNextActivityFromRecyclerNewMember();
    void displayDialogForOldMembers(String message, Context context, String unique_member_id);
    void launchFieldMappingActivity(Context context, String unique_member_id);
}
