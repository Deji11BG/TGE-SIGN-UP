package com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.R;
import com.example.tgesign_up.TGHomeMVP.schedulemodel;
import com.example.tgesign_up.scheduleAsynctask;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import static android.content.Context.MODE_PRIVATE;

public class schedulerecycler extends RecyclerView.Adapter<schedulerecycler.ViewHolder>  {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;

    //private List<modelclass3>number_list;
    schedulemodel model;
    //modelclass3 model3;
    private List<schedulemodel> member_list;
    private List<schedulemodel> examplelistfull;
    // itemsCopy.addAll(items);

    public schedulerecycler(ArrayList<schedulemodel> memberList,Context context) {


        this.member_list=memberList;
        examplelistfull= new ArrayList<>(memberList);

        mContext=context;
        //setHasStableIds(true);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.schedulerecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);

        Log.d(TAG, "onBindViewHolder: called.");
        holder.slot_id.setText(model.getSlot_id());
        holder.first_time.setText(model.getFirst_time());
        holder.first_day.setText(model.getFirst_day());

        holder.second_day.setText(model.getSecond_day());
        holder.second_time.setText(model.getSecond_time());

    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView slot_id,first_day,second_day,first_time,second_time;




        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            slot_id=itemView.findViewById(R.id.slot_id);

            first_day=itemView.findViewById(R.id.day1);
            second_day=itemView.findViewById(R.id.day2);
            first_time=itemView.findViewById(R.id.session1);
            second_time=itemView.findViewById(R.id.session2);

            parentLayout=itemView.findViewById(R.id.parent_layout);



            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView slot_id = view.findViewById(R.id.slot_id);
                    final TextView first_day = view.findViewById(R.id.day1);
                    final TextView second_day = view.findViewById(R.id.day2);
                    final TextView first_time = view.findViewById(R.id.session1);
                    final TextView second_time = view.findViewById(R.id.session2);



                    final String slotID = slot_id.getText().toString();
                    final String firstDay= first_day.getText().toString();
                    final String secondDay = second_day.getText().toString();
                    final String firstTime = first_time.getText().toString();
                    final String secondTime = second_time.getText().toString();


                    final String status="inactive";
                    final String sync_status="0";

                    new AlertDialog.Builder(mContext)
                            .setTitle("Do you want to proceed?")
                            //.setMessage(R.string.deleteFieldQ)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //TODO 2. This is where I send the data to your app, check the response function for the keys
                                    //Intent intent= new Intent(mContext,inactivefield.class);
                                    //intent.putExtra("field_id",field_id);
                                    SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(mContext);
                                    sharedPreferenceController.scheduleInfo(slotID,firstDay,secondDay,firstTime,secondTime);
                                    String count=sharedPreferenceController.getScheduleCount();
                                    Integer countInt=Integer.valueOf(count)+1;
                                    sharedPreferenceController.schedulecount(String.valueOf(countInt));
                                    Log.d("selectresult",slotID+firstDay+" "+first_time+" "+second_time+count);
                                    @SuppressLint("StaticFieldLeak")
                                    scheduleAsynctask.updateScheduleCount x = new scheduleAsynctask.updateScheduleCount(mContext) {

                                    };

                                    //mContext.startActivity(intent);

                                }
                            })

                            .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .show();



                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }


}


