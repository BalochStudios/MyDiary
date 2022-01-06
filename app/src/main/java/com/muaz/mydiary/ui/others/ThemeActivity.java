package com.muaz.mydiary.ui.others;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.databinding.ActivityThemeBinding;

public class ThemeActivity extends AppCompatActivity {
     ActivityThemeBinding activityThemeBinding;
     Context context=ThemeActivity.this;
     SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityThemeBinding = ActivityThemeBinding.inflate(getLayoutInflater());
        View view=activityThemeBinding.getRoot();
        setContentView(view);
        ImageAdapterClass adapterView = new ImageAdapterClass(this);
       activityThemeBinding.viewPage.setAdapter(adapterView);

        sharedPreference=new SharedPreference();
       activityThemeBinding.btnUseTheme.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

            int position=   activityThemeBinding.viewPage.getCurrentItem();
               Toast.makeText(ThemeActivity.this, "position"+position, Toast.LENGTH_SHORT).show();
               sharedPreference.setCurrentTheme(ThemeActivity.this, String.valueOf(position));
               finish();

           }
       });

    }

}