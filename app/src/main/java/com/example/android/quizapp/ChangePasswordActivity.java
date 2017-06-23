package com.example.android.quizapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.quizapp.data.ProfileContract;
import com.example.android.quizapp.data.ProfileDbHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangePasswordActivity extends AppCompatActivity
{


    @InjectView(R.id.current_password_input) EditText currentPasswordEditText;
    @InjectView(R.id.new_password_input) EditText newPasswordEditText;
    @InjectView(R.id.confirm_new_password_input) EditText confirmNewPasswordEditText;

    private static String currentPassword;
    private static String newPassword;
    private static String confirmNewPassword;

    private static Button changePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        ButterKnife.inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));

        //getSupportActionBar().hide();
        changePasswordButton=(Button) findViewById(R.id.btn_change_password);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword()
    {
        Log.d("database", "changePassword");
        if(!validate())
        {
            onChangePasswordFailed();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(ChangePasswordActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Changing Password...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        Window window = progressDialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        newPassword=newPasswordEditText.getText().toString();

        ProfileDbHelper profileDbHelper=new ProfileDbHelper(getApplicationContext());
        SQLiteDatabase database= profileDbHelper.getWritableDatabase();
        ContentValues cv=new ContentValues();

        cv.put(ProfileContract.ProfileEntry.COLUMN_PASSWORD, SignUpActivity.generateHash(SignUpActivity.SALT+newPassword));

        String[] selectArgs=new String[]{String.valueOf(MainActivity.CURRENT_USER_PROFILE_ID)};
        int x=database.update(ProfileContract.ProfileEntry.TABLE_NAME, cv, ProfileContract.ProfileEntry._ID+"=?", selectArgs );

        if(x>0)
        {
            new android.os.Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                            onChangePasswordSuccess();
                            progressDialog.dismiss();

                        }
                    },
                    3000
            );
        }

        else
        {
            new android.os.Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                            onChangePasswordFailed();
                            progressDialog.dismiss();
                        }
                    },
                    3000
            );
        }
    }

    private void onChangePasswordSuccess()
    {
        ProfileDbHelper profileDbHelper = new ProfileDbHelper(getApplicationContext());
        SQLiteDatabase database = profileDbHelper.getWritableDatabase();

        String[] selectArgs=new String[]{String.valueOf(MainActivity.CURRENT_USER_PROFILE_ID)};
        MainActivity.CURRENT_USER_CURSOR=database.query(ProfileContract.ProfileEntry.TABLE_NAME,null, ProfileContract.ProfileEntry._ID+"=?",selectArgs,null,null,null);
        MainActivity.CURRENT_USER_CURSOR.moveToFirst();

        int passwordPos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_PASSWORD);

        MainActivity.CURRENT_USER_PASSWORD=MainActivity.CURRENT_USER_CURSOR.getString(passwordPos);
        finish();
    }

    private void onChangePasswordFailed()
    {
        Toast.makeText(getApplicationContext(), "Could not change password", Toast.LENGTH_SHORT).show();
    }


    public boolean validate() {
        boolean valid = true;


        String currentPassword = currentPasswordEditText.getText().toString();
        String newPassword = newPasswordEditText.getText().toString();
        String confirmNewPassword= confirmNewPasswordEditText.getText().toString();





        PasswordValidator passwordValidator = new PasswordValidator();
        if (currentPassword.isEmpty()  || !SignUpActivity.generateHash(SignUpActivity.SALT+currentPassword).equals(MainActivity.CURRENT_USER_PASSWORD) ) {

            currentPasswordEditText.setError("Wrong Password");
            currentPasswordEditText.setText("");
            valid = false;
        } else {
            currentPasswordEditText.setError(null);
        }

        if(newPassword.isEmpty() || !passwordValidator.validate(newPassword) ){
            newPasswordEditText.setError("Password must be between 8 and 40 characters long and contain atleast one digit, one lower case character, one upper case character and one special character");
            newPasswordEditText.setText("");
            confirmNewPasswordEditText.setText("");
            valid=false;
        }
        else
        {
            newPasswordEditText.setError(null);
        }

        if(!confirmNewPassword.equals(newPassword))
        {
            confirmNewPasswordEditText.setError("Passwords do not match");
            confirmNewPasswordEditText.setText("");
            valid=false;
        }
        else
        {
            confirmNewPasswordEditText.setError(null);
        }

        return valid;
    }

    private boolean checkUser(String email, String password)
    {
        String[] columns=new String[]{ProfileContract.ProfileEntry._ID};

        SQLiteDatabase database=(new ProfileDbHelper(getApplicationContext())).getReadableDatabase();

        String selection= ProfileContract.ProfileEntry.COLUMN_EMAIL+"=? AND "+ ProfileContract.ProfileEntry.COLUMN_PASSWORD+"=?";

        String[] selectArgs={email,SignUpActivity.generateHash(SignUpActivity.SALT+password)};

        Cursor cursor= database.query(
                ProfileContract.ProfileEntry.TABLE_NAME,
                columns,
                selection,
                selectArgs,
                null,
                null,
                null);

        int cursorCount=cursor.getCount();

        //cursor.close();
        database.close();

        if(cursorCount>0)
        {
            Log.d("database", "cursorCount="+String.valueOf(cursorCount));

            cursor.close();
            return true;
        }
        else {
            return false;
        }
    }
}
