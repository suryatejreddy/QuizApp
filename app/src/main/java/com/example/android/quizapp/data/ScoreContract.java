package com.example.android.quizapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vishaal on 21/6/17.
 */

public class ScoreContract
{
    public static final String  AUTHORITY="com.example.android.quizapp";

    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);

    public static final String PATH_SCORE="score";

    public static final class ScoreEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCORE).build();

        public static final String TABLE_NAME="score";

        public static final String COLUMN_PLAYER_ID="player_id";

        public static final String COLUMN_CATEGORY="category";

        public static final String COLUMN_GAME_SCORE="game_score";

        public static final String COLUMN_TIMESTAMP="timestamp";

    }
}
