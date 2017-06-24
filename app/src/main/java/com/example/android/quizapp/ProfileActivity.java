package com.example.android.quizapp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quizapp.data.ScoreContract;
import com.example.android.quizapp.data.ScoreDbHelper;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));

//        //ListView listGameScores = (ListView) findViewById(R.id.list_game_scores);
//        //ArrayList<String> listGames = new ArrayList<String>();
//
//        Cursor cursor1 = (new ScoreDbHelper(getApplicationContext()).getReadableDatabase().rawQuery("select * from " + ScoreContract.ScoreEntry.TABLE_NAME + " where " + ScoreContract.ScoreEntry.COLUMN_PLAYER_ID + " =" + MainActivity.CURRENT_USER_PROFILE_ID, null));
//
//        if (cursor1.moveToFirst()) {
//            while (!cursor1.isAfterLast()) {
//                long id = cursor1.getLong(cursor1.getColumnIndex(ScoreContract.ScoreEntry._ID));
//                String categ = cursor1.getString(cursor1.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_CATEGORY));
//                String score = cursor1.getString(cursor1.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_GAME_SCORE));
//                String time = cursor1.getString(cursor1.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_TIMESTAMP));
//                String pid = cursor1.getString(cursor1.getColumnIndex(ScoreContract.ScoreEntry.COLUMN_PLAYER_ID));
//
//                //listGames.add(categ+", "+score+", "+pid+" ,"+time);
//                Log.d("database","listgames: "+ categ+", "+score+", "+pid+" ,"+time);
//                DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.US);
//
//                DateTime parsed = inputFormatter.parseDateTime(time);
//
//                DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("HH:mm").withLocale(Locale.US).withZone(DateTimeZone.getDefault());
//
//                String outputTime = outputFormatter.print(parsed);
//
//
//                try {
//                    Log.d("database", id + " ," + categ + " ," + score + " ," + outputTime + " ," + time + " ," + pid);
//                    cursor1.moveToNext();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    Log.d("database", e.toString());
//                }
//            }
//        }
//
//        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listGames);
//
//        listGameScores.setAdapter(adapter);
//
//        listGameScores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(), ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
