package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    public static HashMap<String, MapObject[]> mapContainingCategories = new HashMap<String, MapObject[]>();
    public Context mContext;
    public static boolean mTwoPane=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTwoPane = findViewById(R.id.container) != null;
        Log.d("mt","mTwoPane: "+mTwoPane);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#535655")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_category);

        mContext = getApplicationContext();

    }
}