package com.example.tgesign_up.FormMemberLocationMVP;

import android.content.Context;
import android.view.View;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public interface FormMemberLocationPresenterInterface {
    void fillStateSpinner(AutoCompleteTextView autoCompleteTextView, Context context, boolean bool);
    void fillLgaSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String state, boolean bool);
    void fillWardSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String lga, boolean bool);
    void fillVillageSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String ward);
    void clearSpinnerText(AutoCompleteTextView autoCompleteTextView);
    String getTextFromSpinner(AutoCompleteTextView autoCompleteTextView);
    int validateMemberInfo(AutoCompleteTextView state, AutoCompleteTextView lga, AutoCompleteTextView ward, AutoCompleteTextView village);
    void checkIfAutocompleteEmpty(AutoCompleteTextView autoCompleteTextView, TextInputLayout textInputLayout, String error_message);
    void collateAllResult(Context context, String state, String lga, String ward, String village,
                          String other_crops, String not_listed_crops, View view, boolean bool);
    String getViewContentText(TextInputEditText textInputEditText);
    void displayDialog(Context context, AutoCompleteTextView state, AutoCompleteTextView lga, AutoCompleteTextView ward,
                       AutoCompleteTextView village, AutoCompleteTextView other_bg_crops, TextInputEditText other_crops_not_listed);
    void moveToNextActivity();
    void loadPreviousActivity();
    boolean getRegistrationAction(Context context);
    void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text);
    void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text);
    boolean getRegistrationRole(Context context);
    void enableEditTextViewsForMembers(boolean bool);
    void enableEditTextViewsForEdit(boolean bool);
    FormMemberLocationModel getLeaderLocation(Context context);
    String getStateName(Context context, String state_id);
    String getLgaName(Context context, String lga_id);
    String getWardName(Context context, String ward_id);
    void getLocationValuesFromID(Context context);
    void setTextsForMembers();
    FormMemberLocationModel getMyLocation(Context context);
    void setTextForEdit();
    void getMyLocationValuesFromID(Context context);
    void startActivityForResult();
}
