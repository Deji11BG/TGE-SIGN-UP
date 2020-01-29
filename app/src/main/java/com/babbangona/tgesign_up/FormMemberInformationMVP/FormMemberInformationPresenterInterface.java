package com.babbangona.tgesign_up.FormMemberInformationMVP;

import android.content.Context;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public interface FormMemberInformationPresenterInterface {
    void fillThisSpinner(AutoCompleteTextView autoCompleteTextView, Context context, String command);
    void checkIfTextViewEmpty(TextInputEditText textInputEditText, TextInputLayout textInputLayout, String error_message);
    void checkIfAutocompleteEmpty(AutoCompleteTextView autoCompleteTextView, TextInputLayout textInputLayout, String error_message);
    int validateMemberInfo(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                           TextInputEditText age, AutoCompleteTextView sex);
    int phoneNumberChecker(TextInputEditText textInputEditText);
    int ageChecker(TextInputEditText textInputEditText);
    void setTextViewError(TextInputLayout textInputLayout, String error_message);
    void displayDialog(Context context, TextInputEditText et_first_name, TextInputEditText et_last_name, TextInputEditText et_age,
                       TextInputEditText et_phone_number, AutoCompleteTextView sp_sex, AutoCompleteTextView sp_crop, AutoCompleteTextView sp_role);
    String getViewContentText(TextInputEditText textInputEditText);
    String getSpinnerContent(AutoCompleteTextView autoCompleteTextView);
    void moveToNextActivity();
    void saveDetailsToSharedPreference(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                                       TextInputEditText age, AutoCompleteTextView sex,
                                       Context context);
    void loadPreviousActivity();
    void enableEditTextViews(boolean bool);
    boolean getRegistrationAction(Context context);
    String autoFillEditTexts(boolean bool, Context context);
    void setTextOfEditTextViews(TextInputEditText textOfEditTextViews, String text);
    void setTextOfAutoCompleteTextViews(AutoCompleteTextView autoCompleteTextView, String text);
    void setTextOfEditTextViews(boolean bool);
    String calculateAge(String date_of_birth);
    void controlEditTextAppearance(boolean bool);
    void saveDetailsToModelClass(TextInputEditText first_name, TextInputEditText last_name, TextInputEditText phone_number,
                                 TextInputEditText age, AutoCompleteTextView sex,
                                 Context context);
    void textWatcher(TextInputEditText textInputEditText, String module, TextInputLayout textInputLayout, String error_message);
}
