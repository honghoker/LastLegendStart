package com.example.locationsave.HEP.KMS.HashTag;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;

import static com.example.locationsave.HEP.KMS.HashTag.KMS_HashTagCheckBoxFlagManager.hashTagText;
import static com.example.locationsave.HEP.KMS_MainActivity.msHashTag;
import static com.example.locationsave.HEP.KMS_MainActivity.params;

public class KMS_HashTagCheckBoxManager {
    private static final KMS_HashTagCheckBoxManager hashTagCheckBoxInstance = new KMS_HashTagCheckBoxManager();

    public static KMS_HashTagCheckBoxManager getInstanceHashTagCheckBox(){
        return hashTagCheckBoxInstance;
    }

    CheckBox checkBoxAllHashTag; //체크박스 명 선언
    Context context;
    KMS_HashTagCheckBoxFlagManager kms_hashTagCheckBoxFlagManager = KMS_HashTagCheckBoxFlagManager.getInstanceHashTagCheckBox();
    View view;
    String imsi = "";

    public KMS_HashTagCheckBoxManager(){ }
    public KMS_HashTagCheckBoxManager(Context context, View view){
        this.context = context;
        this.view = view;
    }

    //해시태그 선택
    public class HasTagOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
//            Log.d("6","asdasdasd");
            kms_hashTagCheckBoxFlagManager.HashTagClickEvent(context ,v);
        }
    }
    HasTagOnClickListener hasTagOnClickListener = new HasTagOnClickListener();

    public void addHashTag() { //초기 해시태그 세팅
        Log.d("6","asdasdasd");
        for (int i = 1; i < msHashTag.length; i++) {
            msHashTag[i] = new KMS_HashTag(context);
            msHashTag[i].setOnClickListener(hasTagOnClickListener);
            msHashTag[i].setId(i);
            if (i % 3 == 0)
                msHashTag[i].init("1", "#22FFFF", R.drawable.hashtagborder, params);
            else if (i % 2 == 0)
                msHashTag[i].init("초기값", "#22FFFF", R.drawable.hashtagborder, params);
            else
                msHashTag[i].init("asdfan32of2ofndladf", "#3F729B", R.drawable.hashtagborder, params);

            ((KMS_FlowLayout) view.findViewById(R.id.HashTagflowlayout)).addView(msHashTag[i]);
        }
    }//addHashTag 종료

    public void checkAllHashTag() { //해시태그 모두 선택
        checkBoxAllHashTag = view.findViewById(R.id.checkBox);
        checkBoxAllHashTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxAllHashTag.isChecked()) {
                    kms_hashTagCheckBoxFlagManager.CheckBoxAllClick(context);
                } else
                    CheckBoxAllUnClick(context);
            }
        });
    } //checkAllHashTag 종료

    public void AddClickHashTag(Context context) {
        //어레이 리스트를 스트링으로 변경 후 쿼리문 날리기
        String[] stockArr = new String[hashTagText.size()]; //어레이 리스트 스트링으로 변환
        stockArr = hashTagText.toArray(stockArr);

        for(String s : stockArr)
            imsi += s + " ";
        Toast.makeText(context,"Hashtag : " + imsi ,Toast.LENGTH_SHORT).show();
        imsi = "";
    }
    public void CheckBoxAllUnClick(Context context) {
        KMS_HashTag[] hs = KMS_MainActivity.msHashTag;
        for (int j = 1; j < hs.length; j++) {
            hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagunclick, params);
            hs[j].setId(j);
            hashTagText.clear();
        } //for 종료
    }

}
