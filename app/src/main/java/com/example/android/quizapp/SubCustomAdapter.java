package com.example.android.quizapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vishaal on 15/6/17.
 */

public class SubCustomAdapter extends RecyclerView.Adapter<SubCustomAdapter.SubNumberViewHolder>
{
    private static final String TAG=SubCustomAdapter.class.getSimpleName();

    final private SubCustomAdapter.ListItemClickListener mOnClickListener;

    private static int viewHolderCount;

    private int mNumberItems;

    String[] arrayForRecyclerView;

    void setRecyclerViewArray(int numberOfItems)
    {
        if(numberOfItems==10)
        {
            arrayForRecyclerView=new String[]{"Books","Film","Music","Musicals & Theatres","Television","Video Games","Board Games","Comics","Cartoon & Animations","Japanese Anime & Manga"};

        }
        else if(numberOfItems==4)
        {
            arrayForRecyclerView=new String[]{"Nature","Computers","Mathematics","Gadgets"};
        }
    }

    @Override
    public SubNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context=parent.getContext();
        int layoutIdForListItem=R.layout.list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutIdForListItem,parent,false);
        SubCustomAdapter.SubNumberViewHolder viewHolder= new SubCustomAdapter.SubNumberViewHolder(view);
        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubNumberViewHolder holder, int position)
    {
        Log.d(TAG,"#"+position+" clicked");
        holder.bind(arrayForRecyclerView[position]);


        if(arrayForRecyclerView[0].equals("Books"))
        {

            //((TextView) holder.itemView.findViewById(R.id.app_bar_main_view)).setText("Entertainment");

            if(arrayForRecyclerView[position].equals("Books"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
            }
            else if(arrayForRecyclerView[position].equals("Film"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
            }
            else if(arrayForRecyclerView[position].equals("Music"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
            }
            else if(arrayForRecyclerView[position].equals("Musicals & Theatres"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
            }
            else if(arrayForRecyclerView[position].equals("Television"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.red));
            }
            else if(arrayForRecyclerView[position].equals("Video Games"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
            }
            else if(arrayForRecyclerView[position].equals("Board Games"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
            }
            else if(arrayForRecyclerView[position].equals("Comics"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
            }
            else if(arrayForRecyclerView[position].equals("Cartoon & Animations"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
            }
            else if(arrayForRecyclerView[position].equals("Japanese Anime & Manga"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.red));
            }
        }

        else if(arrayForRecyclerView[0].equals("Nature"))
        {

            //((TextView) holder.itemView.findViewById(R.id.app_bar_main_view)).setText("Science");

            if(arrayForRecyclerView[position].equals("Nature"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_amber));
            }
            else if(arrayForRecyclerView[position].equals("Computers"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_green));
            }
            else if(arrayForRecyclerView[position].equals("Mathematics"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_orange));
            }
            else if(arrayForRecyclerView[position].equals("Gadgets"))
            {
                holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(R.color.material_purple));
            }
        }


        if(arrayForRecyclerView[0].equals("Books"))
        {

            if(arrayForRecyclerView[position].equals("Books"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_books_icon);
            }
            else if(arrayForRecyclerView[position].equals("Film"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_film_icon);
            }
            else if(arrayForRecyclerView[position].equals("Music"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_music_icon);
            }
            else if(arrayForRecyclerView[position].equals("Musicals & Theatres"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_theatre_icon);
            }
            else if(arrayForRecyclerView[position].equals("Television"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_television_icon);
            }
            else if(arrayForRecyclerView[position].equals("Video Games"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_video_games_icon);
            }
            else if(arrayForRecyclerView[position].equals("Board Games"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_board_games_icon);
            }
            else if(arrayForRecyclerView[position].equals("Comics"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_comic_icon);
            }
            else if(arrayForRecyclerView[position].equals("Cartoon & Animations"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_cartoon_icon);
            }
            else if(arrayForRecyclerView[position].equals("Japanese Anime & Manga"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_manga_icon);
            }
        }

        else if(arrayForRecyclerView[0].equals("Nature"))
        {
            if(arrayForRecyclerView[position].equals("Nature"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_nature_icon);
            }
            else if(arrayForRecyclerView[position].equals("Computers"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_computer_icon);
            }
            else if(arrayForRecyclerView[position].equals("Mathematics"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_maths_icon);
            }
            else if(arrayForRecyclerView[position].equals("Gadgets"))
            {
                ((ImageView) holder.itemView.findViewById(R.id.icon_category)).setImageResource(R.drawable.ic_gadgets_icon);
            }
        }

    }



    @Override
    public int getItemCount()
    {
        return mNumberItems;
    }

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemListIndex);
    }

    public SubCustomAdapter(int numberofItems, SubCustomAdapter.ListItemClickListener listener) {
        mNumberItems=numberofItems;
        mOnClickListener=listener;
        viewHolderCount=0;
    }

    class SubNumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView displayString;

        public SubNumberViewHolder(View itemView)
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

