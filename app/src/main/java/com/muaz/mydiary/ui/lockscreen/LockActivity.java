package com.muaz.mydiary.ui.lockscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityLockBinding;

import java.util.zip.Inflater;

public class LockActivity extends AppCompatActivity {
    ActivityLockBinding activityLockBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        activityLockBinding.swDiaryLock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (activityLockBinding.swDiaryLock.isChecked() == true) {
                    activityLockBinding.llSetPasscode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          startActivity(new Intent(LockActivity.this,PasswordSetActivity.class));

                        }
                    });
                    activityLockBinding.llSetQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(LockActivity.this,PasswordRecoverSetActivity.class));
                        }
                    });

                    Toast.makeText(LockActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (activityLockBinding.swDiaryLock.isChecked() == false) {
                        Toast.makeText(LockActivity.this, "UnChecked", Toast.LENGTH_SHORT).show();
                    }
                }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu_lock,menu);
        return super.onCreateOptionsMenu(menu);
    }
}