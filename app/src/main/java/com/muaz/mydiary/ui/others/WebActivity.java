package com.muaz.mydiary.ui.others;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityWebBinding;

public class WebActivity extends AppCompatActivity {
    ActivityWebBinding activityWebBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWebBinding = ActivityWebBinding.inflate(getLayoutInflater());
        View view=activityWebBinding.getRoot();
        setContentView(view);
        setTitle("Lock");
        setSupportActionBar(activityWebBinding.toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityWebBinding.toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       // webView.getSettings().setJavaScriptEnabled(true);
      activityWebBinding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
             activityWebBinding.progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
             activityWebBinding.progressBar.setVisibility(View.GONE);
            }
        });
        String url=getIntent().getStringExtra("URL");
       activityWebBinding.webView.loadUrl(url);
    }
}