package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

public class hep_FullImageViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    hep_FullImageViewPagerAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return new hep_locationImageDataArr().getImageDataArrayInstance().size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);

        Picasso.get()
                .load(new hep_locationImageDataArr().getImageDataArrayInstance().get(position).path)
                .fit().centerInside()
                .into(photoView);

        container.addView(photoView);

        return photoView;
    }
}
