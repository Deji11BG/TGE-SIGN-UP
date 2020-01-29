package com.babbangona.tgesign_up.TFMRecyclers.TGRecycler;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.babbangona.tgesign_up.FormMemberInformation;
import com.babbangona.tgesign_up.R;
import com.babbangona.tgesign_up.TGHomeMVP.TGHomePresenter;
import com.babbangona.tgesign_up.TGHomeMVP.TGModel;
import com.babbangona.tgesign_up.VerifyActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTouch;

public class MemberCardRecyclerViewAdapter extends RecyclerView.Adapter<MemberCardRecyclerViewAdapter.MemberCardViewHolder> implements MemberCardRecyclerInterface {

    private List<TGModel> tgModelList;
    private Context mCtx;
    private TGHomePresenter tgHomePresenter;

    public MemberCardRecyclerViewAdapter(List<TGModel> tgModelList, Context mCtx) {
        this.tgModelList = tgModelList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public MemberCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_details_card, parent, false);
        return new MemberCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberCardViewHolder holder, int position) {
        tgHomePresenter = new TGHomePresenter(MemberCardRecyclerViewAdapter.this);
        TGModel.CountModel memberCountModel;
        if (tgModelList != null && position < tgModelList.size()) {
            TGModel tgModel = tgModelList.get(position);
            String full_name = holder.nameFormatter(tgModel.getFirst_name(),tgModel.getMiddle_name(),tgModel.getLast_name());
            String role = "-"+tgHomePresenter.getMemberRoleRecycler(mCtx,tgModel.getUnique_member_id());
            memberCountModel = tgHomePresenter.getMemberCountRecycler(mCtx,tgModel.getUnique_member_id());
            tgHomePresenter.recyclerSwitchController(holder.sw_member, memberCountModel.getCount());
            holder.tv_member_name.setText(full_name);
            holder.tv_member_role.setText(role);
            tgHomePresenter.setTextOfLocationRecycler(holder.tv_location_member,mCtx.getResources().getString(R.string.leader_location),tgModel.getVillage_name());
        }
    }

    @Override
    public int getItemCount() {
        if (tgModelList != null){
            return tgModelList.size();
        }else{
            return 0;
        }
    }

    @Override
    public void textViewController(TextView textView, String starting_text, String info) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml("<b>" + starting_text + "</b>" + ": " + info, Html.FROM_HTML_MODE_COMPACT));
        } else {
            textView.setText(Html.fromHtml("<b>" + starting_text + "</b>" + ": " + info));
        }
    }

    @Override
    public void setCheckOfSwitch(SwitchCompat switchCompat, boolean bool) {
        switchCompat.setChecked(bool);
    }

    @Override
    public void displayDeactivateDialog(String message, Context context, String unique_member_id) {
        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
            dialog.cancel();
            int deactivate_flag = tgHomePresenter.deactivateMember(context,unique_member_id);
            if (deactivate_flag != 0){
                tgHomePresenter.displayToast(context,mCtx.getResources().getString(R.string.tfm_member_deactivated_successfully));
            }else{
                tgHomePresenter.displayToast(context,mCtx.getResources().getString(R.string.tfm_member_deactivation_not_successfully));
            }
            notifyDataSetChanged();
        });
        builder1.setNegativeButton(context.getResources().getString(R.string.cancel), (dialog, which) -> {
            //cancel
            dialog.cancel();
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();*/
    }

    @Override
    public void displayReactivateDialog(String message, Context context, String unique_member_id) {
        /*AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
            dialog.cancel();
            int reactivate_flag = tgHomePresenter.reactivateMember(context,unique_member_id);
            if (reactivate_flag != 0){
                tgHomePresenter.displayToast(context,mCtx.getResources().getString(R.string.tfm_member_reactivated_successfully));
            }else{
                tgHomePresenter.displayToast(context,mCtx.getResources().getString(R.string.tfm_member_reactivation_not_successfully));
            }
            notifyDataSetChanged();
        });
        builder1.setNegativeButton(context.getResources().getString(R.string.cancel), (dialog, which) -> {
            //cancel
            dialog.cancel();
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();*/
    }

    @Override
    public void toastGenerator(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadNextActivityFromRecycler() {
        Intent intent = new Intent(mCtx, FormMemberInformation.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }

    @Override
    public void loadNextActivityFromRecyclerNewMember() {
        Intent intent = new Intent(mCtx, VerifyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("role","Member");
        ((Activity) mCtx).startActivityForResult(intent,419);
    }

    @Override
    public void displayDialogForOldMembers(String message, final Context context, final String unique_member_id) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setPositiveButton(context.getResources().getString(R.string.ok), (dialog, id) -> {
            dialog.cancel();
            tgHomePresenter.roleToRegisterController(mCtx, "Member",
                    mCtx.getResources().getString(R.string.registration_action_old_1));
            tgHomePresenter.loadNextActivityFromRecyclerNewMember(context, unique_member_id);
        });
        builder1.setNegativeButton(context.getResources().getString(R.string.cancel), (dialog, which) -> {
            //cancel
            dialog.cancel();
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void launchFieldMappingActivity(Context context, String unique_member_id) {
        /*Intent intent = new Intent(mCtx, activefield.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setKeyUniqueIdFieldMapping(unique_member_id);
        mCtx.startActivity(intent);*/
    }

    class MemberCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.sw_member)
        SwitchCompat sw_member;

        @BindView(R.id.tv_location_member)
        TextView tv_location_member;

        @BindView(R.id.tv_member_name)
        TextView tv_member_name;

        @BindView(R.id.tv_member_role)
        TextView tv_member_role;

        @BindView(R.id.member_card_container)
        MaterialCardView member_card_container;

        Boolean isTouched = false;

        @SuppressLint("ClickableViewAccessibility")
        MemberCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            tgHomePresenter = new TGHomePresenter(MemberCardRecyclerViewAdapter.this);
        }

        String nameFormatter(String first_name, String middle_name, String last_name){
            String formatted_name = "";
            if (first_name != null && !first_name.equalsIgnoreCase("")){
                if (middle_name != null && !middle_name.equalsIgnoreCase("")){
                    if (last_name != null && !last_name.equalsIgnoreCase("")){
                        formatted_name = first_name + " "+middle_name + " "+last_name;
                    }
                }else if (last_name != null && !last_name.equalsIgnoreCase("")){
                    formatted_name = first_name +" "+last_name;
                }else{
                    formatted_name = first_name;
                }
            }
            return formatted_name;
        }

        @OnTouch(R.id.sw_member)
        boolean onSwitchTouch() {
            isTouched = true;
            return false;
        }

        @OnCheckedChanged(R.id.sw_member)
        void switchAction(boolean isChecked){
            SharedPreference sharedPreference = new SharedPreference(mCtx);
            HashMap<String, String> user = sharedPreference.getUserDetails();
            int leader_count = tgHomePresenter.getLeaderCountForMember(mCtx,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
            TGModel.CountModel memberCountModel1 = tgHomePresenter.getRegisteredMemberCountRecycler(mCtx,
                    tgModelList.get(getAdapterPosition()).getUnique_member_id());
            TGModel.CountModel memberCountModel2 = tgHomePresenter.getMemberCountRecycler(mCtx,
                    tgModelList.get(getAdapterPosition()).getUnique_member_id());
            if (isTouched){
                isTouched = false;
                if (isChecked){
                    if (leader_count > 0){
                        if (memberCountModel1.getCount() == 0){
                            sw_member.setChecked(false);
                            if (memberCalculationLogic()){
                                //tgHomePresenter.displayDialogForOldMembers(mCtx.getResources().getString(R.string.tfm_member_reacttivate_member_question), mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                            }else{
                                //tgHomePresenter.displayToast(mCtx,mCtx.getResources().getString(R.string.tfm_maximum_members_reached));
                            }

                        }else if (memberCountModel1.getCount() > 0){
                            if (memberCalculationLogic()){
                                //tgHomePresenter.displayReactivateDialog(mCtx.getResources().getString(R.string.tfm_member_reacttivate_member_question),mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                                tgHomePresenter.recyclerSwitchController(sw_member,memberCountModel2.getCount());
                            }else{
                                //tgHomePresenter.displayToast(mCtx,mCtx.getResources().getString(R.string.tfm_maximum_members_reached));
                            }

                        }
                    }else{
                        //tgHomePresenter.displayToast(mCtx,mCtx.getResources().getString(R.string.tfm_reactivate_leader_first));
                    }
                }else{
                    //tgHomePresenter.displayDeactivateDialog(mCtx.getResources().getString(R.string.tfm_member_deacttivate_member_question),mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                    tgHomePresenter.recyclerSwitchController(sw_member,memberCountModel1.getCount());
                }
            }
        }

        @OnClick(R.id.member_card_container)
        void viewPopUpMenu(){
            /*//creating a popup menu
            PopupMenu popup = new PopupMenu(mCtx, member_card_container);
            //inflating menu from xml resource
            TGModel.CountModel memberCountModel2 = tgHomePresenter.getMemberCountRecycler(mCtx,
                    tgModelList.get(getAdapterPosition()).getUnique_member_id());
            SharedPreference sharedPreference = new SharedPreference(mCtx);
            SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(mCtx);
            HashMap<String, String> user = sharedPreference.getUserDetails();
            int leader_count = tgHomePresenter.getLeaderCountForMember(mCtx,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));

            if (leader_count > 0){
                if (memberCountModel2.getCount() == 0){
                    tgHomePresenter.displayToast(mCtx,mCtx.getResources().getString(R.string.tfm_other_actions_for_members));
                }else if (memberCountModel2.getCount() > 0){
                    if (tgHomePresenter.getMemberRoleRecycler(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id()).equalsIgnoreCase(mCtx.getResources().getString(R.string.tfm_member_info_role_secretary))){
                        if (sharedPreferenceController.getKeyStaffProgram().equalsIgnoreCase("TGE")){
                            popup.inflate(R.menu.options_menu_secretary_tge);
                        }else{
                            popup.inflate(R.menu.options_menu_secretary);
                        }
                    }else if (tgHomePresenter.getMemberRoleRecycler(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id()).equalsIgnoreCase(mCtx.getResources().getString(R.string.tfm_member_info_role_member))){
                        if (sharedPreferenceController.getKeyStaffProgram().equalsIgnoreCase("TGE")){
                            popup.inflate(R.menu.options_menu_member_tge);
                        }else{
                            popup.inflate(R.menu.options_menu_member);
                        }
                    }
                }
            }else{
                tgHomePresenter.displayToast(mCtx,mCtx.getResources().getString(R.string.tfm_other_actions_for_leaders));
            }

            //adding click listener
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.map_field:
                        //Launches field mapping
                        tgHomePresenter.loadFieldMappingForRecycler(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                        return true;
                    case R.id.edit:
                        //Edit
                        tgHomePresenter.roleToRegisterController(mCtx,"Member","edit");
                        tgHomePresenter.loadNextActivityFromRecycler(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                        return true;
                    case R.id.switch_role_to_secretary:
                        //Switch role to secretary
                        tgHomePresenter.switchRoleToSecretary(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                        notifyDataSetChanged();
                        return true;
                    case R.id.switch_role_to_member:
                        //Switch role
                        tgHomePresenter.switchRoleToMember(mCtx,tgModelList.get(getAdapterPosition()).getUnique_member_id());
                        notifyDataSetChanged();
                        return true;
                    default:
                        return false;
                }
            });
            //displaying the popup
            popup.show();*/
        }

        boolean memberCalculationLogic(){
            SharedPreference sharedPreference = new SharedPreference(mCtx);
            HashMap<String, String> user = sharedPreference.getUserDetails();
            int season_id = Integer.parseInt(tgHomePresenter.getSeasonID(mCtx,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER)));
            int member_count = tgHomePresenter.getMemberCount(mCtx,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
            String member_program = tgHomePresenter.getMemberProgram(mCtx,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
            TFMAppVariables tfmAppVariables = tgHomePresenter.getTFMAppVariables(mCtx,mCtx.getResources().getString(R.string.variable_id));
            if (season_id == 20){
                //get all that is new
                if (member_program.equalsIgnoreCase("MA")){
                    return member_count < Integer.parseInt(tfmAppVariables.getMa_max_new());
                }else{
                    return member_count < Integer.parseInt(tfmAppVariables.getKkg_max_new());
                }
            }else{
                //use all that is old
                if (member_program.equalsIgnoreCase("MA")){
                    return member_count < Integer.parseInt(tfmAppVariables.getMa_max_old());
                }else{
                    return member_count < Integer.parseInt(tfmAppVariables.getKkg_max_old());
                }
            }
        }

    }
}
