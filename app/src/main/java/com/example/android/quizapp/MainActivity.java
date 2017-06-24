package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.quizapp.data.ProfileContract;
import com.example.android.quizapp.data.ProfileDbHelper;

import java.io.IOException;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public static HashMap<String, MapObject[]> mapContainingCategories = new HashMap<String, MapObject[]>();
    public Context mContext;
    public static boolean mTwoPane=false;
    public static Cursor CURRENT_USER_CURSOR;
    public static long CURRENT_USER_PROFILE_ID;
    public static String CURRENT_USER_NAME;
    public static String CURRENT_USER_EMAIL;
    public static String CURRENT_USER_PASSWORD;
    public static String CURRENT_USER_DOB;
    public static String CURRENT_USER_GENDER;
    public static String CURRENT_USER_IMAGE="";
    public static Bitmap CURRENT_USER_IMAGE_BITMAP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.container) != null;
        Log.d("mt","mTwoPane: "+mTwoPane);

        Log.d("database",String.valueOf(CURRENT_USER_PROFILE_ID));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#535655")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_category);

        mContext = getApplicationContext();

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        CURRENT_USER_PROFILE_ID=sharedPreferences.getLong("CURRENT_USER_PROFILE_ID", 0);

        Log.d("database","Current user profile== "+String.valueOf(CURRENT_USER_PROFILE_ID));
        SQLiteDatabase database= (new ProfileDbHelper(getApplicationContext())).getReadableDatabase();

        String[] selectArgs=new String[]{String.valueOf(CURRENT_USER_PROFILE_ID)};
        CURRENT_USER_CURSOR=database.query(ProfileContract.ProfileEntry.TABLE_NAME,null, ProfileContract.ProfileEntry._ID+"=?",selectArgs,null,null,null);
        CURRENT_USER_CURSOR.moveToFirst();


        int namePos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_NAME);
        int emailPos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_EMAIL);
        int passwordPos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_PASSWORD);
        int dobPos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_DOB);
        int genderPos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_GENDER);
        int imagePos=CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_IMAGE);

        CURRENT_USER_NAME = CURRENT_USER_CURSOR.getString(namePos);
        CURRENT_USER_EMAIL = CURRENT_USER_CURSOR.getString(emailPos);
        CURRENT_USER_PASSWORD = CURRENT_USER_CURSOR.getString(passwordPos);
        CURRENT_USER_DOB = CURRENT_USER_CURSOR.getString(dobPos);
        CURRENT_USER_GENDER = CURRENT_USER_CURSOR.getString(genderPos);
        CURRENT_USER_IMAGE = CURRENT_USER_CURSOR.getString(imagePos);

        Log.d("database", CURRENT_USER_IMAGE);

        try {
            CURRENT_USER_IMAGE_BITMAP = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(CURRENT_USER_IMAGE));
        } catch (IOException e) {
            Log.d("database","MainActivity: "+e.toString());
            CURRENT_USER_IMAGE_BITMAP= BitmapFactory.decodeResource(getResources(), R.drawable.ic_profile_default);
            e.printStackTrace();
        }
        //setting title of AppBar with user details
        Log.d("database", "App bar exists: "+String.valueOf((TextView) findViewById(R.id.app_bar_main_view)==null));

        if((TextView) findViewById(R.id.app_bar_main_view)!=null)
        {
            ((TextView) findViewById(R.id.app_bar_main_view)).setText(CURRENT_USER_NAME);
            ((TextView) findViewById(R.id.app_bar_main_view)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("database", "onStart called");
        if((TextView) findViewById(R.id.app_bar_main_view)!=null)
        {
            ((TextView) findViewById(R.id.app_bar_main_view)).setText(CURRENT_USER_NAME);

            ((TextView) findViewById(R.id.app_bar_main_view)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            });
        }

        if((ImageView) findViewById(R.id.app_bar_main_profile_image)!=null)
        {
            Log.d("database", "MainActivity: "+CURRENT_USER_IMAGE);
            ((ImageView) findViewById(R.id.app_bar_main_profile_image)).setImageBitmap(CURRENT_USER_IMAGE_BITMAP);

            ((ImageView) findViewById(R.id.app_bar_main_profile_image)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), AccountActivity.class);
                    startActivity(intent);
                }
            });
        }
    }
}