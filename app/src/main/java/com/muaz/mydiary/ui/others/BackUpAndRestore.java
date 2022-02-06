package com.muaz.mydiary.ui.others;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.muaz.mydiary.R;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityBackUpAndRestoreBinding;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.ui.backuprestore.GoogleDriveFileHolder;
import com.muaz.mydiary.ui.backuprestore.GoogleDriveServiceHelper;

import net.steamcrafted.loadtoast.LoadToast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class BackUpAndRestore extends AppCompatActivity {
    ActivityBackUpAndRestoreBinding backUpAndRestoreBinding;
    SharedPreference sharedPreference;
    public String value;
    private DbHelper dbHelper;
    private ArrayList<Diary> diariesList;
    private Diary diary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        //setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        backUpAndRestoreBinding = ActivityBackUpAndRestoreBinding.inflate(getLayoutInflater());
        View view=backUpAndRestoreBinding.getRoot();
        setContentView(view);
        backUpAndRestoreBinding.tvBackup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForStoragePermission("BackUp");
            }
        });
        backUpAndRestoreBinding.tvRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestForStoragePermission("Restore");
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        setNavigationThemee();
    }

    public void setNavigationThemee() {
        value = sharedPreference.getCurrentTheme(BackUpAndRestore.this);
        if (value.equals("0")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.beach);

        } else if (value.equals("1")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.couple);
        } else if (value.equals("2")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.flower_bunny);
        } else if (value.equals("3")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.flowers);
        } else if (value.equals("4")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.girls);
        } else if (value.equals("5")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.lovely_bear);
        } else if (value.equals("6")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.loves);
        } else if (value.equals("7")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.night);
        } else if (value.equals("8")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.sunset);
        } else if (value.equals("9")) {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.unicorn);
        } else {
            backUpAndRestoreBinding.llBackUpAndRestoreLayout.setBackgroundResource(R.color.beach);

        }
    }
    public void ExportData() {
        diariesList = dbHelper.getAllDiaries();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/My Files/");
            dir.mkdir();
            String fileName = "MyFiles" + timeStamp + ".txt";
            File file = new File(dir, fileName);
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (int i = 0; i < diariesList.size(); i++) {
                Diary diary = diariesList.get(i);
                bufferedWriter.write(diary.getDate()+"\n"+diary.getTitle()+"\n"+diary.getDescription()+"\n --------------------"+"\n");
            }

            bufferedWriter.close();


            Toast.makeText(BackUpAndRestore.this, fileName + "is save to \n" + dir, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(BackUpAndRestore.this, "Some issue ", Toast.LENGTH_SHORT).show();

        }
    }
    public void createFolder() {
        if (mDriveServiceHelper != null) {

            // check folder present or not
            mDriveServiceHelper.isFolderPresent()
                    .addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String id) {
                            if (id.isEmpty()){
                                mDriveServiceHelper.createFolder()
                                        .addOnSuccessListener(new OnSuccessListener<String>() {
                                            @Override
                                            public void onSuccess(String fileId) {
                                                Log.e(TAG, "folder id: "+fileId );
                                                folderId=fileId;
                                                WriteBtn();
                                             //   showMessage("Folder Created with id: "+fileId);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                              //  showMessage("Couldn't create file.");
                                                showMessage("Couldn't able to Backup , Please Try Again");
                                                Log.e(TAG, "Couldn't create file.", exception);
                                            }
                                        });
                            }
                            else {
                                folderId=id;
                                WriteBtn();
                             //   showMessage("Folder already present");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            showMessage("Couldn't able to Backup , Please Try Again");
                            Log.e(TAG, "Couldn't create file..", exception);
                        }
                    });
        }
    }
    public void WriteBtn() {
        // add-write text into file
        loadToast.setText("Bachup ...");
        try {
            DbHelper dbHelper = new DbHelper(BackUpAndRestore.this);
            ArrayList<Diary> diariesList=dbHelper.getAllDiaries();
            Gson gson = new Gson();
            String json = gson.toJson(diariesList);
            Log.e("mytag","jjjj/////"+json);
            ArrayList<Diary> lstArrayList = gson.fromJson(json,
                    new TypeToken<List<Diary>>(){}.getType());
            /*String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/newfoldername/";    // it will return root directory of internal storage
            File root = new File(path);
            if (!root.exists()) {
                root.mkdirs();       // create folder if not exist
            }*/
            File file = new File(getCacheDir().toString()+"/MyDairy.txt");
            if (!file.exists()) {
                file.createNewFile();   // create file if not exist
            }else {
                file.delete();
                file.createNewFile();
            }
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.append(json);
            buf.newLine();  // pointer will be nextline
            buf.close();

            //  loadToast.show();
            if (mDriveServiceHelper != null) {
                mDriveServiceHelper.uploadFileToGoogleDrive(file.toString())
                        .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                            @Override
                            public void onSuccess(Boolean result) {
                                loadToast.success();
                                showMessage("Backup  Completed ...!!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadToast.error();
                                showMessage("Couldn't able to Backup , Please Try Again");
                            }
                        });
            }
            else{
                showMessage("Couldn't able to Backup , Please Try Again");
             //   Toast.makeText(this,"Cannot upload file to server",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getFolderData() {
        if (mDriveServiceHelper != null) {
            //  Intent intent = new Intent(this, ListActivity.class);

            mDriveServiceHelper.getFolderFileList()
                    .addOnSuccessListener(new OnSuccessListener<ArrayList<GoogleDriveFileHolder>>() {
                        @Override
                        public void onSuccess(ArrayList<GoogleDriveFileHolder> result) {
                            Log.e(TAG, "onSuccess: result: "+result.size() );
                          //  showMessage("Extracr file."+result.get(0).getName());
                            Download_file(result);
                            /*intent.putParcelableArrayListExtra("fileList", result);
                            startActivity(intent);*/
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                          //  showMessage("Not able to access Folder data.");
                            showMessage("Couldn't able to Restore , Please Try Again");
                            Log.e(TAG, "Not able to access Folder data.", e);
                        }
                    });
        }
    }

    private void Download_file(ArrayList<GoogleDriveFileHolder> result) {
        mDriveServiceHelper.downloadFile(new java.io.File(getCacheDir().toString(), result.get(0).getName()), result.get(0).getId())
                .addOnSuccessListener(new OnSuccessListener<Boolean>() {
                    @Override
                    public void onSuccess(Boolean result) {
                        if (result){
                          //  showMessage("Successfully downloaded file ...!!");
                            readFromFile();
                        }
                        else{
                            showMessage("Couldn't able to Restore , Please Try Again");
                         //   showMessage("Not Able to downloaded file ...!!");
                        }
                    }
                })
//                        .addOnFailureListener(exception -> showMessage("Got error while downloading file."));
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: error: "+e.getMessage());
                        showMessage("Couldn't able to Restore , Please Try Again");
                    }
                });
    }
    private String readFromFile() {

        String ret = "";

        try {
            FileInputStream inputStream = new FileInputStream(new File(getCacheDir().toString()+"/MyDairy.txt")); // true will be same as Context.MODE_APPEND


            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                    ret=ret+receiveString;
                }
                inputStream.close();
                // ret = stringBuilder.toString();
                ret=ret.replace("][",",");
                Log.e("mytag",ret);
                getSharedPreferences("s",MODE_PRIVATE).edit().putString("ss",ret).commit();
                String str=getSharedPreferences("s",MODE_PRIVATE).getString("ss","");
                Gson gson = new Gson();
                ArrayList<Diary> lstArrayList = gson.fromJson(str,
                        new TypeToken<List<Diary>>(){}.getType());
                DbHelper dbHelper = new DbHelper(BackUpAndRestore.this);
                dbHelper.deleteAllRecord();
                for(int i=0;i<lstArrayList.size();i++){
                    dbHelper.addToDiary(lstArrayList.get(i));
                }
                startActivity(new Intent(BackUpAndRestore.this,MainActivity.class));
                finish();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
            showMessage("Couldn't able to Restore , Please Try Again");
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
            showMessage("Couldn't able to Backup , Please Try Again");
        }

        return ret;
    }
    public void showMessage(String message) {
        Log.i(TAG, message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        switch (requestCode) {
            case REQUEST_CODE_FOR_BACKUP:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData,"BackUp");
                }
                break;
            case REQUEST_CODE_FOR_RESTORE:
                if (resultCode == Activity.RESULT_OK && resultData != null) {
                    handleSignInResult(resultData,"Restore");
                }
                break;
        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }

    private void handleSignInResult(Intent result,String type) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
                .addOnSuccessListener(googleAccount -> {
                    Log.d(TAG, "Signed in as " + googleAccount.getEmail());

                    // Use the authenticated account to sign in to the Drive service.
                    GoogleAccountCredential credential =
                            GoogleAccountCredential.usingOAuth2(
                                    this, Collections.singleton(DriveScopes.DRIVE_FILE));
                    credential.setSelectedAccount(googleAccount.getAccount());
                    Drive googleDriveService =
                            new Drive.Builder(
                                    AndroidHttp.newCompatibleTransport(),
                                    new GsonFactory(),
                                    credential)
                                    .setApplicationName("Drive API Migration")
                                    .build();

                    // The DriveServiceHelper encapsulates all REST API and SAF functionality.
                    // Its instantiation is required before handling any onClick actions.
                    mDriveServiceHelper = new GoogleDriveServiceHelper(googleDriveService);
                    if (type.equals("BackUp")){
                        createFolder();

                    }else {
                        getFolderData();
                    }
                  //  showMessage("Sign-In done...!!");
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e(TAG, "Unable to sign in.", exception);
                        showMessage("Unable to sign in.");
                    }
                });
    }

    private static final String TAG = "MainActivity_ddddd";

    private static final int REQUEST_CODE_FOR_BACKUP = 1;
    private static final int REQUEST_CODE_FOR_RESTORE = 0;
    private static final int PICK_FILE_REQUEST = 100;

    static GoogleDriveServiceHelper mDriveServiceHelper;
    public static String folderId="";

    GoogleSignInClient googleSignInClient;
    LoadToast loadToast;


    // Read/Write permission
    private void requestForStoragePermission(String type) {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                          //  Toast.makeText(getApplicationContext(), "All permissions are granted!", Toast.LENGTH_SHORT).show();
                            requestSignIn(type);
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BackUpAndRestore.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void requestSignIn(String type) {
        Log.d(TAG, "Requesting sign-in");

        GoogleSignInOptions signInOptions =
                new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                        .requestEmail()
                        .build();
        googleSignInClient = GoogleSignIn.getClient(this, signInOptions);

        if (type.equals("BackUp")){
            startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_FOR_BACKUP);
        }else {
            startActivityForResult(googleSignInClient.getSignInIntent(), REQUEST_CODE_FOR_RESTORE);
        }
        // The result of the sign-in Intent is handled in onActivityResult
    }
}