package com.babbangona.tgesign_up;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.babbangona.tgesign_up.Api.SharedPreference;
import com.babbangona.tgesign_up.Api.UploadTFMData;
import com.babbangona.tgesign_up.Api.uploadSchedule;
import com.babbangona.tgesign_up.Home.TFMHomeInterface;
import com.babbangona.tgesign_up.Home.TFMHomePresenter;
import com.babbangona.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerViewAdapter;
import com.babbangona.tgesign_up.TFMRecyclers.TFMHomeRecycler.VerticalSpaceItemDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TFMHome extends AppCompatActivity implements TFMHomeInterface {

    @BindView(R.id.toolbar_tfm)
    Toolbar toolbar_tfm;

    @BindView(R.id.et_search)
    TextView et_search;

    @BindView(R.id.search_edit_text)
    EditText search_edit_text;

    @BindView(R.id.back_to_toolbar)
    ImageView back_to_toolbar;

    @BindView(R.id.search_linear_layout)
    LinearLayout search_linear_layout;

    @BindView(R.id.toolbar_linear_layout)
    LinearLayout toolbar_linear_layout;

    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    @BindView(R.id.bt_ward_selector)
    Button bt_ward_selector;

    @BindView(R.id.tv_ward_selected)
    TextView tv_ward_selected;

    TFMHomePresenter tfmHomePresenter;
    LeaderCardRecyclerViewAdapter adapter;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfm_home_page);
        ButterKnife.bind(TFMHome.this);
        tfmHomePresenter = new TFMHomePresenter(TFMHome.this);
        sharedPreference = new SharedPreference(TFMHome.this);
//        HashMap<String,String> user = sharedPreference.getUserDetails();
        setSupportActionBar(toolbar_tfm);
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);

        tfmHomePresenter.buttonColor(bt_ward_selector);
        tfmHomePresenter.setTextViewText(tv_ward_selected,TFMHome.this);

        recycler_view.setHasFixedSize(true);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.tfm_home_linear_spacing_small);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(smallPadding);
        recycler_view.addItemDecoration(verticalSpaceItemDecoration);
        adapter = new LeaderCardRecyclerViewAdapter(tfmHomePresenter.getLeaderList(TFMHome.this),this);
        tfmHomePresenter.textWatcher(search_edit_text, adapter);
        recycler_view.setAdapter(adapter);

        toolbar_tfm.setNavigationOnClickListener(view -> tfmHomePresenter.loadPreviousActivity());
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
        Log.d("logging_id", String.valueOf(id));

        if (id == R.id.action_help) {
            //sync action goes here
            UploadTFMData uploadTFMData = new UploadTFMData(getApplicationContext());
            uploadTFMData.syncData();

            uploadSchedule cls2 = new uploadSchedule();
            cls2.getScheduleRecords(getApplicationContext());

            return true;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.et_search)
    public void move_to_search(View view){
        tfmHomePresenter.hideFeature(toolbar_linear_layout);
        tfmHomePresenter.showFeature(search_linear_layout);
        search_edit_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(search_edit_text, InputMethodManager.SHOW_IMPLICIT);
    }

    @OnClick(R.id.back_to_toolbar)
    public void move_away_from_search(View view){
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);
        search_edit_text.setText(null);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(et_search.getWindowToken(), 0);
    }

    @OnClick(R.id.bt_ward_selector)
    public void setBt_ward_selector(View view){
        startActivity(new Intent(TFMHome.this, TrainingWardLocation.class));
    }

    @Override
    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadPreviousActivity() {
        startActivity(new Intent(TFMHome.this, Main2Activity.class));
    }

    @Override
    public void showDialogForFailedCapture(MaterialAlertDialogBuilder builder, String s, Context context) {
        builder.setIcon(context.getResources().getDrawable(R.drawable.ic_warning))
                .setTitle(context.getResources().getString(R.string.tfm_dialog_attention))
                .setMessage(s)
                .setPositiveButton(context.getResources().getString(R.string.ok), (dialog, which) -> {
                    //this is to dismiss the dialog
                    dialog.dismiss();
                }).setCancelable(false)
                .show();
    }

    @Override
    public void setColor(Button button) {
        SharedPreference sharedPreference = new SharedPreference(this);
        HashMap<String,String> user = sharedPreference.getUserDetails();
        if (!Objects.requireNonNull(user.get(SharedPreference.KEY_TRAINING_WARD)).equalsIgnoreCase("Nothing Selected")){
            button.setBackgroundColor(this.getResources().getColor(R.color.green));
        }else{
            button.setBackgroundColor(this.getResources().getColor(R.color.colorRed));
        }
    }

    @Override
    public void setTextViewText(TextView textView, String text) {
        String word = "Selected ward: "+ text;
        textView.setText(word);
    }

    @Override
    public void onBackPressed(){
        tfmHomePresenter.loadPreviousActivity();
    }

    /**
     * This method handles whatever happens after capture or authentication of template
     * @param requestCode might be 419 or 519 either for edit or leader validation and registration respectively
     * @param resultCode 0 or 1 depending on whether success or failure of actions from request code
     * @param data Intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SharedPreference sharedPreference = new SharedPreference(this);
        if (requestCode == 419) {
                if (data != null) {
                    if (data.getIntExtra("RESULT", 0) == 1) {
                        //old member passes authentication
                        sharedPreference.setKeyPassAuthentication("1");
                        loadCaptureActivityWithProgressDialog();
                    }else{
                        //old member fails authentication
                        sharedPreference.setKeyPassAuthentication("0");
                        loadCaptureActivityWithProgressDialog();
                    }
                }
        }else if (requestCode == 519){
                if (data != null) {
                    if (data.getIntExtra("RESULT", 0) == 1) {
                        //This is supposed to be used for new or old members...
                        loadFormMemberInformation();
                    }else if (data.getIntExtra("RESULT", 0) == 2){
                        tfmHomePresenter.showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_not_found),TFMHome.this);
                    }else{
                        tfmHomePresenter.showDialogForFailedCapture(this.getResources().getString(R.string.tfm_face_already_registered),TFMHome.this);

                    }
                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void loadCaptureActivityWithProgressDialog(){
        final Intent intent = new Intent(TFMHome.this, CaptureActivity.class);
        final ProgressDialog pd1 = new ProgressDialog(TFMHome.this);
        pd1.setMessage(this.getResources().getString(R.string.tfm_wait_for_recapture));
        pd1.show();
        new CountDownTimer(5000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
            }
            @Override
            public void onFinish() {
                pd1.dismiss();
                startActivityForResult(intent,519);
            }
        }.start();
    }

    void loadFormMemberInformation(){
        Intent intent = new Intent(this, FormMemberInformation.class);
        startActivity(intent);
    }


}
