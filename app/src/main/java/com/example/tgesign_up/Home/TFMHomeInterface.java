package com.example.tgesign_up.Home;

import android.content.Context;
import android.view.View;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public interface TFMHomeInterface {
    void hideView(View view);
    void showView(View view);
    void loadPreviousActivity();
    void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context);
}
