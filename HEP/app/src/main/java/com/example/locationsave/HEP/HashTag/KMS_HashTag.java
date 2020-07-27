package com.example.locationsave.HEP.HashTag;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.locationsave.R;
import com.google.android.material.internal.FlowLayout;

import java.util.ArrayList;

public class KMS_HashTag extends RelativeLayout {
    LayoutInflater inflater = null;
    TextView HashText;
    KMS_HashTag me; // removeView에서 사용할 객체

    static ArrayList<String> HashTagar = new ArrayList<String>();

    public static Context mContext;

    public KMS_HashTag(Context context){
        super(context);
        mContext = context;
        me = this;
        setLayout();
    }

    public static ArrayList<String> getHashTagar(){
        return HashTagar;
    }

    public void init(String Text, String Color, int border, FlowLayout.LayoutParams params){
        HashText.setText(Text);
        HashText.setTextColor(android.graphics.Color.parseColor(Color));
        HashText.setBackgroundResource(border);
        me.setLayoutParams(params);
    }
    public void setLayout(){
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.kms_hashtag, this, true);

        HashText = findViewById(R.id.HashTag_Text);
    }
    public String getHashText(){
        return HashText.getText().toString();
    }


}
