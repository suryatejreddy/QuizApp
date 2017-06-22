package com.example.android.quizapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.quizapp.data.ProfileContract;
import com.example.android.quizapp.data.ProfileDbHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SignUpActivity extends AppCompatActivity {


    @InjectView(R.id.input_name)
    EditText nameEditText;
    @InjectView(R.id.input_email)
    EditText emailEditText;
    @InjectView(R.id.input_password)
    EditText passwordEditText;
    @InjectView(R.id.input_confirm_password)
    EditText passwordConfirmEditText;

    private static EditText dateOfBirthEditText;
    private static RadioButton maleRadioButton;
    private static RadioButton femaleRadioButton;
    private static Button signUpButton;
    private static TextView linkToLogin;
    private static Calendar myCalendar;
    private static DatePickerDialog.OnDateSetListener date;
    private static ImageView profileImageView;
    private static ImageButton editBtn;

    private static String name="";
    private static String email="";
    private static String dob="";


    public static final String SALT="my-salt-text";


    private Bitmap PROFILE_IMAGE;
    private Uri PROFILE_URI;


    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> PermissionsRejected=new ArrayList<>();
    private ArrayList<String> permissions= new ArrayList<>();

    private static final int ALL_PERMISSIONS_RESULT=107;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        if(getSupportActionBar()!=null)
            getSupportActionBar().hide();

        dateOfBirthEditText = (EditText) findViewById(R.id.input_date_of_birth);
        signUpButton = (Button) findViewById(R.id.btn_signup);
        linkToLogin = (TextView) findViewById(R.id.link_login);
        maleRadioButton = (RadioButton) findViewById(R.id.radButton1_signup);
        femaleRadioButton = (RadioButton) findViewById(R.id.radButton2_signup);
        profileImageView = (ImageView) findViewById(R.id.profile_image_sign_up);
        editBtn = (ImageButton) findViewById(R.id.edit_btn);

        maleRadioButton.setChecked(true);


        MainActivity.CURRENT_USER_IMAGE="";
        MainActivity.CURRENT_USER_IMAGE_BITMAP= BitmapFactory.decodeResource(getApplication().getResources(), R.drawable.ic_profile_default);


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getImagePickerIntent(), 200);
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getImagePickerIntent(), 200);
            }
        });





        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO insert values into database
                signup();

            }
        });


        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });


        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        dateOfBirthEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        permissions.add(CAMERA);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissions.add(WRITE_EXTERNAL_STORAGE);
        permissionsToRequest=findUnAskedpermissions(permissions);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(permissionsToRequest.size()>0)
            {
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
            }
        }
    }

    private ArrayList<String> findUnAskedpermissions(ArrayList<String> permissions)
    {
        ArrayList<String> result= new ArrayList<>();

        for(String perm : permissions)
        {
            if(!hasPermission(perm))
            {
                result.add(perm);
            }
        }
        return result;
    }

    private boolean hasPermission(String perm)
    {
        if(canMakeSmores())
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                return (checkSelfPermission(perm)== PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores()
    {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1 );
    }

    private Intent getImagePickerIntent()
    {
        Uri outputFileUri = getCameraImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager=getPackageManager();

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);

        for(ResolveInfo res: listCam)
        {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);

            if(outputFileUri!=null)
            {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

        Intent  galleryIntent= new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery = packageManager.queryIntentActivities(galleryIntent,0);
        for(ResolveInfo res: listGallery)
        {
            Intent intent= new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

        Intent mainIntent=allIntents.get(allIntents.size()-1);
        for(Intent intent : allIntents)
        {
            if(intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity"))
            {
                mainIntent=intent;
                break;
            }
        }

        allIntents.remove(mainIntent);

        Intent chooserIntent=Intent.createChooser(mainIntent, "Select source");

        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;

    }

    private Uri getPickImageResultUri(Intent data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }


        return isCamera ? getCameraImageOutputUri() : data.getData();
    }

    private Uri getCameraImageOutputUri()
    {
        Uri outputFileUri=null;
        File getImage=getExternalCacheDir();
        if(getImage!=null)
        {
            outputFileUri= Uri.fromFile(new File(getImage.getPath(), "profile_pic_"+String.valueOf(MainActivity.CURRENT_USER_PROFILE_ID)+".jpg"));
        }

        return outputFileUri;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateOfBirthEditText.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {
            if(getPickImageResultUri(data) != null)
            {
                PROFILE_URI= getPickImageResultUri(data);
                MainActivity.CURRENT_USER_IMAGE=PROFILE_URI.toString();
                Log.d("database", "Image: "+MainActivity.CURRENT_USER_IMAGE);

                try {

                    PROFILE_IMAGE = MediaStore.Images.Media.getBitmap(this.getContentResolver(), PROFILE_URI);
                    if(data!=null &&    data.getScheme().equals("content"))
                    {
                        InputStream inputStream= getApplicationContext().getContentResolver().openInputStream(PROFILE_URI);
                        PROFILE_IMAGE= BitmapFactory.decodeStream(inputStream);
                        if(inputStream!= null)
                        {
                            inputStream.close();
                        }
                    }
                    else
                    {
                        PROFILE_IMAGE= rotateImageIfRequired(PROFILE_IMAGE, PROFILE_URI);
                    }

                    PROFILE_IMAGE=getResizedBitmap(PROFILE_IMAGE, 500);
                    MainActivity.CURRENT_USER_IMAGE_BITMAP=PROFILE_IMAGE;
                    profileImageView.setImageBitmap(PROFILE_IMAGE);

                } catch (FileNotFoundException e) {
                    Log.d("database", e.toString());
                    e.printStackTrace();
                } catch (IOException e) {
                    Log.d("database", e.toString());
                    e.printStackTrace();
                }
            }

            else
            {
                PROFILE_IMAGE= (Bitmap) data.getExtras().get("data");
                MainActivity.CURRENT_USER_IMAGE_BITMAP=PROFILE_IMAGE;
                profileImageView.setImageBitmap(PROFILE_IMAGE);
            }

            Log.d("database", "Sign Up Activity: "+MainActivity.CURRENT_USER_IMAGE);
        }
    }

    private Bitmap getResizedBitmap(Bitmap image, int maxSize)
    {
        int width=image.getWidth();
        int height=image.getHeight();

        float bitmapRatio= (float) width / (float) height ;
        if(bitmapRatio>0)
        {
            width=maxSize;
            height=(int)  (width/ bitmapRatio);
        }
        else
        {
            height=maxSize;
            width=(int) (height*bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException
    {
        ExifInterface ei= new ExifInterface(selectedImage.getPath());
        int orientation= ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation)
        {
            case ExifInterface.ORIENTATION_ROTATE_90:
                    return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                    return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                    return rotateImage(img, 270);
            default:    return img;
        }
    }

    private Bitmap rotateImage(Bitmap img, int degree)
    {
        Matrix matrix= new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg= Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }

    public void signup() {
        Log.d("TAG", "signup");

        if(!validate())
        {
            onSignUpFailed();
            return;
        }

        signUpButton.setEnabled(false);
            final ProgressDialog progressDialog= new ProgressDialog(SignUpActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        Window window=progressDialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        String name=nameEditText.getText().toString();
        String email=emailEditText.getText().toString();
        String password=passwordEditText.getText().toString();
        String dob=dateOfBirthEditText.getText().toString();

        ProfileDbHelper profileDbHelper = new ProfileDbHelper(getApplicationContext());
        SQLiteDatabase database = profileDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ProfileContract.ProfileEntry.COLUMN_NAME, name);
        cv.put(ProfileContract.ProfileEntry.COLUMN_EMAIL, email);
        cv.put(ProfileContract.ProfileEntry.COLUMN_PASSWORD, generateHash(SALT+password));
        cv.put(ProfileContract.ProfileEntry.COLUMN_DOB,dob);

        if(maleRadioButton.isChecked()){
            cv.put(ProfileContract.ProfileEntry.COLUMN_GENDER,"M");
        }
        else
        {
            cv.put(ProfileContract.ProfileEntry.COLUMN_GENDER,"F");
        }

        cv.put(ProfileContract.ProfileEntry.COLUMN_IMAGE, MainActivity.CURRENT_USER_IMAGE);

        long id=database.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, cv);
        Log.d("database","Current user id: "+String.valueOf(id));
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putLong("CURRENT_USER_PROFILE_ID",id).apply();
        database.close();

        new android.os.Handler().postDelayed(

                new Runnable() {
                    @Override
                    public void run() {
                        onSignUpSuccess();
                        progressDialog.dismiss();
                    }
                },
            3000
        );

    }

    private void onSignUpSuccess() {
        signUpButton.setEnabled(true);
        Intent intent=new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("ACTIONBAR", nameEditText.getText().toString());
        startActivity(intent);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("LOGIN_MODE", true);
        editor.apply();
        finish();
    }

    private void onSignUpFailed() {
        Toast.makeText(getApplicationContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
        signUpButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String DOB = dateOfBirthEditText.getText().toString();
        String confirmPassword= passwordConfirmEditText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            nameEditText.setError("Name should contain atleast 3 characters");
            valid = false;
        } else {
            nameEditText.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter valid email address");
            valid = false;
        } else {
            emailEditText.setError(null);
        }

        PasswordValidator passwordValidator = new PasswordValidator();
        if (password.isEmpty() || !passwordValidator.validate(password)) {
            passwordEditText.setError("Password must be between 8 and 40 characters long and contain atleast one digit, one lower case character, one upper case character and one special character");
            valid = false;
        } else {
            passwordEditText.setError(null);
        }

        if(DOB.isEmpty()){
            dateOfBirthEditText.setError("Enter valid date of birth");
            valid=false;
        }
        else
        {
            dateOfBirthEditText.setError(null);
        }

        if(!confirmPassword.equals(password))
        {
            passwordConfirmEditText.setError("Passwords do not match");
            valid=false;
        }
        else
        {
            passwordConfirmEditText.setError(null);
        }

        return valid;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case ALL_PERMISSIONS_RESULT:
            {
                for(String perms: permissionsToRequest)
                {
                    if(hasPermission(perms))
                    {

                    }
                    else
                    {
                        PermissionsRejected.add(perms);
                    }
                }

                if(PermissionsRejected.size() > 0)
                {




                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    {
                        if(shouldShowRequestPermissionRationale(PermissionsRejected.get(0)))
                        {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",

                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                                            {
                                                requestPermissions(PermissionsRejected.toArray(new String[PermissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    }
                            );

                            return;
                        }
                    }



                }
                break;
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener)
    {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this, R.style.AlertDialogCustomTheme);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("Cancel", null);
        AlertDialog al=alertDialog.create();
        al.show();
        Window window=al.getWindow();
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

    }

    @Override
    protected void onPause() {
        super.onPause();

        name=nameEditText.getText().toString();
        email=emailEditText.getText().toString();
        dob=dateOfBirthEditText.getText().toString();
    }

    @Override
    protected void onStart() {
        super.onStart();

        nameEditText.setText(name);
        emailEditText.setText(email);
        dateOfBirthEditText.setText(dob);
    }

    public static String generateHash(String input) {
        StringBuilder hash = new StringBuilder();

        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha.digest(input.getBytes());
            char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f' };
            for (int idx = 0; idx < hashedBytes.length; ++idx) {
                byte b = hashedBytes[idx];
                hash.append(digits[(b & 0xf0) >> 4]);
                hash.append(digits[b & 0x0f]);
            }
        } catch (NoSuchAlgorithmException e) {
            // handle error here.
            Log.d("database", "Hashing error "+e.toString());
        }

        return hash.toString();
    }
}


class PasswordValidator
{



    private Pattern pattern;
    private Matcher matcher;

    private static final String PASSWORD_PATTERN = "((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{8,40})";

    public PasswordValidator()
    {
        pattern = Pattern.compile(PASSWORD_PATTERN);
    }



    public boolean validate(final String password)
    {
        matcher = pattern.matcher(password);
        return matcher.matches();
    }



}

