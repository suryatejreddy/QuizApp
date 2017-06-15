package com.example.android.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.quizapp.data.ProfileContract;

import java.util.HashMap;
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

public class PlaceholderFragment extends Fragment implements CustomAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 12;

    private CustomAdapter mAdapter;
    private RecyclerView mCategoryList;
    //map containing categories
    public static HashMap<String, MapObject[]> mapContainingCategories = new HashMap<String, MapObject[]>();
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

    public PlaceholderFragment() {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        mCategoryList.setLayoutManager(layoutManager);

        mCategoryList.setHasFixedSize(true);
        mAdapter = new CustomAdapter(NUM_LIST_ITEMS, this);
        mCategoryList.setAdapter(mAdapter);
        mapContainingCategories.put("GK", GKObject);
        mapContainingCategories.put("Entertainment", EntertainmentObject);
        mapContainingCategories.put("Science", ScienceObject);
        mapContainingCategories.put("Mythology", MythologyObject);
        mapContainingCategories.put("Sports", SportsObject);
        mapContainingCategories.put("Geography", GeographyObject);
        mapContainingCategories.put("History", HistoryObject);
        mapContainingCategories.put("Politics", PoliticsObject);
        mapContainingCategories.put("Art", ArtObject);
        mapContainingCategories.put("Celebrities", CelebritiesObject);
        mapContainingCategories.put("Animals", AnimalsObject);
        mapContainingCategories.put("Vehicles", VehiclesObject);

        for (Map.Entry m : mapContainingCategories.entrySet()) {
            //Log.d("myTag","Key: "+m.getKey()+" ,Value: "+m.getValue());
            MapObject[] x = (MapObject[]) m.getValue();
            int i = 0;
            for (i = 0; i < x.length; i++) {
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
        Log.d("thug","onCreateOptionsMenu called");
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage;
        switch (clickedItemIndex) {
            case 0:
                toastMessage = "GK clicked";
                break;
            case 1:
                toastMessage = "Entertainment clicked";
                break;

            case 2:
                toastMessage = "Science clicked";
                break;

            case 3:
                toastMessage = "Mythology clicked";
                break;

            case 4:
                toastMessage =  "Sports clicked";
                break;

            case 5:
                toastMessage = "Geography clicked";
                break;

            case 6:
                toastMessage =  "History clicked";
                break;

            case 7:
                toastMessage =  "Politics clicked";
                break;

            case 8:
                toastMessage =  "Art clicked";
                break;

            case 9:
                toastMessage ="Celebrities clicked";
                break;

            case 10:
                toastMessage =  "Animals clicked";
                break;

            case 11:
                toastMessage =  "Vehicles clicked";
                break;

            default:
                toastMessage = "";
        }
        if (!toastMessage.equals("")) {
            mToast = Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT);
            mToast.show();

        }
    }
}

