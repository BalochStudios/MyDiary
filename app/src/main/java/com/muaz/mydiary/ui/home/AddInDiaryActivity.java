package com.muaz.mydiary.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityAddInDiaryBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddInDiaryActivity extends AppCompatActivity {
    ActivityAddInDiaryBinding activityAddInDiaryBinding;
    private int year, month, day;



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
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(AddInDiaryActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {



                    }
                },year,month,day);
                datePickerDialog.show();


               // Toast.makeText(AddInDiaryActivity.this, "Hellow", Toast.LENGTH_SHORT).show();
            }

        });
    }
}