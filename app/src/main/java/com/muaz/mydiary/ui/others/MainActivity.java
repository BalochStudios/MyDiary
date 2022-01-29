package com.muaz.mydiary.ui.others;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ajts.androidmads.library.SQLiteToExcel;
import com.google.android.material.navigation.NavigationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.muaz.mydiary.R;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityMainBinding;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.ui.lockscreen.LockActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    SharedPreference sharedPreference;
    public String value;
    private DbHelper dbHelper;
    private ArrayList<Diary> diariesList;
    private Diary diary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference();
        setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dbHelper = new DbHelper(MainActivity.this);
        diariesList = new ArrayList<>();

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        setNavigationThemee();


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                 if (id == R.id.actionTheme) {
                    startActivity(new Intent(MainActivity.this, ThemeActivity.class));
                } else if (id == R.id.actionTag) {
                    startActivity(new Intent(MainActivity.this, TagActivity.class));
                } else if (id == R.id.actionDiaryLock) {
                    startActivity(new Intent(MainActivity.this, LockActivity.class));
                } else if (id == R.id.actionBackUpRestore) {
                    Toast.makeText(MainActivity.this, "BackUp Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionExport) {

                    Dexter.withContext(MainActivity.this)
                            .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    ExportData();
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                    Toast.makeText(MainActivity.this, "Permission permanently denied", Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();

                                }
                            })
                            .check();


                }else if (id == R.id.actionShare) {
                    ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(MainActivity.this);
                    intentBuilder.setType("text/plain");
                    intentBuilder.setText("Hi This is amazing App: https://play.google.com/store/apps/details?id=" + getPackageName());
                    intentBuilder.setSubject(getString(R.string.app_name));
                    startActivity(intentBuilder.getIntent());
                } else if (id == R.id.actionMoreApps) {
                    String storeULR = "https://play.google.com/store/search?q=Audio Editor & Voice Recorder";
                    Intent storeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeULR));
                    startActivity(storeIntent);
                    Toast.makeText(MainActivity.this, "MoreApps", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionFAQ) {
                    startActivity(new Intent(MainActivity.this, FAQActivity.class));

                } else if (id == R.id.actionSetting) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThemee();
        setNavigationThemee();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(MainActivity.this);
        if (value.equals("0")) {
            setTheme(R.style.AppTheme0);
        } else if (value.equals("1")) {
            setTheme(R.style.AppTheme1);
        } else if (value.equals("2")) {
            setTheme(R.style.AppTheme2);
        } else if (value.equals("3")) {
            setTheme(R.style.AppTheme3);
        } else if (value.equals("4")) {
            setTheme(R.style.AppTheme4);
        } else if (value.equals("5")) {
            setTheme(R.style.AppTheme5);
        } else if (value.equals("6")) {
            setTheme(R.style.AppTheme6);
        } else if (value.equals("7")) {
            setTheme(R.style.AppTheme7);
        } else if (value.equals("8")) {
            setTheme(R.style.AppTheme8);
        } else if (value.equals("9")) {
            setTheme(R.style.AppTheme9);
        } else {
            setTheme(R.style.AppTheme0);
        }
    }

    public void setNavigationThemee() {
        value = sharedPreference.getCurrentTheme(MainActivity.this);
        if (value.equals("0")) {
            binding.navView.setBackgroundResource(R.color.beach);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (value.equals("1")) {
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
            binding.navView.setBackgroundResource(R.color.couple);
        } else if (value.equals("2")) {
            binding.navView.setBackgroundResource(R.color.flower_bunny);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (value.equals("3")) {
            binding.navView.setBackgroundResource(R.color.flowers);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
        } else if (value.equals("4")) {
            binding.navView.setBackgroundResource(R.color.girls);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));

        } else if (value.equals("5")) {
            binding.navView.setBackgroundResource(R.color.lovely_bear);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.color_blue)));
        } else if (value.equals("6")) {
            binding.navView.setBackgroundResource(R.color.loves);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        } else if (value.equals("7")) {
            binding.navView.setBackgroundResource(R.color.night);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (value.equals("8")) {
            binding.navView.setBackgroundResource(R.color.sunset);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else if (value.equals("9")) {
            binding.navView.setBackgroundResource(R.color.unicorn);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        } else {
            setTheme(R.style.AppTheme0);
            binding.navView.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
            binding.navView.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
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


            Toast.makeText(MainActivity.this, fileName + "is save to \n" + dir, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Some issue ", Toast.LENGTH_SHORT).show();

        }


    }
}