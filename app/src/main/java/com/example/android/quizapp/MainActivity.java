package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#505050")));
        //getSupportActionBar().setTitle("THug");
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_category);
        //setActionBarTitle("Thug");

        Log.d("HII","MainActivity, "+String.valueOf((TextView) findViewById(R.id.app_bar_main_view)==null));
        mContext=getApplicationContext();
        if (savedInstanceState == null)
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,new PlaceholderFragment()).commit();
            //  getSupportFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).addToBackStack(null).commit();
        }

    }

    public void setActionBarTitle(String set)
    {
        View mView=this.getWindow().getDecorView();
        ((TextView) mView.findViewById(R.id.app_bar_main_view)).setText(set);
    }

}