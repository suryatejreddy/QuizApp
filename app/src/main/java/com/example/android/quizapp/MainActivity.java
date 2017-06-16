package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity
{

    public static HashMap<String, MapObject[]> mapContainingCategories = new HashMap<String, MapObject[]>();
    public Context mContext;
    public static boolean mTwoPane;
    public static boolean calledForFirstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // Log.d("actionBar","onCreate() before setContentView");
        setContentView(R.layout.activity_main);
        Log.d("actionBar","onCreate() called");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        Log.d("actionBar","mTwoPane = "+String.valueOf(findViewById(R.id.sub_category_container)!=null));
        if(findViewById(R.id.sub_category_container)==null)
        {
            mTwoPane=false;
        }
        else
        {
            mTwoPane=true;
        }





        Log.d("actionBar","width: "+String.valueOf(width)+", height: "+String.valueOf(height));

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#535655")));

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_category);





        Log.d("actionBar","MainActivity, "+String.valueOf((TextView) findViewById(R.id.app_bar_main_view)==null));
        mContext=getApplicationContext();


        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            PlaceholderFragment placeholderFragment = new PlaceholderFragment();


            if (mTwoPane==false)
            {
                    fragmentTransaction.replace(R.id.container, placeholderFragment).commit();

            }

        }
//        if(PlaceholderFragment.PASS_TO_MAIN_FORSUB_CATEGORY==1) {
//            SubFragment subFragment = new SubFragment();
//
//            Bundle args = new Bundle();
//
//            args.putString("Key", "Entertainment");
//            subFragment.setArguments(args);
//            FragmentManager fragmentManager2 = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
//            fragmentTransaction2.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//            if (!MainActivity.mTwoPane)
//                fragmentTransaction2.replace(R.id.container, subFragment);
//            else
//                fragmentTransaction2.replace(R.id.sub_category_container, subFragment);
//            fragmentTransaction2.addToBackStack(null).commit();
//
//        }
//
//        else if(PlaceholderFragment.PASS_TO_MAIN_FORSUB_CATEGORY==2)
//        {
//            SubFragment subFragment1=new SubFragment();
//            Bundle args1=new Bundle();
//
//            args1.putString("Key","Science");
//            subFragment1.setArguments(args1);
//            FragmentManager fragmentManager1=getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction1=fragmentManager1.beginTransaction();
//            fragmentTransaction1.setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right);
//            if(!MainActivity.mTwoPane)
//                fragmentTransaction1.replace(R.id.container,subFragment1);
//            else
//                fragmentTransaction1.replace(R.id.sub_category_container,subFragment1);
//            fragmentTransaction1.addToBackStack(null).commit();
//
//        }
    }



    public void setActionBarTitle(String set)
    {
        View mView=this.getWindow().getDecorView();
        ((TextView) mView.findViewById(R.id.app_bar_main_view)).setText(set);
    }

}