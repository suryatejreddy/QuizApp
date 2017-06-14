package com.example.android.quizapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ProgressBar;

/**
 * Created by vishaal on 13/6/17.
 */

public class ProfileDbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME="profileDb.db";

    private static final int DATABASE_VERSION=1;

    public ProfileDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        final String CREATE_TABLE;
        CREATE_TABLE="CREATE TABLE "+ ProfileContract.ProfileEntry.TABLE_NAME+" ( "
                + ProfileContract.ProfileEntry._ID+" INTEGER PRIMARY KEY, "
                + ProfileContract.ProfileEntry.COLUMN_NAME+" TEXT NOT NULL, "
                + ProfileContract.ProfileEntry.COLUMN_AGE+" TEXT NOT NULL, "
                + ProfileContract.ProfileEntry.COLUMN_GENDER+" TEXT NOT NULL, "
                + ProfileContract.ProfileEntry.COLUMN_IMAGE+" BLOB "+");";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+ ProfileContract.ProfileEntry.TABLE_NAME);
        onCreate(db);
    }
}
