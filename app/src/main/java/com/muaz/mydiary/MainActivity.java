package com.muaz.mydiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
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
import com.google.android.material.navigation.NavigationView;
import com.muaz.mydiary.databinding.ActivityMainBinding;
import com.muaz.mydiary.ui.lockscreen.LockActivity;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.actionPro) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Upgrade to Pro");
                    builder.setMessage("Sorry at this time, Pro Level of App is not Available for You");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                } else if (id == R.id.actionTheme) {
                    Toast.makeText(MainActivity.this,"Theme Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionTag) {
                    Toast.makeText(MainActivity.this,"Tag Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionDiaryLock) {
                    startActivity(new Intent(MainActivity.this, LockActivity.class));
                } else if (id == R.id.actionBackUpRestore) {
                    Toast.makeText(MainActivity.this,"BackUp Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionExport) {
                    Toast.makeText(MainActivity.this,"Export Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionDonate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Donate SomeThing");
                    builder.setMessage("Sorry at this time, Pro Level of App is not Available for You .So you can't Donate anythings to other's");
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                } else if (id == R.id.actionShare) {
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
                    startActivity(new Intent(MainActivity.this,FAQActivity.class));

                } else if (id == R.id.actionSetting) {
                    startActivity(new Intent(MainActivity.this,SettingsActivity.class));
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
}