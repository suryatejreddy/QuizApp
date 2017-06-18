package com.example.android.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DisplayResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        getSupportActionBar().setTitle("Result");
        int Correct=0,Incorrect=10;
        if(getIntent().hasExtra("Correct"))
            Correct=getIntent().getIntExtra("Correct",0);
        if(getIntent().hasExtra("Incorrect"))
            Incorrect=getIntent().getIntExtra("Incorrect",10);


        ((TextView) findViewById(R.id.correct)).setText("Correct: "+String.valueOf(Correct));
        ((TextView) findViewById(R.id.incorrect)).setText("Incorrect: "+String.valueOf(Incorrect));
    }

    public void onPlayAgainClick(View view)
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

}
