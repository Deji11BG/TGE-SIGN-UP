package com.example.tgesign_up.TGHomeMVP;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;

public interface TGHomePresenterInterface {
    void printLeaderFullName(String first_name, String middle_name, String last_name, String role, String member_program);
    void setTextOfTextViews(TextView view, String starting_text, String texts);
    TGLeaderModel getLeaderDetails(Context context, String ik_number);
    TGLeaderModel.CountModel getLeaderCount(Context context, String ik_number);
    String ikNumberManipulator(String required_ik_number, String temporary_ik_number);
    void setLeader_image(Context context, String unique_id, String unique_ik_number);
    void recyclerViewSetter(RecyclerView recyclerView, Context context, String unique_ik_number);
    void switchController(SwitchCompat switchCompat, int count);
    void loadNextActivity();
    void loadNextActivityFromRecycler(Context context, String unique_member_id);
    void loadNextActivityFromRecyclerNewMember(Context context, String unique_member_id);
    void loadNextActivityForLeaderEdit(Context context, String unique_member_id);
    void snackBarPresenter(Context context, String message, View view);
    void loadPreviousActivity();
    void displayDialog(String message, Context context);
    void roleToRegisterController(Context context, String role_to_register, String kind_of_registration);

    void setTextOfLocationRecycler(TextView view, String starting_text, String texts);
    TGModel.CountModel getMemberCountRecycler(Context context, String unique_member_id);
    TGModel.CountModel getRegisteredMemberCountRecycler(Context context, String unique_member_id);
    void recyclerSwitchController(SwitchCompat switchCompat, int count);
    void displayDeactivateDialog(String message, Context context, String unique_member_id);
    void displayReactivateDialog(String message, Context context, String unique_member_id);
    int deactivateMember(Context context, String unique_member_id);
    int reactivateMember(Context context, String unique_member_id);
    void displayToast(Context context, String message);
    void switchRoleToSecretary(Context context, String unique_member_id);
    void switchRoleToMember(Context context, String unique_member_id);
    String getMemberRoleRecycler(Context context, String unique_member_id);
    void setTemplateToSharedPreference(Context context, String template, String member_picture, String bundledTemplate, String member_picture_large);
    String returnTemplateFromSharedPreference(Context context);
    void displayToastForLeader(Context context, String message);
    String getLeaderUniqueID(Context context, String unique_ik_number);
    String getMemberTemplate(Context context, String unique_member_id);
    String returnUniqueMemberIDFromSharedPreference(Context context);
    void loadNextActivityForLeader();
    String getUniqueIKNumberLeaderTemplate(Context context);
    void showDialogForLeaderVerification(String s, Context context);
    String getSeasonID(Context context, String unique_ik_number);
    String getMemberProgram(Context context, String unique_ik_number);
    int getMemberCount(Context context, String unique_ik_number);
    TFMAppVariables getTFMAppVariables(Context context, String variable_id);
    int getLeaderCountForMember(Context context, String unique_ik_number);
    String getOldMemberTemplate(Context context, String unique_member_id);
    void displayDialogForOldMembers(String message, Context context, String unique_member_id);
    void loadFieldMappingForRecycler(Context context, String unique_member_id);
    void loadFieldMappingForLeader(Context context, String unique_member_id);
    String getTrackerPile(Context context);
    void showDialogForFailedCapture(String s, Context context);
    void showDialogForFailedVerification(String s, Context context);
    void showDialogForMemberCapture(String s, Context context);

}
