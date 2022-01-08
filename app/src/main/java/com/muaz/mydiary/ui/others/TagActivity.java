package com.muaz.mydiary.ui.others;

import static com.muaz.mydiary.utils.Constants.TAG_ADAPTER_TYPE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.adapter.TagsAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityTagBinding;
import com.muaz.mydiary.models.Tag;
import com.muaz.mydiary.ui.diary.DiaryDisplayActivity;
import com.muaz.mydiary.ui.lockscreen.LockActivity;
import com.muaz.mydiary.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    ActivityTagBinding activityTagBinding;
    SharedPreference sharedPreference;
    public String value;
    List<Tag> allTags = new ArrayList<>();
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        activityTagBinding = ActivityTagBinding.inflate(getLayoutInflater());
        View view=activityTagBinding.getRoot();
        setContentView(view);
        setTitle("Tag");
        setSupportActionBar(activityTagBinding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activityTagBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dbHelper=new DbHelper(TagActivity.this);
        setAllTags();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setThemee();
        setAllTags();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(TagActivity.this);
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
    private void setAllTags() {
        allTags = dbHelper.getAllTags();
        @SuppressLint("NotifyDataSetChanged")
       TagsAdapterClass tagsAdapterClass=new TagsAdapterClass(allTags, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(TagActivity.this, DiaryDisplayActivity.class);
                intent.putExtra(Constants.INTENT_SELECTED_DIARY_POSITION, i);
                startActivity(intent);
            }
        });
       activityTagBinding.rvAlreadyTags.setLayoutManager(new LinearLayoutManager(this));
       activityTagBinding.rvAlreadyTags.setAdapter(tagsAdapterClass);
    }
}