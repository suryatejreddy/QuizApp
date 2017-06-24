package com.example.android.quizapp;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

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
        String color;
        int backgroundColor;
        int differenceInShade = 50;
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
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("Entertainment"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.material_yellow);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("Science"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.material_purple);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("Mythology"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.material_orange);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            ////holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("Sports"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.material_green);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            ////holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("Geography"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.material_amber);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(backgroundColor & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(backgroundColor);
        }
        else if(arrayForRecyclerView[position].equals("History"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.red) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.red));
        }
        else if(arrayForRecyclerView[position].equals("Politics"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.material_yellow) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_yellow));
        }
        else if(arrayForRecyclerView[position].equals("Art"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.material_purple) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
        }
        else if(arrayForRecyclerView[position].equals("Celebrities"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.material_orange) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
        }
        else if(arrayForRecyclerView[position].equals("Animals"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.material_green) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
        }
        else if(arrayForRecyclerView[position].equals("Vehicles"))
        {
            backgroundColor = holder.itemView.getResources().getColor(R.color.red);
            //((TextView) holder.itemView.findViewById(R.id.item_category)).setTextColor(backgroundColor);
            color = Integer.toHexString(holder.itemView.getResources().getColor(R.color.material_amber) & 0x00ffffff);
            //holder.itemView.findViewById(R.id.category_card_view).setBackgroundColor(getDarkerShade(color, differenceInShade));
            //holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
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

    @NonNull
    private int getDarkerShade(String color, int differenceInShade) {
        int red = (Integer.parseInt(color.substring(0, 2), 16 ) - differenceInShade) % 256;
        int green = (Integer.parseInt(color.substring(2, 4), 16 ) - differenceInShade) % 256;
        int blue = (Integer.parseInt(color.substring(4, 6), 16 ) - differenceInShade) % 256;
        String newColor = ("#" + Integer.toHexString(0x100 |red).substring(1, 3) +
                Integer.toHexString(0x100 |green).substring(1, 3) +
                Integer.toHexString(0x100 |blue).substring(1, 3)).trim();
        return Color.parseColor(newColor);
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
