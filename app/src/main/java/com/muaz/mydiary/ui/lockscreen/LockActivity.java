package com.muaz.mydiary.ui.lockscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityLockBinding;
import com.muaz.mydiary.databinding.ActivityLockInfoBinding;
import com.muaz.mydiary.ui.others.FAQActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.muaz.mydiary.ui.others.TagActivity;


import java.util.zip.Inflater;

public class LockActivity extends AppCompatActivity {
    ActivityLockBinding activityLockBinding;
    SharedPreference sharedPreference;
    public String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();

        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        activityLockBinding = ActivityLockBinding.inflate(getLayoutInflater());
        View view=activityLockBinding.getRoot();
        setContentView(view);
        setTitle("Lock");
        setSupportActionBar(activityLockBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityLockBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityLockBinding.swDiaryLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (activityLockBinding.swDiaryLock.isChecked() == true) {
                    activityLockBinding.tvPasscode.setTextColor(getResources().getColor(R.color.white));
                    activityLockBinding.tvSetPasscode.setTextColor(getResources().getColor(R.color.defaultwhite));
                    activityLockBinding.tvLockQuestion.setTextColor(getResources().getColor(R.color.white));
                    activityLockBinding.tvSetLockQuestion.setTextColor(getResources().getColor(R.color.defaultwhite));
                    activityLockBinding.llSetPasscode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          startActivity(new Intent(LockActivity.this,PasswordSetActivity.class));
                          finish();

                        }
                    });
                    activityLockBinding.llSetQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(LockActivity.this,PasswordRecoverSetActivity.class));
                            finish();
                        }
                    });

                } else  {
                    activityLockBinding.tvPasscode.setTextColor(getResources().getColor(R.color.dimwhite));
                    activityLockBinding.tvSetPasscode.setTextColor(getResources().getColor(R.color.dimwhite));
                    activityLockBinding.tvLockQuestion.setTextColor(getResources().getColor(R.color.dimwhite));
                    activityLockBinding.tvSetLockQuestion.setTextColor(getResources().getColor(R.color.dimwhite));
                    activityLockBinding.llSetPasscode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            return;
                        }
                    });
                    activityLockBinding.llSetQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            return;
                        }
                    });
                    }
                }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu_lock,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id == R.id.navInfo) {


        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
        setNavigationThemee();


    }
    public void setNavigationThemee() {
        value = sharedPreference.getCurrentTheme(LockActivity.this);
        if (value.equals("0")) {
           activityLockBinding.llLockActivity.setBackgroundResource(R.color.beach);
        } else if (value.equals("1")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.couple);
        } else if (value.equals("2")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.flower_bunny);
        } else if (value.equals("3")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.flowers);
        } else if (value.equals("4")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.girls);
        } else if (value.equals("5")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.lovely_bear);
        } else if (value.equals("6")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.loves);
        } else if (value.equals("7")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.night);
        } else if (value.equals("8")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.sunset);
        } else if (value.equals("9")) {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.unicorn);
        } else {
            activityLockBinding.llLockActivity.setBackgroundResource(R.color.beach);

        }
    }
}