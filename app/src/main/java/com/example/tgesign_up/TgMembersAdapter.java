package com.example.tgesign_up;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class TgMembersAdapter extends RecyclerView.Adapter<TgMembersAdapter.ProductViewHolder> {


    private Context mCtx;
    ArrayList<Map<String, String>> wordlist;
    List<TgMembersModel> fieldList;
    Boolean fieldStatus;
    private TgMembersModel field;
    SharedPreferenceController sharedPreferenceController;



    public TgMembersAdapter(Context mCtx, List<TgMembersModel> fieldList) {
        this.mCtx = mCtx;
        this.fieldList = fieldList;
    }


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.tg_members_recycler, null);


        return new ProductViewHolder(view);

    }


    @SuppressLint("SetTextI18n")

    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        field = fieldList.get(position);
        db = new HarvestDBHandler(mCtx);
        holder.fieldId.setText(field.getFieldId());
        fieldStatus= db.getCCappStatus(field.getFieldId());
        if(fieldStatus){
            holder.fieldCard.setCardBackgroundColor(Color.GREEN);

        }
        else {
        }

    }


    @Override
    public int getItemCount() {

        return fieldList.size();
    }


    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView fieldId;
        CardView fieldCard;


        public ProductViewHolder(View itemView) {
            super(itemView);

            fieldId = itemView.findViewById(R.id.field_id);
            fieldCard= itemView.findViewById(R.id.field_card);


        }


    }
}