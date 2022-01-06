package com.muaz.mydiary.ui.lockscreen;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityPasswordSetBinding;
import com.muaz.mydiary.ui.others.FAQActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.takwolf.android.lock9.Lock9View;


public class PasswordSetActivity extends AppCompatActivity {
    boolean isEnteringFirstTime = true;
    boolean isEnteringSecondTime = false;
    String enteredPassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean instancePwdActivty = false;
    ActivityPasswordSetBinding activityPasswordSetBinding;
    SharedPreference sharedPreference;
    public String value;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        setThemee();
        activityPasswordSetBinding = ActivityPasswordSetBinding.inflate(getLayoutInflater());
        View view = activityPasswordSetBinding.getRoot();
        setContentView(view);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        activityPasswordSetBinding.tvTextPassword.setText(getResources().getText(R.string.drawpattern));
        activityPasswordSetBinding.SetLock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {

                if (isEnteringFirstTime) {

                    enteredPassword = password;
                    isEnteringFirstTime = false;
                    isEnteringSecondTime = true;
                    activityPasswordSetBinding.tvTextPassword.setText(getResources().getText(R.string.redrawpattern));

                } else if (isEnteringSecondTime) {

                    if (enteredPassword.matches(password)) {

                        boolean first_time_app_install = sharedPreferences.getBoolean(AppLockConstants.FIRST_TIME_APP_INSTALL, false);

                        if (first_time_app_install) {

                            if (sharedPreferences.getBoolean("dnt_show_passwrd_recovery", true)) {

                                Intent i = new Intent(PasswordSetActivity.this, PasswordActivity.class);
                                startActivity(i);
                                editor.putString(AppLockConstants.PASSWORD, enteredPassword);
                                editor.commit();
                                finish();
                            }

                            editor.putString(AppLockConstants.PASSWORD, enteredPassword);
                            editor.commit();

                            Intent i = new Intent(PasswordSetActivity.this, PasswordRecoverSetActivity.class);
                            startActivity(i);
                            finish();
                            sharedPreferences.edit().remove(AppLockConstants.FIRST_TIME_APP_INSTALL).commit();

                        } else {

                            Intent i = new Intent(PasswordSetActivity.this, PasswordActivity.class);
                            startActivity(i);
                            editor.putString(AppLockConstants.PASSWORD, enteredPassword);
                            editor.commit();
                            finish();
                            AppLockLogEvents.logEvents(AppLockConstants.FIRST_TIME_PASSWORD_SET_SCREEN, "Confirm Password", "confirm_password", "");
                        }
                    } else {

                        activityPasswordSetBinding.tvTextPassword.setText(getResources().getText(R.string.drawpattern));
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.wrongpatter), Toast.LENGTH_SHORT).show();
                        isEnteringFirstTime = true;
                        isEnteringSecondTime = false;

                    }
                }
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
    protected void onRestart() {
        Intent i = new Intent(this, PasswordActivity.class);
        startActivity(i);

        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setThemee();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(PasswordSetActivity.this);
        if (value.equals("0")) {
            setTheme(R.style.AppTheme0);
        } else if (value.equals("1")) {
            setTheme(R.style.AppTheme1);
        } else if (value.equals("2")) {
            setTheme(R.style.AppTheme2);
        } else if (value.equals("3")) {
            setTheme(R.style.AppTheme3);
        }else if (value.equals("4")) {
            setTheme(R.style.AppTheme4);
        }else if (value.equals("5")) {
            setTheme(R.style.AppTheme5);
        }else if (value.equals("6")) {
            setTheme(R.style.AppTheme6);
        }else if (value.equals("7")) {
            setTheme(R.style.AppTheme7);
        }else if (value.equals("8")) {
            setTheme(R.style.AppTheme8);
        }else if (value.equals("9")) {
            setTheme(R.style.AppTheme9);
        } else{
            setTheme(R.style.AppTheme0);
        }
    }
}
