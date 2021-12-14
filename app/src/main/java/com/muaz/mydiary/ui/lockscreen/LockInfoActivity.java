package com.muaz.mydiary.ui.lockscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityLockBinding;
import com.muaz.mydiary.databinding.ActivityLockInfoBinding;

import java.util.ArrayList;
import java.util.List;

public class LockInfoActivity extends AppCompatActivity {
    ActivityLockInfoBinding activityLockInfoBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLockInfoBinding = ActivityLockInfoBinding.inflate(getLayoutInflater());
        View view=activityLockInfoBinding.getRoot();
        setContentView(view);
        
    }
}