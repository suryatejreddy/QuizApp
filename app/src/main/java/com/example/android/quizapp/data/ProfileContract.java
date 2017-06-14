package com.example.android.quizapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vishaal on 13/6/17.
 */

public class ProfileContract
{
    public static final String  AUTHORITY="com.example.android.quizapp";

    public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);

    public static final String PATH_PROFILE="profile";

    public static final class ProfileEntry implements BaseColumns
    {
        public static final Uri CONTENT_URI=BASE_CONTENT_URI.buildUpon().appendPath(PATH_PROFILE).build();

        public static final String TABLE_NAME="profile";

        public static final String COLUMN_NAME="name";

        public static final String COLUMN_AGE="age";

        public static final String COLUMN_GENDER="gender";

        public static final String COLUMN_IMAGE="image";
    }
}
