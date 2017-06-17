package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SubCategoryActivity extends AppCompatActivity {

    public Context mContext;
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        mTwoPane = findViewById(R.id.sub_category_fragment) != null;

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#535655")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_subcategory);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
        String actionBarTitle="CATEGORIES";
        if(getIntent().hasExtra("Key"))
            actionBarTitle=getIntent().getStringExtra("Key");
        TextView actionBarText=((TextView) findViewById(R.id.app_bar_subcategory_view));
        if(actionBarText!=null)
        {
            actionBarText.setText(actionBarTitle.toUpperCase());
        }
    }
}
