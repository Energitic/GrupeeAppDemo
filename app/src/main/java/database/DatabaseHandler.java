package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import model.DogDetails;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dogDetailsProfile";
    private static final String TABLE_DOG_DETAILS = "details";
    private static final String KEY_ID = "id";
    private static final String KEY_DOG_IMG = "dog_img";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DOG_DETAILS_TABLE = "CREATE TABLE " + TABLE_DOG_DETAILS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_DOG_IMG + " TEXT,"
                + KEY_DATE + " TEXT,"
                + KEY_TIME + " TEXT" + ")";

        db.execSQL(CREATE_DOG_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOG_DETAILS);

        // Create tables again
        onCreate(db);
    }

    //insert dog details
    public void addDogDetails(DogDetails dogDetails) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DOG_IMG, dogDetails.getDog_img()); // Dog Img
        values.put(KEY_DATE, dogDetails.getDate()); // Date
        values.put(KEY_TIME, dogDetails.getTime()); // Time

        // Inserting Row
        db.insert(TABLE_DOG_DETAILS, null, values);
       // Log.e("Details Insert", "" + values);

        db.close(); // Closing database connection
    }

    //get all Details in a list view
    public ArrayList<DogDetails> getDogDetails() {

        ArrayList<DogDetails> dogDetailsList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DOG_DETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                DogDetails dogDetails = new DogDetails();

                dogDetails.setDog_img(cursor.getString(1));
                dogDetails.setDate(cursor.getString(2));
                dogDetails.setTime(cursor.getString(3));

                // Adding contact to list
                dogDetailsList.add(dogDetails);
            } while (cursor.moveToNext());
        }

        // Log.e("dogDetailsList", dogDetailsList.toString());
        // return contact list
        return dogDetailsList;
    }
}
