package com.babbangona.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.babbangona.tgesign_up.Database.TFM.TFMDatabase;
import com.babbangona.tgesign_up.R;
import com.babbangona.tgesign_up.TFMHome;
import com.babbangona.tgesign_up.TGHomeMVP.schedulemodel;
import com.babbangona.tgesign_up.TGPage.TgMembers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class schedulerecycler extends RecyclerView.Adapter<schedulerecycler.ViewHolder>  {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;
    TFMDatabase tfmDatabase;
    SharedPreference sharedPreference;


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

                    //3nd Alert Dialog
                    AlertDialog.Builder alertDialogBuilderSuccess3 = new AlertDialog.Builder(
                            mContext);
                    alertDialogBuilderSuccess3.setTitle("Does your secretary want to create a Supa TG?");
                    // set dialog message
                    alertDialogBuilderSuccess3

                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            Intent intent= new Intent(mContext, TgMembers.class);
                                            mContext.startActivity(intent);

                                            //finish();
                                        }
                                    })
                            .setNeutralButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            Intent intent= new Intent(mContext, TFMHome.class);
                                            mContext.startActivity(intent);
                                            dialog.cancel();
                                        }
                                    });

                    //2nd Alert Dialog
                    AlertDialog.Builder alertDialogBuilderSuccess = new AlertDialog.Builder(
                            mContext);
                    alertDialogBuilderSuccess.setTitle("Is your secretary here with you?");
                    // set dialog message
                    alertDialogBuilderSuccess

                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            alertDialogBuilderSuccess3.show();
                                            //finish();
                                        }
                                    })
                            .setNeutralButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(
                                        DialogInterface dialog, int id) {
                                    Intent intent= new Intent(mContext, TFMHome.class);
                                    mContext.startActivity(intent);
                                    dialog.cancel();
                                }
                            });

                    // create alert dialog
                    final AlertDialog alertDialogSuccess = alertDialogBuilderSuccess.create();








                    //////////////////////////////////
                    //1st Alert
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            mContext);
                    alertDialogBuilder.setTitle("Do you want to proceed?");
                    // set dialog message
                    alertDialogBuilder

                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(mContext);
                                            sharedPreferenceController.scheduleInfo(slotID,firstDay,secondDay,firstTime,secondTime);
                                            sharedPreference = new SharedPreference(mContext);

                                            //String slotID=sharedPreferenceController.getSlotId();
                                            //Log.d("countii",slotID.toString());
                                            String count=sharedPreferenceController.getScheduleCount();
                                            Integer countInt=Integer.valueOf(count)+1;
                                            sharedPreferenceController.schedulecount(String.valueOf(countInt));
                                            Log.d("selectresult",slotID+firstDay+" "+first_time+" "+second_time+count);
                                            HashMap<String,String> user = sharedPreference.getUserDetails();
                                            String unique_memberID = user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
                                            tfmDatabase = TFMDatabase.getInstance(mContext);
                                            Log.d("unuquuu",unique_memberID);
                                            tfmDatabase.getTGEDao().updateSlotID(unique_memberID,sharedPreferenceController.getSlotId());
                                            //calling the second alert when it user press the confirm button
                                            alertDialogSuccess.show();
                                        }
                                    })
                            .setNeutralButton("No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(
                                                DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                     //create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();
//
//                    new AlertDialog.Builder(mContext)
//                            .setTitle("Do you want to proceed?")
//                            //.setMessage(R.string.deleteFieldQ)
//                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                    //TODO 2. This is where I send the data to your app, check the response function for the keys
//                                    //Intent intent= new Intent(mContext,inactivefield.class);
//                                    //intent.putExtra("field_id",field_id);
//                                    SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(mContext);
//                                    sharedPreferenceController.scheduleInfo(slotID,firstDay,secondDay,firstTime,secondTime);
//                                    sharedPreference = new SharedPreference(mContext);
//
//                                    //String slotID=sharedPreferenceController.getSlotId();
//                                    //Log.d("countii",slotID.toString());
//                                    String count=sharedPreferenceController.getScheduleCount();
//                                    Integer countInt=Integer.valueOf(count)+1;
//                                    sharedPreferenceController.schedulecount(String.valueOf(countInt));
//                                    Log.d("selectresult",slotID+firstDay+" "+first_time+" "+second_time+count);
//                                    HashMap<String,String> user = sharedPreference.getUserDetails();
//                                    String unique_memberID = user.get(SharedPreference.KEY_UNIQUE_MEMBER_ID);
//                                    tfmDatabase = TFMDatabase.getInstance(mContext);
//                                    Log.d("unuquuu",unique_memberID);
//                                    tfmDatabase.getTGEDao().updateSlotID(unique_memberID,sharedPreferenceController.getSlotId());
//
//
//
//                                    //tf.fieldsdao().updateSyncStatusRoom(h.getStatus(), h.getField_id());
//
//                                    //uploadSchedule uploadTFMData = new uploadSchedule(this);
//                                    //uploadTFMData.saveScheduleData();
//                                    //saveToScheduleTable();
//
//                                    //mContext.startActivity(intent);
//
//                                }
//                            })
//
//                            .setNeutralButton(R.string.no, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int id) {
//                                }
//                            })
//                            .show();



                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
//    private void saveToScheduleTable() {
//        uploadSchedule uploadTFMData = new uploadSchedule(mContext);
//        uploadTFMData.saveScheduleData();
//        //count = uploadTFMData.countTFMData() + uploadTFMData.countTGData();
//        //Log.d("tfm_counter", String.valueOf(count));
//    }

}


