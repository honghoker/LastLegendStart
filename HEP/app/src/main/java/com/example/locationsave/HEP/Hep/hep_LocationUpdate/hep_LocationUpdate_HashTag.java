package com.example.locationsave.HEP.Hep.hep_LocationUpdate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_FlowLayout;
import com.example.locationsave.HEP.Hep.hep_LocationSave.hep_HashTagArr;
import com.example.locationsave.R;

public class hep_LocationUpdate_HashTag extends RelativeLayout {
    LayoutInflater inflater = null;
    TextView hashText;
    ImageButton btnDelete;
    hep_LocationUpdate_HashTag me;

    public static Context mContext;

    public hep_LocationUpdate_HashTag(Context context){
        super(context);
        mContext = context;
        me = this;
        setLayout();

        hashText = findViewById(R.id.hashTagText);
        btnDelete = findViewById(R.id.btnDeleteHashTag);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((hep_FlowLayout) ((hep_LocationUpdateActivity) mContext).findViewById(R.id.locationupdate_hashtagFlowLayout)).removeView(me);
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
