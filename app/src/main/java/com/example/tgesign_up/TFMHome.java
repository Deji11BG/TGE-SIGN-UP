package com.example.tgesign_up;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Home.TFMHomeInterface;
import com.example.tgesign_up.Home.TFMHomePresenter;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.LeaderCardRecyclerViewAdapter;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.VerticalSpaceItemDecoration;

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
        HashMap<String,String> user = sharedPreference.getUserDetails();
        setSupportActionBar(toolbar_tfm);
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);


        recycler_view.setHasFixedSize(true);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.tfm_home_linear_spacing_small);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(layoutManager);
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(smallPadding);
        recycler_view.addItemDecoration(verticalSpaceItemDecoration);
        adapter = new LeaderCardRecyclerViewAdapter(tfmHomePresenter.getLeaderList(TFMHome.this,user.get(SharedPreference.KEY_STAFF_ID)),this);
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
        startActivity(new Intent(TFMHome.this, MainActivity.class));
    }

    @Override
    public void onBackPressed(){
        tfmHomePresenter.loadPreviousActivity();
    }


}
