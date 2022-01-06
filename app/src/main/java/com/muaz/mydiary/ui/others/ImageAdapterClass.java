package com.muaz.mydiary.ui.others;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.muaz.mydiary.R;

public class ImageAdapterClass extends PagerAdapter {
    Context mContext;

    ImageAdapterClass(Context context) {
        this.mContext = context;
    }
    private int[] sliderImageId = new int[]{
            R.drawable.beach, R.drawable.couple, R.drawable.flower_bunny,R.drawable.flowers, R.drawable.girls,R.drawable.lovely_bear,R.drawable.loves,R.drawable.night,R.drawable.sunset,R.drawable.unicorn
    };

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(sliderImageId[position]);
        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
    @Override
    public int getCount() {
        return sliderImageId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((ImageView) object);
    }

}
