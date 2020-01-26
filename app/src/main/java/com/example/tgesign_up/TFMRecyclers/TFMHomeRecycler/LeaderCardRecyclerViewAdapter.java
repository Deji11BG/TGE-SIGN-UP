package com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
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

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.TFM.Table.TFMAppVariables;
import com.example.tgesign_up.Home.LeaderModel;
import com.example.tgesign_up.Home.TFMHomePresenter;
import com.example.tgesign_up.R;
import com.example.tgesign_up.TGHome;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Adapter used to show a simple grid of products.
 */
@SuppressWarnings("unchecked")
public class LeaderCardRecyclerViewAdapter extends RecyclerView.Adapter<LeaderCardRecyclerViewAdapter.LeaderCardViewHolder> implements Filterable {

    private List<LeaderModel> leaderList;
    private List<LeaderModel> mFilteredList;
    private TFMHomePresenter tfmHomePresenter;
    private Context mCtx;

    public LeaderCardRecyclerViewAdapter(List<LeaderModel> leaderList, Context mCtx) {
        this.leaderList = leaderList;
        this.mFilteredList = leaderList;
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public LeaderCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.leader_details_card_landscape, parent, false);
        return new LeaderCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderCardViewHolder holder, int position) {
        tfmHomePresenter = new TFMHomePresenter();
        if (mFilteredList != null && position < mFilteredList.size()) {
            LeaderModel leaderModel = mFilteredList.get(position);
            String full_name = holder.nameFormatter(leaderModel.getFirst_name(),leaderModel.getMiddle_name(),leaderModel.getLast_name());
            holder.setTextController(holder.tv_leader_name, full_name);
            holder.setTextController(holder.tv_leader_village, leaderModel.getVillage_name());
            String temp_ik_number = holder.ikNumberManipulator(leaderModel.getIk_number(),leaderModel.getUnique_ik_number());
            holder.setTextController(holder.tv_leader_ik_number, temp_ik_number);
            holder.setLeader_image(holder.leader_image,leaderModel.getUnique_member_id());
            holder.setProgress_bar(leaderModel.getUnique_ik_number(),holder.check_circle,
                    Integer.parseInt(leaderModel.getSeason_id()),mCtx);
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
                mFilteredList = (List<LeaderModel>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class LeaderCardViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.leader_image)
        ImageView leader_image;

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

        @BindView(R.id.check_circle)
        CircleImageView check_circle;

        LeaderCardViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @OnClick(R.id.leader_card_container)
        void submit(){
            //Toast.makeText(mCtx, "Position1 " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            String finished_check_list_flag = tfmHomePresenter.getFinishedCheckListFlagResult(mCtx,
                    mFilteredList.get(getAdapterPosition()).getUnique_ik_number());
            String unique_ik_number = mFilteredList.get(getAdapterPosition()).getUnique_ik_number();

            SharedPreference sharedPreference = new SharedPreference(mCtx);
            sharedPreference.setUniqueIkNumber(unique_ik_number);
            sharedPreference.setIKNumber(mFilteredList.get(getAdapterPosition()).getIk_number());

            if (!finished_check_list_flag.equalsIgnoreCase("0")){
                Intent intent = new Intent (mCtx, TGHome.class);
                mCtx.startActivity(intent);
            }else{
                /*Intent intent = new Intent (mCtx, CheckList.class);
                mCtx.startActivity(intent);*/
            }

        }

        void setTextController(TextView textView, String text) {
            textView.setText(text);
        }

        String ikNumberManipulator(String required_ik_number, String temporary_ik_number){
            String temp_ik_number;
            if (required_ik_number == null || required_ik_number.equalsIgnoreCase("")){
                temp_ik_number = temporary_ik_number;
            }else{
                temp_ik_number = required_ik_number;
            }
            return temp_ik_number;
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

            /*Bitmap mImageBitmap = null;
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(iv_picture.getContext().getContentResolver(), Uri.parse(imageFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_picture.setImageBitmap(mImageBitmap);*/
        }

        String nameFormatter(String first_name, String middle_name, String last_name){
            /*String formatted_name = "";
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
            }*/
            Log.d("middle_name",middle_name+"_what");
            return first_name +" "+last_name;
        }

        void setProgress_bar(String unique_ik_number, CircleImageView check_circle, int season_id, Context context){
            int count_unique_id = tfmHomePresenter.getLeaderCountForMember(context,unique_ik_number);
            TFMAppVariables tfmAppVariables = tfmHomePresenter.getTFMAppVariables(context,context.getResources().getString(R.string.variable_id));
            if (count_unique_id <= 0){
                check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_red));
            }else{
                String memberProgram = tfmHomePresenter.getMemberProgram(context,unique_ik_number);
                int count_members = tfmHomePresenter.getMemberCount(context,unique_ik_number);
                int ma_max,ma_min,kkg_max,kkg_min;
                if (season_id >= 20){
                    ma_max = Integer.parseInt(tfmAppVariables.getMa_max_new());
                    kkg_max = Integer.parseInt(tfmAppVariables.getKkg_max_new());
                    ma_min = Integer.parseInt(tfmAppVariables.getMa_min_new());
                    kkg_min = Integer.parseInt(tfmAppVariables.getKkg_min_new());
                }else{
                    ma_max = Integer.parseInt(tfmAppVariables.getMa_max_old());
                    kkg_max = Integer.parseInt(tfmAppVariables.getKkg_max_old());
                    ma_min = Integer.parseInt(tfmAppVariables.getMa_min_old());
                    kkg_min = Integer.parseInt(tfmAppVariables.getKkg_min_old());
                }
                if (memberProgram.equalsIgnoreCase("MA")){
                    /*progress_bar.setProgress(count_members);
                    progress_bar.setMax(ma_max);*/
                    if (count_members >= ma_max){
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_green));
                    }else if (count_members >= ma_min){
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_blue));
                    }else{
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_red));
                    }
                }else{
                    /*progress_bar.setProgress(count_members);
                    progress_bar.setMax(kkg_max);*/
                    if (count_members >= kkg_max){
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_green));
                    }else if (count_members >= kkg_min){
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_blue));
                    }else{
                        check_circle.setCircleBackgroundColor(context.getResources().getColor(R.color.view_red));
                    }
                }
            }


        }
    }
}
