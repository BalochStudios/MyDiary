package com.muaz.mydiary.ui.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivitySplashBinding;
import com.muaz.mydiary.ui.lockscreen.LockActivity;
import com.muaz.mydiary.ui.lockscreen.PasswordActivity;

public class SplashActivity extends AppCompatActivity {
    SharedPreference sharedPreference;
    String value;
    ActivitySplashBinding activitySplashBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FFFFFF"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        View view=activitySplashBinding.getRoot();
        setContentView(view);
        sharedPreference=new SharedPreference();
        value = sharedPreference.getCurrentUser(SplashActivity.this);
        if (value.equals("true")) {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, PasswordActivity.class));


                }
            },3000);

        } else{
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(SplashActivity.this, MainActivity.class));


                }
            },3000);

        }

    }
}