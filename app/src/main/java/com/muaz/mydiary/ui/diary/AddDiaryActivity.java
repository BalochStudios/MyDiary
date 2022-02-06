package com.muaz.mydiary.ui.diary;

import static com.muaz.mydiary.utils.Constants.DEFAULT;
import static com.muaz.mydiary.utils.Constants.DEFAULT_SLIDER_VALUE;
import static com.muaz.mydiary.utils.Constants.FONT_SIZE;
import static com.muaz.mydiary.utils.Constants.SAVE_TYPE_DRAFT;
import static com.muaz.mydiary.utils.Constants.SAVE_TYPE_SAVED;
import static com.muaz.mydiary.utils.Constants.TAG_ADAPTER_DELETE_TYPE;
import static com.muaz.mydiary.utils.Constants.TAG_ADAPTER_TYPE;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_CENTER;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_LEFT;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_RIGHT;
import static com.muaz.mydiary.utils.UtilityFunctions.makeToast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.divyanshu.draw.activity.DrawingActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.muaz.mydiary.R;
import com.muaz.mydiary.adapter.BackgroundsAdapter;
import com.muaz.mydiary.adapter.ColorsAdapter;
import com.muaz.mydiary.adapter.DiaryCategoryAdapter;
import com.muaz.mydiary.adapter.FontsAdapter;
import com.muaz.mydiary.adapter.ImagesAdapter;
import com.muaz.mydiary.adapter.MoodAdapter;
import com.muaz.mydiary.adapter.TagsAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityAddInDiaryBinding;
import com.muaz.mydiary.models.Color;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.models.DiaryCategory;
import com.muaz.mydiary.models.Mood;
import com.muaz.mydiary.models.Tag;
import com.muaz.mydiary.ui.others.MainActivity;
import com.muaz.mydiary.ui.others.SharedPreference;
import com.muaz.mydiary.utils.Constants;
import com.muaz.mydiary.utils.DataSource;
import com.muaz.mydiary.utils.UtilityFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class AddDiaryActivity extends AppCompatActivity {

    ActivityAddInDiaryBinding binding;
    Calendar calendar;
    List<Bitmap> bitmaps = new ArrayList<>();
    List<Tag> thisDiaryTags = new ArrayList<>();
    List<Tag> allTags = new ArrayList<>();
    int saveType = SAVE_TYPE_DRAFT;
    int backgroundId = DEFAULT;
    int fontId = DEFAULT;
    int colorId = DEFAULT;
    int textSize = DEFAULT_SLIDER_VALUE;
    int textDirection = TEXT_DIRECTION_LEFT;

    private TagsAdapter thisDiaryTagsAdapter;
    private MoodAdapter moodAdapter;
    private DiaryCategoryAdapter diaryCategoryAdapter;

    DbHelper dbHelper;
    SharedPreference sharedPreference;
    public String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreference = new SharedPreference();
        setThemee();
        if (Build.VERSION.SDK_INT > 21) {
            getWindow().setStatusBarColor(android.graphics.Color.parseColor("#00000000"));
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        binding = ActivityAddInDiaryBinding.inflate(getLayoutInflater());
        setFullScreenFlags();
        setContentView(binding.getRoot());

        dbHelper = new DbHelper(this);
        setMoods();
        setCategories();
        setCalendar();
        setListeners();
        setAlignmentListeners();
        setBackgrounds();
        setColors();
        setSizeSlider();
        setFonts();
        setAllTags();
    }

    private void setFullScreenFlags() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setAllTags() {
        allTags = dbHelper.getAllTags();
        @SuppressLint("NotifyDataSetChanged")
        TagsAdapter tagsAdapter = new TagsAdapter(allTags, TAG_ADAPTER_TYPE, (adapterView, view, i, l) -> {
            if (thisDiaryTags.size() > 0) {
                for (Tag tag : thisDiaryTags) {
                    if (allTags.get(i).getTag().equals(tag.getTag())) {
                        makeToast(this, "Already Exists");
                    } else {
                        thisDiaryTags.add(allTags.get(i));
                        if (thisDiaryTagsAdapter == null) {
                            setThisDiaryTagsAdapter();
                        } else {
                            thisDiaryTagsAdapter.notifyDataSetChanged();
                        }
                    }
                }
            } else {
                thisDiaryTags.add(allTags.get(i));
                if (thisDiaryTagsAdapter == null) {
                    setThisDiaryTagsAdapter();
                } else {
                    thisDiaryTagsAdapter.notifyDataSetChanged();
                }
            }
        }, (adapterView, view, i, l) -> {
            //ignore this
        });
        binding.rvAlreadyTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvAlreadyTags.setAdapter(tagsAdapter);
    }


    @Override
    public void onBackPressed() {
        showProperExitDialog();
    }
    //Your entry is not saved yet. Do you want to save draft?
    private void showProperExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Your entry is not saved yet. Do you want to save draft?")
                .setPositiveButton(Html.fromHtml("<font color='#FF1AEA'>Save Draft</font>"), (dialogInterface, i) -> {
                    saveType = SAVE_TYPE_DRAFT;
                    saveDiary();
                })
                .setNegativeButton(Html.fromHtml("<font color='#FF0000'>Delete</font>"), (dialogInterface, i) -> finish())
                //.setNeutralButton("Cancel Dialog", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private void setFonts() {
        List<String> fonts = DataSource.getAllFonts(this);
        FontsAdapter fontsAdapter = new FontsAdapter(fonts, (adapterView, view, i, l) -> {
            fontId = i;
            Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/" + fonts.get(i));
            binding.etTitle.setTypeface(typeface);
            binding.etStory.setTypeface(typeface);
            binding.tvDate.setTypeface(typeface);
        });
        binding.rvFonts.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvFonts.setAdapter(fontsAdapter);
    }

    private void setSizeSlider() {
        binding.sizeSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

                textSize = (int) slider.getValue();
                binding.etStory.setTextSize(FONT_SIZE + slider.getValue());
                binding.etTitle.setTextSize(FONT_SIZE + slider.getValue());
                binding.tvDate.setTextSize(FONT_SIZE + slider.getValue());
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                binding.etStory.setTextSize(FONT_SIZE + slider.getValue());
                binding.etTitle.setTextSize(FONT_SIZE + slider.getValue());
                binding.tvDate.setTextSize(FONT_SIZE + slider.getValue());
            }
        });
    }

    private void setColors() {
        List<Color> colorsList = DataSource.getAllColors();
        ColorsAdapter colorsAdapter = new ColorsAdapter(colorsList, (adapterView, view, i, l) -> {
            colorId = i;
            binding.tvDate.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            binding.etTitle.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            binding.etTitle.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            binding.etStory.setTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
            binding.etStory.setHintTextColor(getResources().getColor(colorsList.get(i).getColorResId()));
        });
        binding.rvColors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvColors.setAdapter(colorsAdapter);
    }

    private void setAlignmentListeners() {
        binding.ivRightAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_RIGHT;
            binding.etTitle.setGravity(Gravity.END);
            binding.etStory.setGravity(Gravity.END);
            binding.tvDate.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
        });

        binding.ivLeftAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_LEFT;
            binding.etTitle.setGravity(Gravity.START);
            binding.etStory.setGravity(Gravity.START);
            binding.tvDate.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        });

        binding.ivCenterAlign.setOnClickListener(view -> {
            textDirection = TEXT_DIRECTION_CENTER;
            binding.etTitle.setGravity(Gravity.CENTER);
            binding.etStory.setGravity(Gravity.CENTER);
            binding.tvDate.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        });
    }

    private void setBackgrounds() {
        List<Mood> backgrounds = DataSource.getAllBitmaps();
        BackgroundsAdapter backgroundsAdapter = new BackgroundsAdapter(backgrounds, (adapterView, view, i, l) -> {
            backgroundId = i;
            binding.getRoot().setBackgroundResource(backgrounds.get(i).getMoodImage());
        });
        binding.rvBackgrounds.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvBackgrounds.setAdapter(backgroundsAdapter);
    }

    private void setCalendar() {
        calendar = Calendar.getInstance();
        binding.tvDate.setText(UtilityFunctions.getDateFormat().format(calendar.getTime()));
        DatePickerDialog.OnDateSetListener diaryDate = (view, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            binding.tvDate.setText(UtilityFunctions.getDateFormat().format(calendar.getTime()));
        };

        binding.tvDate.setOnClickListener(view -> new DatePickerDialog(this,
                diaryDate,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void setListeners() {
        binding.ivChooseImage.setOnClickListener(view -> {
            closeKeyboard();
            ImagePicker.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start();
        });
        binding.ivDraw.setOnClickListener(view -> {
            closeKeyboard();
            Intent intent = new Intent(this, DrawingActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE_DRAW);
        });
        binding.ivBackgrounds.setOnClickListener(view -> {
            closeKeyboard();
            binding.backgroundsLayout.setVisibility(View.VISIBLE);
            binding.footer.setVisibility(View.GONE);
        });
        binding.ivCancelBackgrounds.setOnClickListener(view -> {
            closeKeyboard();
            binding.backgroundsLayout.setVisibility(View.GONE);
            binding.footer.setVisibility(View.VISIBLE);
        });

        binding.ivTextStyles.setOnClickListener(view -> {
            closeKeyboard();
            binding.textStyleLayout.setVisibility(View.VISIBLE);
            binding.footer.setVisibility(View.GONE);
        });
        binding.ivCancelTextStyling.setOnClickListener(view -> {
            closeKeyboard();
            binding.textStyleLayout.setVisibility(View.GONE);
            binding.footer.setVisibility(View.VISIBLE);
        });

        binding.ivCancel.setOnClickListener(view -> showProperExitDialog());

        binding.btnSave.setOnClickListener(view -> {
            saveType = SAVE_TYPE_SAVED;
            saveDiary();
        });

        binding.ivTags.setOnClickListener(view -> {
            closeKeyboard();
            binding.tagsLayout.setVisibility(View.VISIBLE);
            binding.footer.setVisibility(View.GONE);
        });

        binding.ivCancelTags.setOnClickListener(view -> {
            closeKeyboard();
            binding.tagsLayout.setVisibility(View.GONE);
            binding.footer.setVisibility(View.VISIBLE);
        });

        binding.ivSend.setOnClickListener(view -> {
            closeKeyboard();
            String tag = Objects.requireNonNull(binding.etTag.getText()).toString().replace(" ", "");
            if (thisDiaryTags.size() > 0) {
                for (Tag tag1 : thisDiaryTags) {
                    if (tag1.getTag().equals(tag)) {
                        makeToast(this, "Already exists");
                        binding.etTag.setText("");
                    } else {
                        thisDiaryTags.add(new Tag(tag));
                        binding.etTag.setText("");
                        setThisDiaryTagsAdapter();
                    }
                }
            } else {
                thisDiaryTags.add(new Tag(tag));
                binding.etTag.setText("");
                setThisDiaryTagsAdapter();
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setThisDiaryTagsAdapter() {
        thisDiaryTagsAdapter = new TagsAdapter(thisDiaryTags, TAG_ADAPTER_DELETE_TYPE, (adapterView, view1, i, l) -> {

        }, (adapterView, view1, i, l) -> {
            //removeTag
            thisDiaryTags.remove(thisDiaryTags.get(i));
            thisDiaryTagsAdapter.notifyDataSetChanged();
        });
        binding.rvTags.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        binding.rvTags.setAdapter(thisDiaryTagsAdapter);
    }

    private void saveDiary() {
        Diary diary = new Diary(
                binding.tvDate.getText().toString(),
                binding.etTitle.getText().toString(),
                binding.etStory.getText().toString(),
                moodAdapter.getSelectedPosition(),
                diaryCategoryAdapter.getSelectedPosition(),
                backgroundId,
                bitmaps,
                fontId,
                textSize,
                textDirection,
                colorId,
                thisDiaryTags,
                saveType
        );
        long result = dbHelper.addToDiary(diary);
        if (result != -1) {
            makeToast(AddDiaryActivity.this, "Saved Successfully");
            finish();
        } else {
            makeToast(AddDiaryActivity.this, "Something went wrong");
        }
    }

    private void setMoods() {
        List<Mood> moodArrayList = DataSource.getAllMoods();
        moodAdapter = new MoodAdapter(moodArrayList);
        binding.rvMoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvMoods.setAdapter(moodAdapter);
    }

    private void setCategories() {
        List<DiaryCategory> diaryCategories = DataSource.getAllCategories();
        diaryCategoryAdapter = new DiaryCategoryAdapter(diaryCategories);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategory.setAdapter(diaryCategoryAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        binding.rvImages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        if (resultCode == Activity.RESULT_OK) {
            assert data != null;
            if (requestCode == Constants.REQUEST_CODE_DRAW) {
                byte[] bytes = data.getByteArrayExtra("bitmap");
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                bitmaps.add(bitmap);
            } else {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    bitmaps.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ImagesAdapter imagesAdapter = new ImagesAdapter(bitmaps, (adapterView, view, i, l) -> {
                Intent intent = new Intent(AddDiaryActivity.this, ImagesDisplayActivity.class);
                intent.putExtra(Constants.INTENT_BITMAP_KEY, new Gson().toJson(bitmaps));
                intent.putExtra(Constants.INTENT_BITMAP_POSITION_KEY, i);
                startActivity(intent);
            }, Constants.IMAGES_ADAPTER_TYPE);
            binding.rvImages.setAdapter(imagesAdapter);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
    }

    private void showCustomUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    public void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setThemee();


    }
    public void setThemee() {
        value = sharedPreference.getCurrentTheme(AddDiaryActivity.this);
        if (value.equals("0")) {
            setTheme(R.style.AppTheme0);
        } else if (value.equals("1")) {
            setTheme(R.style.AppTheme1);
        } else if (value.equals("2")) {
            setTheme(R.style.AppTheme2);
        } else if (value.equals("3")) {
            setTheme(R.style.AppTheme3);
        } else if (value.equals("4")) {
            setTheme(R.style.AppTheme4);
        } else if (value.equals("5")) {
            setTheme(R.style.AppTheme5);
        } else if (value.equals("6")) {
            setTheme(R.style.AppTheme6);
        } else if (value.equals("7")) {
            setTheme(R.style.AppTheme7);
        } else if (value.equals("8")) {
            setTheme(R.style.AppTheme8);
        } else if (value.equals("9")) {
            setTheme(R.style.AppTheme9);
        } else {
            setTheme(R.style.AppTheme0);
        }
    }
}