package com.muaz.mydiary.ui.others;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.muaz.mydiary.R;
import com.muaz.mydiary.databinding.ActivityFaqactivityBinding;

public class FAQActivity extends AppCompatActivity {
    private ActivityFaqactivityBinding faqactivityBinding;
      private int clickcount=0;
    SharedPreference sharedPreference;
    public String value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference=new SharedPreference();
        setThemee();
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
        faqactivityBinding.rlQ1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns1.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns1.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns2.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ2.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns2.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns3.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ3.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns3.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns4.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ4.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns4.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ5.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns5.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ5.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns5.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ6.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns6.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ6.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns6.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });

        faqactivityBinding.rlQ7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ7.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns7.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ7.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns7.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ8.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns8.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ8.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns8.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ9.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns9.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ9.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns9.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });
        faqactivityBinding.rlQ10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickcount=clickcount+1;
                if(clickcount==1)
                {
                    faqactivityBinding.ibQ10.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    faqactivityBinding.tvAns10.setVisibility(View.VISIBLE);
                }
                else
                {
                    faqactivityBinding.ibQ10.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    faqactivityBinding.tvAns10.setVisibility(View.GONE);
                    clickcount=0;
                }
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        setThemee();


    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(FAQActivity.this);
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