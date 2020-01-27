package com.example.tgesign_up.FormMemberLocationMVP;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public interface FormMemberLocationInterface {
    void spinnerViewController(AutoCompleteTextView autoCompleteTextView, ArrayAdapter arrayAdapter);
    void setToStateModel(AutoCompleteTextView autoCompleteTextView, boolean bool);
    void setToLGAModel(AutoCompleteTextView autoCompleteTextView, boolean bool);
    void setToWardModel(AutoCompleteTextView autoCompleteTextView, boolean bool);
    void setToVillageModel(AutoCompleteTextView autoCompleteTextView);
    void clearSpinnerText(AutoCompleteTextView autoCompleteTextView);
    void setErrorOfTextView(TextInputLayout textInputLayout, String error_message);
    void removeErrorFromText(TextInputLayout textInputLayout);
    void alertDialogDisplay(Context context, LinearLayout linearLayout);
    void displaySnackBar(View view, String message);
    void moveToAnotherActivity();
    void loadPreviousActivity();
    void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text);
    void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text);
    void enableEditTextViewsForMembers(boolean bool);
    void enableEditTextViewsForEdit(boolean bool);
    void getLocationValuesFromID(Context context);
    void setTextsForMembers();
    void setTextForEdit();
    void getMyLocationValuesFromID(Context context);
    void startActivityForResult();
    void secretaryPresenceDialog(Context context);
    void secretaryTGQuestion(Context context);
    void startHomeActivity();
    void startTGMembersActivity();

}
