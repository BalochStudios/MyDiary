package com.muaz.mydiary.ui.lockscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityRecoverSetPasswordBinding;

import java.util.ArrayList;
import java.util.List;


public class PasswordRecoverSetActivity extends AppCompatActivity {
    ActivityRecoverSetPasswordBinding activityRecoverSetPasswordBinding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int questionNumber = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityRecoverSetPasswordBinding = ActivityRecoverSetPasswordBinding.inflate(getLayoutInflater());
        View view=activityRecoverSetPasswordBinding.getRoot();
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

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(PasswordRecoverSetActivity.this, android.R.layout.simple_spinner_item, list);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       activityRecoverSetPasswordBinding.questionsSpinner.setAdapter(stringArrayAdapter);

        activityRecoverSetPasswordBinding.questionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                questionNumber = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        activityRecoverSetPasswordBinding.confirmButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (questionNumber != 0 && !activityRecoverSetPasswordBinding.answer.getText().toString().isEmpty()) {
                    editor.putBoolean(AppLockConstants.IS_PASSWORD_SET, true);
                    editor.commit();
                    editor.putString(AppLockConstants.ANSWER,activityRecoverSetPasswordBinding.answer.getText().toString());
                    editor.commit();
                    editor.putInt(AppLockConstants.QUESTION_NUMBER, questionNumber);
                    editor.commit();

                    System.out.println("ques..."+ sharedPreferences.getInt(AppLockConstants.QUESTION_NUMBER, 0));
                    System.out.println("ans..."+ sharedPreferences.getString(AppLockConstants.ANSWER, ""));



                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (!Settings.canDrawOverlays(PasswordRecoverSetActivity.this)) {


                            AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_RECOVER_SET_SCREEN, "Confirm", "confirm", "");

                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + getPackageName()));
                            startActivity(intent);

                        }else{
                            Intent i = new Intent(PasswordRecoverSetActivity.this, LockActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }else{

                        Intent i = new Intent(PasswordRecoverSetActivity.this, LockActivity.class);
                        startActivity(i);
                        finish();

                    }


                } else {
                    Toast.makeText(getApplicationContext(), getResources().getText(R.string.pleaseselectanswer), Toast.LENGTH_SHORT).show();
                }
                AppLockLogEvents.logEvents(AppLockConstants.PASSWORD_RECOVER_SET_SCREEN, (String) getResources().getText(R.string.pleaseselectanswer), "confirm", "");

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent i = new Intent(PasswordRecoverSetActivity.this, PasswordActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(PasswordRecoverSetActivity.this, PasswordSetActivity.class);
            startActivity(i);
            finish();
        }

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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Intent i = new Intent(PasswordRecoverSetActivity.this, PasswordActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(PasswordRecoverSetActivity.this, PasswordSetActivity.class);
            startActivity(i);
            finish();
        }

        super.onRestart();
    }
}
