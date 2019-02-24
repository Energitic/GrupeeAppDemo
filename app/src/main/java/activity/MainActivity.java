package activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dipali.grupeedemoapp.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import database.DatabaseHandler;
import model.ApiResponse;
import model.DogDetails;
import rest.ApiClient;
import rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseHandler db;
    private DogDetails dogDetails;
    private ApiInterface apiService;
    private ArrayList<DogDetails> dogDetailsArrayList;

    private ImageView img_dog;
    private Button btn_dislike, btn_like, btn_liked_photos;
    private String imageUri, responseString, Date, Time;
    private SimpleDateFormat date, time;
    private ActionBar actionBar;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        initialization();
    }

    //initialization of view component
    public void initialization() {
        db = new DatabaseHandler(MainActivity.this);

        actionBar = getSupportActionBar();
        actionBar.hide();

        img_dog = (ImageView) findViewById(R.id.img_dog);
        btn_dislike = (Button) findViewById(R.id.btn_dislike);
        btn_like = (Button) findViewById(R.id.btn_like);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn_liked_photos = (Button) findViewById(R.id.btn_liked_photos);

        //Onclick method
        btn_dislike.setOnClickListener(MainActivity.this);
        btn_like.setOnClickListener(MainActivity.this);
        btn_liked_photos.setOnClickListener(MainActivity.this);

        //ApiCall for Dog Image
        GetApiResponse();
    }

    @Override
    public void onClick(View view) {

        //Get Dog Random image
        if (view == btn_dislike) {
            //ApiCall for Dog Image
            GetApiResponse();
        }

        //Save Dog Details
        if (view == btn_like) {

            Date curDate = new Date();

            date = new SimpleDateFormat("E, dd MMM yyyy");
            time = new SimpleDateFormat("HH:mm:ss");
            Date = date.format(curDate);
            Time = time.format(curDate);

            dogDetails = new DogDetails();
            dogDetails.setDog_img(imageUri);
            dogDetails.setDate(Date);
            dogDetails.setTime(Time);

            //Insert Dog Details
            db.addDogDetails(dogDetails);

            //ApiCall for Dog Image
            GetApiResponse();
        }

        if (view == btn_liked_photos) {

            dogDetailsArrayList = db.getDogDetails();

            if (dogDetailsArrayList.size() == 0) {

                Toast.makeText(getApplicationContext(), "No Data In List", Toast.LENGTH_LONG).show();
            } else {
                //Show Liked Dog List
                Intent intent = new Intent(this, LikedProfileActivity.class);
                startActivity(intent);
            }
        }
    }

    //Get Api Response
    public void GetApiResponse() {

        apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponse> call = apiService.getDogRandomImage();

        // Set up progressbar before call
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                progressBar.setVisibility(View.GONE);
                responseString = response.body().getStatus();
                imageUri = response.body().getMessage();

                Picasso.with(MainActivity.this).load(imageUri).into(img_dog);
               /* Log.e("Api Status", responseString);
                Log.e("img_url", imageUri);*/
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);
                // Log error here since request failed
                Log.e("Not Get Response", t.toString());
            }
        });
    }
}

