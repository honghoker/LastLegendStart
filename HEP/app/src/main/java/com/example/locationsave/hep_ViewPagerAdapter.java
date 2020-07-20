package com.example.locationsave;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.viewpager.widget.PagerAdapter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class hep_ViewPagerAdapter extends PagerAdapter {
    private List<hep_ImageData> imageDataList;
    private Context mcontext;

    hep_ViewPagerAdapter(Context context, List<hep_ImageData> imageDataList) {
        this.mcontext = context;
        this.imageDataList = imageDataList;
    }

    @Override
    public int getCount() {
        return imageDataList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.hep_viewpageritem, container, false);

        ImageView imageView = layout.findViewById(R.id.locationImage);
        imageView.setImageBitmap(imageDataList.get(position).bitmap);

        ImageButton btnCloseLocationImage = layout.findViewById(R.id.btnCloseLocationImage);
        btnCloseLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((hep_LocationSave)mcontext).removeCurrentItem();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                Bitmap bitmap = imageDataList.get(position).bitmap;
                float scale = (float) (1024/(float)bitmap.getWidth());
                int image_w = (int) (bitmap.getWidth() * scale);
                int image_h = (int) (bitmap.getHeight() * scale);
                Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
                resize.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byteArray = stream.toByteArray();
                Intent intent = new Intent(mcontext, hep_FullImage.class);
                intent.putExtra("image", byteArray);
                mcontext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        container.addView(layout);
        return layout;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
            return POSITION_NONE;
    }

}