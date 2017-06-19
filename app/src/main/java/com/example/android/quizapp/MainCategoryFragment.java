package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Map;

/**
 * Created by vishaal on 14/6/17.
 */


class MapObject
{
    int apiCategory;
    String subCategory;

    public MapObject(int a, String s)
    {
        this.apiCategory=a;
        this.subCategory=s;
    }
}

public class MainCategoryFragment extends Fragment implements CustomAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 12;
    public static int PASS_TO_MAIN_FORSUB_CATEGORY;
    private CustomAdapter mAdapter;
    private RecyclerView mCategoryList;
    private RecyclerView.LayoutManager layoutManager;
    private static int REQUEST_EXIT=14;
    //map containing categories

    MapObject[] GKObject = new MapObject[]{new MapObject(9, "")};
    MapObject[] EntertainmentObject = new MapObject[]{new MapObject(10, "Books"), new MapObject(11, "Film"), new MapObject(12, "Music"), new MapObject(13, "Musicals & Theatres"), new MapObject(14, "Television"), new MapObject(15, "Video Games"), new MapObject(16, "Board Games"), new MapObject(29, "Comics"), new MapObject(32, "Cartoon & Animations"), new MapObject(31, "Japanese Anime & Manga")};
    MapObject[] ScienceObject = new MapObject[]{new MapObject(17, "Nature"), new MapObject(18, "Computers"), new MapObject(19, "Mathematics"), new MapObject(30, "Gadgets")};
    MapObject[] MythologyObject = new MapObject[]{new MapObject(20, "")};
    MapObject[] SportsObject = new MapObject[]{new MapObject(21, "")};
    MapObject[] GeographyObject = new MapObject[]{new MapObject(22, "")};
    MapObject[] HistoryObject = new MapObject[]{new MapObject(23, "")};
    MapObject[] PoliticsObject = new MapObject[]{new MapObject(24, "")};
    MapObject[] ArtObject = new MapObject[]{new MapObject(25, "")};
    MapObject[] CelebritiesObject = new MapObject[]{new MapObject(26, "")};
    MapObject[] AnimalsObject = new MapObject[]{new MapObject(27, "")};
    MapObject[] VehiclesObject = new MapObject[]{new MapObject(28, "")};

    private Toast mToast;

    public MainCategoryFragment() {
        setHasOptionsMenu(true);
        //setRetainInstance(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mCategoryList = (RecyclerView) rootView.findViewById(R.id.categories_list);
        layoutManager = new LinearLayoutManager(rootView.getContext());
        mCategoryList.setLayoutManager(layoutManager);

        mCategoryList.setHasFixedSize(true);
        mAdapter = new CustomAdapter(NUM_LIST_ITEMS, this);
        mCategoryList.setAdapter(mAdapter);
        MainActivity.mapContainingCategories.put("GK", GKObject);
        MainActivity.mapContainingCategories.put("Entertainment", EntertainmentObject);
        MainActivity.mapContainingCategories.put("Science", ScienceObject);
        MainActivity.mapContainingCategories.put("Mythology", MythologyObject);
        MainActivity.mapContainingCategories.put("Sports", SportsObject);
        MainActivity.mapContainingCategories.put("Geography", GeographyObject);
        MainActivity.mapContainingCategories.put("History", HistoryObject);
        MainActivity.mapContainingCategories.put("Politics", PoliticsObject);
        MainActivity.mapContainingCategories.put("Art", ArtObject);
        MainActivity.mapContainingCategories.put("Celebrities", CelebritiesObject);
        MainActivity.mapContainingCategories.put("Animals", AnimalsObject);
        MainActivity.mapContainingCategories.put("Vehicles", VehiclesObject);

        for (Map.Entry m : MainActivity.mapContainingCategories.entrySet()) {
            //Log.d("myTag","Key: "+m.getKey()+" ,Value: "+m.getValue());
            MapObject[] x = (MapObject[]) m.getValue();
            for (int i = 0; i < x.length; i++) {
                Log.d("myTag", "Key: " + m.getKey() + " ,Value: { " + x[i].apiCategory + " , " + x[i].subCategory + " }");
            }
        }
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        Log.d("thug", "onCreateOptionsMenu called");
        inflater.inflate(R.menu.menu_category, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.profile_activity_open) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        View v1=layoutManager.findViewByPosition(1);
        View v2=layoutManager.findViewByPosition(2);
        String toastMessage;
        switch (clickedItemIndex) {
            case 0:
                toastMessage = "GK";
                break;
            case 1:
                PASS_TO_MAIN_FORSUB_CATEGORY = 1;



                Log.d("actionBar", "Main activity mTwoPane: " + MainActivity.mTwoPane);

                if (!MainActivity.mTwoPane) {
                    Intent subCategoryIntent = new Intent(getActivity(), SubCategoryActivity.class);
                    subCategoryIntent.putExtra("Key", "Entertainment");
                    startActivity(subCategoryIntent);
                } else if(!((TextView) v1.findViewById(R.id.right_button)).getText().toString().equals("●")){


                    ((TextView)v1.findViewById(R.id.right_button)).setText("●");
                    ((TextView)v2.findViewById(R.id.right_button)).setText("›");
                    SubCategoryFragment subCategoryFragment1 = new SubCategoryFragment();
                    Bundle args1 = new Bundle();

                    args1.putString("Key", "Entertainment");
                    subCategoryFragment1.setArguments(args1);
                    FragmentManager fragmentManager1 = getFragmentManager();
                    FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                    fragmentTransaction1.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction1.replace(R.id.container, subCategoryFragment1);
                    fragmentTransaction1.commit();
                }
                toastMessage = "";
                break;

            case 2:
                PASS_TO_MAIN_FORSUB_CATEGORY = 2;

                Log.d("actionBar", "Main activity mTwoPane: " + MainActivity.mTwoPane);



                if (!MainActivity.mTwoPane) {
                    Intent subCategoryIntent = new Intent(getActivity(), SubCategoryActivity.class);
                    subCategoryIntent.putExtra("Key", "Science");
                    startActivity(subCategoryIntent);
                } else if(!((TextView) v2.findViewById(R.id.right_button)).getText().toString().equals("●")){

                    ((TextView)v1.findViewById(R.id.right_button)).setText("›");
                    ((TextView)v2.findViewById(R.id.right_button)).setText("●");
                    SubCategoryFragment subCategoryFragment2 = new SubCategoryFragment();
                    Bundle args2 = new Bundle();

                    args2.putString("Key", "Science");
                    subCategoryFragment2.setArguments(args2);
                    FragmentManager fragmentManager2 = getFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    fragmentTransaction2.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_left, R.anim.exit_to_right);
                    fragmentTransaction2.replace(R.id.container, subCategoryFragment2);
                    fragmentTransaction2.commit();
                }

                toastMessage = "";
                break;

            case 3:
                toastMessage = "Mythology";
                break;

            case 4:
                toastMessage = "Sports";
                break;

            case 5:
                toastMessage = "Geography";
                break;

            case 6:
                toastMessage = "History";
                break;

            case 7:
                toastMessage = "Politics";
                break;

            case 8:
                toastMessage = "Art";
                break;

            case 9:
                toastMessage = "Celebrities";
                break;

            case 10:
                toastMessage = "Animals";
                break;

            case 11:
                toastMessage = "Vehicles";
                break;

            default:
                toastMessage = "";
        }
        if (!toastMessage.equals("")) {
            //mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT);
            //mToast.show();
            Intent intent = new Intent(getContext(), QuizActivity.class);
            intent.putExtra("ActionBar", toastMessage);
            startActivity(intent);
        }
    }

}

