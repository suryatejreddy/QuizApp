package com.example.android.quizapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

/**
 * Created by vishaal on 24/6/17.
 */

public class ProfileCustomAdapter extends RecyclerView.Adapter<ProfileCustomAdapter.ProfileNumberViewHolder>
{
    private static final String TAG=ProfileCustomAdapter.class.getSimpleName();

    private  ProfileCustomAdapter.ListItemClickListener mOnClickListener;


    private static int viewHolderCount;

    private int mNumberItems;


    @Override
    public ProfileNumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context=parent.getContext();
        int layoutForListItem= R.layout.score_list_item;
        LayoutInflater inflater=LayoutInflater.from(context);

        View view= inflater.inflate(layoutForListItem, parent, false);

        ProfileCustomAdapter.ProfileNumberViewHolder viewHolder = new ProfileNumberViewHolder(view);

        viewHolderCount++;

        Log.d("database", "onCreateViewHolder: number of views created: "+viewHolderCount);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ProfileNumberViewHolder holder, int position) {


        Log.d("database", "#"+position+"clicked");
        holder.bind(ProfileActivity.listGames.get(position).Date,ProfileActivity.listGames.get(position).Category , ProfileActivity.listGames.get(position).Score);

       // setAnimation(holder.itemView);

    }

    private void setAnimation(View view)
    {
        AlphaAnimation anim= new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1000);
        view.startAnimation(anim);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemListIndex);
    }

    public ProfileCustomAdapter(int numberOfItems, ProfileCustomAdapter.ListItemClickListener listener){
        mNumberItems=numberOfItems;
        mOnClickListener=listener;
        viewHolderCount=0;
    }

    class ProfileNumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView displayDate;
        TextView displayCategory;
        TextView displayScore;


        public ProfileNumberViewHolder(View itemView) {
            super(itemView);
            displayDate=(TextView) itemView.findViewById(R.id.text_view_date);
            displayCategory=(TextView) itemView.findViewById(R.id.text_view_category);
            displayScore=(TextView) itemView.findViewById(R.id.text_view_score);

            itemView.setOnClickListener(this);
        }


        void bind(String date, String category, String score)
        {
            displayDate.setText(date);
            displayCategory.setText(category);
            displayScore.setText(score);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
