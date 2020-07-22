package com.example.locationsave;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class hep_HashTag extends RelativeLayout {
    LayoutInflater inflater = null;
    TextView hashText;
    ImageButton btnDelete;
    hep_HashTag me;

    public static Context mContext;

    public hep_HashTag(Context context){
        super(context);
        mContext = context;
        me = this;
        setLayout();

        hashText = findViewById(R.id.hashTagText);
        btnDelete = findViewById(R.id.btnDeleteHashTag);

        btnDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((hep_FlowLayout) ((hep_LocationSave) mContext).findViewById(R.id.hashtagFlowLayout)).removeView(me);
                new hep_HashTagArr().getHashTagArr().remove(hashText.getText().toString());
            }
        });
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
    }
}
