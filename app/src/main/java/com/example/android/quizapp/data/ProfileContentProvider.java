package com.example.android.quizapp.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by vishaal on 13/6/17.
 */

public class ProfileContentProvider extends ContentProvider
{

    public static final int PROFILE=100;
    public static final int PROFILE_WITH_ID=101;

    private static final UriMatcher sUriMatcher=buildUriMatcher();

    public static UriMatcher buildUriMatcher()
    {
        UriMatcher uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ProfileContract.AUTHORITY, ProfileContract.PATH_PROFILE,PROFILE);
        uriMatcher.addURI(ProfileContract.AUTHORITY,ProfileContract.PATH_PROFILE+"/#",PROFILE_WITH_ID);
        return uriMatcher;
    }

    private ProfileDbHelper mDbHelper;

    @Override
    public boolean onCreate()
    {
        Context context=getContext();
        mDbHelper=new ProfileDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        int match=sUriMatcher.match(uri);
        final SQLiteDatabase database=mDbHelper.getReadableDatabase();
        Cursor retCursor=null;

        switch (match)
        {
            case PROFILE:   retCursor=database.query(ProfileContract.ProfileEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                            break;

            case PROFILE_WITH_ID:   String id=uri.getPathSegments().get(1);
                                    String[] passArgs=new String[]{id};
                                    retCursor=database.query(ProfileContract.ProfileEntry.TABLE_NAME,projection, ProfileContract.ProfileEntry._ID+"=?",passArgs,null,null,sortOrder);
                                    break;

            default:    throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(retCursor!=null)
            retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        final SQLiteDatabase database=mDbHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        long id;
        Uri retUri=null;
        switch (match)
        {
            case PROFILE:    id=database.insert(ProfileContract.ProfileEntry.TABLE_NAME,null,values);
                             if(id>0)
                             {
                                retUri= ContentUris.withAppendedId(ProfileContract.ProfileEntry.CONTENT_URI,id);
                             }
                             else
                             {
                                 throw new SQLException("Could not insert row into "+uri);
                             }
                             break;

            default:    throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return retUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase database=mDbHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        int tasksDeleted=0;

        switch (match)
        {
            case PROFILE_WITH_ID:   String id=uri.getPathSegments().get(1);
                                    String[] passArgs=new String[]{id};
                                    tasksDeleted=database.delete(ProfileContract.ProfileEntry.TABLE_NAME, ProfileContract.ProfileEntry._ID+"=?",passArgs);
                                    break;
            default:                throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(tasksDeleted>0)
            getContext().getContentResolver().notifyChange(uri,null);
        return tasksDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        final SQLiteDatabase database=mDbHelper.getWritableDatabase();
        int match=sUriMatcher.match(uri);
        int tasksUpdated;

        switch (match)
        {
            case PROFILE_WITH_ID:   String id=uri.getPathSegments().get(1);
                                    String[] passArgs=new String[]{id};
                                    tasksUpdated=database.update(ProfileContract.ProfileEntry.TABLE_NAME,values, ProfileContract.ProfileEntry._ID+"=?",passArgs);
                                    break;

            default:    throw new UnsupportedOperationException("Unknown uri: "+uri);
        }

        if(tasksUpdated>0)
            getContext().getContentResolver().notifyChange(uri,null);
        return tasksUpdated;
    }
}
