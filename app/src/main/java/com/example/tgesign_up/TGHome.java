package com.example.tgesign_up;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Api.GPSController;
import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import com.example.tgesign_up.FormMemberLocationMVP.FormMemberLocationModel;
import com.example.tgesign_up.TFMRecyclers.TGRecycler.MemberCardRecyclerViewAdapter;
import com.example.tgesign_up.TGHomeMVP.TGHomeInterface;
import com.example.tgesign_up.TGHomeMVP.TGHomePresenter;
import com.example.tgesign_up.TGHomeMVP.TGLeaderModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TGHome extends AppCompatActivity implements TGHomeInterface {

    //Verify recapture and load form member information activity

    @BindView(R.id.toolbar_tg)
    Toolbar toolbar_tg;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.sw_leader)
    SwitchCompat sw_leader;

    @BindView(R.id.tv_lga)
    TextView tv_lga;

    @BindView(R.id.tv_location)
    TextView tv_location;

    @BindView(R.id.tv_crop)
    TextView tv_crop;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;

    @BindView(R.id.btn_add_new_member)
    MaterialButton btn_add_new_member;

    @BindView(R.id.leader_card)
    LinearLayout leader_card;

    @BindView(R.id.tv_leader_name)
    TextView tv_leader_name;

    @BindView(R.id.tv_leader_role)
    TextView tv_leader_role;

    @BindView(R.id.tv_member_program)
    TextView tv_member_program;

    @BindView(android.R.id.content)
    View parentLayout;

    /*@BindView(R.id.leader_registration_confirmation_dialog)
    LinearLayout leader_registration_confirmation_dialog;

    @BindView(R.id.ma_radio_button)
    MaterialRadioButton ma_radio_button;

    @BindView(R.id.kkg_radio_button)
    MaterialRadioButton kkg_radio_button;

    @BindView(R.id.btnCancel)
    Button btnCancel;

    @BindView(R.id.btnConfirm)
    Button btnConfirm;

    private BottomSheetBehavior sheetBehavior;*/

    TGHomePresenter tgHomePresenter;
    FormMemberLocationModel formMemberLocationModel;
    TGLeaderModel tgLeaderModel;
    TGLeaderModel.CountModel countModel;
    SharedPreferenceController sharedPreferenceController;
    static Boolean isTouched = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_home_page);
        ButterKnife.bind(TGHome.this);
        tgLeaderModel = new TGLeaderModel();
        countModel = new TGLeaderModel.CountModel();
        tgHomePresenter = new TGHomePresenter(TGHome.this);
        formMemberLocationModel = new FormMemberLocationModel();
        setSupportActionBar(toolbar_tg);

        Objects.requireNonNull(getSupportActionBar()).setTitle("TG Home");
        GPSController.checkPermission(this);
        GPSController.initialiseLocationListener(this);

        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String, String> user = sharedPreference.getUserDetails();
        tgLeaderModel.setUnique_ik_number(user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));

        tgLeaderModel = tgHomePresenter.getLeaderDetails(TGHome.this,tgLeaderModel.getUnique_ik_number());
        Objects.requireNonNull(getSupportActionBar()).setTitle(tgHomePresenter.ikNumberManipulator(tgLeaderModel.getIk_number(),tgLeaderModel.getUnique_ik_number()));
        tgHomePresenter.printLeaderFullName(tgLeaderModel.getFirst_name(),tgLeaderModel.getMiddle_name(),
                tgLeaderModel.getLast_name(),tgLeaderModel.getRole(),tgLeaderModel.getMember_program());
        tgHomePresenter.setTextOfTextViews(tv_lga,this.getResources().getString(R.string.leader_lga), formMemberLocationModel.getLgaName(this,TGLeaderModel.getLgaIDFromWardIDResult(this,tgLeaderModel.getWard_id())));
        tgHomePresenter.setTextOfTextViews(tv_location,this.getResources().getString(R.string.leader_location),formMemberLocationModel.getWardName(this,tgLeaderModel.getWard_id())+", "+tgLeaderModel.getVillage_name());
        tgHomePresenter.setTextOfTextViews(tv_crop,this.getResources().getString(R.string.leader_crop),tgLeaderModel.getCrop_type());
        tgHomePresenter.setLeader_image(this,tgLeaderModel.getUnique_member_id(),tgLeaderModel.getUnique_ik_number());
        tgHomePresenter.recyclerViewSetter(recycler_view, TGHome.this,tgLeaderModel.getUnique_ik_number());

        countModel = tgHomePresenter.getLeaderCount(TGHome.this,tgLeaderModel.getUnique_ik_number());
        tgHomePresenter.switchController(sw_leader,countModel.getCount());

        toolbar_tg.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tgHomePresenter.loadPreviousActivity();
            }
        });

        sw_leader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouched = true;
                return false;
            }
        });

        /*sheetBehavior = BottomSheetBehavior.from(leader_registration_confirmation_dialog);

        addBehaviourToBottomSheet(sheetBehavior);*/

    }

    void addBehaviourToBottomSheet(BottomSheetBehavior sheetBehavior){
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                    case BottomSheetBehavior.STATE_DRAGGING:{
                        /*ma_radio_button.setChecked(false);
                        kkg_radio_button.setChecked(false);*/
                        tgHomePresenter.switchController(sw_leader,countModel.getCount());
                    }
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    /*void showBottomSheet(){
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }*/

    /*@OnClick(R.id.btnCancel)
    public  void setBtnCancel(){
        showBottomSheet();
        ma_radio_button.setChecked(false);
        kkg_radio_button.setChecked(false);
        tgHomePresenter.switchController(sw_leader,countModel.getCount());
    }*/

    /*@OnClick(R.id.btnConfirm)
    public  void setBtnConfirm(View view){
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        if (!ma_radio_button.isChecked() && !kkg_radio_button.isChecked()){
            tgHomePresenter.displayToastForLeader(this,this.getResources().getString(R.string.tfm_select_member_program));
        }else{
            if (ma_radio_button.isChecked()){
                sharedPreference.setKeyMemberProgram(this.getResources().getString(R.string.bottom_sheet_select_MA));
            }else if (kkg_radio_button.isChecked()){
                sharedPreference.setKeyMemberProgram(this.getResources().getString(R.string.bottom_sheet_select_KKG));
            }
            showBottomSheet();
            tgHomePresenter.loadNextActivityForLeader();
        }
    }*/

    @OnCheckedChanged(R.id.sw_leader)
    public void registerLeader(boolean isChecked){
        if (isTouched){
            if (isChecked){
                isTouched = false;
                tgHomePresenter.displayDialog(this.getResources().getString(R.string.tfm_question_register_leader_now),this);
            }else{
                if (countModel.getCount() == 0){
                    sw_leader.setChecked(false);
                }else{
                    Toast toast = Toast.makeText(this, this.getResources().getString(R.string.tfm_leader_cannot_be_deactivated), Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                    sw_leader.setChecked(true);
                }
            }
        }

    }

    @OnClick(R.id.btn_add_new_member)
    public void setBtn_add_new_member(){

        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String, String> user = sharedPreference.getUserDetails();
        TGLeaderModel.CountModel countModel = tgHomePresenter.getLeaderCount(TGHome.this,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));

        if (countModel.getCount() == 0){
            tgHomePresenter.snackBarPresenter(this,this.getResources().getString(R.string.tfm_register_leader_before_member),parentLayout);
        }else{
            /*int season_id = Integer.parseInt(tgHomePresenter.getSeasonID(this,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER)));
            int member_count = tgHomePresenter.getMemberCount(this,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
            String member_program = tgHomePresenter.getMemberProgram(this,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER));
            TFMAppVariables tfmAppVariables = tgHomePresenter.getTFMAppVariables(this,this.getResources().getString(R.string.variable_id));
            if (season_id == 20){
                //get all that is new
                if (member_program.equalsIgnoreCase(this.getResources().getString(R.string.bottom_sheet_select_MA))){
                    if (member_count >= Integer.parseInt(tfmAppVariables.getMa_max_new())){
                        tgHomePresenter.snackBarPresenter(this,this.getResources().getString(R.string.tfm_maximum_member_reached),parentLayout);
                    }else{
                        tgHomePresenter.showDialogForMemberCapture(this.getResources().getString(R.string.tfm_capture_new_member_template),this);
                    }
                }else{
                    if (member_count >= Integer.parseInt(tfmAppVariables.getKkg_max_new())){
                        tgHomePresenter.snackBarPresenter(this,this.getResources().getString(R.string.tfm_maximum_member_reached),parentLayout);
                    }else{
                        tgHomePresenter.showDialogForMemberCapture(this.getResources().getString(R.string.tfm_capture_new_member_template),this);
                    }
                }
            }else{
                //use all that is old
                if (member_program.equalsIgnoreCase(this.getResources().getString(R.string.bottom_sheet_select_MA))){
                    if (member_count >= Integer.parseInt(tfmAppVariables.getMa_max_old())){
                        tgHomePresenter.snackBarPresenter(this,this.getResources().getString(R.string.tfm_maximum_member_reached),parentLayout);
                    }else{
                        tgHomePresenter.showDialogForMemberCapture(this.getResources().getString(R.string.tfm_capture_new_member_template),this);
                    }
                }else{
                    if (member_count >= Integer.parseInt(tfmAppVariables.getKkg_max_old())){
                        tgHomePresenter.snackBarPresenter(this,this.getResources().getString(R.string.tfm_maximum_member_reached),parentLayout);
                    }else{
                        tgHomePresenter.showDialogForMemberCapture(this.getResources().getString(R.string.tfm_capture_new_member_template),this);
                    }
                }
            }*/
        }
    }

    @OnClick(R.id.leader_card)
    public void setLeader_card(){
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        final HashMap<String, String> user = sharedPreference.getUserDetails();
        //creating a popup menu
        PopupMenu popup = new PopupMenu(this, leader_card);
        //inflating menu from xml resource
        TGLeaderModel.CountModel countModel = tgHomePresenter.getLeaderCount(TGHome.this,tgLeaderModel.getUnique_ik_number());

        if (countModel.getCount() == 0){
            tgHomePresenter.displayToastForLeader(this,this.getResources().getString(R.string.tfm_caution_register_member_first));
        }else if (countModel.getCount() > 0){
            /*if (sharedPreferenceController.getKeyStaffProgram().equalsIgnoreCase("TGE")){
                popup.inflate(R.menu.options_menu_leader_tge);
            }else{
                popup.inflate(R.menu.options_menu_leader);
            }*/
        }

        //adding click listener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_field:
                        //Launches field mapping
                        tgHomePresenter.loadFieldMappingForLeader(TGHome.this, tgHomePresenter.getLeaderUniqueID(TGHome.this, user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER)));
                        return true;
                    case R.id.edit:
                        //Edit
                        tgHomePresenter.roleToRegisterController(TGHome.this, "Leader", "edit");
                        tgHomePresenter.loadNextActivityForLeaderEdit(TGHome.this, tgHomePresenter.getLeaderUniqueID(TGHome.this, user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER)));
                        return true;
                    default:
                        return false;
                }
            }
        });
        //displaying the popup
        popup.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tfm_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            HashMap<String,String> user = sharedPreferenceController.getUserDetails();
            try {
                /*startActivity(new Intent(this, ViewActivityIssues.class)
                        .putExtra("activity_id", "tfm_1")
                        .putExtra("app_id", "tfm")
                        .putExtra("staff_id", user.get(SharedPreferenceController.KEY_STAFF_ID))
                        .putExtra("app_language", user.get(SharedPreferenceController.KEY_APP_LANG))
                        .putExtra("user_location", "Lagos"));*/

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Activity not found", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setLeaderSwitchTextView(String info, String role, String member_program) {
        tv_leader_name.setText(info);
        tv_leader_role.setText(role);
        tgHomePresenter.setTextOfTextViews(tv_member_program,this.getResources().getString(R.string.leader_member_program),
                checkNullString(member_program,this.getResources().getString(R.string.leader_member_none)));
    }

    public String checkNullString(String input, String output){
        String  result;
        if (input == null){
            result = output;
        }else{
            result = input;
        }
        return result;
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
    public void setLeader_image(Bitmap bitmap) {
        if (bitmap != null){
            profile_image.setImageBitmap(bitmap);
        }else{
            profile_image.setImageResource(R.drawable.bg_logo);
        }
    }

    @Override
    public void recyclerController(RecyclerView member_recycler_view, MemberCardRecyclerViewAdapter adapter) {
        member_recycler_view.setAdapter(adapter);
    }

    @Override
    public void setCheckOfSwitch(SwitchCompat switchCompat, boolean bool) {
        switchCompat.setChecked(bool);
    }

    @Override
    public void loadNextActivity() {
        Intent intent = new Intent(this, CaptureActivity.class);
        startActivityForResult(intent,719);
    }

    @Override
    public void loadPreviousActivity() {
        Intent intent = new Intent(TGHome.this, TFMHome.class);
        isTouched = false;
        startActivity(intent);
    }

    @Override
    public void displaySnackBar(Context context, String message, View view) {
        //View parentLayout = findViewById(android.R.id.content);
        final Snackbar snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        snackBar.setAction(context.getResources().getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                //cancel snackBar
                snackBar.dismiss();
            }
        })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

    @Override
    public void displayDialog(String message, final Context context){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(false);
        builder1.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
                //leader logic changed to
                tgHomePresenter.roleToRegisterController(context, "Leader", "new");
                //showBottomSheet();
                //tgHomePresenter.loadNextActivityForLeader();
                TGHome.this.loadFormMemberInformation();
            }
        });
        builder1.setNeutralButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                tgHomePresenter.switchController(sw_leader, countModel.getCount());
            }
        });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void displayToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    @Override
    public void loadNextActivityForLeaderEdit() {
        startActivity(new Intent(this, FormMemberInformation.class));
    }

    @Override
    public void loadNextActivityForLeader() {
        String  message = this.getResources().getString(R.string.tfm_authentication_preamble);
        tgHomePresenter.showDialogForLeaderVerification(message,this);
    }

    @Override
    public void showDialogForLeaderVerification(MaterialAlertDialogBuilder builder, String s, Context context) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //this is to dismiss the dialog
                        dialog.dismiss();
                        TGHome.this.loadVerificationActivity();
                    }
                }).setNeutralButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //this dismisses dialog
                dialog.dismiss();
            }
        }).setCancelable(false)
                .show();
    }

    @Override
    public void launchFieldMappingActivity(Context context, String unique_member_id) {
        /*Intent intent = new Intent(this, activefield.class);
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.setKeyUniqueIdFieldMapping(unique_member_id);
        startActivity(intent);*/
    }

    @Override
    public void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //this is to dismiss the dialog
                        dialog.dismiss();
                    }
                }).setCancelable(false)
                .show();
    }

    @Override
    public void showDialogForFailedVerification(MaterialAlertDialogBuilder builder, String s, Context context) {
        LinearLayout layout = new LinearLayout(this);

        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(300, 300);
        layoutParams.gravity=Gravity.CENTER;

        final ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(layoutParams);

        File ImgDirectory = new File(Environment.getExternalStorageDirectory().getPath(), "TestPictures");
        String image_name = File.separator + tgLeaderModel.getUnique_member_id() + ".jpg";
        File imgFile = new File(ImgDirectory.getAbsoluteFile(),image_name);
        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }else{
            imageView.setImageResource(R.drawable.bg_logo);
        }

        layout.addView(imageView);

        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setView(layout)
                .setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //this is to dismiss the dialog
                        dialog.dismiss();
                    }
                }).setCancelable(false)
                .show();
    }

    @Override
    public void showDialogForMemberCapture(MaterialAlertDialogBuilder builder, String s, final Context context) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //this is to dismiss the dialog
                        dialog.dismiss();
                        tgHomePresenter.roleToRegisterController(context, "Member", "new");
                        tgHomePresenter.loadNextActivity();
                    }
                }).setNeutralButton(context.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //this dismisses dialog
                dialog.dismiss();
            }
        }).setCancelable(false)
                .show();
    }

    void loadVerificationActivity(){
        Intent intent = new Intent(this, VerifyActivity.class);
        intent.putExtra("role","Leader");
        startActivityForResult(intent,519);
    }

    @Override
    public void onResume(){
        super.onResume();
        isTouched = false;
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        TGLeaderModel.CountModel countModel = tgHomePresenter.getLeaderCount(TGHome.this,unique_ik_number);
        tgHomePresenter.switchController(sw_leader,countModel.getCount());
        TGLeaderModel tgLeaderModel = tgHomePresenter.getLeaderDetails(TGHome.this,unique_ik_number);
        FormMemberLocationModel formMemberLocationModel = new FormMemberLocationModel();
        tgHomePresenter.printLeaderFullName(tgLeaderModel.getFirst_name(),tgLeaderModel.getMiddle_name(),
                tgLeaderModel.getLast_name(),tgLeaderModel.getRole(),tgLeaderModel.getMember_program());
        tgHomePresenter.setTextOfTextViews(tv_lga,this.getResources().getString(R.string.leader_lga), formMemberLocationModel.getLgaName(this,TGLeaderModel.getLgaIDFromWardIDResult(this,tgLeaderModel.getWard_id())));
        tgHomePresenter.setTextOfTextViews(tv_location,this.getResources().getString(R.string.leader_location),formMemberLocationModel.getWardName(this,tgLeaderModel.getWard_id())+", "+tgLeaderModel.getVillage_name());
        tgHomePresenter.setTextOfTextViews(tv_crop,this.getResources().getString(R.string.leader_crop),tgLeaderModel.getCrop_type());
        tgHomePresenter.setLeader_image(this,tgLeaderModel.getUnique_member_id(),tgLeaderModel.getUnique_ik_number());
    }

    @Override
    public  void  onBackPressed(){
        isTouched = false;
        super.onBackPressed();
        tgHomePresenter.loadPreviousActivity();
    }

    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode might be 419 or 519 either for edit or leader validation and registration respectively
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        SharedPreference sharedPreference = new SharedPreference(getApplicationContext());
        HashMap<String, String> user = sharedPreference.getUserDetails();
        String unique_ik_number = user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER);
        int season_id = Integer.parseInt(tgHomePresenter.getSeasonID(this,user.get(SharedPreference.KEY_UNIQUE_IK_NUMBER)));
        if (requestCode == 419) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //old member passes authentication
                    sharedPreference.setKeyPassAuthentication("1");
                    loadCaptureActivityForOldMembers();
                }else{
                    //old member fails authentication
                    sharedPreference.setKeyPassAuthentication("0");
                    loadCaptureActivityForOldMembers();
                }
            }
        }else if (requestCode == 519){
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //leader passes authentication
                    sharedPreference.setKeyPassAuthentication("1");
                    loadCaptureActivityWithProgressDialog();
                }else{
                    //leader fails authentication
                    if (season_id == 20){
                        tgHomePresenter.showDialogForFailedVerification(this.getResources().getString(R.string.tfm_new_leader_template_failed),TGHome.this);
                    }else{
                        sharedPreference.setKeyPassAuthentication("0");
                        loadCaptureActivityWithProgressDialog();
                    }
                }
            }
        }else if (requestCode == 619) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //This is supposed to be used to save the leader's details after capture...

                    //TGLeaderModel tgLeaderModel = tgHomePresenter.getLeaderDetails(TGHome.this,unique_ik_number);

                    //tgHomePresenter.collateAllResult(this,tgLeaderModel);

                    //The logic is now changed to load form member information activity

                    loadFormMemberInformation();

                    TGLeaderModel.CountModel countModel = tgHomePresenter.getLeaderCount(TGHome.this,unique_ik_number);
                    tgHomePresenter.switchController(sw_leader,countModel.getCount());
                }else{
                    tgHomePresenter.showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_already_registered),TGHome.this);
                }
            }
        }else if (requestCode == 719) {
            if (data != null) {
                if (data.getIntExtra("RESULT", 0) == 1) {
                    //This is supposed to be used for new or old members...
                    loadFormMemberInformation();
                }else if (data.getIntExtra("RESULT", 0) == 2){
                    tgHomePresenter.showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_not_found),TGHome.this);
                }else{
                    tgHomePresenter.showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_already_registered),TGHome.this);

                }
            }
        }
        TGLeaderModel.CountModel countModel = tgHomePresenter.getLeaderCount(TGHome.this,unique_ik_number);
        tgHomePresenter.switchController(sw_leader,countModel.getCount());
        super.onActivityResult(requestCode, resultCode, data);
    }

    void loadCaptureActivityWithProgressDialog(){
        final Intent intent = new Intent(TGHome.this, CaptureActivity.class);
        final ProgressDialog pd1 = new ProgressDialog(TGHome.this);
        pd1.setMessage(this.getResources().getString(R.string.tfm_wait_for_recapture));
        pd1.show();
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                pd1.dismiss();
                startActivityForResult(intent,619);
            }
        }.start();
    }

    void loadCaptureActivityForOldMembers(){
        final Intent intent = new Intent(TGHome.this, CaptureActivity.class);
        final ProgressDialog pd1 = new ProgressDialog(TGHome.this);
        pd1.setMessage(this.getResources().getString(R.string.tfm_wait_for_recapture));
        pd1.show();
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                pd1.dismiss();
                startActivityForResult(intent,719);
            }
        }.start();
    }

    void loadFormMemberInformation(){
        Intent intent = new Intent(this, FormMemberInformation.class);
        startActivity(intent);
    }


}
