package com.example.android.quizapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.quizapp.data.ScoreContract;
import com.example.android.quizapp.data.ScoreDbHelper;

public class DisplayResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_result);
        getSupportActionBar().setTitle("Result");
        int Correct=0,Incorrect=10;
        String category="";
        if(getIntent().hasExtra("Correct"))
            Correct=getIntent().getIntExtra("Correct",0);
        if(getIntent().hasExtra("Incorrect"))
            Incorrect=getIntent().getIntExtra("Incorrect",10);

        if(getIntent().hasExtra("Category"))
            category=getIntent().getStringExtra("Category");

        ((TextView) findViewById(R.id.correct)).setText("Correct: "+String.valueOf(Correct));
        ((TextView) findViewById(R.id.incorrect)).setText("Incorrect: "+String.valueOf(Incorrect));



        SQLiteDatabase database = (new ScoreDbHelper(getApplicationContext())).getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(ScoreContract.ScoreEntry.COLUMN_CATEGORY, category);
        cv.put(ScoreContract.ScoreEntry.COLUMN_GAME_SCORE, String.valueOf(Correct));
        cv.put(ScoreContract.ScoreEntry.COLUMN_PLAYER_ID, MainActivity.CURRENT_USER_PROFILE_ID);

        long id=database.insert(ScoreContract.ScoreEntry.TABLE_NAME, null, cv);

        Log.d("database", "GAME_ID: "+String.valueOf(id));
    }

    public void onPlayAgainClick(View view)
    {
        finish();
    }

}
