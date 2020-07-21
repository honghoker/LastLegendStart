package com.example.locationsave;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;


public class hep_ViewPagerAdapter extends PagerAdapter {
    private Context mcontext;

    public hep_ViewPagerAdapter(Context context) {
        this.mcontext = context;
    }

    @Override
    public int getCount() {
        return new hep_locationImageDataArr().getImageDataArrayInstance().size();
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
        imageView.setImageBitmap(new hep_locationImageDataArr().getImageDataArrayInstance().get(position).bitmap);

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
                Intent intent = new Intent(mcontext, hep_FullImage.class);
                intent.putExtra ("CurrentItem", ((hep_LocationSave)mcontext).viewPager.getCurrentItem());
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