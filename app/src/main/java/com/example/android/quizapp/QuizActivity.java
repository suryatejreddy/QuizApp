package com.example.android.quizapp;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.quizapp.NetworkUtils.CustomJSONObjectRequest;
import com.example.android.quizapp.NetworkUtils.CustomVolleyRequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {


    private static String BASE_API_URL = "https://opentdb.com/api.php?amount=10&type=multiple&category=";
    private static String CATEGORY_PASSED;
    private static String API_URL;
    public static final String REQUEST_TAG = "QuizActivity";
    private static int CATEGORY;
    private RequestQueue mQueue;

    private static int NUMBER_CORRECT=0;

    private static TextView QuestionTextView;
    private static Button AnswerButton1;
    private static Button AnswerButton2;
    private static Button AnswerButton3;
    private static Button AnswerButton4;
    private static Button NextButton;
    private static ProgressBar progressBar;
    private static ProgressBar loadingIndicator;
    private static LinearLayout linearLayout;
    private static int PROGRESS=0;
    private static int NUMBER_QUESTIONS_COMPLETED=0;
    private static int CURRENT_CORRECT_POSITION;
    private static String CURRENT_QUESTION;
    private static String CURRENT_ANSWER_1;
    private static String CURRENT_ANSWER_2;
    private static String CURRENT_ANSWER_3;
    private static String CURRENT_ANSWER_4;

    private static int QUESTION_KEY=1;
    private static int ANSWER_1_KEY=2;
    private static int ANSWER_2_KEY=3;
    private static int ANSWER_3_KEY=4;
    private static int ANSWER_4_KEY=5;


    private static String[] CORRECT_ANSWERS;
    private static String[] QUESTIONS;
    private static List<String[]> WRONG_ANSWERS_LIST;


    private static boolean buttonNextClicked=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionTextView = (TextView) findViewById(R.id.question_text_view);
        NextButton = (Button) findViewById(R.id.next_button);
        AnswerButton1 = (Button) findViewById(R.id.answer_button_1);
        AnswerButton2 = (Button) findViewById(R.id.answer_button_2);
        AnswerButton3 = (Button) findViewById(R.id.answer_button_3);
        AnswerButton4 = (Button) findViewById(R.id.answer_button_4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        loadingIndicator = (ProgressBar) findViewById(R.id.circle_load);
        linearLayout = (LinearLayout) findViewById(R.id.layout_quiz);



        if (savedInstanceState == null) {

            Log.d("TAG","Goes into savedInstanceState=null");



            progressBar.setProgress(PROGRESS);


            AnswerButton1.setVisibility(View.GONE);
            AnswerButton2.setVisibility(View.GONE);
            AnswerButton3.setVisibility(View.GONE);
            AnswerButton4.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            NextButton.setVisibility(View.GONE);
            if (linearLayout != null)
                linearLayout.setBackgroundColor(getResources().getColor(R.color.activity_grey_background));
            QuestionTextView.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.VISIBLE);


            Intent intent = getIntent();
            if (intent.hasExtra("ActionBar")) {
                getSupportActionBar().setTitle(intent.getStringExtra("ActionBar"));
                CATEGORY_PASSED = intent.getStringExtra("ActionBar");
            }

            for (Map.Entry m : MainActivity.mapContainingCategories.entrySet()) {

                MapObject[] x = (MapObject[]) m.getValue();
                for (int i = 0; i < x.length; i++) {
                    Log.d("myTag", "Key: " + m.getKey() + " ,Value: { " + x[i].apiCategory + " , " + x[i].subCategory + " }");
                    if (m.getKey().toString().equals(CATEGORY_PASSED)) {
                        CATEGORY = x[i].apiCategory;
                    } else if (x[i].subCategory.equals(CATEGORY_PASSED)) {
                        CATEGORY = x[i].apiCategory;
                    }
                }
            }

            Log.d("volley", "CATEGORY_PASSED: " + CATEGORY_PASSED + ", CATEGORY: " + CATEGORY);
            API_URL = BASE_API_URL + String.valueOf(CATEGORY);

            Log.d("volley", API_URL);


            RequestQueue queue = Volley.newRequestQueue(this);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("volley", response.toString());


                    try {
                        JSONArray jsonArray = ((JSONObject) response).getJSONArray("results");
                        int i = 0;
                        CORRECT_ANSWERS = new String[jsonArray.length()];
                        QUESTIONS = new String[jsonArray.length()];
                        WRONG_ANSWERS_LIST = new ArrayList<>(jsonArray.length());
                        while (i < jsonArray.length()) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            QUESTIONS[i] = jsonObject.getString("question");
                            CORRECT_ANSWERS[i] = jsonObject.getString("correct_answer");

                            String[] WRONG_ANSWERS = new String[3];
                            JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");

                            for (int j = 0; j < jsonArray1.length(); j++) {
                                WRONG_ANSWERS[j] = jsonArray1.getString(j);
                            }

                            WRONG_ANSWERS_LIST.add(i, WRONG_ANSWERS);

                            i++;
                        }
                        AnswerButton1.setVisibility(View.VISIBLE);
                        AnswerButton2.setVisibility(View.VISIBLE);
                        AnswerButton3.setVisibility(View.VISIBLE);
                        AnswerButton4.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        NextButton.setVisibility(View.VISIBLE);
                        QuestionTextView.setVisibility(View.VISIBLE);
                        loadingIndicator.setVisibility(View.GONE);
                        getQuestionsButtons(QUESTIONS, CORRECT_ANSWERS, WRONG_ANSWERS_LIST);

                    } catch (JSONException e) {

                        e.printStackTrace();
                    }


                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("volley", error.toString());

                            QuestionTextView.setText(error.toString());
                        }
                    }
            );
            queue.add(getRequest);


        }


        else
        {
            loadingIndicator.setVisibility(View.GONE);
            Log.d("TAG","Goes into else");
            Log.d("TAG",savedInstanceState.getString(String.valueOf(QUESTION_KEY)));
            QuestionTextView.setText(Html.fromHtml("Q. "+savedInstanceState.getString(String.valueOf(QUESTION_KEY))));
            AnswerButton1.setText(Html.fromHtml(savedInstanceState.getString(String.valueOf(ANSWER_1_KEY))));
            AnswerButton2.setText(Html.fromHtml(savedInstanceState.getString(String.valueOf(ANSWER_2_KEY))));
            AnswerButton3.setText(Html.fromHtml(savedInstanceState.getString(String.valueOf(ANSWER_3_KEY))));
            AnswerButton4.setText(Html.fromHtml(savedInstanceState.getString(String.valueOf(ANSWER_4_KEY))));
        }
    }




    public void onNextButtonClick(View view)
    {
        buttonNextClicked=true;


        boolean colourAnswerButton1=AnswerButton1.getBackground() instanceof ColorDrawable;
        boolean colourAnswerButton2=AnswerButton2.getBackground() instanceof ColorDrawable;
        boolean colourAnswerButton3=AnswerButton3.getBackground() instanceof ColorDrawable;
        boolean colourAnswerButton4=AnswerButton4.getBackground() instanceof ColorDrawable;

        if(colourAnswerButton1)
        {
            if(CURRENT_CORRECT_POSITION==1)
            {
                NUMBER_CORRECT++;
            }
            PROGRESS+=10;
            NUMBER_QUESTIONS_COMPLETED++;
            progressBar.setProgress(PROGRESS);
            AnswerButton1.setText("");
            AnswerButton2.setText("");
            AnswerButton3.setText("");
            AnswerButton4.setText("");
            int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
            int backgroundResource=typedArray.getResourceId(0,0);

            AnswerButton1.setBackgroundResource(backgroundResource);
            AnswerButton2.setBackgroundResource(backgroundResource);
            AnswerButton3.setBackgroundResource(backgroundResource);
            AnswerButton4.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            if(NUMBER_QUESTIONS_COMPLETED==10)
            {
                Intent intentToStartResult=new Intent(this,DisplayResultActivity.class);
                intentToStartResult.putExtra("Correct",NUMBER_CORRECT);
                intentToStartResult.putExtra("Incorrect",10-NUMBER_CORRECT);
                NUMBER_QUESTIONS_COMPLETED=0;
                progressBar.setProgress(0);
                PROGRESS=0;
                NUMBER_CORRECT=0;
                startActivity(intentToStartResult);
                finish();
            }

            if(NUMBER_QUESTIONS_COMPLETED<10)
                getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);
        }

        else if(colourAnswerButton2)
        {
            if(CURRENT_CORRECT_POSITION==2)
            {
                NUMBER_CORRECT++;
            }
            PROGRESS+=10;
            NUMBER_QUESTIONS_COMPLETED++;
            progressBar.setProgress(PROGRESS);
            AnswerButton1.setText("");
            AnswerButton2.setText("");
            AnswerButton3.setText("");
            AnswerButton4.setText("");
            int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
            int backgroundResource=typedArray.getResourceId(0,0);

            AnswerButton1.setBackgroundResource(backgroundResource);
            AnswerButton2.setBackgroundResource(backgroundResource);
            AnswerButton3.setBackgroundResource(backgroundResource);
            AnswerButton4.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            if(NUMBER_QUESTIONS_COMPLETED==10)
            {
                Intent intentToStartResult=new Intent(this,DisplayResultActivity.class);
                intentToStartResult.putExtra("Correct",NUMBER_CORRECT);
                intentToStartResult.putExtra("Incorrect",10-NUMBER_CORRECT);
                NUMBER_QUESTIONS_COMPLETED=0;
                progressBar.setProgress(0);
                PROGRESS=0;
                NUMBER_CORRECT=0;
                startActivity(intentToStartResult);
                finish();
            }

            if(NUMBER_QUESTIONS_COMPLETED<10)
                getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);
        }

        else if(colourAnswerButton3)
        {
            if(CURRENT_CORRECT_POSITION==3)
            {
                NUMBER_CORRECT++;
            }
            PROGRESS+=10;
            NUMBER_QUESTIONS_COMPLETED++;
            progressBar.setProgress(PROGRESS);
            AnswerButton1.setText("");
            AnswerButton2.setText("");
            AnswerButton3.setText("");
            AnswerButton4.setText("");
            int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
            int backgroundResource=typedArray.getResourceId(0,0);

            AnswerButton1.setBackgroundResource(backgroundResource);
            AnswerButton2.setBackgroundResource(backgroundResource);
            AnswerButton3.setBackgroundResource(backgroundResource);
            AnswerButton4.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            if(NUMBER_QUESTIONS_COMPLETED==10)
            {
                Intent intentToStartResult=new Intent(this,DisplayResultActivity.class);
                intentToStartResult.putExtra("Correct",NUMBER_CORRECT);
                intentToStartResult.putExtra("Incorrect",10-NUMBER_CORRECT);
                NUMBER_QUESTIONS_COMPLETED=0;
                progressBar.setProgress(0);
                PROGRESS=0;
                NUMBER_CORRECT=0;
                startActivity(intentToStartResult);
                finish();
            }

            if(NUMBER_QUESTIONS_COMPLETED<10)
                getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);
        }

        else if(colourAnswerButton4)
        {
            if(CURRENT_CORRECT_POSITION==4)
            {
                NUMBER_CORRECT++;
            }
            PROGRESS+=10;
            NUMBER_QUESTIONS_COMPLETED++;
            progressBar.setProgress(PROGRESS);
            AnswerButton1.setText("");
            AnswerButton2.setText("");
            AnswerButton3.setText("");
            AnswerButton4.setText("");
            int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
            TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
            int backgroundResource=typedArray.getResourceId(0,0);

            AnswerButton1.setBackgroundResource(backgroundResource);
            AnswerButton2.setBackgroundResource(backgroundResource);
            AnswerButton3.setBackgroundResource(backgroundResource);
            AnswerButton4.setBackgroundResource(backgroundResource);
            typedArray.recycle();

            if(NUMBER_QUESTIONS_COMPLETED==10)
            {
                Intent intentToStartResult=new Intent(this,DisplayResultActivity.class);
                intentToStartResult.putExtra("Correct",NUMBER_CORRECT);
                intentToStartResult.putExtra("Incorrect",10-NUMBER_CORRECT);
                NUMBER_QUESTIONS_COMPLETED=0;
                progressBar.setProgress(0);
                PROGRESS=0;
                NUMBER_CORRECT=0;
                startActivity(intentToStartResult);
                finish();
            }

            if(NUMBER_QUESTIONS_COMPLETED<10)
                getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);
        }

        else
        {
            Toast.makeText(getApplicationContext(),"Please select an option.",Toast.LENGTH_SHORT).show();
        }

    }

    public void onAnswerButton1Click(View view)
    {
//           if(CURRENT_CORRECT_POSITION==1)
//           {
//               NUMBER_CORRECT++;
//           }
           AnswerButton1.setBackgroundColor(getResources().getColor(R.color.maroon));
            int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
        int backgroundResource=typedArray.getResourceId(0,0);
        AnswerButton2.setBackgroundResource(backgroundResource);
        AnswerButton3.setBackgroundResource(backgroundResource);
        AnswerButton4.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }

    public void onAnswerButton2Click(View view)
    {

//        if(CURRENT_CORRECT_POSITION==2)
//        {
//            NUMBER_CORRECT++;
//        }
        AnswerButton2.setBackgroundColor(getResources().getColor(R.color.maroon));
        int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
        int backgroundResource=typedArray.getResourceId(0,0);
        AnswerButton1.setBackgroundResource(backgroundResource);
        AnswerButton3.setBackgroundResource(backgroundResource);
        AnswerButton4.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }

    public void onAnswerButton3Click(View view)
    {

//        if(CURRENT_CORRECT_POSITION==3)
//        {
//            NUMBER_CORRECT++;
//        }
        AnswerButton3.setBackgroundColor(getResources().getColor(R.color.maroon));
        int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
        int backgroundResource=typedArray.getResourceId(0,0);
        AnswerButton1.setBackgroundResource(backgroundResource);
        AnswerButton2.setBackgroundResource(backgroundResource);
        AnswerButton4.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }

    public void onAnswerButton4Click(View view)
    {

//        if(CURRENT_CORRECT_POSITION==4)
//        {
//            NUMBER_CORRECT++;
//        }
        AnswerButton4.setBackgroundColor(getResources().getColor(R.color.maroon));
        int[] attrs=new int[]{R.attr.selectableItemBackgroundBorderless};
        TypedArray typedArray=getApplicationContext().obtainStyledAttributes(attrs);
        int backgroundResource=typedArray.getResourceId(0,0);
        AnswerButton1.setBackgroundResource(backgroundResource);
        AnswerButton2.setBackgroundResource(backgroundResource);
        AnswerButton3.setBackgroundResource(backgroundResource);
        typedArray.recycle();
    }


    private void getQuestionsButtons(String[] questions, String[] correct_answers, List<String[]> wrong_answers_list)
    {
        Log.d("volley","getQuestions called");
        String correctAnswer=correct_answers[NUMBER_QUESTIONS_COMPLETED];
        String question=questions[NUMBER_QUESTIONS_COMPLETED];
        String[] wrongAnswers=wrong_answers_list.get(NUMBER_QUESTIONS_COMPLETED);
        Log.d("volley","Number completed: "+NUMBER_QUESTIONS_COMPLETED);
        Log.d("volley","Correct: "+correctAnswer);
        Log.d("volley","Question: "+question);

       // QuestionTextView.setText("Q. "+question);
        QuestionTextView.setText(Html.fromHtml("Q. "+question));
        CURRENT_QUESTION=question;

        Random random=new Random();
        int correctAnswerButtonPosition=random.nextInt(4)+1;
        CURRENT_CORRECT_POSITION=correctAnswerButtonPosition;

        switch (correctAnswerButtonPosition)
        {
                case 1: AnswerButton1.setText(Html.fromHtml("A. "+correctAnswer));
                    break;

                case 2: AnswerButton2.setText(Html.fromHtml("B. "+correctAnswer));
                    break;

                case 3: AnswerButton3.setText(Html.fromHtml("C. "+correctAnswer));
                    break;

                case 4: AnswerButton4.setText(Html.fromHtml("D. "+correctAnswer));
                    break;
        }


            for(int k=0;k<wrongAnswers.length;k++)
            {
                if(AnswerButton1.getText().toString().equals(""))
                {
                    AnswerButton1.setText(Html.fromHtml("A. "+wrongAnswers[k]));
                }

                else if(AnswerButton2.getText().toString().equals(""))
                {
                    AnswerButton2.setText(Html.fromHtml("B. "+wrongAnswers[k]));
                }

                else if(AnswerButton3.getText().toString().equals(""))
                {
                    AnswerButton3.setText(Html.fromHtml("C. "+wrongAnswers[k]));
                }

                else if(AnswerButton4.getText().toString().equals(""))
                {
                    AnswerButton4.setText(Html.fromHtml("D. "+wrongAnswers[k]));
                }
            }

            CURRENT_ANSWER_1=AnswerButton1.getText().toString();
            CURRENT_ANSWER_2=AnswerButton2.getText().toString();
            CURRENT_ANSWER_3=AnswerButton3.getText().toString();
            CURRENT_ANSWER_4=AnswerButton4.getText().toString();

    }

    @Override
    public void onBackPressed() {
      AlertDialog.Builder builder=  new AlertDialog.Builder(QuizActivity.this);
              builder
                .setMessage("Are you sure you want to leave in the middle of the quiz?")
                .setCancelable(false);
               builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Intent intentToStartMain=new Intent(getApplicationContext(),MainActivity.class);
                       NUMBER_QUESTIONS_COMPLETED=0;
                       progressBar.setProgress(0);
                       PROGRESS=0;
                       NUMBER_CORRECT=0;
                       startActivity(intentToStartMain);
                       finish();
                   }
               });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        Log.d("TAG","onSaveInstanceState called");
        outState.putString(String.valueOf(QUESTION_KEY),CURRENT_QUESTION);
        outState.putString(String.valueOf(ANSWER_1_KEY),CURRENT_ANSWER_1);
        outState.putString(String.valueOf(ANSWER_2_KEY),CURRENT_ANSWER_2);
        outState.putString(String.valueOf(ANSWER_3_KEY),CURRENT_ANSWER_3);
        outState.putString(String.valueOf(ANSWER_4_KEY),CURRENT_ANSWER_4);
        super.onSaveInstanceState(outState);
    }

//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//
//        QuestionTextView.setText(savedInstanceState.getString(String.valueOf(QUESTION_KEY)));
//        AnswerButton1.setText(savedInstanceState.getString(String.valueOf(ANSWER_1_KEY)));
//        AnswerButton2.setText(savedInstanceState.getString(String.valueOf(ANSWER_2_KEY)));
//        AnswerButton3.setText(savedInstanceState.getString(String.valueOf(ANSWER_3_KEY)));
//        AnswerButton4.setText(savedInstanceState.getString(String.valueOf(ANSWER_4_KEY)));
//    }

    //@Override
    //public void onBackPressed() {
//        new AlertDialog.Builder(this)
//                .setMessage("Are you sure you want to exit?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        ExampleActivity.this.finish();
//                    }
//                })
//                .setNegativeButton("No", null)
//                .show();
//    }


//    public void getQuestionsButtons(JSONArray jsonArray)
//    {
//        String QUESTION = null;
//        String CORRECT_ANSWER = null;
//        int i=0;
//        while(i<jsonArray.length() && buttonNextClicked) {
//
//            buttonNextClicked=false;
//
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//            QUESTION = jsonObject.getString("question");
//            CORRECT_ANSWER = jsonObject.getString("correct_answer");
//
//
//            String[] WRONG_ANSWERS = new String[3];
//            JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");
//
//            for (int j = 0; j < jsonArray1.length(); j++) {
//                WRONG_ANSWERS[j] = jsonArray1.getString(j);
//            }
//
//
//            Log.d("volley", "Question: " + QUESTION);
//            Log.d("volley", "Correct Answer " + CORRECT_ANSWER);
//
//            QuestionTextView.setText("Q. "+QUESTION);
//            Log.d("volley","QuestionTextView set");
//
//
//
//            for(int k=0;k<WRONG_ANSWERS.length;k++)
//            {
//                Log.d("volley","Wrong Answer: "+WRONG_ANSWERS[k]);
//            }
//
//
//            Random random=new Random();
//            int correctAnswerButtonPosition=random.nextInt(4)+1;
//
//            switch (correctAnswerButtonPosition)
//            {
//                case 1: AnswerButton1.setText("A. "+CORRECT_ANSWER);
//                    break;
//
//                case 2: AnswerButton2.setText("B. "+CORRECT_ANSWER);
//                    break;
//
//                case 3: AnswerButton3.setText("C. "+CORRECT_ANSWER);
//                    break;
//
//                case 4: AnswerButton4.setText("D. "+CORRECT_ANSWER);
//                    break;
//            }
//
//            for(int k=0;k<WRONG_ANSWERS.length;k++)
//            {
//                if(AnswerButton1.getText().toString().equals(""))
//                {
//                    AnswerButton1.setText("A. "+WRONG_ANSWERS[k]);
//                }
//
//                else if(AnswerButton2.getText().toString().equals(""))
//                {
//                    AnswerButton2.setText("B. "+WRONG_ANSWERS[k]);
//                }
//
//                else if(AnswerButton3.getText().toString().equals(""))
//                {
//                    AnswerButton3.setText("C. "+WRONG_ANSWERS[k]);
//                }
//
//                else if(AnswerButton4.getText().toString().equals(""))
//                {
//                    AnswerButton4.setText("D. "+WRONG_ANSWERS[k]);
//                }
//            }
//
//            i++;
//        }
//    }

}