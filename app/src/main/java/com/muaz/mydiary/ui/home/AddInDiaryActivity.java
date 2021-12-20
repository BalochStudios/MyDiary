package com.muaz.mydiary.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityAddInDiaryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddInDiaryActivity extends AppCompatActivity {
    ActivityAddInDiaryBinding activityAddInDiaryBinding;
    int year;
    int month;
    int days;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAddInDiaryBinding = ActivityAddInDiaryBinding.inflate(getLayoutInflater());
        View view=activityAddInDiaryBinding.getRoot();
        setContentView(view);
        Calendar calendar=Calendar.getInstance();
        activityAddInDiaryBinding.rlDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                days = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddInDiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        activityAddInDiaryBinding.etYear.setText(i);
                        activityAddInDiaryBinding.etMonth.setText(i1);
                        activityAddInDiaryBinding.etDay.setText(i2);

                    }
                },year,month,days);
                datePickerDialog.show();
            }
        });
    }
}