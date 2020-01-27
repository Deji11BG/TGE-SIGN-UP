package com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public interface LeaderCardRecyclerInterface {
    void showDialogToVerifyTemplate(MaterialAlertDialogBuilder builder, String s, Context context,String ik_number, String unique_member_id);
}
