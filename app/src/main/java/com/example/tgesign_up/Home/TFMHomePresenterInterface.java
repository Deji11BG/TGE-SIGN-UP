package com.example.tgesign_up.Home;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerViewAdapter;

import java.util.List;

public interface TFMHomePresenterInterface {

    void hideFeature(View view);
    void showFeature(View view);
    List<prospectiveTGETable.prospectiveTGETableRecycler> getLeaderList(Context context);
    void loadPreviousActivity();
    List<prospectiveTGETable.prospectiveTGETableRecycler> getSearchParameters(CharSequence constraint, List<prospectiveTGETable.prospectiveTGETableRecycler> total_leader_list);
    void textWatcher(EditText editText, LeaderCardRecyclerViewAdapter adapter);
    int getMemberCount(Context context, String unique_ik_number);
    TFMAppVariables getTFMAppVariables(Context context, String variable_id);
    int getLeaderCountForMember(Context context, String unique_ik_number);
    String getMemberProgram(Context context, String unique_ik_number);
    void showDialogForFailedCapture(String s, Context context);
    String getProspectiveTGETemplate(Context context);
    String getProspectiveTGLTemplate(Context context);
    void showDialogToVerifyTemplate(String s, Context context,String ik_number, String unique_member_id);
}
