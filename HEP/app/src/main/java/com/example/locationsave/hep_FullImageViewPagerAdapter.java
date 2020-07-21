package com.example.locationsave;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class hep_FullImageViewPagerAdapter extends PagerAdapter {

    private Context mContext;

    hep_FullImageViewPagerAdapter(Context context){
        this.mContext = context;
    }

    private Uri getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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
        ImageView imageView = new ImageView(mContext);
        Picasso.get()
                .load(new hep_locationImageDataArr().getImageDataArrayInstance().get(position).path)
                .fit()
                .centerCrop()
                .into(imageView);

        container.addView(imageView);

        return imageView;
    }
}
