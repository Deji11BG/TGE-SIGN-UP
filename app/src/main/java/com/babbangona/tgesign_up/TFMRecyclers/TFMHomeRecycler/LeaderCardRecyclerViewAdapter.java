package com.babbangona.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Database.TFM.Table.prospectiveTGETable;
import com.babbangona.tgesign_up.Home.LeaderModel;
import com.babbangona.tgesign_up.Home.TFMHomePresenter;
import com.babbangona.tgesign_up.R;
import com.babbangona.tgesign_up.VerifyActivity;
import com.babbangona.tgesign_up.VerifyTemplate;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Adapter used to show a simple grid of products.
 */
@SuppressWarnings("unchecked")
public class LeaderCardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderCardRecyclerViewAdapter.LeaderCardViewHolder> implements Filterable,LeaderCardRecyclerInterface {

    private List<prospectiveTGETable.prospectiveTGETableRecycler> leaderList;
    private List<prospectiveTGETable.prospectiveTGETableRecycler> mFilteredList;
    private TFMHomePresenter tfmHomePresenter;
    private Context mCtx;
    LeaderModel leaderModel;

    public LeaderCardRecyclerViewAdapter(List<prospectiveTGETable.prospectiveTGETableRecycler> leaderList, Context mCtx) {
        this.leaderList = leaderList;
        this.mFilteredList = leaderList;
        this.mCtx = mCtx;
        leaderModel = new LeaderModel();
    }

    @NonNull
    @Override
    public LeaderCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_details_card_landscape, parent, false);
        return new LeaderCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderCardViewHolder holder, int position) {
        tfmHomePresenter = new TFMHomePresenter(LeaderCardRecyclerViewAdapter.this);
        if (mFilteredList != null && position < mFilteredList.size()) {
            prospectiveTGETable.prospectiveTGETableRecycler leaderModel = mFilteredList.get(position);
            String full_name = holder.nameFormatter(leaderModel.getFirst_name(),leaderModel.getLast_name());
            holder.setTextController(holder.tv_leader_name, full_name);
            holder.setTextController(holder.tv_leader_ik_number, leaderModel.getIk_number());
            holder.setTextController(holder.tv_leader_village, leaderModel.getMember_id());
//            holder.setLeader_image(holder.leader_image,leaderModel.getUnique_member_id());
        }
    }

    @Override
    public int getItemCount() {
        if (mFilteredList != null){
            return mFilteredList.size();
        }else{
            return 0;
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = tfmHomePresenter.getSearchParameters(constraint,leaderList);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (List<prospectiveTGETable.prospectiveTGETableRecycler>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public void showDialogToVerifyTemplate(MaterialAlertDialogBuilder builder, String s, Context context,String ik_number, String unique_member_id) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, which) -> {
                    //this is to dismiss the dialog
                    startFormMemberInformationActivity(ik_number,unique_member_id);
                    dialog.dismiss();
                }).setNeutralButton(context.getResources().getString(R.string.cancel),(dialog, which) -> {
                    dialog.dismiss();
                }).setCancelable(false)
                .show();
    }

    private void startFormMemberInformationActivity(String ik_number, String unique_member_id){
        SharedPreference sharedPreference = new SharedPreference(mCtx);
        Intent intent = new Intent(mCtx, VerifyTemplate.class);
        sharedPreference.setIKNumber(ik_number);
        sharedPreference.setUniqueMemberId(unique_member_id);
        sharedPreference.setKeyRoleToRegisterFor("Leader");
        sharedPreference.setKeyRegistrationAction("new");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mCtx.startActivity(intent);
    }

    private void startVerifyActivity(String ik_number, String unique_member_id){
        Intent intent = new Intent(mCtx, VerifyActivity.class);
        SharedPreference sharedPreference = new SharedPreference(mCtx);
        sharedPreference.setIKNumber(ik_number);
        sharedPreference.setUniqueMemberId(unique_member_id);
        sharedPreference.setKeyRoleToRegisterFor("Leader");
        sharedPreference.setKeyRegistrationAction("new");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("role","Leader");
        intent.putExtra("verification_stage","stage_1");
        ((Activity) mCtx).startActivityForResult(intent,419);
    }

    class LeaderCardViewHolder extends RecyclerView.ViewHolder {

        /*@BindView(R.id.leader_image)
        ImageView leader_image;*/

        @BindView(R.id.tv_leader_name)
        TextView tv_leader_name;

        @BindView(R.id.tv_leader_village)
        TextView tv_leader_village;

        @BindView(R.id.tv_leader_ik_number)
        TextView tv_leader_ik_number;

        @BindView(R.id.leader_card_container)
        LinearLayout leader_card_container;

        /*@BindView(R.id.progress_bar)
        ProgressBar progress_bar;*/

        /*@BindView(R.id.check_circle)
        CircleImageView check_circle;*/

        LeaderCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.leader_card_container)
        void submit(View view){
            SharedPreference sharedPreference = new SharedPreference(mCtx);
            HashMap<String,String> user = sharedPreference.getUserDetails();
            if (!Objects.requireNonNull(user.get(SharedPreference.KEY_TRAINING_WARD)).equalsIgnoreCase("Nothing Selected")){

                if (leaderModel.getRegisteredMemberCountResult(mCtx,mFilteredList.get(getAdapterPosition()).getUnique_member_id()) > 0){
                    Snackbar.make(view, "TGE Already Registered", Snackbar.LENGTH_LONG)
                            .setAction("CLOSE", view1 -> {

                            })
                            .setActionTextColor(mCtx.getResources().getColor(android.R.color.holo_red_light ))
                            .show();
                }else{
                    tfmHomePresenter.showDialogToVerifyTemplate(mCtx.getResources().getString(R.string.verify_intro),mCtx,mFilteredList.get(getAdapterPosition()).getIk_number(),
                            mFilteredList.get(getAdapterPosition()).getUnique_member_id());
                }

            }else{
                Snackbar.make(view, "Please select a training ward", Snackbar.LENGTH_LONG)
                        .setAction("CLOSE", view1 -> {
                            //do nothing
                        })
                        .setActionTextColor(mCtx.getResources().getColor(android.R.color.holo_red_light ))
                        .show();
            }
        }

        void setTextController(TextView textView, String text) {
            textView.setText(text);
        }

        void setLeader_image(ImageView iv_picture, String unique_id){

            File ImgDirectory = new File(Environment.getExternalStorageDirectory().getPath(), "TestPictures");
            String image_name = File.separator + unique_id + ".jpg";
            //String imageFile = (ImgDirectory.getAbsolutePath() + image_name);

            File imgFile = new File(ImgDirectory.getAbsoluteFile(),image_name);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                iv_picture.setImageBitmap(myBitmap);

            }else{
                iv_picture.setImageResource(R.drawable.bg_logo);
            }
        }

        String nameFormatter(String first_name, String last_name){

            return first_name +" "+last_name;
        }

    }
}
