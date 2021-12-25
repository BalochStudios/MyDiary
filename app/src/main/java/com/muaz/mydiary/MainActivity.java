package com.muaz.mydiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                /*if (id == R.id.actionRateUs) {
                    try {
                        String storeULR = "market://details?id=" + getPackageName();
                        Intent storeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeULR));
                        startActivity(storeIntent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            String storeURL = "https://play.google.com/store/apps/details?id=" + getPackageName();
                            Intent storeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(storeURL));
                            startActivity(storeIntent);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            Toast.makeText(HomeActivity.this, "unable to  open this link", Toast.LENGTH_SHORT).show();
                        }
                    }

                }*/
                if (id == R.id.actionPro) {
                    Toast.makeText(MainActivity.this, "Pro Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionTheme) {
                    Toast.makeText(MainActivity.this,"Theme Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionTag) {
                    Toast.makeText(MainActivity.this,"Tag Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionDiaryLock) {
                    Toast.makeText(MainActivity.this,"Diary Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionBackUpRestore) {
                    Toast.makeText(MainActivity.this,"BackUp Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionExport) {
                    Toast.makeText(MainActivity.this,"Export Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionDonate) {
                    Toast.makeText(MainActivity.this,"Donate Level", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionShare) {
                    ShareCompat.IntentBuilder intentBuilder = ShareCompat.IntentBuilder.from(MainActivity.this);
                    intentBuilder.setType("text/plain");
                    intentBuilder.setText("Hi This is amazing App: https://play.google.com/store/apps/details?id=" + getPackageName());
                    intentBuilder.setSubject(getString(R.string.app_name));
                    startActivity(intentBuilder.getIntent());
                } else if (id == R.id.actionMoreApps) {
                    Toast.makeText(MainActivity.this, "MoreApps", Toast.LENGTH_SHORT).show();
                } else if (id == R.id.actionFAQ) {
                    Toast.makeText(MainActivity.this, "FAQ", Toast.LENGTH_SHORT);

                } else if (id == R.id.actionSetting) {
                    Toast.makeText(MainActivity.this, "Setting", Toast.LENGTH_SHORT).show();
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