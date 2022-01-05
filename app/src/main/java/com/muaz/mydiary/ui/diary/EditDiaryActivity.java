package com.muaz.mydiary.ui.diary;

import static com.muaz.mydiary.utils.Constants.DEFAULT;
import static com.muaz.mydiary.utils.Constants.FONT_SIZE;
import static com.muaz.mydiary.utils.Constants.SAVE_TYPE_DRAFT;
import static com.muaz.mydiary.utils.Constants.SAVE_TYPE_SAVED;
import static com.muaz.mydiary.utils.Constants.TAG_ADAPTER_DELETE_TYPE;
import static com.muaz.mydiary.utils.Constants.TAG_ADAPTER_TYPE;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_CENTER;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_LEFT;
import static com.muaz.mydiary.utils.Constants.TEXT_DIRECTION_RIGHT;
import static com.muaz.mydiary.utils.UtilityFunctions.makeToast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.divyanshu.draw.activity.DrawingActivity;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.slider.Slider;
import com.google.gson.Gson;
import com.muaz.mydiary.adapter.BackgroundsAdapter;
import com.muaz.mydiary.adapter.ColorsAdapter;
import com.muaz.mydiary.adapter.DiaryCategoryAdapter;
import com.muaz.mydiary.adapter.FontsAdapter;
import com.muaz.mydiary.adapter.ImagesAdapter;
import com.muaz.mydiary.adapter.MoodAdapter;
import com.muaz.mydiary.adapter.TagsAdapter;
import com.muaz.mydiary.database.DbHelper;
import com.muaz.mydiary.databinding.ActivityEditDiaryBinding;
import com.muaz.mydiary.models.Color;
import com.muaz.mydiary.models.Diary;
import com.muaz.mydiary.models.DiaryCategory;
import com.muaz.mydiary.models.Mood;
import com.muaz.mydiary.models.Tag;
import com.muaz.mydiary.utils.Constants;
import com.muaz.mydiary.utils.DataSource;
import com.muaz.mydiary.utils.UtilityFunctions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class EditDiaryActivity extends AppCompatActivity {

    ActivityEditDiaryBinding binding;
    Diary selectedDiary;

    Calendar calendar;
    List<Bitmap> bitmaps = new ArrayList<>();
    List<Tag> thisDiaryTags = new ArrayList<>();
    List<Tag> allTags = new ArrayList<>();
    int saveType = SAVE_TYPE_DRAFT;
    int backgroundId = DEFAULT;
    int fontId = DEFAULT;
    int colorId = DEFAULT;
    int textDirection = TEXT_DIRECTION_LEFT;

    private TagsAdapter thisDiaryTagsAdapter;
    private MoodAdapter moodAdapter;
    private DiaryCategoryAdapter diaryCategoryAdapter;

    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDiaryBinding.inflate(getLayoutInflater());
        setFullScreenFlags();
        setContentView(binding.getRoot());

        initUtils();
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

    private void initUtils() {
        dbHelper = new DbHelper(this);
        selectedDiary = new Gson().fromJson(getIntent().getStringExtra(Constants.INTENT_SELECTED_DIARY),
                Diary.class);

        binding.tvDate.setText(selectedDiary.getDate());
        binding.etTitle.setText(selectedDiary.getTitle());
        binding.etStory.setText(selectedDiary.getDescription());

    }

    private void setFullScreenFlags() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void setAllTags() {
        allTags = dbHelper.getAllTags();
        @SuppressLint("NotifyDataSetChanged")
        TagsAdapter tagsAdapter = new TagsAdapter(allTags, TAG_ADAPTER_TYPE, (adapterView, view, i, l) -> {
            thisDiaryTags.add(allTags.get(i));
            if (thisDiaryTagsAdapter == null) {
                setThisDiaryTagsAdapter();
            } else {
                thisDiaryTagsAdapter.notifyDataSetChanged();
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

    private void showProperExitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Do you want to close your feelings?")
                .setPositiveButton("Save Draft", (dialogInterface, i) -> {
                    saveType = SAVE_TYPE_DRAFT;
                    saveDiary();
                })
                .setNegativeButton("Delete", (dialogInterface, i) -> finish())
                .setNeutralButton("Cancel Dialog", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    private void setFonts() {
        List<String> fonts = DataSource.getAllFonts(this);
        if (selectedDiary.getFontId() != DEFAULT) {
            Typeface typeface1 = Typeface.createFromAsset(getResources().getAssets(), "fonts/" + fonts.get(selectedDiary.getFontId()));
            binding.etTitle.setTypeface(typeface1);
            binding.etStory.setTypeface(typeface1);
            binding.tvDate.setTypeface(typeface1);
        }
        FontsAdapter fontsAdapter = new FontsAdapter(fonts, (adapterView, view, i, l) -> {
            fontId = i;
            Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), "fonts/" + fonts.get(i));
            binding.etTitle.setTypeface(typeface);
            binding.etStory.setTypeface(typeface);
            binding.tvDate.setTypeface(typeface);
        });
        binding.rvFonts.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvFonts.setAdapter(fontsAdapter);
    }

    private void setSizeSlider() {
        binding.etStory.setTextSize(selectedDiary.getTextSize());
        binding.etTitle.setTextSize(selectedDiary.getTextSize());
        binding.tvDate.setTextSize(selectedDiary.getTextSize());
        binding.sizeSlider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {
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
        if (selectedDiary.getTextColorId() != DEFAULT) {
            int color = getResources().getColor(colorsList.get(selectedDiary.getTextColorId()).getColorResId());
            binding.tvDate.setTextColor(color);
            binding.etTitle.setTextColor(color);
            binding.etTitle.setHintTextColor(color);
            binding.etStory.setTextColor(color);
            binding.etStory.setHintTextColor(color);
        }
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

        if (selectedDiary.getTextDirection() == TEXT_DIRECTION_RIGHT) {
            binding.etTitle.setGravity(Gravity.END);
            binding.etStory.setGravity(Gravity.END);
        } else if (selectedDiary.getTextDirection() == TEXT_DIRECTION_LEFT) {
            binding.etTitle.setGravity(Gravity.START);
            binding.etStory.setGravity(Gravity.START);
        } else {
            binding.etTitle.setGravity(Gravity.CENTER);
            binding.etStory.setGravity(Gravity.CENTER);
        }

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
            thisDiaryTags.add(new Tag(tag));
            binding.etTag.setText("");
            setThisDiaryTagsAdapter();
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
                (int) binding.tvDate.getTextSize(),
                textDirection,
                colorId,
                thisDiaryTags,
                saveType
        );
        long result = dbHelper.addToDiary(diary);
        if (result != -1) {
            makeToast(EditDiaryActivity.this, "Saved Successfully");
            finish();
        } else {
            makeToast(EditDiaryActivity.this, "Something went wrong");
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setMoods() {
        List<Mood> moodArrayList = DataSource.getAllMoods();
        moodAdapter = new MoodAdapter(moodArrayList);
        binding.rvMoods.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvMoods.setAdapter(moodAdapter);
        moodAdapter.setSelectedPosition(selectedDiary.getMoodId());
        moodAdapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setCategories() {
        List<DiaryCategory> diaryCategories = DataSource.getAllCategories();
        diaryCategoryAdapter = new DiaryCategoryAdapter(diaryCategories);
        binding.rvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvCategory.setAdapter(diaryCategoryAdapter);
        diaryCategoryAdapter.setSelectedPosition(selectedDiary.getCategoryId());
        diaryCategoryAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(EditDiaryActivity.this, ImagesDisplayActivity.class);
                intent.putExtra(Constants.INTENT_BITMAP_KEY, new Gson().toJson(bitmaps));
                intent.putExtra(Constants.INTENT_BITMAP_POSITION_KEY, i);
                startActivity(intent);
            }, Constants.IMAGES_ADAPTER_TYPE);
            binding.rvImages.setAdapter(imagesAdapter);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
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
}