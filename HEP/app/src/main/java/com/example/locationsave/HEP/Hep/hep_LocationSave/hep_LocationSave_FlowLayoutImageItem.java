package com.example.locationsave.HEP.Hep.hep_LocationSave;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.locationsave.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class hep_LocationSave_FlowLayoutImageItem extends RelativeLayout {
    LayoutInflater inflater = null;
    Context mContext;
    ImageButton imageButton;

    static int idnum = 0;
    public hep_LocationSave_FlowLayoutImageItem(Context context) {
        super(context);
        mContext = context;
        setLayout();
        setInit();
    }

    public hep_LocationSave_FlowLayoutImageItem(Context context, AttributeSet attrs) {
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
                hep_FlowLayout hep_FlowLayout = ((hep_LocationSaveActivity)mContext).findViewById(R.id.imageFlowLayout);

                for(int i = 0; i < hep_FlowLayout.getChildCount(); i++){
                    hep_LocationSave_FlowLayoutImageItem hep_FlowLayoutImageItem = (com.example.locationsave.HEP.Hep.hep_LocationSave.hep_LocationSave_FlowLayoutImageItem) hep_FlowLayout.getChildAt(i);

                    if(hep_FlowLayoutImageItem.imageButton.getId() == v.getId()){
                        ((hep_LocationSaveActivity)mContext).viewPager.setCurrentItem(i);
                        break;
                    }
                }
            }
        });
    }

    public void setBackgroundBitmap(Bitmap b){
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), b);
        imageButton.setBackgroundDrawable(bitmapDrawable);
    }

    public void setBackgroundUri(final Uri uri) throws IOException {
        final Handler handler = new Handler()
        {
            public void handleMessage(Message msg)
            {
                Bitmap bitmap = (Bitmap) msg.obj;
                imageButton.setBackgroundDrawable(new BitmapDrawable(Resources.getSystem(), bitmap));
            }
        };

        new Thread() {
            public void run() {
                Bitmap x;
                HttpURLConnection connection = null;
                try {
                    connection = (HttpURLConnection) new URL(uri.toString()).openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    connection.connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                InputStream input = null;
                try {
                    input = connection.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x = BitmapFactory.decodeStream(input);
                Message msg = handler.obtainMessage();
                msg.obj = x;
                handler.sendMessage(msg);
            }
        }.start();
    }

}
