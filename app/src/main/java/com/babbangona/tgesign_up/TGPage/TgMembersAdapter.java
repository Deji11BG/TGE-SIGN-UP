package com.babbangona.tgesign_up.TGPage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.babbangona.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.babbangona.tgesign_up.FormMemberInformation;
import com.babbangona.tgesign_up.Home.LeaderModel;
import com.babbangona.tgesign_up.R;
import com.babbangona.tgesign_up.VerifyTemplate;
import com.google.android.material.snackbar.Snackbar;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TgMembersAdapter extends RecyclerView.Adapter<TgMembersAdapter.ProductViewHolder> {


    private Context mCtx;
    ArrayList<Map<String, String>> wordlist;
    List<prospectiveTGLTable.prospectiveTGLTableRecycler> memberList;
    private prospectiveTGLTable.prospectiveTGLTableRecycler member;
    SharedPreferenceController sharedPreferenceController;
    LeaderModel leaderModel;

    TgMembersAdapter(Context mCtx, List<prospectiveTGLTable.prospectiveTGLTableRecycler> memberList) {
        this.mCtx = mCtx;
        this.memberList = memberList;
        leaderModel = new LeaderModel();
    }


    @NotNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tg_members_recycler, null);
        return new ProductViewHolder(view);

    }


    @SuppressLint("SetTextI18n")

    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        member = memberList.get(position);
        sharedPreferenceController=new SharedPreferenceController(mCtx);

        holder.memberName.setText("Member Name:  "+ member.getFirst_name()+" "+ member.getLast_name());
        holder.memberId.setText("Member ID:  "+member.getMember_id());
        holder.memberVillage.setText("Member Village:  ");

        holder.memberCard.setOnClickListener(view -> {

            if (leaderModel.getRegisteredMemberCountResult(mCtx,member.getUnique_member_id()) > 0){
                Snackbar.make(view, "Member Already Captured", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", view1 -> {

                        })
                        .setActionTextColor(mCtx.getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }else{
                startVerificationActivity(member.getIk_number(),member.getUnique_member_id());
                Log.d("unique_id",member.getUnique_member_id());
            }


        });

    }

    private void startVerificationActivity(String ik_number, String unique_member_id){
        SharedPreference sharedPreference = new SharedPreference(mCtx);
        Intent intent = new Intent(mCtx, VerifyTemplate.class);
        sharedPreference.setIKNumber(ik_number);
        sharedPreference.setUniqueMemberId(unique_member_id);
        sharedPreference.setKeyRoleToRegisterFor("Member");
        sharedPreference.setKeyRegistrationAction("new");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }

    private void startMemberInformationActivity(String ik_number, String unique_member_id){
        SharedPreference sharedPreference = new SharedPreference(mCtx);
        Intent intent = new Intent(mCtx, FormMemberInformation.class);
        sharedPreference.setIKNumber(ik_number);
        sharedPreference.setUniqueMemberId(unique_member_id);
        sharedPreference.setKeyRoleToRegisterFor("Member");
        sharedPreference.setKeyRegistrationAction("new");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }


    @Override
    public int getItemCount() {

        return memberList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView memberName;
        TextView memberId;
        TextView memberVillage;
        CardView memberCard;

        ProductViewHolder(View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.tv_member_name);
            memberId= itemView.findViewById(R.id.tv_member_id);
            memberVillage= itemView.findViewById(R.id.tv_member_village);
            memberCard= itemView.findViewById(R.id.member_card);

        }

    }


}