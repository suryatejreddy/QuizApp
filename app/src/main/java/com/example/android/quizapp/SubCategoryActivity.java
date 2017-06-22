package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class SubCategoryActivity extends AppCompatActivity {

    public Context mContext;
    //public static boolean mTwoPane =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        MainActivity.mTwoPane = findViewById(R.id.container) != null;
        Log.d("mt","mTwoPane: "+MainActivity.mTwoPane);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#535655")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_subcategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
        //String actionBarTitle="CATEGORIES";
        //if(getIntent().hasExtra("Key"))
          //  actionBarTitle=getIntent().getStringExtra("Key");
        TextView actionBarText=((TextView) findViewById(R.id.app_bar_category_view));
        if(actionBarText!=null)
        {
            actionBarText.setText(MainActivity.CURRENT_USER_NAME);
        }

        if((ImageView) findViewById(R.id.app_bar_category_profile_image)!=null)
        {
            Log.d("database", "MainActivity: "+MainActivity.CURRENT_USER_IMAGE);
            ((ImageView) findViewById(R.id.app_bar_category_profile_image)).setImageBitmap(MainActivity.CURRENT_USER_IMAGE_BITMAP);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        TextView actionBarText=((TextView) findViewById(R.id.app_bar_category_view));
        if(actionBarText!=null)
        {
            actionBarText.setText(MainActivity.CURRENT_USER_NAME);
        }

        if((ImageView) findViewById(R.id.app_bar_category_profile_image)!=null)
        {
            Log.d("database", "MainActivity: "+MainActivity.CURRENT_USER_IMAGE);
            ((ImageView) findViewById(R.id.app_bar_category_profile_image)).setImageBitmap(MainActivity.CURRENT_USER_IMAGE_BITMAP);
        }
    }
}
