package com.example.locationsave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.sql.Array;
import java.util.ArrayList;

public class hep_HashTag extends RelativeLayout {
    private static ArrayList<String> hashTagArr = null;
    LayoutInflater inflater = null;
    TextView hashText;
    ImageButton btnDelete;
    hep_HashTag me;

    public static Context mContext;


    public ArrayList<String> getHashTagArr(){
        if(hashTagArr == null)
            hashTagArr = new ArrayList<>();
        return hashTagArr;
    }

    public hep_HashTag(Context context){
        super(context);
        mContext = context;
        me = this;
        setLayout();
        deleteHashTag();
    }

    public void init(String Text, String Color, int border, hep_FlowLayout.LayoutParams params){
        hashText.setText(Text);
        hashText.setTextColor(android.graphics.Color.parseColor(Color));
        hashText.setBackgroundResource(border);
        me.setLayoutParams(params);
    }

    public void setLayout(){
        inflater = (LayoutInflater) getContext().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.hep_hashtag, this, true);

        hashText = findViewById(R.id.hashTagText);
        btnDelete = findViewById(R.id.btnDeleteHashTag);
    }

    public void deleteHashTag(){
        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((hep_FlowLayout) ((hep_LocationSave) mContext).findViewById(R.id.flowLayout)).removeView(me);
                hashTagArr.remove(hashText.getText().toString());
            }
        });
    }
}
