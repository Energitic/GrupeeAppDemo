package activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dipali.grupeedemoapp.R;

import java.util.ArrayList;

import adapter.DogDetailsAdapter;
import database.DatabaseHandler;
import model.DogDetails;

public class LikedProfileActivity extends AppCompatActivity {
    private DatabaseHandler db;

    private RecyclerView recyclerView;
    private ActionBar actionBar;
    private ArrayList<DogDetails> dogDetails;
    private DogDetailsAdapter dogDetailsAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liked_profile_activity);
        initialization();
    }

    //Initialized view
    public void initialization() {
        db = new DatabaseHandler(LikedProfileActivity.this);
        actionBar = getSupportActionBar();
        actionBar.hide();

        recyclerView = (RecyclerView) findViewById(R.id.rcv_profile_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        dogDetails = db.getDogDetails();

        dogDetailsAdapter = new DogDetailsAdapter(this, dogDetails);
        recyclerView.setAdapter(dogDetailsAdapter);
        dogDetailsAdapter.notifyDataSetChanged();
    }
}
