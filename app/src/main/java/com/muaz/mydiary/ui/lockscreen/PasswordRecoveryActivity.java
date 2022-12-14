package com.muaz.mydiary.ui.lockscreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityRecoveryPasswordBinding;
import com.muaz.mydiary.ui.others.SharedPreference;

import java.util.ArrayList;
import java.util.List;

import info.hoang8f.widget.FButton;


public class PasswordRecoveryActivity extends AppCompatActivity {
    ActivityRecoveryPasswordBinding activityRecoveryPasswordBinding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int questionNumber = 0;
    SharedPreference sharedPreference;
    public String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        setThemee();
        activityRecoveryPasswordBinding = ActivityRecoveryPasswordBinding.inflate(getLayoutInflater());
        View view=activityRecoveryPasswordBinding.getRoot();
        setContentView(view);
        sharedPreferences = getSharedPreferences(AppLockConstants.MyPREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        List<String> list = new ArrayList<String>();
        list.add((String) getResources().getText(R.string.selectanswer));
        list.add((String) getResources().getText(R.string.one));
        list.add((String) getResources().getText(R.string.two));
        list.add((String) getResources().getText(R.string.three));
        list.add((String) getResources().getText(R.string.four));
        list.add((String) getResources().getText(R.string.five));
        list.add((String) getResources().getText(R.string.six));

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      activityRecoveryPasswordBinding.questionssSpinner.setAdapter(stringArrayAdapter);

      activityRecoveryPasswordBinding.questionssSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      activityRecoveryPasswordBinding.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber != 0 && ! activityRecoveryPasswordBinding.answer.getText().toString().isEmpty()) {
                    if (sharedPreferences.getInt(AppLockConstants.QUESTION_NUMBER, 0) == questionNumber && sharedPreferences.getString(AppLockConstants.ANSWER, "").matches(activityRecoveryPasswordBinding.answer.getText().toString())) {
                        editor.putBoolean(AppLockConstants.IS_PASSWORD_SET, false);
                        editor.commit();
                        editor.putString(AppLockConstants.PASSWORD, "");
                        editor.commit();
                        Intent i = new Intent(PasswordRecoveryActivity.this, PasswordSetActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getText(R.string.wrongpatter), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.pleaseselectanswer), Toast.LENGTH_SHORT).show();
                }
                AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_RECOVER_SET_SCREEN, "Recover", "recover", "");

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        Intent i = new Intent(this, LockActivity.class);
        startActivity(i);
        super.onRestart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        setThemee();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(PasswordRecoveryActivity.this);
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


}
