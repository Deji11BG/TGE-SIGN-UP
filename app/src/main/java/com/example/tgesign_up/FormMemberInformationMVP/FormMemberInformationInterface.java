package com.example.tgesign_up.FormMemberInformationMVP;


import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public interface FormMemberInformationInterface {
    /*void hideView(View view);
    void showView(View view);*/

    void spinnerViewController(AutoCompleteTextView autoCompleteTextView, ArrayAdapter arrayAdapter);
    void setErrorOfTextView(TextInputLayout textInputLayout, String error_message);
    void removeErrorFromText(TextInputLayout textInputLayout);
    void alertDialogDisplay(Context context, LinearLayout linearLayout);
    void moveToAnotherActivity();
    void loadPreviousActivity();
    void enableEditTextViews(boolean bool);
    void setTextOfEditTextViews(boolean bool);
    void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text);
    void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text);
    void controlEditTextAppearance(boolean bool);
}
