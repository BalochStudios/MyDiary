package com.muaz.mydiary.ui.others;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivitySettingsBinding;
import com.muaz.mydiary.ui.lockscreen.LockActivity;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding activitySettingsBinding;
   SharedPreference sharedPreference;
    public String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        //setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        activitySettingsBinding = ActivitySettingsBinding.inflate(getLayoutInflater());
        View view=activitySettingsBinding.getRoot();
        setContentView(view);
        setTitle("Lock");
        setSupportActionBar(activitySettingsBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activitySettingsBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
      activitySettingsBinding.rlTags.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(SettingsActivity.this,TagActivity.class));
          }
      });

      activitySettingsBinding.rlDiaryLock.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(SettingsActivity.this, LockActivity.class));
          }
      });
  

        activitySettingsBinding.rlRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        Toast.makeText(SettingsActivity.this, "unable to  open this link", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        activitySettingsBinding.rlFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent feedbackEmail = new Intent(Intent.ACTION_SEND);

                feedbackEmail.setType("text/email");
                feedbackEmail.putExtra(Intent.EXTRA_EMAIL, new String[] {"shakirfarooq.baloch@gmail.com"});
                feedbackEmail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                startActivity(Intent.createChooser(feedbackEmail, "Send Feedback:"));
                
            }
        });
        activitySettingsBinding.rlPrivacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String storePirvacylink = "http://shakirfarooq.androidstudent.net/wallpaper/privacy.html";
                Intent privacyIntent = new Intent(SettingsActivity.this, WebActivity.class);
                privacyIntent.putExtra("URL", storePirvacylink);
                startActivity(privacyIntent);

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        setNavigationThemee();
       // setThemee();


    }

/*    public void setThemee() {
        value = sharedPreference.getCurrentTheme(SettingsActivity.this);
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
    }*/
    public void setNavigationThemee() {
        value = sharedPreference.getCurrentTheme(SettingsActivity.this);
        if (value.equals("0")) {
           activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.beach);

        } else if (value.equals("1")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.couple);
        } else if (value.equals("2")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.flower_bunny);
        } else if (value.equals("3")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.flowers);
        } else if (value.equals("4")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.girls);
        } else if (value.equals("5")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.lovely_bear);
        } else if (value.equals("6")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.loves);
        } else if (value.equals("7")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.night);
        } else if (value.equals("8")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.sunset);
        } else if (value.equals("9")) {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.unicorn);
        } else {
            activitySettingsBinding.llSettingLayout.setBackgroundResource(R.color.beach);

        }
    }

}