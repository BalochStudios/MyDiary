package com.muaz.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.muaz.mydiary.databinding.ActivityFaqactivityBinding;

public class FAQActivity extends AppCompatActivity {
    private ActivityFaqactivityBinding faqactivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        faqactivityBinding = ActivityFaqactivityBinding.inflate(getLayoutInflater());
        View view=faqactivityBinding.getRoot();
        setContentView(view);

        setTitle("FAQ");
        setSupportActionBar(faqactivityBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        faqactivityBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}