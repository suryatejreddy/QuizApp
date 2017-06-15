package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by vishaal on 15/6/17.
 */

public class SubFragment extends Fragment implements SubCustomAdapter.ListItemClickListener
{
    private SubCustomAdapter mAdapter;
    private RecyclerView mCategoryList;
    private Context mContext;
    private int NUM_LIST_ITEMS;
    private Toast mToast;

    public SubFragment()
    {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View rootView=inflater.inflate(R.layout.fragment_main,container,false);
        String displayString=getArguments().getString("Key");
        if(displayString.equals("Entertainment"))
        {
            NUM_LIST_ITEMS=10;
        }
        else if(displayString.equals("Science"))
        {
            NUM_LIST_ITEMS=4;
        }

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("actionBar","keyCode: "+keyCode);
                if(((MainActivity) mContext).findViewById(R.id.app_bar_main_view)!=null)
                {
                    ((TextView) ((MainActivity) mContext).findViewById(R.id.app_bar_main_view)).setText("CATEGORIES");
                }
                ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(false);
                ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                if(keyCode==KeyEvent.KEYCODE_BACK)
                {
                    Log.i("actionBar","onKeyListener is working");
                    getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                else {
                    return false;
                }
                }
        });



        mContext=getActivity();
        ((MainActivity) mContext).getSupportActionBar().getTitle();
        //TextView actionBarTextView=(TextView) view.findViewById(R.id.app_bar_main_view);
        Log.d("actionBar","Fragment, "+String.valueOf((TextView) ((MainActivity) mContext).findViewById(R.id.app_bar_main_view)==null));

        if(((MainActivity) mContext).findViewById(R.id.app_bar_main_view)!=null && NUM_LIST_ITEMS==10)
        {
            ((TextView) ((MainActivity) mContext).findViewById(R.id.app_bar_main_view)).setText("ENTERTAINMENT");
            ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) mContext).getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
            Log.d("HII","Entertainment");

        }

        else if(((MainActivity) mContext).findViewById(R.id.app_bar_main_view)!=null && NUM_LIST_ITEMS==4)
        {
            ((TextView) ((MainActivity) mContext).findViewById(R.id.app_bar_main_view)).setText("SCIENCE");
            ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(true);
            ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((MainActivity) mContext).getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
            Log.d("HII","Science");
        }

//        if((((MainActivity) mContext).getSupportActionBar()!=null) && NUM_LIST_ITEMS==10)
//        {
//            //String title=((MainActivity) mContext).getSupportActionBar().getTitle().toString();
//            ((MainActivity) mContext).getSupportActionBar().setTitle("ENTERTAINMENT");
//            ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(true);
//            ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            ((MainActivity) mContext).getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
//            Log.d("actionBar",String.valueOf(NUM_LIST_ITEMS)+" , ENTERTAINMENT");
//        }
//        else if((((MainActivity) mContext).getSupportActionBar()!=null) && NUM_LIST_ITEMS==4)
//        {
//            ((MainActivity) mContext).getSupportActionBar().setTitle("SCIENCE");
//            ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(true);
//            ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            ((MainActivity) mContext).getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
//            Log.d("actionBar",String.valueOf(NUM_LIST_ITEMS)+" , SCIENCE");
//        }
        else
        {
            Log.d("actionBar","Null object");
        }
        mCategoryList= (RecyclerView) rootView.findViewById(R.id.categories_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(rootView.getContext());
        mCategoryList.setLayoutManager(layoutManager);

        mCategoryList.setHasFixedSize(true);
        mAdapter=new SubCustomAdapter(NUM_LIST_ITEMS, this);
        mCategoryList.setAdapter(mAdapter);
        mAdapter.setRecyclerViewArray(NUM_LIST_ITEMS);
        return rootView;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_category,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.profile_activity_open)
        {
            Intent intent=new Intent(getContext(),ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if(id==android.R.id.home)
        {
            //Toast.makeText(getContext(),"Home clicked",Toast.LENGTH_SHORT).show();
            Log.d("actionBar","Home clicked");
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            if(((MainActivity) mContext).findViewById(R.id.app_bar_main_view)!=null)
            {
                ((TextView) ((MainActivity) mContext).findViewById(R.id.app_bar_main_view)).setText("CATEGORIES");
            }
            ((MainActivity) mContext).getSupportActionBar().setDisplayShowHomeEnabled(false);
            ((MainActivity) mContext).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            fragmentManager.popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onListItemClick(int clickedItemListIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage;

        if(NUM_LIST_ITEMS==10) {


            switch (clickedItemListIndex) {
                case 0:
                    toastMessage = "Books clicked";

                    break;
                case 1:
                    toastMessage = "Film clicked";
                    break;

                case 2:
                    toastMessage = "Music clicked";
                    break;

                case 3:
                    toastMessage = "Musicals & Theatres clicked";
                    break;

                case 4:
                    toastMessage = "Television clicked";
                    break;

                case 5:
                    toastMessage = "Video Games clicked";
                    break;

                case 6:
                    toastMessage = "Board Games clicked";
                    break;

                case 7:
                    toastMessage = "Comics clicked";
                    break;

                case 8:
                    toastMessage = "Cartoon & Animations clicked";
                    break;

                case 9:
                    toastMessage = "Japanese Anime & Manga clicked";
                    break;

                default:
                    toastMessage = "";
            }
            if (!toastMessage.equals("")) {
                mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT);
                mToast.show();
            }
        }

        else if(NUM_LIST_ITEMS==4)
        {

            switch (clickedItemListIndex)
            {
                case 0:toastMessage="Nature Clicked";
                    break;

                case 1:toastMessage="Computers clicked";
                    break;

                case 2:toastMessage="Mathematics clicked";
                    break;

                case 3:toastMessage="Gadgets clicked";
                    break;

                default:toastMessage="";
            }

            if(!toastMessage.equals(""))
            {
                mToast=Toast.makeText(getContext(),toastMessage,Toast.LENGTH_SHORT);
                mToast.show();
            }
        }
    }
}
