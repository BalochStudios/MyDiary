package com.muaz.mydiary.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.muaz.mydiary.R;
import com.muaz.mydiary.adapter.DiaryAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.FragmentHomeBinding;
import com.muaz.mydiary.ui.diary.AddDiaryActivity;
import com.muaz.mydiary.ui.diary.DiaryDisplayActivity;
import com.muaz.mydiary.ui.others.MainActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.muaz.mydiary.utils.Constants;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    SharedPreference sharedPreference;
    public String value;
    DiaryAdapter diaryAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        sharedPreference = new SharedPreference();
        setThemee();
        //yahan wo logic likh k dekho
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreference = new SharedPreference();
        setThemee();
        setDiaryRV();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sharedPreference = new SharedPreference();
        setThemee();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivAddNewDiary.setOnClickListener(view1 -> startActivity(new Intent(getContext(), AddDiaryActivity.class)));
        setHasOptionsMenu(true);
        setDiaryRV();
    }

    private void setDiaryRV() {
        DbHelper dbHelper = new DbHelper(getContext());
         diaryAdapter = new DiaryAdapter(dbHelper.getAllDiaries(), (adapterView, view, i, l) -> {
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
        } else if (value.equals("4")) {
            getContext().setTheme(R.style.AppTheme3);
        } else if (value.equals("5")) {
            getContext().setTheme(R.style.AppTheme3);
        } else if (value.equals("6")) {
            getContext().setTheme(R.style.AppTheme3);
        } else if (value.equals("7")) {
            getContext().setTheme(R.style.AppTheme3);
        } else if (value.equals("8")) {
            getContext().setTheme(R.style.AppTheme3);
        } else if (value.equals("9")) {
            getContext().setTheme(R.style.AppTheme3);
        } else {
        }
        getContext().setTheme(R.style.AppTheme3);
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                diaryAdapter.filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}