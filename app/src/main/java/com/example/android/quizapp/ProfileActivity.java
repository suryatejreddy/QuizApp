package com.example.android.quizapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProfileActivity extends AppCompatActivity {


    //private static EditText nameEditText;
    //private static EditText emailEditText;
    @InjectView(R.id.input_name_profile) EditText nameEditText;
    @InjectView(R.id.input_email_profile) EditText emailEditText;
    private static EditText dobEditText;
    private static RadioButton maleRadioButton;
    private static RadioButton femaleRadioButton;
    private static Button doneButton;
    private static Calendar myCalendar;
    private static DatePickerDialog.OnDateSetListener date;
    private static Menu MENU;
    private static ImageView profileImageView;
    private static ImageButton editButton;
    private static TextView changePasswordTextView;



    private Bitmap PROFILE_IMAGE=null;
    private Uri PROFILE_URI=null;

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> PermissionsRejected=new ArrayList<>();
    private ArrayList<String> permissions= new ArrayList<>();

    private static final int ALL_PERMISSIONS_RESULT=107;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);
        //getSupportActionBar().setDisplayOptions(ActionBar.NAVIGATION_MODE_STANDARD);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_button));
        getSupportActionBar().setCustomView(R.layout.action_bar_profile);


        dobEditText=(EditText) findViewById(R.id.input_date_of_birth_profile);
        maleRadioButton=(RadioButton) findViewById(R.id.radButton1_profile);
        femaleRadioButton=(RadioButton) findViewById(R.id.radButton2_profile);
        doneButton=(Button) findViewById(R.id.btn_edit_changes);
        profileImageView=(ImageView) findViewById(R.id.profile_image_profile);
        editButton=(ImageButton) findViewById(R.id.edit_button);
        changePasswordTextView=(TextView) findViewById(R.id.link_change_password);


        nameEditText.setText(MainActivity.CURRENT_USER_NAME);
        emailEditText.setText(MainActivity.CURRENT_USER_EMAIL);
        dobEditText.setText(MainActivity.CURRENT_USER_DOB);
        editButton.setVisibility(View.GONE);
        changePasswordTextView.setVisibility(View.GONE);
        profileImageView.setImageBitmap(MainActivity.CURRENT_USER_IMAGE_BITMAP);

        if(MainActivity.CURRENT_USER_GENDER.equals("M"))
            maleRadioButton.setChecked(true);
        else
            femaleRadioButton.setChecked(true);

        doneButton.setVisibility(View.GONE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==R.id.edit_profile_open)
        {
            nameEditText.setEnabled(true);
            emailEditText.setEnabled(true);
            dobEditText.setEnabled(true);
            maleRadioButton.setEnabled(true);
            femaleRadioButton.setEnabled(true);
            editButton.setVisibility(View.VISIBLE);
            doneButton.setVisibility(View.VISIBLE);
            changePasswordTextView.setVisibility(View.VISIBLE);
            item.setVisible(false);

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


            dobEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new DatePickerDialog(ProfileActivity.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            profileImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(getImagePickerIntent(), 200);
                }
            });

            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult(getImagePickerIntent(), 200);
                }
            });


            doneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    update();
                }
            });


            changePasswordTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getApplicationContext(), ChangePasswordActivity.class);
                    startActivity(intent);
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


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("database","onCreateOptionsMenu called");
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        MENU=menu;
        return true;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dobEditText.setText(sdf.format(myCalendar.getTime()));
    }


    private void update() {
        Log.d("database", "update");
        if (!validate()) {
            onUpdateFailed();
            return;
        }



        final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Saving Changes...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Please Wait");
        progressDialog.show();
        Window window = progressDialog.getWindow();
        window.setLayout(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);

        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String dob = dobEditText.getText().toString();

        ProfileDbHelper profileDbHelper = new ProfileDbHelper(getApplicationContext());
        SQLiteDatabase database = profileDbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ProfileContract.ProfileEntry.COLUMN_NAME, name);
        cv.put(ProfileContract.ProfileEntry.COLUMN_EMAIL, email);
        cv.put(ProfileContract.ProfileEntry.COLUMN_DOB, dob);

        if (maleRadioButton.isChecked()) {
            cv.put(ProfileContract.ProfileEntry.COLUMN_GENDER, "M");
        } else {
            cv.put(ProfileContract.ProfileEntry.COLUMN_GENDER, "F");
        }



        if(PROFILE_IMAGE!=null && PROFILE_URI!=null) {
            MainActivity.CURRENT_USER_IMAGE = PROFILE_URI.toString();
            MainActivity.CURRENT_USER_IMAGE_BITMAP = PROFILE_IMAGE;
        }
        cv.put(ProfileContract.ProfileEntry.COLUMN_IMAGE, MainActivity.CURRENT_USER_IMAGE);
        String[] selectArgs=new String[]{String.valueOf(MainActivity.CURRENT_USER_PROFILE_ID)};
        int x=database.update(ProfileContract.ProfileEntry.TABLE_NAME, cv, ProfileContract.ProfileEntry._ID+"=?", selectArgs );
        if(x>0) {
            new android.os.Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                            onUpdateSuccess();
                            progressDialog.dismiss();
                            MenuItem menuItem=MENU.findItem(R.id.edit_profile_open);
                            menuItem.setVisible(true);
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
                            onUpdateFailed();
                            progressDialog.dismiss();
                            MenuItem menuItem=MENU.findItem(R.id.edit_profile_open);
                            menuItem.setVisible(true);
                        }
                    },
                    3000
            );
        }

    }

    private void onUpdateFailed() {
        Toast.makeText(getApplicationContext(), "Could not save details", Toast.LENGTH_SHORT).show();
        nameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        dobEditText.setEnabled(false);
        maleRadioButton.setEnabled(false);
        femaleRadioButton.setEnabled(false);
        editButton.setVisibility(View.GONE);
        doneButton.setVisibility(View.GONE);
        changePasswordTextView.setVisibility(View.GONE);
    }

    private void onUpdateSuccess() {

        ProfileDbHelper profileDbHelper = new ProfileDbHelper(getApplicationContext());
        SQLiteDatabase database = profileDbHelper.getWritableDatabase();

        String[] selectArgs=new String[]{String.valueOf(MainActivity.CURRENT_USER_PROFILE_ID)};
        MainActivity.CURRENT_USER_CURSOR=database.query(ProfileContract.ProfileEntry.TABLE_NAME,null, ProfileContract.ProfileEntry._ID+"=?",selectArgs,null,null,null);
        MainActivity.CURRENT_USER_CURSOR.moveToFirst();


        int namePos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_NAME);
        int emailPos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_EMAIL);
        int dobPos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_DOB);
        int genderPos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_GENDER);
        int imagePos=MainActivity.CURRENT_USER_CURSOR.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_IMAGE);

        MainActivity.CURRENT_USER_NAME = MainActivity.CURRENT_USER_CURSOR.getString(namePos);
        MainActivity.CURRENT_USER_EMAIL = MainActivity.CURRENT_USER_CURSOR.getString(emailPos);
        MainActivity.CURRENT_USER_DOB = MainActivity.CURRENT_USER_CURSOR.getString(dobPos);
        MainActivity.CURRENT_USER_GENDER = MainActivity.CURRENT_USER_CURSOR.getString(genderPos);
        MainActivity.CURRENT_USER_IMAGE = MainActivity.CURRENT_USER_CURSOR.getString(imagePos);

        nameEditText.setText(MainActivity.CURRENT_USER_NAME);
        emailEditText.setText(MainActivity.CURRENT_USER_EMAIL);
        dobEditText.setText(MainActivity.CURRENT_USER_DOB);
        if(MainActivity.CURRENT_USER_GENDER.equals("M"))
        {
            maleRadioButton.setChecked(true);
        }
        else
        {
            femaleRadioButton.setChecked(true);
        }

        TextView appBarForMain=(TextView) findViewById(R.id.app_bar_main_view);
        if (appBarForMain != null)
        {
            appBarForMain.setText(MainActivity.CURRENT_USER_NAME);
        }

        nameEditText.setEnabled(false);
        emailEditText.setEnabled(false);
        dobEditText.setEnabled(false);
        maleRadioButton.setEnabled(false);
        femaleRadioButton.setEnabled(false);
        editButton.setVisibility(View.GONE);
        doneButton.setVisibility(View.GONE);
        changePasswordTextView.setVisibility(View.GONE);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        TextView appBarForMain=(TextView) findViewById(R.id.app_bar_main_view);
        Log.d("database","onBackPressed called");
        if (appBarForMain != null)
        {
            appBarForMain.setText(MainActivity.CURRENT_USER_NAME);
        }
    }

    public boolean validate() {
        boolean valid = true;
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String DOB = dobEditText.getText().toString();

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


        if(DOB.isEmpty()){
            dobEditText.setError("Enter valid date of birth");
            valid=false;
        }
        else
        {
            dobEditText.setError(null);
        }

        return valid;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {
            if(getPickImageResultUri(data) != null)
            {
                PROFILE_URI= getPickImageResultUri(data);
               // MainActivity.CURRENT_USER_IMAGE=PROFILE_URI.toString();
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
                   // MainActivity.CURRENT_USER_IMAGE_BITMAP=PROFILE_IMAGE;
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
               // MainActivity.CURRENT_USER_IMAGE_BITMAP=PROFILE_IMAGE;
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
}



