package com.example.tgesign_up;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TgMembers extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor prefsEdit;
    List<TgMembersModel> fieldList;
    RecyclerView recyclerView;
    List dataResult;
    String memberId,infoSource;
    public static Button nextButton;
    public static TextView harvestStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_members);

        prefs = getSharedPreferences("Harvest", MODE_PRIVATE);
        prefsEdit = prefs.edit();
        recyclerView = (RecyclerView) findViewById(R.id.recylcerViewMemberFields);
        nextButton= (Button) findViewById(R.id.next);
        harvestStatus= (TextView) findViewById(R.id.tv_harvest_status) ;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        memberId= prefs.getString("member_id",null);
        infoSource= prefs.getString("info_source",null);
        Log.i("load", memberId+infoSource);
        HarvestDBHandler db =  new HarvestDBHandler(this);
        dataResult= db.getMembersFields(memberId);
        fieldList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(dataResult);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject fieldData = jsonArray.getJSONObject(i);
                fieldList.add(new Field(
                    fieldData.getString("field_id")
                ));
            }
        } catch (JSONException e1) {
            Log.e("FAILED", "Json parsing error: " + e1.getMessage());
        }
        TgMembersAdapter adapter = new TgMembersAdapter(TgMembers.this, fieldList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }




}
