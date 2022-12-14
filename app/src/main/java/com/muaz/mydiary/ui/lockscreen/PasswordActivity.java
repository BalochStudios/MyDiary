package com.muaz.mydiary.ui.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityPasswordBinding;
import com.muaz.mydiary.ui.others.FAQActivity;
import com.muaz.mydiary.ui.others.MainActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.takwolf.android.lock9.Lock9View;

import me.aflak.libraries.callback.FingerprintDialogCallback;
import me.aflak.libraries.dialog.DialogAnimation;
import me.aflak.libraries.dialog.FingerprintDialog;


public class PasswordActivity extends AppCompatActivity {
    ActivityPasswordBinding activityPasswordBinding;
    SharedPreferences sharedPreferences;
    public static String PACKAGE_NAME;
    SharedPreference sharedPreference;
    public String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
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

                    Intent i = new Intent(PasswordActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Correct Password", "correct_password", "");
    // password match
                } else {
                 activityPasswordBinding.textView.setText(getResources().getText(R.string.tryagain));
                    Toast.makeText(PasswordActivity.this, getResources().getText(R.string.tryagain), Toast.LENGTH_SHORT).show();
                    AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_CHECK_SCREEN, "Wrong Password", "wrong_password", "");
                }
            }
        });
      activityPasswordBinding.btnFingerPrint.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              FingerprintDialog.initialize(PasswordActivity.this)
                      .title(R.string.fingerprint_title)
                      .message(R.string.fingerprint_message)
                      .enterAnimation(DialogAnimation.Enter.RIGHT)
                      .exitAnimation(DialogAnimation.Exit.RIGHT)
                      .circleScanningColor(R.color.colorAccent)
                      .callback(new FingerprintDialogCallback() {
                          @Override
                          public void onAuthenticationSucceeded() {
                              Intent i = new Intent(PasswordActivity.this, MainActivity.class);
                              startActivity(i);

                          }

                          @Override
                          public void onAuthenticationCancel() {


                          }
                      })
                      .show();

          }
      });

      activityPasswordBinding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
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
    @Override
    protected void onResume() {
        super.onResume();
        setThemee();
        setNavigationThemee();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(PasswordActivity.this);
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
    public void setNavigationThemee() {
        value = sharedPreference.getCurrentTheme(PasswordActivity.this);
        if (value.equals("0")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.beach);
        } else if (value.equals("1")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.couple);
        } else if (value.equals("2")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.flower_bunny);
        } else if (value.equals("3")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.flowers);
        } else if (value.equals("4")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.girls);
        } else if (value.equals("5")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.lovely_bear);
        } else if (value.equals("6")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.loves);
        } else if (value.equals("7")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.night);
        } else if (value.equals("8")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.sunset);
        } else if (value.equals("9")) {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.unicorn);
        } else {
            activityPasswordBinding.llPasswordLayout.setBackgroundResource(R.color.beach);

        }
    }
}
