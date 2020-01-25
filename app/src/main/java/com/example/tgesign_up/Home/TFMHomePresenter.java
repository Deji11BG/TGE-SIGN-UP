package com.example.tgesign_up.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TFMHomePresenter  implements TFMHomePresenterInterface {
    private TFMHomeInterface viewObject;
    private LeaderModel leaderModel;

    public TFMHomePresenter(TFMHomeInterface viewObject){
        this.viewObject = viewObject;
        leaderModel = new LeaderModel();
    }

    public TFMHomePresenter(){
        leaderModel = new LeaderModel();
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
    public List<prospectiveTGETable> getLeaderList(Context context) {
        List<prospectiveTGETable> leader_data = new ArrayList<>();
        try {
            leader_data = new LeaderModel.getLeaderDetails(context){
                @Override
                protected void onPostExecute(List<prospectiveTGETable> s) {}
            }.execute().get();
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
    public List<prospectiveTGETable> getSearchParameters(CharSequence constraint, List<LeaderModel> total_leader_list) {
        String charString = constraint.toString().trim();

        List<LeaderModel> mFilteredList;

        if(charString.isEmpty()){

            mFilteredList = total_leader_list;
        }else{
            List<LeaderModel> filteredList = new ArrayList<>();

            for(LeaderModel leaders : total_leader_list){

                if(leaders.getUnique_ik_number().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getIk_number().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getFirst_name().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getLast_name().toLowerCase().contains(charString.toLowerCase()) ||
                        leaders.getVillage_name().toLowerCase().contains(charString.toLowerCase()) ||
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
