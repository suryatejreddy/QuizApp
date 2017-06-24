package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by vishaal on 15/6/17.
 */

public class SubCategoryFragment extends Fragment implements SubCustomAdapter.ListItemClickListener
{
    private SubCustomAdapter mAdapter;
    private RecyclerView mCategoryList;
    private Context mContext;
    private int NUM_LIST_ITEMS;
    private Toast mToast;

    public SubCategoryFragment()
    {
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View rootView=inflater.inflate(R.layout.fragment_main,container,false);

        if(!MainActivity.mTwoPane) {
            String displayString = getActivity().getIntent().getStringExtra("Key");
            if (displayString != null) {
                if (displayString.equals("Entertainment")) {
                    NUM_LIST_ITEMS = 10;
                } else if (displayString.equals("Science")) {
                    NUM_LIST_ITEMS = 4;
                }
            }
        }
        else {

            if (getArguments() != null) {
                String displayString = getArguments().getString("Key");
                if (displayString != null) {

                    if (displayString.equals("Entertainment")) {
                        NUM_LIST_ITEMS = 10;
                    } else if (displayString.equals("Science")) {
                        NUM_LIST_ITEMS = 4;
                    }
                }
            }
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

        if(id==R.id.display_game_scores)
        {
            Intent intent=new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        if(id==R.id.profile_activity_open)
        {
            Intent intent=new Intent(getContext(),AccountActivity.class);
            startActivity(intent);
            return true;
        }

        if(id==R.id.logout_btn)
        {
            Intent intent=new Intent(getContext(), LoginActivity.class);
            SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getContext());
            preferences.edit().putBoolean("LOGIN_MODE", false).apply();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

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
                    toastMessage = "Books";

                    break;
                case 1:
                    toastMessage = "Film";
                    break;

                case 2:
                    toastMessage = "Music";
                    break;

                case 3:
                    toastMessage = "Musicals & Theatres";
                    break;

                case 4:
                    toastMessage = "Television";
                    break;

                case 5:
                    toastMessage = "Video Games";
                    break;

                case 6:
                    toastMessage = "Board Games";
                    break;

                case 7:
                    toastMessage = "Comics";
                    break;

                case 8:
                    toastMessage = "Cartoon & Animations";
                    break;

                case 9:
                    toastMessage = "Anime & Manga";
                    break;

                default:
                    toastMessage = "";
            }
            if (!toastMessage.equals("")) {
                //mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT);
                //mToast.show();
                Intent intent=new Intent(getContext(),QuizActivity.class);
                intent.putExtra("ActionBar",toastMessage);
                startActivity(intent);
                getActivity().finish();

            }
        }

        else if(NUM_LIST_ITEMS==4)
        {

            switch (clickedItemListIndex)
            {
                case 0:toastMessage="Nature";
                    break;

                case 1:toastMessage="Computers";
                    break;

                case 2:toastMessage="Mathematics";
                    break;

                case 3:toastMessage="Gadgets";
                    break;

                default:toastMessage="";
            }

            if(!toastMessage.equals(""))
            {
                //mToast=Toast.makeText(getContext(),toastMessage,Toast.LENGTH_SHORT);
                //mToast.show();
                Intent intent=new Intent(getContext(),QuizActivity.class);
                intent.putExtra("ActionBar",toastMessage);
                startActivity(intent);
                getActivity().finish();
            }
        }
    }
}
