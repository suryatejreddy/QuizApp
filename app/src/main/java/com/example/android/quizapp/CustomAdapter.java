package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import static com.example.android.quizapp.R.color.material_blue;

/**
 * Created by vishaal on 14/6/17.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.NumberViewHolder>
{

    private static final String TAG=CustomAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    String[] arrayForRecyclerView=new String[]{"GK","Entertainment","Science","Mythology","Sports","Geography","History","Politics","Art","Celebrities","Animals","Vehicles"};

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }

    public CustomAdapter(int numberOfItems, ListItemClickListener listener)
    {
        mNumberItems=numberOfItems;
        mOnClickListener=listener;
        viewHolderCount=0;
    }




    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layoutIdForListItem=R.layout.list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutIdForListItem,parent,false);
        NumberViewHolder viewHolder=new NumberViewHolder(view);
        switch (viewHolderCount%6)
        {
            case 0: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.material_amber));
                    break;
           case 1: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.material_green));
                    break;
            case 2: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.material_orange));
                    break;
            case 3: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.material_purple));
                    break;
            case 4: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.material_yellow));
                    break;
            case 5: viewHolder.itemView.setBackgroundColor(view.getResources().getColor(R.color.red));
                    break;
        }

        switch (viewHolderCount)
        {
            case 0: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_gk_icon);
                    break;
            case 1: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_entertainment_icon);
                    break;
            case 2: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_science_icon);
                    break;
            case 3: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_mythology_icon);
                    break;
            case 4: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_sports_icon);
                    break;
            case 5: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_geography_icon);
                    break;
            case 6: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_history_icon);
                    break;
            case 7: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_politics_icon);
                    break;
            case 8: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_art_icon);
                    break;
            case 9: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_celebrity_icon);
                    break;
            case 10: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_animal_icon);
                     break;
            case 11: ((ImageView) viewHolder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_vehicles_icon);
                        break;

        }
        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG,"#"+position);
        holder.bind(arrayForRecyclerView[position]);
        if(PlaceholderFragment.mapContainingCategories.get(arrayForRecyclerView[position]).length>1)
        {
            ImageButton im=(ImageButton) holder.itemView.findViewById(R.id.right_button);
            im.setVisibility(View.VISIBLE);

        }
    }



    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView displayString;

        public NumberViewHolder(View itemView)
        {
            super(itemView);
            displayString=(TextView) itemView.findViewById(R.id.item_category);
            itemView.setOnClickListener(this);
        }

        void bind(String textForView)
        {
            displayString.setText(textForView);
        }

        @Override
        public void onClick(View v)
        {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
