package com.example.android.quizapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vishaal on 21/6/17.
 */

public class ScoreDbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME="scoreDb.db";

    private static final int DATABASE_VERSION=1;

    public ScoreDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_TABLE;
        CREATE_TABLE="CREATE TABLE "+ ScoreContract.ScoreEntry.TABLE_NAME+" ( "
                + ScoreContract.ScoreEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ScoreContract.ScoreEntry.COLUMN_CATEGORY+" TEXT NOT NULL, "
                + ScoreContract.ScoreEntry.COLUMN_GAME_SCORE+" TEXT NOT NULL, "
                + ScoreContract.ScoreEntry.COLUMN_TIMESTAMP+" DATETIME  DEFAULT CURRENT_TIMESTAMP, "
                + ScoreContract.ScoreEntry.COLUMN_PLAYER_ID+" INTEGER NOT NULL "
                +");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ ScoreContract.ScoreEntry.TABLE_NAME);
        onCreate(db);
    }
}
