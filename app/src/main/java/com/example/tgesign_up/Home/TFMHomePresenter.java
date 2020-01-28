package com.example.tgesign_up.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.R;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerInterface;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerViewAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class TFMHomePresenter  implements TFMHomePresenterInterface {
    private TFMHomeInterface viewObject;
    private LeaderCardRecyclerInterface leaderCardRecyclerInterface;
    private LeaderModel leaderModel;

    public TFMHomePresenter(TFMHomeInterface viewObject){
        this.viewObject = viewObject;
        leaderModel = new LeaderModel();
    }

    public TFMHomePresenter(){
        leaderModel = new LeaderModel();
    }

    public TFMHomePresenter(LeaderCardRecyclerInterface leaderCardRecyclerInterface){
        leaderModel = new LeaderModel();
        this.leaderCardRecyclerInterface = leaderCardRecyclerInterface;
    }

    @Override
    public void hideFeature(View view) {
        viewObject.hideView(view);
    }

    @Override
    public void showFeature(View view) {
        viewObject.showView(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public List<prospectiveTGETable.prospectiveTGETableRecycler> getLeaderList(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String,String> user = sharedPreference.getUserDetails();
        List<prospectiveTGETable.prospectiveTGETableRecycler> leader_data = new ArrayList<>();
        try {
            leader_data = new LeaderModel.getLeaderDetails(context){
                @Override
                protected void onPostExecute(List<prospectiveTGETable.prospectiveTGETableRecycler> s) {}
            }.execute(user.get(SharedPreference.KEY_FILTER_HUB)).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return leader_data;
    }

//    @Override
//    public String getFinishedCheckListFlagResult(Context context, String unique_ik_number) {
//        return stringOutputController(leaderModel.getFinishedCheckListFlagResult(context, unique_ik_number));
//    }

    @Override
    public void loadPreviousActivity() {
        viewObject.loadPreviousActivity();
    }

    @Override
    public List<prospectiveTGETable.prospectiveTGETableRecycler> getSearchParameters(CharSequence constraint, List<prospectiveTGETable.prospectiveTGETableRecycler> total_leader_list) {
        String charString = constraint.toString().trim();

        List<prospectiveTGETable.prospectiveTGETableRecycler> mFilteredList;

        if(charString.isEmpty()){

            mFilteredList = total_leader_list;
        }else{
            List<prospectiveTGETable.prospectiveTGETableRecycler> filteredList = new ArrayList<>();

            for(prospectiveTGETable.prospectiveTGETableRecycler leaders : total_leader_list){

                if(
                        leaders.getFirst_name().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getLast_name().toLowerCase().contains(charString.toLowerCase()) ||
                        ((leaders.getFirst_name().toLowerCase())+" "+(leaders.getLast_name().toLowerCase())).contains(charString.toLowerCase())){

                    filteredList.add(leaders);
                }
            }

            mFilteredList = filteredList;
        }

        return mFilteredList;
    }

    @Override
    public void textWatcher(EditText editText, final  LeaderCardRecyclerViewAdapter adapter) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null){
                    adapter.getFilter().filter(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public int getMemberCount(Context context, String unique_ik_number) {
        return leaderModel.getMemberCountResult(context, unique_ik_number);
    }

    @Override
    public TFMAppVariables getTFMAppVariables(Context context, String variable_id) {
        return leaderModel.getTFMAppVariablesResult(context, variable_id);
    }

    @Override
    public int getLeaderCountForMember(Context context, String unique_ik_number) {
        return leaderModel.getLeaderCountForMemberResult(context, unique_ik_number);
    }

    @Override
    public String getMemberProgram(Context context, String unique_ik_number) {
        return leaderModel.getMemberProgramResult(context, unique_ik_number);
    }

    @Override
    public void showDialogForFailedCapture(String s, Context context) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        viewObject.showDialogForFailedCapture(builder,s,context);
    }

    @Override
    public String getProspectiveTGETemplate(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String,String> user = sharedPreference.getUserDetails();
        return leaderModel.getProspectiveTGETemplateResult(context,user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID));
    }

    @Override
    public String getProspectiveTGLTemplate(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String,String> user = sharedPreference.getUserDetails();
        return leaderModel.getProspectiveTGLTemplateResult(context,user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID));
    }

    @Override
    public void showDialogToVerifyTemplate(String s, Context context, String ik_number, String unique_member_id) {
        MaterialAlertDialogBuilder builder = (new MaterialAlertDialogBuilder(context));
        leaderCardRecyclerInterface.showDialogToVerifyTemplate(builder,s,context,ik_number,unique_member_id);
    }

    @Override
    public void buttonColor(Button button) {
        viewObject.setColor(button);
    }

    @Override
    public void setTextViewText(TextView view, Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        HashMap<String,String> user = sharedPreference.getUserDetails();
        viewObject.setTextViewText(view,user.get(SharedPreference.KEY_TRAINING_WARD));
    }

    private String stringOutputController(String input){
        String result;
        if (input == null || input.equalsIgnoreCase("")){
            result = "0";
        }else{
            result = input;
        }
        return result;
    }
}
