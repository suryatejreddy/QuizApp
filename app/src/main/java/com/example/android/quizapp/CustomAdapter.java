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
        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG,"#"+position);
        holder.bind(arrayForRecyclerView[position]);
        TextView im=(TextView) holder.itemView.findViewById(R.id.right_button);
        if(arrayForRecyclerView[position].equals("Entertainment") || arrayForRecyclerView[position].equals("Science"))
        {
            im.setText("›");
            //im.setTextColor(Color.parseColor("#000"));
            im.setVisibility(View.VISIBLE);
        }

        else
        {
            im.setVisibility(View.GONE);
        }

        if(arrayForRecyclerView[position].equals("GK"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.red));
        }
        else if(arrayForRecyclerView[position].equals("Entertainment"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_yellow));
        }
        else if(arrayForRecyclerView[position].equals("Science"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
        }
        else if(arrayForRecyclerView[position].equals("Mythology"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
        }
        else if(arrayForRecyclerView[position].equals("Sports"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
        }
        else if(arrayForRecyclerView[position].equals("Geography"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
        }
        else if(arrayForRecyclerView[position].equals("History"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.red));
        }
        else if(arrayForRecyclerView[position].equals("Politics"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_yellow));
        }
        else if(arrayForRecyclerView[position].equals("Art"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
        }
        else if(arrayForRecyclerView[position].equals("Celebrities"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
        }
        else if(arrayForRecyclerView[position].equals("Animals"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
        }
        else if(arrayForRecyclerView[position].equals("Vehicles"))
        {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
        }


        if(arrayForRecyclerView[position].equals("GK"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_gk_icon);
        }
        else if(arrayForRecyclerView[position].equals("Entertainment"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_entertainment_icon);
        }
        else if(arrayForRecyclerView[position].equals("Science"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_science_icon);
        }
        else if(arrayForRecyclerView[position].equals("Mythology"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_mythology_icon);
        }
        else if(arrayForRecyclerView[position].equals("Sports"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_sports_icon);
        }
        else if(arrayForRecyclerView[position].equals("Geography"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_geography_icon);
        }
        else if(arrayForRecyclerView[position].equals("History"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_history_icon);
        }
        else if(arrayForRecyclerView[position].equals("Politics"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_politics_icon);
        }
        else if(arrayForRecyclerView[position].equals("Art"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_art_icon);
        }
        else if(arrayForRecyclerView[position].equals("Celebrities"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_celebrity_icon);
        }
        else if(arrayForRecyclerView[position].equals("Animals"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_animal_icon);
        }
        else if(arrayForRecyclerView[position].equals("Vehicles"))
        {
            ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_vehicles_icon);
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
