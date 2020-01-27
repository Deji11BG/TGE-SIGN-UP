package com.example.tgesign_up.TGPage;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.example.tgesign_up.R;
import com.example.tgesign_up.VerifyTemplate;


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

    TgMembersAdapter(Context mCtx, List<prospectiveTGLTable.prospectiveTGLTableRecycler> memberList) {
        this.mCtx = mCtx;
        this.memberList = memberList;
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

            SharedPreference sharedPreference = new SharedPreference(mCtx);
            Intent intent = new Intent(mCtx, VerifyTemplate.class);
            sharedPreference.setIKNumber(member.getIk_number());
            sharedPreference.setUniqueMemberId(member.getUnique_member_id());
            sharedPreference.setKeyRoleToRegisterFor("Member");
            sharedPreference.setKeyRegistrationAction("new");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent);

        });

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