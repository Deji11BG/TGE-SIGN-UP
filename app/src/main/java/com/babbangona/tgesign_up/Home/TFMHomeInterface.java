package com.babbangona.tgesign_up.Home;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public interface TFMHomeInterface {
    void hideView(View view);
    void showView(View view);
    void loadPreviousActivity();
    void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context);
    void setColor(Button button);
    void setTextViewText(TextView textView, String text);
}
