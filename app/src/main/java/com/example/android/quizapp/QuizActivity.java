package com.example.android.quizapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    private static int PROGRESS=10;
    private static int NUMBER_QUESTIONS_COMPLETED=0;
    private static int CURRENT_CORRECT_POSITION;


    private static String[] CORRECT_ANSWERS;
    private static String[] QUESTIONS;
    private static List<String[]> WRONG_ANSWERS_LIST;


    private static boolean buttonNextClicked=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        QuestionTextView=(TextView) findViewById(R.id.question_text_view);

        AnswerButton1=(Button) findViewById(R.id.answer_button_1);
        AnswerButton2=(Button) findViewById(R.id.answer_button_2);
        AnswerButton3=(Button) findViewById(R.id.answer_button_3);
        AnswerButton4=(Button) findViewById(R.id.answer_button_4);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);

        progressBar.setProgress(PROGRESS);




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
                    int i=0;
                    CORRECT_ANSWERS=new String[jsonArray.length()];
                    QUESTIONS=new String[jsonArray.length()];
                    WRONG_ANSWERS_LIST=new ArrayList<>(jsonArray.length());
                    while(i<jsonArray.length())
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        QUESTIONS[i]=jsonObject.getString("question");
                        CORRECT_ANSWERS[i]=jsonObject.getString("correct_answer");

                        String[] WRONG_ANSWERS = new String[3];
                        JSONArray jsonArray1 = jsonObject.getJSONArray("incorrect_answers");

                        for (int j = 0; j < jsonArray1.length(); j++)
                        {
                            WRONG_ANSWERS[j] = jsonArray1.getString(j);
                        }

                        WRONG_ANSWERS_LIST.add(i,WRONG_ANSWERS);

                        i++;
                    }

                    getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);

                }
                catch (JSONException e)
                {

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




    public void onNextButtonClick(View view)
    {
        buttonNextClicked=true;
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
            progressBar.setProgress(10);
            PROGRESS=10;
            NUMBER_CORRECT=0;
            startActivity(intentToStartResult);
            finish();
        }

        if(NUMBER_QUESTIONS_COMPLETED<10)
            getQuestionsButtons(QUESTIONS,CORRECT_ANSWERS,WRONG_ANSWERS_LIST);

    }

    public void onAnswerButton1Click(View view)
    {
           if(CURRENT_CORRECT_POSITION==1)
           {
               NUMBER_CORRECT++;
           }
           AnswerButton1.setBackgroundColor(getResources().getColor(R.color.material_green));
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

        if(CURRENT_CORRECT_POSITION==2)
        {
            NUMBER_CORRECT++;
        }
        AnswerButton2.setBackgroundColor(getResources().getColor(R.color.material_green));
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

        if(CURRENT_CORRECT_POSITION==3)
        {
            NUMBER_CORRECT++;
        }
        AnswerButton3.setBackgroundColor(getResources().getColor(R.color.material_green));
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

        if(CURRENT_CORRECT_POSITION==4)
        {
            NUMBER_CORRECT++;
        }
        AnswerButton4.setBackgroundColor(getResources().getColor(R.color.material_green));
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

        QuestionTextView.setText("Q. "+question);

        Random random=new Random();
        int correctAnswerButtonPosition=random.nextInt(4)+1;
        CURRENT_CORRECT_POSITION=correctAnswerButtonPosition;

        switch (correctAnswerButtonPosition)
        {
                case 1: AnswerButton1.setText("A. "+correctAnswer);
                    break;

                case 2: AnswerButton2.setText("B. "+correctAnswer);
                    break;

                case 3: AnswerButton3.setText("C. "+correctAnswer);
                    break;

                case 4: AnswerButton4.setText("D. "+correctAnswer);
                    break;
        }


            for(int k=0;k<wrongAnswers.length;k++)
            {
                if(AnswerButton1.getText().toString().equals(""))
                {
                    AnswerButton1.setText("A. "+wrongAnswers[k]);
                }

                else if(AnswerButton2.getText().toString().equals(""))
                {
                    AnswerButton2.setText("B. "+wrongAnswers[k]);
                }

                else if(AnswerButton3.getText().toString().equals(""))
                {
                    AnswerButton3.setText("C. "+wrongAnswers[k]);
                }

                else if(AnswerButton4.getText().toString().equals(""))
                {
                    AnswerButton4.setText("D. "+wrongAnswers[k]);
                }
            }

    }


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