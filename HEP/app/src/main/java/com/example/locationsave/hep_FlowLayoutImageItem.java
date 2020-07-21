package com.example.locationsave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class hep_FlowLayoutImageItem extends RelativeLayout {
    LayoutInflater inflater = null;
    Context mContext;
    ImageButton imageButton;

    static int idnum = 0;
    public hep_FlowLayoutImageItem(Context context) {
        super(context);
        mContext = context;
        setLayout();
        setInit();
    }

    public hep_FlowLayoutImageItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setLayout(){
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hep_flowlayoutimageitem, this, true);
    }

    public void setInit(){
        imageButton = findViewById(R.id.flowLayoutImage);
        imageButton.setId(idnum++);

        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hep_FlowLayout hep_FlowLayout = ((hep_LocationSave)mContext).findViewById(R.id.imageFlowLayout);

                for(int i = 0; i < hep_FlowLayout.getChildCount(); i++){
                    hep_FlowLayoutImageItem hep_FlowLayoutImageItem = (com.example.locationsave.hep_FlowLayoutImageItem) hep_FlowLayout.getChildAt(i);

                    if(hep_FlowLayoutImageItem.imageButton.getId() == v.getId()){
                        ((hep_LocationSave)mContext).viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    public void setBackground(Bitmap b){
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), b);
        imageButton.setBackgroundDrawable(bitmapDrawable);
    }
}
