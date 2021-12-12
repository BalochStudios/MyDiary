package com.muaz.mydiary.ui.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityPasswordBinding;
import com.takwolf.android.lock9.Lock9View;

import info.hoang8f.widget.FButton;


public class PasswordActivity extends AppCompatActivity {
    ActivityPasswordBinding activityPasswordBinding;
    SharedPreferences sharedPreferences;
    public static String PACKAGE_NAME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPasswordBinding = ActivityPasswordBinding.inflate(getLayoutInflater());
        View view=activityPasswordBinding.getRoot();
        setContentView(view);

        final Intent i =getIntent();

        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        final boolean  val = sharedPreferences.getBoolean(AppLockConstants.UNINSTALL_APP,false);
        sharedPreferences.getBoolean(AppLockConstants.IS_PASSWORD_SET,true);



      activityPasswordBinding.lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {

                if (sharedPreferences.getString(AppLockConstants.PASSWORD, "").matches(password)) {

                    Intent i = new Intent(PasswordActivity.this, LockActivity.class);
                    startActivity(i);
                    finish();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Correct Password", "correct_password", "");
    // password match
                } else {
                 activityPasswordBinding.textView.setText(getResources().getText(R.string.tryagain));
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Wrong Password", "wrong_password", "");
                }
            }
        });

      activityPasswordBinding.forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PasswordActivity.this, PasswordRecoveryActivity.class);
                startActivity(i);
                AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Forget Password", "forget_password", "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onStop() {

        super.onStop();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Intent i = new Intent(this, PasswordActivity.class);
        startActivity(i);

        super.onRestart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
