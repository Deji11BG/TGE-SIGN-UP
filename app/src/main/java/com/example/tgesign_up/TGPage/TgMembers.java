package com.example.tgesign_up.TGPage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.tgesign_up.Api.SharedPreference;
import com.example.tgesign_up.Database.SharedPreferences.SharedPreferenceController;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tgesign_up.Database.TFM.TFMDatabase;
import com.example.tgesign_up.Database.TFM.Table.prospectiveTGLTable;
import com.example.tgesign_up.R;
import com.example.tgesign_up.TFMRecyclers.TFMHomeRecycler.VerticalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TgMembers extends AppCompatActivity {
    SharedPreference sharedPreferenceController;
    List<prospectiveTGLTable.prospectiveTGLTableRecycler> membertList;
    RecyclerView recyclerView;
    List dataResult;
    String ikNumber;
    public static Button nextButton;
    public static TextView harvestStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tg_members);
        sharedPreferenceController= new SharedPreference(getApplicationContext());


        int smallPadding = getResources().getDimensionPixelSize(R.dimen.tfm_home_linear_spacing_small);
        VerticalSpaceItemDecoration verticalSpaceItemDecoration = new VerticalSpaceItemDecoration(smallPadding);

        recyclerView = findViewById(R.id.recyclerViewMemberFields);
        nextButton= findViewById(R.id.next);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(verticalSpaceItemDecoration);
        HashMap<String,String> user = sharedPreferenceController.getUserDetails();
        ikNumber = user.get(SharedPreference.KEY_IK_NUMBER);

        membertList = new ArrayList<>();
        membertList= getMemberDetails(getApplicationContext(),ikNumber);

        TgMembersAdapter adapter = new TgMembersAdapter(TgMembers.this, membertList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // function to retrieve lga data from preloaded data on state_info table
    @SuppressLint("StaticFieldLeak")
    private List<prospectiveTGLTable.prospectiveTGLTableRecycler> getMemberDetails(Context context, String ikNumber) {
        List<prospectiveTGLTable.prospectiveTGLTableRecycler> members = new ArrayList<>();
        try {
            members = new getMembers(context){
                @Override
                protected void onPostExecute(List<prospectiveTGLTable.prospectiveTGLTableRecycler> s) {}
            }.execute(ikNumber).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("members",members.toString());

        return members;
    }

    //getStateDetails
    @SuppressLint("StaticFieldLeak")
    public static abstract class getMembers extends AsyncTask<String, Void, List<prospectiveTGLTable.prospectiveTGLTableRecycler> > {
        Context mCtx;
        TFMDatabase tfmDatabase;

        getMembers(Context context) {
            this.mCtx = context;
        }

        @Override
        protected List<prospectiveTGLTable.prospectiveTGLTableRecycler> doInBackground(String... strings) {
            try{
                tfmDatabase = TFMDatabase.getInstance(mCtx);
                Log.d("state00",strings[0]);

                return tfmDatabase.getProspectiveTGLDao().getMembers(strings[0]);
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
    }

}
