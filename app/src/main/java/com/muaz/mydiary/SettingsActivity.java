package com.muaz.mydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.muaz.mydiary.databinding.ActivitySettingsBinding;
import com.muaz.mydiary.ui.lockscreen.LockActivity;

public class SettingsActivity extends AppCompatActivity {
    ActivitySettingsBinding activitySettingsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

      activitySettingsBinding.rlModeStyle.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Mode", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlStickerMall.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Sticker", Toast.LENGTH_SHORT).show();

          }
      });
      activitySettingsBinding.rlTags.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Tag", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlNotification.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Notification", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlDiaryLock.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              startActivity(new Intent(SettingsActivity.this, LockActivity.class));
          }
      });
      activitySettingsBinding.rlDayOfWeek.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Day Of Week", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlDateFormat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Date format", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlTimeFormat.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Time Format", Toast.LENGTH_SHORT).show();
          }
      });
      activitySettingsBinding.rlSkipMode.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Toast.makeText(SettingsActivity.this, "Skip Mode", Toast.LENGTH_SHORT).show();
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
        activitySettingsBinding.rlLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Language section", Toast.LENGTH_SHORT).show();
            }
        });
    }

}