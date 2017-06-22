package com.example.android.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quizapp.data.ProfileContract;
import com.example.android.quizapp.data.ProfileDbHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {


    private static long CurrentUserId;

    private static String email="";


    @InjectView(R.id.email_input) EditText emailEditText;
    @InjectView(R.id.password_input) EditText passwordEditText;
    private Button loginButton;
    private TextView linkToSignUpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        Cursor cursor=(new ProfileDbHelper(getApplicationContext()).getReadableDatabase().rawQuery("select * from "+ ProfileContract.ProfileEntry.TABLE_NAME, null));

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String email = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_EMAIL));
                String pass = cursor.getString(cursor.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_PASSWORD));
                Log.d("database", email+" ,"+pass);
                cursor.moveToNext();
            }
        }

        final SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(sharedPreferences.getBoolean("LOGIN_MODE",false))
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        else
        {
            setContentView(R.layout.activity_login);
            ButterKnife.inject(this);
            if(getSupportActionBar()!=null)
                getSupportActionBar().hide();

            loginButton= (Button) findViewById(R.id.btn_login);
            linkToSignUpTextView= (TextView) findViewById(R.id.link_signup);

            String emailEntered = emailEditText.getText().toString();
            String passwordEntered = passwordEditText.getText().toString();

            //TODO Implement AUTH logic


            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

            linkToSignUpTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), SignUpActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }


    private void login() {

        Log.d("database","login");

        if(!validate())
        {
            return;
        }

        loginButton.setEnabled(false);
        final ProgressDialog progressDialog= new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        Window window=progressDialog.getWindow();

        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        String email=emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        if(checkUser(email,password))
        {
            new android.os.Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                            onLoginSuccess();
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
                            onLoginFailed();
                            progressDialog.dismiss();
                        }
                    },
                    3000
            );
        }


    }

    private void onLoginSuccess()
    {
        loginButton.setEnabled(true);
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);

        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putLong("CURRENT_USER_PROFILE_ID",CurrentUserId).apply();
        sharedPreferences.edit().putBoolean("LOGIN_MODE", true).apply();
        startActivity(intent);
        finish();
    }

    private void onLoginFailed()
    {
        Toast.makeText(getApplicationContext(),"Sorry, we could not find your account.", Toast.LENGTH_SHORT).show();
        passwordEditText.setText("");
        loginButton.setEnabled(true);
    }

    private boolean validate()
    {
        boolean valid=true;
        String email=emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();

        if(email.isEmpty())
        {
            emailEditText.setError("Field cannot be empty");
            valid=false;
        }
        else {
            emailEditText.setError(null);
        }

        if(password.isEmpty())
        {
            passwordEditText.setError("Field cannot be empty");
            valid=false;
        }
        else
        {
            passwordEditText.setError(null);
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
            cursor.moveToFirst();
            CurrentUserId=cursor.getLong(cursor.getColumnIndex(ProfileContract.ProfileEntry._ID));
            cursor.close();
            return true;
        }
        else {
            return false;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        email=emailEditText.getText().toString();

    }

    @Override
    protected void onStart() {
        super.onStart();

        emailEditText.setText(email);

    }
}


