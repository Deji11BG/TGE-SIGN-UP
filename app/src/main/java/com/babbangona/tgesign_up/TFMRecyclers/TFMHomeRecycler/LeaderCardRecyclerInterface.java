package com.babbangona.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.content.Context;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public interface LeaderCardRecyclerInterface {
    void showDialogToVerifyTemplate(MaterialAlertDialogBuilder builder, String s, Context context,String ik_number, String unique_member_id);
}
