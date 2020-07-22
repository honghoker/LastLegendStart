package com.example.locationsave.HEP.Hep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.viewpager.widget.PagerAdapter;

import com.example.locationsave.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class hep_ViewPagerAdapter extends PagerAdapter {
    private Context mContext;

    public hep_ViewPagerAdapter(Context context) {
        this.mContext = context;
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
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.hep_viewpageritem, container, false);

        PhotoView photoView = layout.findViewById(R.id.locationImage);

        Picasso.get()
                .load(new hep_locationImageDataArr().getImageDataArrayInstance().get(position).path)
                .fit()
                .centerCrop()
                .into(photoView);

        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, hep_FullImage.class);
                intent.putExtra ("CurrentItem", ((hep_LocationSave)mContext).viewPager.getCurrentItem());
                mContext.startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        ImageButton btnCloseLocationImage = layout.findViewById(R.id.btnCloseLocationImage);
        btnCloseLocationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((hep_LocationSave)mContext).removeCurrentItem();
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