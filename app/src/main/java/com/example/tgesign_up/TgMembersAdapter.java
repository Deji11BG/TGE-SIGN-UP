package com.example.tgesign_up;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class TgMembersAdapter extends RecyclerView.Adapter<TgMembersAdapter.ProductViewHolder> {


    private Context mCtx;
    ArrayList<Map<String, String>> wordlist;
    List<TgMembersModel> memberList;
    private TgMembersModel member;
    SharedPreferenceController sharedPreferenceController;



    public TgMembersAdapter(Context mCtx, List<TgMembersModel> memberList) {
        this.mCtx = mCtx;
        this.memberList = memberList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tg_members_recycler, null);


        return new ProductViewHolder(view);

    }


    @SuppressLint("SetTextI18n")

    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        member = memberList.get(position);
        sharedPreferenceController=new SharedPreferenceController(mCtx);

        holder.memberName.setText("Member Name:  "+ member.getMemberName());
        holder.memberId.setText("Member ID:  "+member.getMemberId());
        holder.memberVillage.setText("Member Village:  "+member.getMemberVillage());

        holder.memberCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sharedPreferenceController.saveUniqueMemberId(member.getUniqueMemberId());
                Intent i = new  Intent(mCtx,VerifyActivity.class);
                mCtx.startActivity(i);
            }
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



        public ProductViewHolder(View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.tv_member_name);
            memberId= itemView.findViewById(R.id.tv_member_id);
            memberVillage= itemView.findViewById(R.id.tv_member_village);
            memberCard= itemView.findViewById(R.id.member_card);


        }


    }


}