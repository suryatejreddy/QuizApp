package com.example.android.quizapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by vishaal on 12/7/17.
 */

public class DisplayResultCustomAdapter extends RecyclerView.Adapter<DisplayResultCustomAdapter.NumberViewHolder>
{

    private static final String TAG= DisplayResultCustomAdapter.class.getSimpleName();

    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private int mNumberItems;


    public DisplayResultCustomAdapter(int numberOfItems, ListItemClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
        mNumberItems=numberOfItems;
        viewHolderCount=0;
    }

    public interface ListItemClickListener
    {
        void onListItemClick(int clickedItemIndex);
    }


    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        int layoutIdForListItem=R.layout.answers_list_item;
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(layoutIdForListItem,parent,false);
        NumberViewHolder viewHolder= new NumberViewHolder(view);
        viewHolderCount++;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

        holder.bind(QuizActivity.QUIZ_QUESTIONS_LIST.get(position).question, QuizActivity.QUIZ_QUESTIONS_LIST.get(position).correctAnswer, QuizActivity.QUIZ_QUESTIONS_LIST.get(position).result);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView displayQuestion;
        TextView displayAnswer;
        ImageView displayResult;

        public NumberViewHolder(View itemView) {
            super(itemView);
            displayQuestion=(TextView) itemView.findViewById(R.id.text_view_question);
            displayAnswer=(TextView) itemView.findViewById(R.id.text_view_answer);
            displayResult=(ImageView) itemView.findViewById(R.id.image_view_result);
            itemView.setOnClickListener(this);
        }

        void bind(String question, String answer, boolean correct)
        {
            displayQuestion.setText(question);
            displayAnswer.setText(answer);
            if(correct)
                displayResult.setImageResource(R.drawable.ic_check_mark);
            else
                displayResult.setImageResource(R.drawable.ic_cross_mark);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition=getAdapterPosition();
        }
    }
}
