package com.muaz.mydiary.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.adapter.DiaryAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.FragmentHomeBinding;
import com.muaz.mydiary.ui.diary.AddDiaryActivity;
import com.muaz.mydiary.ui.diary.DiaryDisplayActivity;
import com.muaz.mydiary.ui.lockscreen.LockActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.muaz.mydiary.utils.Constants;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    SharedPreference sharedPreference;
    public String value;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sharedPreference=new SharedPreference();
          setThemee();


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setThemee();
        setDiaryRV();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivAddNewDiary.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddDiaryActivity.class)));
        setDiaryRV();
    }

    private void setDiaryRV() {
        DbHelper dbHelper = new DbHelper(getContext());
        DiaryAdapter diaryAdapter = new DiaryAdapter(dbHelper.getAllDiaries(), (adapterView, view, i, l) -> {
            Intent intent = new Intent(getContext(), DiaryDisplayActivity.class);
            intent.putExtra(Constants.INTENT_SELECTED_DIARY_POSITION, i);
            startActivity(intent);
        });
        binding.rvDiary.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.rvDiary.setAdapter(diaryAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setThemee() {
        value = sharedPreference.getCurrentTheme(getContext());
        if (value.equals("0")) {
           getContext().setTheme(R.style.AppTheme0);
        } else if (value.equals("1")) {
           getContext().setTheme(R.style.AppTheme1);
        } else if (value.equals("2")) {
           getContext().setTheme(R.style.AppTheme2);
        } else if (value.equals("3")) {
            getContext().setTheme(R.style.AppTheme3);
        }else if (value.equals("4")) {
           getContext().setTheme(R.style.AppTheme4);
        }else if (value.equals("5")) {
          getContext(). setTheme(R.style.AppTheme5);
        }else if (value.equals("6")) {
           getContext().setTheme(R.style.AppTheme6);
        }else if (value.equals("7")) {
          getContext().setTheme(R.style.AppTheme7);
        }else if (value.equals("8")) {
           getContext().setTheme(R.style.AppTheme8);
        }else if (value.equals("9")) {
           getContext().setTheme(R.style.AppTheme9);
        } else{
           getContext().setTheme(R.style.AppTheme0);
        }
    }
}