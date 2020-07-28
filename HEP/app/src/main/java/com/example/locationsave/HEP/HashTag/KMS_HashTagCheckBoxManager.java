package com.example.locationsave.HEP.HashTag;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.locationsave.HEP.KMS_MainActivity;
import com.example.locationsave.R;
import static com.example.locationsave.HEP.KMS_MainActivity.params;

import java.util.ArrayList;
import java.util.List;

public class KMS_HashTagCheckBoxManager extends AppCompatActivity {
    private static final KMS_HashTagCheckBoxManager hashTagCheckBoxInstance = new KMS_HashTagCheckBoxManager();

    private KMS_HashTagCheckBoxManager() {}

    public static KMS_HashTagCheckBoxManager getInstanceHashTagCheckBox(){
        return hashTagCheckBoxInstance;
    }

    //hashtag checkbox flag 설정
    boolean hashTagCheckBoxFlag = false;

    public void flagChangehashTagCheckBox(){
        if(hashTagCheckBoxFlag == true) {
            flagSetFalsehashTagCheckBoxFlag();
            Log.d("####hashTagCheckBoxFlag","flagChange hashTagCheckBoxFlag sington change flag = " + hashTagCheckBoxFlag);
        }

        else if(hashTagCheckBoxFlag == false){
            flagSetTruehashTagCheckBoxFlag();
            Log.d("####hashTagCheckBoxFlag","flagChange hashTagCheckBoxFlag sington change flag = " + hashTagCheckBoxFlag);
        }
    }

    public boolean flagGethashTagCheckBoxFlag(){
        Log.d("####hashTagCheckBoxFlag","flagChange hashTagCheckBoxFlag sington check flag = " + hashTagCheckBoxFlag);
        return hashTagCheckBoxFlag;

    }

    public void flagSetTruehashTagCheckBoxFlag(){
        hashTagCheckBoxFlag = true;
        Log.d("####hashTagCheckBoxFlag","hashTagCheckBox sington " + hashTagCheckBoxFlag);
    }

    public void flagSetFalsehashTagCheckBoxFlag(){
        hashTagCheckBoxFlag = false;
        Log.d("####hashTagCheckBoxFlag","hashTagCheckBox sington " + hashTagCheckBoxFlag);
    }



    KMS_HashTag[] hs = KMS_MainActivity.msHashTag; //메인액티비티 해시태그 배열
    public static List<String> hashTagText = new ArrayList(); //확인누르면 추가된다.

    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void HashTagClickEvent(Context context, View v){
        for (int j = 1; j < hs.length; j++) {
            if (v.getId() == j) {
                hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagclick, params);
                hs[j].setId(-j);
                Toast.makeText(context,"id : " + hs[j].getId() + "/ text : " + hs[j].getHashText(),Toast.LENGTH_SHORT).show();
                hashTagText.add(hs[j].getHashText());
                break;
            } else if (v.getId() == -j) {
                hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagunclick, params);
                hs[j].setId(j);
                hashTagText.remove(hs[j].getHashText());
                break;
            }
        }//for 문 종료
    }

    public void CheckBoxAllClick(Context context) {
        Toast.makeText(context,"3gfgff",Toast.LENGTH_SHORT).show();
        hashTagText.clear();
        for (int j = 1; j < hs.length; j++) {
            hs[j].init(hs[j].getHashText(), "#3F729B", R.drawable.hashtagclick, params);
            hs[j].setId(-j);

            hashTagText.add(hs[j].getHashText());
        } //for 종료
    }

    String imsi = "";

    public void AddClickHashTag(Context context) {
        //어레이 리스트를 스트링으로 변경 후 쿼리문 날리기
        String[] stockArr = new String[hashTagText.size()]; //어레이 리스트 스트링으로 변환
        stockArr = hashTagText.toArray(stockArr);

        for(String s : stockArr)
            imsi += s + " ";
        Toast.makeText(context,"Hashtag : " + imsi ,Toast.LENGTH_SHORT).show();
        imsi = "";
    }

    public void CheckBoxAllClick2(Context context) {
        //int a = MainActivity.hashTag.length;
        Toast.makeText(context,"3gfgff",Toast.LENGTH_SHORT).show();
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
