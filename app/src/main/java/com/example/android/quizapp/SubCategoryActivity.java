package com.example.android.quizapp;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SubCategoryActivity extends AppCompatActivity {

    public Context mContext;
    public static boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        mTwoPane = findViewById(R.id.sub_category_container) != null;

    }
}
